package com.example.agendaapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class AgendaItem(val fecha: String, val asunto: String, val actividad: String)

interface AgendaApi {
    @POST("add_agenda.php")
    fun addAgenda(@Body agendaItem: AgendaItem): Call<Void>

    @GET("get_agenda.php")
    fun getAgenda(): Call<List<AgendaItem>>
}
