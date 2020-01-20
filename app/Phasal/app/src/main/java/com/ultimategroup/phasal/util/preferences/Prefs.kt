package com.ultimategroup.phasal.util.preferences

import android.content.Context

/**
 * Class for storing & retrieving application data.
 *
 *
 * Abstracts clients from from DB / SharedPreferences / Hawk.
 */
object Prefs {
    private var sharedPrefs: SharedPrefs? = null
    /**
     * Initialise the Prefs object for future static usage.
     * Make sure to initialise this in Application class.
     *
     * @param context The context to initialise with.
     */
    fun init(context: Context) {
        if (sharedPrefs != null) {
            throw RuntimeException("Prefs has already been instantiated")
        }
        sharedPrefs = SharedPrefs(context)
    }
    /********** GETTERS  */
    /**
     * Get number of folder columns to display in Portrait orientation
     */
    /**
     * Set the number of folder columns in Portrait orientation.
     */
    var folderColumnsPortrait: Int
        get() = prefs.get(
            Keys.FOLDER_COLUMNS_PORTRAIT,
            Defaults.FOLDER_COLUMNS_PORTRAIT
        )
        set(value) {
            prefs
                .put(Keys.FOLDER_COLUMNS_PORTRAIT, value)
        }

    /**
     * Get number of folder columns to display in Landscape orientation
     */
    /**
     * Set the number of folder columns in Landscape orientation.
     */
    var folderColumnsLandscape: Int
        get() = prefs.get(
            Keys.FOLDER_COLUMNS_LANDSCAPE,
            Defaults.FOLDER_COLUMNS_LANDSCAPE
        )
        set(value) {
            prefs
                .put(Keys.FOLDER_COLUMNS_LANDSCAPE, value)
        }

    /**
     * Get number of media columns to display in Portrait orientation
     */
    /**
     * Set the number of media columns in Portrait orientation.
     */
    var mediaColumnsPortrait: Int
        get() {
            return prefs.get(
                Keys.MEDIA_COLUMNS_PORTRAIT,
                Defaults.MEDIA_COLUMNS_PORTRAIT
            )
        }
        set(value) {
            prefs
                .put(Keys.MEDIA_COLUMNS_PORTRAIT, value)
        }

    /**
     * Get number of media columns to display in Landscape orientation
     */
    /**
     * Set the number of media columns in Landscape orientation.
     */
    var mediaColumnsLandscape: Int
        get() {
            return prefs.get(
                Keys.MEDIA_COLUMNS_LANDSCAPE,
                Defaults.MEDIA_COLUMNS_LANDSCAPE
            )
        }
        set(value) {
            prefs
                .put(Keys.MEDIA_COLUMNS_LANDSCAPE, value)
        }

    /**
     * Should display video files in media.
     */
    fun showVideos(): Boolean {
        return prefs
            .get(Keys.SHOW_VIDEOS, Defaults.SHOW_VIDEOS)
    }

    /**
     * Should display count of media.
     */
    fun showMediaCount(): Boolean {
        return prefs.get(
            Keys.SHOW_MEDIA_COUNT,
            Defaults.SHOW_MEDIA_COUNT
        )
    }

    /**
     * Should display path of albums.
     */
    fun showAlbumPath(): Boolean {
        return prefs.get(
            Keys.SHOW_ALBUM_PATH,
            Defaults.SHOW_ALBUM_PATH
        )
    }

    /**
     * Should show the Emoji Easter Egg.
     */
    fun showEasterEgg(): Boolean {
        return prefs.get(
            Keys.SHOW_EASTER_EGG,
            Defaults.SHOW_EASTER_EGG
        )
    }

    /**
     * Should use animations
     */
    fun animationsEnabled(): Boolean {
        return !prefs.get(
            Keys.ANIMATIONS_DISABLED,
            Defaults.ANIMATIONS_DISABLED
        )
    }

    /**
     * Whether the Timeline view is enabled.
     */
    fun timelineEnabled(): Boolean {
        return prefs.get(
            Keys.TIMELINE_ENABLED,
            Defaults.TIMELINE_ENABLED
        )
    }


    /**
     * Set the last version code of Application.
     */
    var lastVersionCode: Int
        get() {
            return prefs.get(
                Keys.LAST_VERSION_CODE,
                Defaults.LAST_VERSION_CODE
            )
        }
        set(value) {
            prefs
                .put(Keys.LAST_VERSION_CODE, value)
        }
    /********** SETTERS  */

    /**
     * Set show video files in media collection.
     */
    fun setShowVideos(value: Boolean) {
        prefs.put(Keys.SHOW_VIDEOS, value)
    }

    /**
     * Set show the media count.
     */
    fun setShowMediaCount(value: Boolean) {
        prefs.put(Keys.SHOW_MEDIA_COUNT, value)
    }

    /**
     * Set show the full album path.
     */
    fun setShowAlbumPath(value: Boolean) {
        prefs.put(Keys.SHOW_ALBUM_PATH, value)
    }

    /**
     * Set show the Emoji Easter Egg.
     */
    fun setShowEasterEgg(value: Boolean) {
        prefs.put(Keys.SHOW_EASTER_EGG, value)
    }

    // ***** TODO Calvin: These methods does not belong here, DO NOT expose generic methods to clients
    @Deprecated("")
    fun setToggleValue(key: String, value: Boolean) {
        prefs.put(key, value)
    }

    @Deprecated("")
    fun getToggleValue(key: String, defaultValue: Boolean): Boolean {
        return prefs.get(key, defaultValue)
    }

    // ***** Remove these methods when SettingWithSwitchView is refactored.
    private val prefs: SharedPrefs
        private get() {
            if (sharedPrefs == null) {
                throw RuntimeException("Prefs has not been instantiated. Call init() with context")
            }
            return sharedPrefs as SharedPrefs
        }
}