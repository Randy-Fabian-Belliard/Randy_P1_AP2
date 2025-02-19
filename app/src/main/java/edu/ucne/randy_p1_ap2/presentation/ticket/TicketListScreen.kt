package edu.ucne.randy_p1_ap2.presentation.ticket

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.randy_p1_ap2.data.remote.dto.TicketDto
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun TicketListScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    onVerServicio: (TicketDto) -> Unit,
    onAddServicio: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TicketListBody(
        tickets = uiState.tickets,
        onVerServicio = onVerServicio,
        onAddServicio = onAddServicio,
        onList = { viewModel.getTickets() },
        uistate = uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketListBody(
    tickets: List<TicketDto>,
    onVerServicio: (TicketDto) -> Unit,
    onAddServicio: () -> Unit,
    onList: () -> Unit,
    uistate: TicketUistate,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Tickets")
                        TextButton(onClick = { onList() }) {
                            Text(text = "Cargar tickets")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddServicio) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Id", modifier = Modifier.weight(0.10f))
                Text(text = "Descripción", modifier = Modifier.weight(0.300f))
                Text(text = "Fecha", modifier = Modifier.weight(0.30f))
                Text(text = "Precio", modifier = Modifier.weight(0.30f))
            }

            if (uistate.isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(tickets) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onVerServicio(item) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = item.ticketId.toString(), modifier = Modifier.weight(0.10f))
                        Text(text = item.descripcion, modifier = Modifier.weight(0.300f))
                        Text(text = item.fecha, modifier = Modifier.weight(0.30f))
                        Text(text = item.precio.toString(), modifier = Modifier.weight(0.30f))
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun TecnicoListPreview() {
        TicketListBody(
            tickets = emptyList(),
            onVerServicio = {},
            onAddServicio = {},
            onList = {},
            uistate = TicketUistate()
        )
}