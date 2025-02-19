package edu.ucne.randy_p1_ap2.presentation.ticket



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.randy_p1_ap2.data.remote.dto.TicketDto
import edu.ucne.randy_p1_ap2.data.repository.Resource
import edu.ucne.randy_p1_ap2.data.repository.TicketRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
) : ViewModel() {
    val ticketId: Int = 0
    var uiState = MutableStateFlow(TicketUistate())
        private set
    fun onSetTicket(tickestId: Int) {
        viewModelScope.launch {
            val ticket = ticketRepository.getTicket(tickestId)
            ticket?.let {
                uiState.update {
                    it.copy(
                        ticketId = ticket.ticketId,
                        descripcion = ticket.descripcion,
                        fecha = ticket.fecha,
                        precio = ticket.precio
                    )
                }
            }
        }
    }

    fun onDescripcionChanged(descripcion: String) {
        uiState.update {
            it.copy(descripcion = descripcion)
        }
    }
    fun onFechaChanged(fecha: String) {
        uiState.update {
            it.copy(fecha = fecha)
        }
    }
    fun onPrecioChanged(precio: String) {
        val letras = Regex("[a-zA-Z ]+")
        val numeros= precio.replace(letras, "").toDoubleOrNull()
        uiState.update {
            it.copy(precio = numeros)
        }
    }

    fun setTicket(){
        viewModelScope.launch {
            val ticket = ticketRepository.getTicket(uiState.value.ticketId?: 0)
            ticket?.let {
                uiState.update {
                    it.copy(
                        ticketId = ticket.ticketId,
                        descripcion = ticket.descripcion,
                        fecha = ticket.fecha,
                        precio = ticket.precio
                    )
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            val ticket = ticketRepository.getTicket(ticketId)
            ticket?.let {
                uiState.update {
                    it.copy(
                        ticketId = ticket.ticketId,
                        descripcion = ticket.descripcion,
                        fecha = ticket.fecha,
                        precio = ticket.precio
                    )
                }
            }
        }
    }

    fun getTickets() {
        viewModelScope.launch {
            ticketRepository.getTickets().collect{result   ->
                when(result){
                    is Resource.Loading -> {
                        uiState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                        delay(1000)
                    }
                    is Resource.Success -> {
                        uiState.update {
                            it.copy(
                                isLoading = false,
                                tickets = result.data ?: emptyList()
                            )
                        }
                    }
                    is Resource.Error -> {
                        uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                        }
                    }
                }
            }
        }

    }


    fun saveTicket(): Boolean {
        viewModelScope.launch {
            if(uiState.value.ticketId == null || uiState.value.ticketId == 0){
                ticketRepository.saveTicket(uiState.value.toDTO())
                uiState.value = TicketUistate()
            }
            else{
                ticketRepository.updateTicket(uiState.value.toDTO())
                uiState.value = TicketUistate()
            }
        }
        return true
    }


    fun newTicket() {
        viewModelScope.launch {
            uiState.value = TicketUistate()
        }
    }

    fun deleteTicket() {
        viewModelScope.launch {
            ticketRepository.deleteTicket(uiState.value.toDTO())
        }
    }

}

data class TicketUistate(
    val ticketId: Int? = null,
    var descripcion: String = "",
    var descripcionError: String? = null,
    var fecha: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
    var precio: Double? = null,
    var precioError: String? = null,
    val tickets: List<TicketDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)


fun TicketUistate.toDTO() = TicketDto(
    ticketId = ticketId?: 0,
    descripcion = descripcion,
    fecha = fecha,
    precio = precio?: 0.0,
)