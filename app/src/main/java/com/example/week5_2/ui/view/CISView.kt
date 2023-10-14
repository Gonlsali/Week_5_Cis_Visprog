package com.example.week5_2.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week5_2.model.MatkulUIState
import com.example.week5_2.viewmodel.CISViewModel

@Composable
fun CISView(
    cisViewModel: CISViewModel = viewModel()
) {
    val cisUIState by cisViewModel.cisUIState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Title(cisViewModel = cisViewModel)
        }

        items(cisUIState) {
            CardMatkul(matkul = it, cisViewModel = cisViewModel)
        }
    }
}

@Composable
fun CardMatkul(
    matkul: MatkulUIState,
    cisViewModel: CISViewModel
) {
    Card(
        modifier = Modifier
            .size(width = 350.dp, height = 90.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
        ) {

            Column(
                modifier = Modifier
                    .weight(1.5f)
            ) {

                Text(
                    text = matkul.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "SKS: ${matkul.sks}",
                    fontSize = 14.sp
                )

                Text(
                    text = "Score: ${matkul.score}",
                    fontSize = 14.sp
                )
            }

            Button(
                onClick = { cisViewModel.deleteMatkul(matkul)},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red,
                )
            }
        }
    }
}

@Composable
fun Title(
    cisViewModel: CISViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Courses",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.padding(bottom = 10.dp))

        Text(
            text = "Total SKS : ${cisViewModel.getSks()}",
            fontSize = 16.sp
        )

        Text(
            text = "IPK : ${cisViewModel.getIpk()}",
            fontSize = 16.sp
        )

        Form(cisViewModel = cisViewModel)

    }
}

@Composable
fun Form(
    cisViewModel: CISViewModel
) {
    var matkulName by rememberSaveable {
        mutableStateOf("")
    }
    var Nilai by  rememberSaveable {
        mutableStateOf("")
    }
    var Sks by rememberSaveable {
        mutableStateOf("")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        MatkulInput(
            value = Nilai,
            onValueChanged = { Nilai = it},
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .size(width = 175.dp, height = 60.dp),
            name = "Nilai"
        )

        MatkulInput(
            value = Sks,
            onValueChanged = { Sks = it},
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .size(width = 175.dp, height = 60.dp),
            name = "Sks"
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        MatkulInput(
            value = matkulName,
            onValueChanged = { matkulName = it},
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .size(width = 250.dp, height = 60.dp),
            name = "Nama Mata Kuliah"
        )

        Button(
            onClick = {
                cisViewModel.addMatkul(matkulName, Sks, Nilai)
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "add"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatkulInput(
    value:String,
    onValueChanged:(String)-> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier,
    name: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        label = { Text(text = name)},
        shape = CircleShape
    )
}

@Preview (showSystemUi = true, showBackground = true)
@Composable
fun CISPreview() {
    CISView()
}