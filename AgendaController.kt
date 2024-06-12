// AgendaController.kt
package com.example.agendaapp.controller

import android.content.Context
import android.widget.Toast
import com.example.agendaapp.AgendaItem
import com.example.agendaapp.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AgendaController {
    fun saveAgendaItem(
        agendaItem: AgendaItem,
        context: Context,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val call = RetrofitInstance.api.addAgenda(agendaItem)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Evento guardado con éxito", Toast.LENGTH_SHORT).show()
                    onSuccess()
                } else {
                    Toast.makeText(context, "Error al guardar el evento: ${response.code()}", Toast.LENGTH_SHORT).show()
                    onError()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(context, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                onError()
            }
        })
    }

    fun fetchAgendaItems(
        context: Context,
        onSuccess: (List<AgendaItem>) -> Unit,
        onError: () -> Unit
    ) {
        val call = RetrofitInstance.api.getAgenda()
        call.enqueue(object : Callback<List<AgendaItem>> {
            override fun onResponse(call: Call<List<AgendaItem>>, response: Response<List<AgendaItem>>) {
                if (response.isSuccessful) {
                    val agendaItems = response.body()
                    if (agendaItems != null) {
                        onSuccess(agendaItems)
                    } else {
                        onError()
                    }
                } else {
                    Toast.makeText(context, "Error al obtener la agenda: ${response.code()}", Toast.LENGTH_SHORT).show()
                    onError()
                }
            }

            override fun onFailure(call: Call<List<AgendaItem>>, t: Throwable) {
                Toast.makeText(context, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                onError()
            }
        })
    }
}
