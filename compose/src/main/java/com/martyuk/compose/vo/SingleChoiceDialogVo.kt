package com.martyuk.compose.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SingleChoiceDialogVo(
  val title: String,
  val radioOptions: List<String>,
  val indexOfSelectedItem: Int
) : Parcelable
