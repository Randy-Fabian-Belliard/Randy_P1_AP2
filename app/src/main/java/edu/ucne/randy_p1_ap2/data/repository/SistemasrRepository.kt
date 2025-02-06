package edu.ucne.randy_p1_ap2.data.repository

import edu.ucne.randy_p1_ap2.data.local.dao.SistemasDao
import edu.ucne.randy_p1_ap2.data.local.entity.SistemasEntity
import javax.inject.Inject


class SistemasRepository @Inject constructor(
    private val sistemasDao: SistemasDao
){
    suspend fun save (sistemas: SistemasEntity)= sistemasDao.save(sistemas)
    fun getAll() = sistemasDao.getAll()
    suspend fun delete(sistemas: SistemasEntity)= sistemasDao.delete(sistemas)
    suspend fun getSistemas(sistemasId: Int) = sistemasDao.find(sistemasId)
}