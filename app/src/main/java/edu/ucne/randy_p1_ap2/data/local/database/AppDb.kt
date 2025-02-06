package edu.ucne.randy_p1_ap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.randy_p1_ap2.data.local.dao.SistemasDao
import edu.ucne.randy_p1_ap2.data.local.entity.SistemasEntity

@Database(

    entities = [SistemasEntity::class],
    version = 2,
    exportSchema = false

)
abstract class AppDb : RoomDatabase(){
    abstract fun sistemasDao(): SistemasDao

}