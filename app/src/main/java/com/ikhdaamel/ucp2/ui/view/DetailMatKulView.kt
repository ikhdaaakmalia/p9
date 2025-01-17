package com.ikhdaamel.ucp2.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ikhdaamel.ucp2.data.entity.MataKuliah
import com.ikhdaamel.ucp2.ui.viewmodel.DetailMatKulViewModel
import com.ikhdaamel.ucp2.ui.viewmodel.MatKulDetailUiState
import com.ikhdaamel.ucp2.ui.viewmodel.toMataKuliahEntity
import com.ikhdaamel.ucp2.ui.customwidget.TopAppBar
import com.ikhdaamel.ucp2.ui.viewmodel.PenyediaViewModel

@Composable
fun DetailMatKulView(
    modifier: Modifier = Modifier,
    viewModel: DetailMatKulViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = {},
    onEditClick: (String) ->Unit = {},
    onDeleteClick: () -> Unit = {}
){
    val detailUiState by viewModel.detailUiState.collectAsState()

    Scaffold (
        topBar = {
            TopAppBar(
                judul = "Daftar Dosen",
                showBackButton = false,
                onBack = {}
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEditClick(viewModel.detailUiState.value.detailUiEvent.kode) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Mata Kuliah")
            }
        }
    ) { innerPadding ->

        BodyDetailMatKul(
            modifier = Modifier.padding(innerPadding),
            matKulDetailUiState = detailUiState,
            onDeleteClick = {
                viewModel.deleteMatKul()
                onDeleteClick()
            }
        )
    }
}

@Composable
fun BodyDetailMatKul(
    modifier: Modifier = Modifier,
    matKulDetailUiState: MatKulDetailUiState = MatKulDetailUiState(),
    onDeleteClick: () -> Unit = {}
){
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    when{
        matKulDetailUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        matKulDetailUiState.isUiEventNotEmpty -> {
            Column (
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                ItemDetailMataKuliah(
                    mataKuliah = matKulDetailUiState.detailUiEvent.toMataKuliahEntity(),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button(onClick = {
                    deleteConfirmationRequired = true
                },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Delete")
                }
                if (deleteConfirmationRequired) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            onDeleteClick()
                        },
                        onDeleteCancel = {deleteConfirmationRequired = false },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
        matKulDetailUiState.isUiEventNotEmpty -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Data Tidak Ditemukan",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ItemDetailMataKuliah(
    modifier: Modifier = Modifier,
    mataKuliah: MataKuliah
){
    Card(
       modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.primaryContainer,
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ){
        Column (modifier = Modifier.padding(16.dp)){
            ComponentDetailMatKul(judul = "KODE", isinya = mataKuliah.kode)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMatKul(judul = "Nama Mata Kuliah", isinya = mataKuliah.nama_mk)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMatKul(judul = "Jumlah SKS", isinya = mataKuliah.sks)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMatKul(judul = "Semester", isinya = mataKuliah.semester)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMatKul(judul = "Jenis Mata Kuliah", isinya = mataKuliah.jenis)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMatKul(judul = "Dosen Pengampu", isinya = mataKuliah.dosenPengampu)
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun ComponentDetailMatKul(
    modifier: Modifier = Modifier,
    judul : String,
    isinya: String
) {
    Column (
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ){
        Text(
            text = "$judul :",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(onDismissRequest = {},
        title = { Text("DElete Data") },
        text = { Text("Apakah Anda Ingin Menghapus Data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "YES")
            }
        }
    )
}