package com.example.dagger2mvvm.repository.common

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread

/**
 * @param ResultType The client facing result.
 * @param RequestType The result returned from the network.
 */
abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource(Status.LOADING)
        loadFromDb().let { dbSource ->
            result.addSource(dbSource) { data ->
                result.removeSource(dbSource)
                if (shouldFetch(data)) {
                    fetchFromNetwork(dbSource)
                } else {
                    result.addSource(dbSource) { newData ->
                        result.value = Resource(data = newData)
                    }
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        result.addSource(dbSource) { newData ->
            result.value = Resource(Status.LOADING, newData)
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            //noinspection ConstantCondition
            if (response?.isSuccessful() ?: false) {
                appExecutors.diskIO.execute {
                    saveCallResult(processResponse(response!!))
                    appExecutors.mainThread.execute {
                        // We specifically request a new live data,
                        // otherwise we will get immediately last cached value,
                        // which may not be updated with latest results received from network
                        result.addSource(loadFromDb()) { newData ->
                            result.value = Resource(data = newData)
                        }
                    }
                }
            } else {
                onFetchFailed()
                result.addSource(dbSource) { newData ->
                    result.value = Resource(Status.NETWORK_ERROR, newData)
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData(): LiveData<Resource<ResultType>> = result

    @WorkerThread
    protected fun processResponse(response: ApiResponse<RequestType>): RequestType =
            response.body ?: throw IllegalArgumentException("Invalid response: $response")

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @WorkerThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}