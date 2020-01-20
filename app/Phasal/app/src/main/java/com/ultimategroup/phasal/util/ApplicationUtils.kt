package com.ultimategroup.phasal.util

import android.content.Context
import androidx.annotation.NonNull
import com.ultimategroup.phasal.BuildConfig

object ApplicationUtils {
    /**
     * Get the Application's package name specified in Manifest
     */

    var PACKAGE_NAME: String? = null

    fun init(@NonNull context: Context) {
        PACKAGE_NAME = context.packageName
    }

    fun getPackageName(): String? {
        return PACKAGE_NAME
    }

    fun getAppVersion(): String? {
        return BuildConfig.VERSION_NAME
    }

    fun isDebug(): Boolean? {
        return BuildConfig.DEBUG
    }
}
