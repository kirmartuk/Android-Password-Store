package com.martyuk.compose.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Switch
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.martyuk.compose.R
import com.martyuk.compose.ui.theme.Material2AppTheme
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
  returnUserInput: (String) -> Unit) {
  val input = remember {
    mutableStateOf(userInputDialogVo.subtitle)
  }
  Material2AppTheme {
    Surface {
      AlertDialog(
        title = {
          Text(text = userInputDialogVo.title)
        },
        text = {
          OutlinedTextField(value = input.value, onValueChange = {
            input.value = it
          })
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
            returnUserInput(input.value)
          }) {
            Text(text = stringResource(id = R.string.dialog_ok))
          }
        })
    }
  }
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
      RadioItems(singleChoiceDialogVo.radioOptions, selectedItemIndex, setSelectedItemIndex)
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
        setSelectedItem(singleChoiceDialogVo.radioOptions[selectedItemIndex])
      }) {
        Text(text = stringResource(id = R.string.dialog_ok))
      }
    }
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadioItems(items: List<String>, selectedItemIndex: Int, setIndexOfSelected: (Int) -> Unit) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckboxWithTitleAndSubtitle(
  title: String,
  subtitle: String,
  isSelected: Boolean,
  modifier: Modifier,
  stateClickListener: (Boolean) -> Unit
) {
  val (isChecked, setCheckboxState) = remember { mutableStateOf(isSelected) }
  Column(
    Modifier
      .clickable {
        stateClickListener(!isChecked)
        setCheckboxState(!isChecked)
      }
      .fillMaxWidth()) {
    Box(
      modifier = modifier
        .fillMaxWidth()
    ) {
      Column(Modifier.align(Alignment.CenterStart)) {
        Text(text = title, style = Title)
        Text(modifier = Modifier.requiredWidth(280.dp), text = subtitle, style = SubTitle)
      }
      Checkbox(
        modifier = Modifier
          .align(Alignment.CenterEnd),
        checked = isChecked,
        onCheckedChange = {
          stateClickListener(it)
          setCheckboxState(it)
        }
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckboxWithTitle(
  title: String,
  isSelected: Boolean,
  stateClickListener: (Boolean) -> Unit
) {
  val (isChecked, setCheckboxState) = remember { mutableStateOf(isSelected) }
  Row(
    Modifier
      .clickable {
        stateClickListener(!isChecked)
        setCheckboxState(!isChecked)
      }
      .fillMaxWidth()) {
    Checkbox(
      checked = isChecked,
      onCheckedChange = {
        stateClickListener(it)
        setCheckboxState(it)
      }
    )
    Text(text = title)
  }
}

@Composable
fun TextWithSwitch(title: String, isChecked: Boolean, modifier: Modifier) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(start = 40.dp, top = 11.dp, bottom = 11.dp, end = 40.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(text = title, style = Title)
    Switch(checked = isChecked, onCheckedChange = {})
  }
}