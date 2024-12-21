package com.ikhdaamel.ucp2.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ikhdaamel.ucp2.ui.customwidget.TopAppBar
import com.ikhdaamel.ucp2.ui.viewmodel.PenyediaViewModel
import com.ikhdaamel.ucp2.ui.viewmodel.DosenEvent
import com.ikhdaamel.ucp2.ui.viewmodel.DosenViewModel
import com.ikhdaamel.ucp2.ui.viewmodel.DosenFormErrorState
import kotlinx.coroutines.launch

@Composable
fun InsertDosenView(
    onBack: () -> Unit,
    onNavigateToDosen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DosenViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val homeUiState = viewModel.homeUiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(homeUiState.SnackBarMessage) {
        homeUiState.SnackBarMessage?.let { message: String ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }
    Scaffold (
        modifier = modifier,
        snackbarHost  = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ){
            TopAppBar(
              onBack = onBack,
              showBackButton = true,
              judul = "Tambah Dosen"
            )
            FormDosen(
                dosenEvent = homeUiState.dosenEvent,
                onValueChange = { viewModel.updateState(it) },
                errorState = homeUiState.isEntryValid,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    viewModel.saveData()
                    onNavigateToDosen()
                },
                modifier = Modifier.fillMaxWidth(),
        ) {
                Text("Simpan")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun FormDosen(
    dosenEvent: DosenEvent = DosenEvent(),
    onValueChange: (DosenEvent) -> Unit = {},
    errorState: DosenFormErrorState = DosenFormErrorState(),
    modifier: Modifier = Modifier
) {
    val jenisKelamin = listOf("laki-laki", "Perempuan")

    Column (modifier = Modifier
        .fillMaxSize()
        .background(Color(0xE4931743))
        .padding(10.dp))
    {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dosenEvent.nidn,
            onValueChange = {
                onValueChange(dosenEvent.copy(nidn = it))
            },
            label = { Text("Masukkan Nomor NIDN") },
            isError = errorState.nidn != null,
            placeholder = { Text("Masukkan NIDN") },
        )
        Text(
            text = errorState.nidn ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dosenEvent.nama,
            onValueChange = {
                onValueChange(dosenEvent.copy(nama = it))
            },
            label = { Text("Masukkan Nama lengkap") },
            isError = errorState.nama != null,
            placeholder = { Text("Nama") },
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis Kelamin")
        Row (modifier = Modifier.fillMaxWidth())
        {
            jenisKelamin.forEach{ jk ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    RadioButton(
                        selected = dosenEvent.jeniKelamin ==jk,
                        onClick = {
                            onValueChange(dosenEvent.copy(jeniKelamin = jk))
                        },
                    )
                    Text(text = jk)
                }
            }
        }
    }
}