package com.martyuk.compose.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.martyuk.compose.R
import com.martyuk.compose.ui.theme.SubTitle
import com.martyuk.compose.ui.theme.Title
import com.martyuk.compose.vo.SingleChoiceDialogVo
import com.martyuk.compose.vo.UserInputDialogVo

@Composable
fun TextWithSubtitle(title: String, subtitle: String, modifier: Modifier) {
  Column(modifier = modifier) {
    Text(text = title, style = Title)
    Text(text = subtitle, style = SubTitle)
  }
}

@Composable
fun UserInputDialog(
  navController: NavController,
  userInputDialogVo: UserInputDialogVo,
  setUserInput: (String) -> Unit) {
  AlertDialog(
    title = { Text(text = userInputDialogVo.title) },
    onDismissRequest = {
      navController.popBackStack()
    },
    dismissButton = {
      TextButton(onClick = {
        navController.popBackStack()
      }) {
        Text(text = stringResource(id = R.string.dialog_cancel))
      }
    },
    confirmButton = {
      TextButton(onClick = {
        navController.popBackStack()
        setUserInput("")
      }) {
        Text(text = stringResource(id = R.string.dialog_ok))
      }
    })
}

@Composable
fun SingleChoiceDialog(
  navController: NavController,
  singleChoiceDialogVo: SingleChoiceDialogVo,
  setSelectedItem: (String) -> Unit
) {
  val (selectedItemIndex, setSelectedItemIndex) = remember {
    mutableStateOf(singleChoiceDialogVo.indexOfSelectedItem)
  }
  AlertDialog(
    title = { Text(text = singleChoiceDialogVo.title) },
    text = {
      RadioItem(singleChoiceDialogVo.radioOptions, selectedItemIndex, setSelectedItemIndex)
    },
    onDismissRequest = {
      navController.popBackStack()
    },
    dismissButton = {
      TextButton(onClick = {
        navController.popBackStack()
      }) {
        Text(text = stringResource(id = R.string.dialog_cancel))
      }
    },
    confirmButton = {
      TextButton(onClick = {
        navController.popBackStack()
        setSelectedItem(singleChoiceDialogVo.radioOptions[selectedItemIndex])
      }) {
        Text(text = stringResource(id = R.string.dialog_ok))
      }
    }
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadioItem(items: List<String>, selectedItemIndex: Int, setIndexOfSelected: (Int) -> Unit) {
  Column(modifier = Modifier.fillMaxWidth()) {
    items.forEachIndexed { index, text ->
      Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .fillMaxWidth()
        .clickable {
          setIndexOfSelected(index)
        }) {
        RadioButton(
          selected = (index == selectedItemIndex),
          onClick = {
            setIndexOfSelected(index)
          }
        )
        Text(
          text = text
        )
      }
    }
  }
}