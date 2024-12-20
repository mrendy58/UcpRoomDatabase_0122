package com.example.ucp2.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucp2.data.entity.MataKuliah

@Composable
fun DetailMkView(){

}
@Composable
fun CardDetailMk(
    modifier: Modifier = Modifier,
    mataKuliah: MataKuliah
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            PartDetailMk(judul = "Kode MK", data = mataKuliah.kode)
            Spacer(modifier = Modifier.padding(4.dp))
            PartDetailMk(judul = "Nama MK", data = mataKuliah.namaMk)
            Spacer(modifier = Modifier.padding(4.dp))
            PartDetailMk(judul = "SKS", data = mataKuliah.sks)
            Spacer(modifier = Modifier.padding(4.dp))
            PartDetailMk(judul = "Semester", data = mataKuliah.semester)
            Spacer(modifier = Modifier.padding(4.dp))
            PartDetailMk(judul = "Jenis MK", data = mataKuliah.jenisMK)
            Spacer(modifier = Modifier.padding(4.dp))
            PartDetailMk(judul = "Dosen Pengampu", data = mataKuliah.DosenPengampu)
        }
    }
}
@Composable
fun PartDetailMk(
    modifier: Modifier = Modifier,
    judul: String,
    data: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = data,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
@Composable
private fun DeleteConfDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Hapus Data") },
        text = { Text("Apakah anda yakin ingin menghapus data ini?") },
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("Batal")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Ya")
            }
        }
    )
}