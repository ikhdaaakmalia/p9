package com.ikhdaamel.ucp2.ui.view.matakuliah

import android.widget.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ikhdaamel.ucp2.ui.view.FormMataKuliah
import com.ikhdaamel.ucp2.ui.viewmodel.PenyediaViewModel
import com.ikhdaamel.ucp2.ui.viewmodel.DosenViewModel
import com.ikhdaamel.ucp2.ui.viewmodel.UpdateMatKulViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UpdateMatKulView(
    onBack: () -> Unit,
    onNavigateUpMatkul: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateMatKulViewModel = viewModel(factory = PenyediaViewModel.Factory),
    dosenViewModel: DosenViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.updateUiState
    val homeDosenUiState by dosenViewModel.homeDosenUiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var selectedDosen by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(homeDosenUiState.listDosen) {
        if (homeDosenUiState.listDosen.isNotEmpty()){
            selectedDosen = homeDosenUiState.listDosen.first().nama
        }
    }
    Scaffold (
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
//        topBar = {
//            TopAppBar(
//                title = "Edit Mata Kuliah",
//                showBackButton = true,
//                onBack = onBack
//            )
//        }
    ){ padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ){
            FormMataKuliah (
                mataKuliahEvent = uiState.mataKuliahEvent,
                onValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                //onNavigateUpMatkul = {},
                errorState = uiState.isEntryValid,
                modifier = Modifier.fillMaxWidth()
            )
//            Button(
//                onClick = {
//                    coroutineScope.launch {
//                        if (viewModel.validateFields()) {
//                            viewModel.updateData( selectedDosen)
//                            delay(600)
//                            withContext(Dispatchers.Main){
//                                onNavigateUpMatkul()
//                            }
//                        }
//                    }
//                }
//            ){ Text("Edit") }
        }
    }
}