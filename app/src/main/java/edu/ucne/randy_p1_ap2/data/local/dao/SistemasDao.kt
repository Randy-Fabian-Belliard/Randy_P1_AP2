package edu.ucne.randy_p1_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

import edu.ucne.randy_p1_ap2.data.local.entity.SistemasEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SistemasDao {


    @Upsert
    suspend fun save(sistemas: SistemasEntity)

    @Query(
        """
            Select *
            from sistemas
            where sistemaId = :id
            limit 1
            """
    )
    suspend fun find(id: Int): SistemasEntity?

    @Delete()
    suspend fun delete(sistemasEntity: SistemasEntity)

    @Query("select * from sistemas ")
    fun getAll(): Flow<List<SistemasEntity>>

}