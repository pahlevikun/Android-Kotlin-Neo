package com.beehapps.e_dokterkudokter.presenter.interfaces

/**
 * Created by farhan on 1/9/18.
 */
interface ServerCallback {
    fun onSuccess(response: String)

    fun onFailed(isFailed: String)

    fun onFailure(throwable: Throwable)
}