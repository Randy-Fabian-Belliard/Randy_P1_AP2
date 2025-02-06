package edu.ucne.randy_p1_ap2.presentation.sistenas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.randy_p1_ap2.data.local.entity.SistemasEntity
import edu.ucne.randy_p1_ap2.data.repository.SistemasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SistemaViewModel @Inject constructor(
    private val repository: SistemasRepository

): ViewModel() {
    private val _uiState = MutableStateFlow(Uistate())
    val uiState = _uiState.asStateFlow()

    init{
      getSistema()
    }

   fun getSistema(){
        viewModelScope.launch {
            repository.getAll().collect {sistemas ->
            _uiState.update {
                    it.copy(sistemas = sistemas)
                }
            }
        }
   }

    fun delete(sistemasId: Int) {
        viewModelScope.launch {
            repository.delete(repository.getSistemas(sistemasId)!!)
        }
    }


       fun delete(){
           viewModelScope.launch {
               repository.delete(_uiState.value.toEntity())
           }
       }

    fun selectedSistema(sistemasId: Int){
        viewModelScope.launch {
            if(sistemasId > 0){
                val sistemas = repository.getSistemas(sistemasId)
                _uiState.update {
                    it.copy(
                        sistemaId = sistemas?.sistemaId,
                        nombre = sistemas?.nombre ?: ""
                    )
                }
            }
        }
    }

    fun save(){
        if(uiState.value.nombre.isBlank()){
            _uiState.update {
                it.copy(errorMessage = "Verifique los datos")
            }
        }else{
            viewModelScope.launch {
                repository.save(uiState.value.toEntity())
            }
            nuevo()
        }
    }

    fun nuevo() {
        _uiState.value = Uistate()
    }

    fun onNameChange(nombre: String){
        _uiState.update {
            it.copy(
                nombre =nombre,
                errorMessage =  if(nombre.isBlank()) "No se Puede Guardar con nombre Vacío"
                else null
            )
        }
    }


    fun onPrecioChange(precio: Double) {
        _uiState.update {
            it.copy(
                precio = precio,
                errorMessage = if (precio < 0.0) "No se Puede Guardar con precio si es un muemero negatico"
                else null
            )
        }
    }


}

fun Uistate.toEntity() = SistemasEntity(
    sistemaId = this.sistemaId,
    nombre = this.nombre,
    precio = this.precio

)

data class Uistate(
    val sistemaId: Int? = null,
    val nombre: String = "",
    val precio: Double = 0.0,
    val errorMessage: String? = null,
    val sistemas: List<SistemasEntity> = emptyList()
)

