package edu.ucne.randy_p1_ap2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sistemas" )
data class SistemasEntity (
    @PrimaryKey
    val sistemaId: Int? = null,
    val nombre: String = "",
    val precio: Double = 0.0,

)