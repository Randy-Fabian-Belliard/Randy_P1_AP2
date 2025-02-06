
package edu.ucne.randy_p1_ap2.presentation.sistenas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.randy_p1_ap2.data.local.entity.SistemasEntity
import java.text.NumberFormat
import java.util.Locale


@Composable
fun SistemaListScreen(
    viewModel: SistemaViewModel = hiltViewModel(),
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SistemaBodyListScreen(
        uiState = uiState,
        onCreate = onCreate,
        onDelete = viewModel::delete,
        onEdit = onEdit,
        onBack = onBack
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SistemaBodyListScreen(
    uiState: Uistate,
    onCreate: () -> Unit,
    onDelete: (Int) -> Unit,
    onEdit: (Int) -> Unit,
    onBack: () -> Unit


){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de sistema") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(red=0, green=0, blue=0, alpha=1),
                    titleContentColor = Color.Black
                ),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreate
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar sistema")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.sistemas) { sistemas ->
                    SistemaRow(
                        sistemas = sistemas,
                        onDelete = onDelete,
                        onEdit = onEdit
                    )
                }
            }
        }
    }
}

@Composable
fun SistemaRow(
    sistemas: SistemasEntity,
    onDelete: (Int) -> Unit,
    onEdit: (Int) -> Unit,


    ){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = sistemas.sistemaId.toString(),
                modifier = Modifier.weight(0.2f),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = sistemas.nombre,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = NumberFormat.getCurrencyInstance(Locale.US).format(sistemas.precio),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(0.5f)
            )

            Row(modifier = Modifier.weight(0.5f)) {
                IconButton(
                    onClick = { onEdit(sistemas.sistemaId!!) },
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = "Editar sistema", tint = Color.Black)
                }
                IconButton(
                    onClick = { onDelete(sistemas.sistemaId!!)},
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = "Eliminar sistema", tint = Color.Red)

                }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SistemasListScreenPreview() {
    val sampleSistema = listOf(

        SistemasEntity(
            sistemaId = 1,
            nombre = "Randy",
            precio = 20000.0


            )
    )
    SistemaBodyListScreen(
        uiState = Uistate(sistemas =sampleSistema),
        onCreate = {  },
        onDelete = {  },
        onEdit = {  },
        onBack = {}
    )


}






