package edu.ucne.randy_p1_ap2.data.remote.dto

data class TicketDto(
    val ticketId: Int,
    val descripcion: String,
    val fecha: String,
    val precio: Double,
)