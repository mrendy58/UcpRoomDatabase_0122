package com.example.ucp2.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucp2.data.entity.MataKuliah

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardMataKuliah(
    mataKuliah: MataKuliah,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = mataKuliah.namaMk,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "Kode: ${mataKuliah.kode}",
                fontSize = 16.sp
            )
            Text(
                text = "SKS: ${mataKuliah.sks}",
                fontSize = 16.sp
            )
            Text(
                text = "Semester: ${mataKuliah.semester}",
                fontSize = 16.sp
            )
            Text(
                text = "Jenis MK: ${mataKuliah.jenisMK}",
                fontSize = 16.sp
            )
            Text(
                text = "Dosen Pengampu: ${mataKuliah.DosenPengampu}",
                fontSize = 16.sp
            )
        }
    }
}