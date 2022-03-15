package com.martyuk.compose.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import com.martyuk.compose.R
import com.martyuk.compose.ui.theme.APSTheme
import com.martyuk.compose.ui.theme.TitleLarge
import com.martyuk.compose.utils.asImageBitmap

@Composable
fun Welcome(navController: NavController) {
  APSTheme {
    Surface {
      Box(Modifier.fillMaxSize()) {
        TextButton(
          onClick = {
            navController.navigate(Screen.Settings.name)
          },
          modifier = Modifier.align(Alignment.TopEnd),
          shape = CircleShape
        ) {
          Text(text = stringResource(R.string.action_settings))
        }
        Column(modifier = Modifier.fillMaxSize(), Arrangement.Center) {
          val drawable =
            ResourcesCompat.getDrawable(
              LocalContext.current.resources,
              R.mipmap.ic_launcher, LocalContext.current.theme
            )!!
          Image(bitmap = drawable.asImageBitmap(), contentDescription = "", modifier = Modifier
            .size(100.dp)
            .align(alignment = Alignment.CenterHorizontally))

          Text(text = stringResource(R.string.app_name), modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 16.dp), style = TitleLarge)
        }
        Button(onClick = { /*TODO*/ }, shape = CircleShape, modifier = Modifier
          .widthIn(100.dp, 300.dp)
          .align(alignment = Alignment.BottomCenter)
          .padding(bottom = 50.dp)) {
          Text(text = stringResource(id = R.string.let_s_go))
        }
      }
    }
  }
}