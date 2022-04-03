package com.martyuk.utils.extensions

import android.content.res.AssetManager
import android.graphics.drawable.Icon
import androidx.annotation.ArrayRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.PluralsRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import java.io.InputStream

public interface ResourcesManager {

    fun getString(@StringRes stringResourceId: Int): String

    fun getFormattedString(
        @StringRes stringResourceId: Int,
        vararg formatArguments: Any?
    ): String

    fun getQuantityString(@PluralsRes quantityResourceId: Int, quantity: Int): String

    fun getFormattedQuantityString(
        @PluralsRes quantityResourceId: Int,
        quantity: Int,
        vararg formatArguments: Any
    ): String

    fun getStringArray(@ArrayRes stringArrayResourceId: Int): Array<String>

    @ColorInt
    fun getColor(@ColorRes colorRes: Int): Int

    fun getIcon(@DrawableRes drawableResourceId: Int): Icon

    fun getInteger(id: Int): Int

    fun getDimensionPixelSize(id: Int): Int

    fun getRawResource(@RawRes id: Int): InputStream

    val assets: AssetManager
}