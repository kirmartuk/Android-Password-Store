package com.martyuk.compose.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.martyuk.compose.ui.theme.APSTheme
import com.martyuk.compose.utils.CheckboxWithTitle
import com.martyuk.compose.viewmodel.SshKeyGeneratorViewModel
import com.martyuk.resources.R
import com.martyuk.utils.ssh_keygen.Algorithm
import com.martyuk.views.MaterialButtonToggleGroup

@Composable
fun SshKeyGenScreen(
  navController: NavController,
  sshKeyGeneratorViewModel: SshKeyGeneratorViewModel = hiltViewModel()
) {
  APSTheme {
    Scaffold(
      topBar = {
        SmallTopAppBar(
          title = {
            Text(text = stringResource(id = R.string.pref_ssh_keygen_title))
          },
          navigationIcon = {
            Icon(imageVector = Icons.Filled.ArrowBack,
              contentDescription = "",
              modifier = Modifier.clickable { navController.popBackStack() })
          }
        )
      }
    ) {
      Surface {
        Column() {
          Row(modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)) {
            MaterialButtonToggleGroup(
              modifier = Modifier,
              items = listOf("asdasdssdasdadadadsa", "asdsa", "asd33"),
              selectedItemIndex = 0
            ) {
            }
          }
          Button(onClick = { sshKeyGeneratorViewModel.generateSsh(Algorithm.Ecdsa, true) }) {
          }
          CheckboxWithTitle(title = "asdasdasdasdas", isSelected = true) {
          }
        }
      }
    }
  }
}

@Composable
fun DrawSshKeyGenScreenWidget(){

}