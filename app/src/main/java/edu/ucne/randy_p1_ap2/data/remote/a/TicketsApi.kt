package edu.ucne.randy_p1_ap2.data.remote.a



import edu.ucne.randy_p1_ap2.data.remote.dto.TicketDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

public interface TicketsApi {
    @GET("api/Tickets/{id}")
    suspend fun getTicket(@Path("id") id: Int): TicketDto
    @GET("api/Tickets")
    suspend fun getTickets(): List<TicketDto>
    @POST("api/Tickets")
    suspend fun saveTicket(@Body ticketDto: TicketDto?):TicketDto?
    @PUT("api/Tickets/{id}")
    suspend fun updateTicket(@Path("id") id: Int, @Body ticketDto: TicketDto?): Response<TicketDto>
    @DELETE("api/Tickets/{id}")
    suspend fun deleteTicket(@Path("id") id: Int): Response<Unit>
}

