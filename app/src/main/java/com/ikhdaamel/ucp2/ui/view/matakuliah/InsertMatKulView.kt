package com.ikhdaamel.ucp2.ui.view.matakuliah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ikhdaamel.ucp2.ui.viewmodel.FormErrorState
import com.ikhdaamel.ucp2.ui.viewmodel.MatKulUiState
import com.ikhdaamel.ucp2.ui.viewmodel.MataKuliahEvent
import com.ikhdaamel.ucp2.ui.viewmodel.formErrorState

@Composable
fun InsertBodyMatKul(
    modifier: Modifier = Modifier,
    onValueChange: (MataKuliahEvent) -> Unit,
    uiState: MatKulUiState,
    onClick: () -> Unit
){
    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment =  Alignment.CenterHorizontally
    ){
        FormMataKuliah(
            mataKuliahEvent = uiState.mataKuliahEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun FormMataKuliah(
    mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    onValueChange: (MataKuliahEvent) -> Unit = {},
    errorState: formErrorState = formErrorState(),
    modifier: Modifier = Modifier
){
    val sks = listOf("1", "2", "3")
    val semester = listOf("1", "3", "5", "7")
    val jenis = listOf("wajib", "peminatan")
    val dosenPengampu = listOf("Arif, S.Kom.,M.Kom", "Junaedi S.Kom.,M.Kom., Ph.d", "Wawan S.Kom., M.Kom,")

    var expanded by remember { mutableStateOf(false) }
    var selectedDosen by remember { mutableStateOf("") }

    Column (modifier = modifier.fillMaxWidth())
    {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.kode,
            onValueChange = {
                onValueChange(mataKuliahEvent.copy(kode = it))
            },
            label = { Text("Kode") },
            isError = errorState.kode != null,
            placeholder = { Text("Masukkan Kode") }
        )
        Text(
            text = errorState.kode ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.nama_mk,
            onValueChange = {
                onValueChange(mataKuliahEvent.copy(nama_mk = it))
            },
            label = { Text("Nama Mata Kuliah") },
            isError = errorState.kode != null,
            placeholder = { Text("Masukkan Mata Kuliah") }
        )
        Text(
            text = errorState.nama_mk ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jumlah SKS")
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            sks.forEach{ sks ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    RadioButton(
                        selected = mataKuliahEvent.sks == sks,
                        onClick = {
                            onValueChange(mataKuliahEvent.copy(sks = sks))
                        },
                    )
                    Text (text = sks)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Semester")
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            semester.forEach{ sm ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    RadioButton(
                        selected = mataKuliahEvent.semester == sm,
                        onClick = {
                            onValueChange(mataKuliahEvent.copy(semester = sm))
                        },
                    )
                    Text (text = sm)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis Mata Kuliah")
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            jenis.forEach{ jn ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    RadioButton(
                        selected = mataKuliahEvent.jenis == jn,
                        onClick = {
                            onValueChange(mataKuliahEvent.copy(jenis = jn))
                        },
                    )
                    Text (text = jn)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Pilih Dosen Pengampu")
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedDosen,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                label = { Text("Dosen Pengampu") },
                placeholder = { Text("Pilih Dosen") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                isError = errorState.kode != null
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                dosenPengampu.forEach { dosen ->
                    DropdownMenuItem(
                        text = { Text(dosen) },
                        onClick = {
                            selectedDosen = dosen
                            onValueChange(mataKuliahEvent.copy(kode = dosen)) // Update state
                            expanded = false
                        }
                    )
                }
            }
            Text(
                text = errorState.kode ?: "",
                color = Color.Red,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}