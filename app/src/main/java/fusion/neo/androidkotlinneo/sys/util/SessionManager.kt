/*
 * SessionManager Created with ♥ by PT. Deggan Technowave on 1/8/18 11:36 AM
 * PT. Deggan Technowave Copyright (c) 2018. All Rights Reserved.
 * Last Modified 1/8/18 10:59 AM
 */

package com.beehapps.e_dokterkudokter.system.util

import android.app.Activity
import android.content.SharedPreferences
import fusion.neo.androidkotlinneo.R

/**
 * Created by farhan on 1/8/18.
 */

class SessionManager
(private val context: Activity) {

    private val pref: SharedPreferences
    private val editor: SharedPreferences.Editor
    private val PRIVATE_MODE = 0

    init {
        pref = context.getSharedPreferences(context.getString(R.string.sharedAccount),
                PRIVATE_MODE)
        editor = pref.edit()
    }

    val isSession: Boolean
        get() = pref.getBoolean(context.getString(R.string.accountSession), false)

    val accessToken: String
        get() = pref.getString(context.getString(R.string.accountToken), "")

    fun startSession(token: String) {
        editor.putBoolean(context.getString(R.string.accountSession), true)
        editor.putString(context.getString(R.string.accountToken), token)
        editor.commit()
    }

    fun endSession() {
        editor.clear()
        editor.commit()
    }
}