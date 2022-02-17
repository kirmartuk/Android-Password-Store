package com.martyuk.compose

import com.martyuk.compose.utils.DataStoreManager
import com.martyuk.compose.utils.ResourcesManager
import com.martyuk.compose.utils.indexOfOrNull
import com.martyuk.compose.vo.SingleChoiceDialogVo
import com.martyuk.utils.extensions.PreferenceKeys
import javax.inject.Inject
import kotlin.Exception

class SingleChoiceDialogFormatter @Inject constructor(
  private val resourcesManager: ResourcesManager,
  private val dataStoreManager: DataStoreManager
) {

  fun format(key: String): SingleChoiceDialogVo {
    val selectedItem = dataStoreManager.getStringBlocking(key)
    return when (key) {
      PreferenceKeys.SORT_ORDER -> {
        val radioOptions = resourcesManager.getStringArray(R.array.sort_order_entries).asList()
        SingleChoiceDialogVo(
          title = resourcesManager.getString(R.string.pref_sort_order_title),
          radioOptions = radioOptions,
          indexOfSelectedItem = getIndexOfSelectedItemInRadioOptions(radioOptions, selectedItem)
        )
      }
      PreferenceKeys.APP_THEME -> {
        val radioOptions = resourcesManager.getStringArray(R.array.app_theme_options).asList()
        SingleChoiceDialogVo(
          title = resourcesManager.getString(R.string.pref_app_theme_title),
          radioOptions = radioOptions,
          indexOfSelectedItem = getIndexOfSelectedItemInRadioOptions(radioOptions, selectedItem))
      }
      PreferenceKeys.OREO_AUTOFILL_DIRECTORY_STRUCTURE -> {
        val radioOptions = resourcesManager.getStringArray(R.array.sort_order_entries).asList()
        SingleChoiceDialogVo(
          title = resourcesManager.getString(R.string.oreo_autofill_preference_directory_structure),
          radioOptions = radioOptions,
          indexOfSelectedItem = getIndexOfSelectedItemInRadioOptions(radioOptions, selectedItem))
      }
      else -> {
        throw Exception("")
      }
    }
  }

  private fun getIndexOfSelectedItemInRadioOptions(radioOptions: List<String>, selectedItem: String?): Int {
    return selectedItem?.let { radioOptions.indexOfOrNull(selectedItem) } ?: 0
  }
}
