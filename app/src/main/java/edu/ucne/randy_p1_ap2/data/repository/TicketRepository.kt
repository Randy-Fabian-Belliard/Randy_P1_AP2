package edu.ucne.randy_p1_ap2.data.repository

import edu.ucne.randy_p1_ap2.data.remote.a.TicketsApi
import edu.ucne.randy_p1_ap2.data.remote.dto.TicketDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val ticketsApi: TicketsApi
) {
     suspend fun getTickets(): Flow<Resource<List<TicketDto>>> = flow {
         emit(Resource.Loading())
         try{
             val tickets = ticketsApi.getTickets()
             emit(Resource.Success(tickets))
         }catch (e: Exception){
             emit(Resource.Error(e.message ?: "An unexpected error occurred"))
         }
     }

    suspend fun saveTicket(ticket: TicketDto){
        try {
            ticketsApi.saveTicket(ticket)
        }

        catch (e: Exception){

        }
    }
    suspend fun updateTicket(ticket: TicketDto){
        try {
            ticketsApi.updateTicket(ticket.ticketId, ticket)
        }

        catch (e: Exception){

        }
    }
    suspend fun deleteTicket(ticket: TicketDto){
        try {
            ticketsApi.deleteTicket(ticket.ticketId)
        }

        catch (e: Exception){

        }
    }

    suspend fun getTicket(id: Int): TicketDto? {
        return try {
            ticketsApi.getTicket(id)
        } catch (e: Exception) {
            null
        }
    }

}

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}