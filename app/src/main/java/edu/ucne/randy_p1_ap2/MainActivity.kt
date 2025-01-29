package edu.ucne.randy_p1_ap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.randy_p1_ap2.ui.theme.Randy_P1_AP2Theme
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Randy_P1_AP2Theme {
              /*  Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {

                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = ScreenOne
                        ) {
                            composable<ScreenOne> {
                                Screen1 {
                                    navController.navigate(ScreenTwo("Enel"))
                                }
                            }
                            composable<ScreenTwo> {
                                val name = it.toRoute<ScreenTwo>().name
                                Screen2(name)
                            }

                        }

                    }
                }*/
            }
        }
    }
}



/*
@Composable
fun Screen1(modifier: Modifier = Modifier, onGoToScreen2: () -> Unit) {
    Column {

        Text("Screen 1", modifier = modifier.padding(16.dp))
        OutlinedButton(
            onClick = {
                onGoToScreen2()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Go to Screen 2")
        }
    }
}

@Composable
fun Screen2(nombre: String, modifier: Modifier = Modifier) {
    Text("Screen 2", modifier = modifier.padding(16.dp))
    Text("Valor recibido: $nombre")
}


@Composable
fun Screen3(modifier: Modifier = Modifier) {
    Text("Screen 1", modifier = modifier.padding(16.dp))
}

@Serializable
data object ScreenOne

@Serializable
data class ScreenTwo(val name: String)
*/