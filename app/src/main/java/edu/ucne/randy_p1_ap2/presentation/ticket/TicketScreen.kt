package edu.ucne.randy_p1_ap2.presentation.ticket

import androidx.compose.foundation.clickable
import androidx.compose.material3.TopAppBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Composable
fun TicketScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    goToServicioList: () -> Unit,
    ticketId: Int?
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) {
        viewModel.onSetTicket(ticketId?:0)
    }

    TciketBody(
        uiState = uiState,
        onSaveServico = {
            viewModel.saveTicket()
        },
        goToServicioList = goToServicioList,
        onNewServicio = {
            viewModel.newTicket()
        },
        onDescripcionChanged = viewModel::onDescripcionChanged,
        onFechaChaged = viewModel::onFechaChanged,
        onPrecioChanged = viewModel::onPrecioChanged,
        onDeleteTicket = {viewModel.deleteTicket()}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TciketBody(
    uiState: TicketUistate,
    onSaveServico: () -> Boolean,
    onDeleteTicket: () -> Unit,
    goToServicioList: () -> Unit,
    onDescripcionChanged: (String) -> Unit,
    onFechaChaged: (String) -> Unit,
    onPrecioChanged: (String) -> Unit,
    onNewServicio: () -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val unDia = 86400000

    val state = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= System.currentTimeMillis() - unDia
        }
    })

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {

            TopAppBar(
                title = { Text("Ticket") },
                navigationIcon = {
                    IconButton(onClick = goToServicioList) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    }
                }
            )
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(4.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    OutlinedTextField(
                        label = { Text(text = "Descripción") },
                        value = uiState.descripcion ?: "",
                        onValueChange = onDescripcionChanged,
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.descripcionError != null
                    )
                    if (uiState.descripcionError != null) {
                        Text(
                            text = uiState.descripcionError ?: "",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    OutlinedTextField(
                        label = { Text(text = "Fecha") },
                        value = uiState.fecha.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                        onValueChange = onFechaChaged,
                        readOnly = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    showDatePicker = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Date Picker"
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()

                            .clickable(enabled = true) {
                                showDatePicker = true
                            }
                    )

                    Spacer(modifier = Modifier.padding(2.dp))

                    OutlinedTextField(
                        label = { Text(text = "Precio") },
                        value = uiState.precio.toString().replace("null", ""),
                        onValueChange = onPrecioChanged,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.precioError != null
                    )
                    if (uiState.precioError != null) {
                        Text(
                            text = uiState.precioError ?: "",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(onClick = onNewServicio) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Nuevo"
                            )
                            Text(text = "Nuevo")
                        }
                        OutlinedButton(
                            onClick = {
                                if (onSaveServico()) {
                                    goToServicioList()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Guardar"
                            )
                            Text(text = "Guardar")
                        }
                        OutlinedButton(
                            onClick = {
                                if(uiState.ticketId != null && uiState.ticketId != 0){
                                    onDeleteTicket()
                                    goToServicioList()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Borrar"
                            )
                            Text(text = "Borrar")
                        }
                    }
                }
            }
        }
    }
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(
                    onClick = {
                        onFechaChaged( state.selectedDateMillis?.let {
                            Instant.ofEpochMilli(it).atZone(
                                ZoneId.of("UTC")
                            ).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                        }.toString()
                        )
                        showDatePicker = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Aceptar")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDatePicker = false }) {
                    Text(text = "Cancelar")
                }
            },
        )
        {
            DatePicker(state)
        }
    }
}

@Preview
@Composable
private fun ServicioPreview() {

        TciketBody(
            uiState = TicketUistate(),
            onSaveServico = { true },
            goToServicioList = {},
            onDescripcionChanged = {},
            onFechaChaged = {},
            onNewServicio = {},
            onPrecioChanged = {},
            onDeleteTicket = {}
        )

}
