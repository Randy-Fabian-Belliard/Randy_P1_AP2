package edu.ucne.randy_p1_ap2.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {



    @Serializable
    data object SistemaList : Screen()

    @Serializable
    data object SistemaScreen : Screen()

    @Serializable
    data class Sistema(val SistemaId: Int): Screen()

    @Serializable
    data class  SistemaDelete(val SistemaId: Int) : Screen()

    @Serializable
    data class SistemaEdit(val SistemaId: Int) : Screen()
}