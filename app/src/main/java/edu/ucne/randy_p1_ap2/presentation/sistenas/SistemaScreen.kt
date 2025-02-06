package edu.ucne.randy_p1_ap2.presentation.sistenas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun SistemaScreen(
    viewModel: SistemaViewModel = hiltViewModel(),
    goBack: ( ) -> Unit,

    ){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DishwasherBodyScreen(
        uiState = uiState,
        onNameChange = viewModel::onNameChange,
        onPrecioChange = viewModel::onPrecioChange,
        onSave = viewModel::save,
       goBack = goBack
    )

}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
 fun DishwasherBodyScreen(
    uiState: Uistate,
    onNameChange: (String) -> Unit,
    onPrecioChange: (Double) -> Unit,
    onSave: () -> Unit,
    goBack: () -> Unit,
){

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Agregar sistema") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(red=0, green=0, blue=0, alpha=1),
                    titleContentColor = Color.Black
                )
            )
        }
    ){ innerPadding ->
        Column (
            modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding)
            .padding(16.dp)

        ){
            OutlinedTextField(
                label = { Text(text = "Nombre") },
                value = uiState.nombre,
                onValueChange = { newValue ->
                    onNameChange(newValue.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase() else it.toString()
                    })
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                label = { Text("Precio") },
                value = uiState.precio.toString(),
                onValueChange = { newValue ->
                    val precio = newValue.toDoubleOrNull() ?: 0.0
                    onPrecioChange(precio)
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )

            uiState.errorMessage?.let {
                Text(text = it, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(8.dp))

            uiState.errorMessage?.let {
                Text(text = it, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
              /*  Button(
                    modifier = Modifier.padding(15.dp),
                    onClick = {
                        goBack()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Go back"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Volver")
                }*/

                Button(
                    modifier = Modifier.padding(15.dp),
                    onClick = {
                        goBack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Go back"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Volver")
                }

                Button(
                    modifier = Modifier.padding(15.dp),
                    onClick = {
                        onSave()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Save"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Guardar")
                }
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
private fun TecnicoBodyScreenPreview() {
    DishwasherBodyScreen(
        uiState = Uistate(
            nombre = "jose",
            errorMessage = null
        ),
        onNameChange = {},
        onPrecioChange = {},
        onSave = {},
        goBack = {}
    )
}
