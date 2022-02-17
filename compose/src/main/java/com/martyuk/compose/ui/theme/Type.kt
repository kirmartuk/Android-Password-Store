package com.martyuk.compose.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
  titleMedium = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    color = Color.White
  )
  /* button = TextStyle(
     fontFamily = FontFamily.Default,
     fontWeight = FontWeight.W500,
     fontSize = 14.sp
   ),
   caption = TextStyle(
     fontFamily = FontFamily.Default,
     fontWeight = FontWeight.Normal,
     fontSize = 12.sp
   )*/
)