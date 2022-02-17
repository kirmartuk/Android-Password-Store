package com.martyuk.compose.utils

import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.drawable.Icon
import androidx.annotation.ArrayRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.PluralsRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import java.io.InputStream

public class ResourcesManagerImpl(private val resources: Resources) : ResourcesManager {

    override fun getString(@StringRes stringResourceId: Int): String {
        return resources.getString(stringResourceId)
    }

    override fun getFormattedString(
        @StringRes stringResourceId: Int,
        vararg formatArguments: Any?
    ): String {
        return resources.getString(stringResourceId, *formatArguments)
    }

    override fun getQuantityString(@PluralsRes quantityResourceId: Int, quantity: Int): String {
        return resources.getQuantityString(quantityResourceId, quantity, quantity)
    }

    override fun getFormattedQuantityString(
        @PluralsRes quantityResourceId: Int,
        quantity: Int,
        vararg formatArguments: Any
    ): String {
        return resources.getQuantityString(quantityResourceId, quantity, *formatArguments)
    }

    override fun getStringArray(@ArrayRes stringArrayResourceId: Int): Array<String> {
        return resources.getStringArray(stringArrayResourceId)
    }

    @ColorInt
    override fun getColor(@ColorRes colorRes: Int): Int {
        return resources.getColor(colorRes)
    }

    override fun getIcon(@DrawableRes drawableResourceId: Int): Icon {
        return Icon.createWithResource(resources.getResourcePackageName(drawableResourceId), drawableResourceId)
    }

    override fun getInteger(id: Int): Int {
        return resources.getInteger(id)
    }

    override fun getDimensionPixelSize(id: Int): Int {
        return resources.getDimensionPixelSize(id)
    }

    override fun getRawResource(@RawRes id: Int): InputStream {
        return resources.openRawResource(id)
    }

    override val assets: AssetManager
        get() = resources.assets
}