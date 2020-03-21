package com.ultimategroup.phasal.util

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.ultimategroup.phasal.util.preferences.Prefs

object AnimationUtils {
    fun getItemAnimator(itemAnimator: RecyclerView.ItemAnimator?): RecyclerView.ItemAnimator? {
        return if (Prefs.animationsEnabled()) {
            itemAnimator
        } else null
    }

    fun getPageTransformer(pageTransformer: ViewPager.PageTransformer?): ViewPager.PageTransformer? {
        return if (Prefs.animationsEnabled()) {
            pageTransformer
        } else null
    }
}