package com.beehapps.e_dokterkudokter.presenter.interfaces

import android.app.Activity

/**
 * Created by farhan on 12/21/17.
 */
interface LoginInterface {

    fun checkPassword(email: String, password: String): Boolean
    fun performLogin(context: Activity, email: String, password: String,
                     callback: ServerCallback)

    fun saveSession(activity: Activity, response: String) : Boolean
    fun isSuccess(response: String): Boolean
}