 package edu.ucne.randy_p1_ap2.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.randy_p1_ap2.presentation.sistenas.DistemaEditScreen
import edu.ucne.randy_p1_ap2.presentation.sistenas.SistemaListScreen
import edu.ucne.randy_p1_ap2.presentation.sistenas.SistemaScreen


 @RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SistemaList
    ) {

        composable<Screen.SistemaList> {
            SistemaListScreen(
                onCreate = { navController.navigate(Screen.Sistema(0)) },
                onDelete = { navController.navigate(Screen.SistemaDelete(it)) },
                onEdit = { navController.navigate(Screen.SistemaEdit(it)) },
                onBack = { navController.navigateUp() }
            )
        }

        composable<Screen.Sistema> {
            SistemaScreen(
                goBack = { navController.navigateUp() }
            )
        }

        composable<Screen.SistemaEdit> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.SistemaEdit>()
            DistemaEditScreen(
                sistemaId  = args.SistemaId,
                goBack = { navController.navigateUp() }
            )
        }






    }
}




