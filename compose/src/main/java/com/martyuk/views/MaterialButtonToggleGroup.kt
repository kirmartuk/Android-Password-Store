package com.martyuk.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun MaterialButtonToggleGroup(
  modifier: Modifier,
  items: List<String>,
  selectedItemIndex: Int,
  cornerRadius: Dp = 8.dp,
  borderWidth: Dp = 1.dp,
  setSelectedIndex: (Int) -> Unit
) {
  val selectedIndex = remember { mutableStateOf(selectedItemIndex) }
  items.forEachIndexed { index, text ->
    OutlinedButton(
      modifier = when (index) {
        0 -> {
          if (selectedIndex.value == index) {
            modifier
              .offset(0.dp, 0.dp)
              .zIndex(1f)
          } else {
            modifier
              .offset(0.dp, 0.dp)
              .zIndex(0f)
          }
        }
        else -> {
          val offset = -1 * index
          if (selectedIndex.value == index) {
            modifier
              .offset(offset.dp, 0.dp)
              .zIndex(1f)
          } else {
            modifier
              .offset(offset.dp, 0.dp)
              .zIndex(0f)
          }
        }
      },
      onClick = {
        selectedIndex.value = index
        setSelectedIndex(index)
      },
      shape = when (index) {
        0 -> getShapeForFirstElement(cornerRadius)
        items.lastIndex -> getShapeForLastElement(cornerRadius)
        else -> getShapeForMiddleElement()
      },
      border = BorderStroke(borderWidth, if (selectedIndex.value == index) {
        MaterialTheme.colorScheme.primary
      } else {
        Color.DarkGray.copy(alpha = 0.75f)
      }),
      colors = if (selectedIndex.value == index) {
        ButtonDefaults.outlinedButtonColors(
          containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
          contentColor = MaterialTheme.colorScheme.primary
        )
      } else {
        ButtonDefaults.outlinedButtonColors(
          containerColor = MaterialTheme.colorScheme.surface,
          contentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
      },
    ) {
      Text(text = text, maxLines = 1)
    }
  }
}

private fun getShapeForFirstElement(cornerRadius: Dp): RoundedCornerShape {
  return RoundedCornerShape(topStart = cornerRadius, topEnd = 0.dp, bottomStart = cornerRadius, bottomEnd = 0.dp)
}

private fun getShapeForLastElement(cornerRadius: Dp): RoundedCornerShape {
  return RoundedCornerShape(topStart = 0.dp, topEnd = cornerRadius, bottomStart = 0.dp, bottomEnd = cornerRadius)
}

private fun getShapeForMiddleElement(): RoundedCornerShape {
  return RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp)
}