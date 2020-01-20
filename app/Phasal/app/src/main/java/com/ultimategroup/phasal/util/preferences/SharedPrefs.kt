package com.ultimategroup.phasal.util.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.NonNull


/**
 * Android's SharedPreferences for storing key-value pairs.
 * DO NOT INSTANTIATE THIS CLASS - Use [Prefs] for your needs.
 */
/* package */
internal class SharedPrefs(@NonNull context: Context) {
    private val sharedPrefs: SharedPreferences
    @get:NonNull
    private val editor: SharedPreferences.Editor
        private get() = sharedPrefs.edit()

    /* package */
    operator fun get(@NonNull key: String?, defaultValue: Int): Int {
        return sharedPrefs.getInt(key, defaultValue)
    }

    /* package */
    fun put(@NonNull key: String?, value: Int) {
        editor.putInt(key, value).commit()
    }

    /* package */
    operator fun get(@NonNull key: String?, defaultValue: Boolean): Boolean {
        return sharedPrefs.getBoolean(key, defaultValue)
    }

    /* package */
    fun put(@NonNull key: String?, value: Boolean) {
        editor.putBoolean(key, value).commit()
    }

    companion object {
        private const val PREFERENCES_NAME = "org.horaapps.leafpic.SHARED_PREFS"
        private const val PREFERENCES_MODE = Context.MODE_PRIVATE
    }

    /* package */
    init {
        sharedPrefs = context.applicationContext
            .getSharedPreferences(
                PREFERENCES_NAME,
                PREFERENCES_MODE
            )
    }
}
