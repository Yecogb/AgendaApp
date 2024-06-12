// AgendaScreen.kt
package com.example.agendaapp.view

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agendaapp.AgendaItem
import com.example.agendaapp.controller.AgendaController


@Composable
fun AgendaScreen() {
    var fecha by remember { mutableStateOf("") }
    var asunto by remember { mutableStateOf("") }
    var actividad by remember { mutableStateOf("") }
    var agendaItems by remember { mutableStateOf<List<AgendaItem>>(emptyList()) }

    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Agenda",
            style = TextStyle(color = Color(0xFF00008B), fontSize = 30.sp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Agregar Evento",
            style = TextStyle(color = Color(0xFF0000CD), fontSize = 24.sp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(label = "Fecha:", text = fecha, onTextChange = { fecha = it })
        CustomTextField(label = "Asunto:", text = asunto, onTextChange = { asunto = it })
        CustomTextField(label = "Actividad:", text = actividad, onTextChange = { actividad = it })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            Log.d("Agenda", "Fecha: $fecha, Asunto: $asunto, Actividad: $actividad")
            val agendaItem = AgendaItem(fecha, asunto, actividad)

            AgendaController.saveAgendaItem(agendaItem, context, onSuccess = {
                Toast.makeText(context, "Evento guardado", Toast.LENGTH_LONG).show()
                fecha = ""
                asunto = ""
                actividad = ""
            }, onError = {
                Toast.makeText(context, "Error al guardar el evento", Toast.LENGTH_LONG).show()
            })
        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00008B))) {
            Text(text = "Guardar Evento", color = Color.White)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            Log.d("Agenda", "Botón Consultar Agenda clicado")
            AgendaController.fetchAgendaItems(context, onSuccess = {
                agendaItems = it
                Log.d("Agenda", "Items de la agenda obtenidos: $agendaItems")
            }, onError = {
                Toast.makeText(context, "Error al obtener la agenda", Toast.LENGTH_LONG).show()
            })
        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00008B))) {
            Text(text = "Consultar Agenda", color = Color.White)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            (context as? ComponentActivity)?.finishAffinity()
        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF8B0000))) {
            Text(text = "Salir", color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (agendaItems.isNotEmpty()) {
            Text(text = "Agenda Items", style = TextStyle(fontSize = 20.sp, color = Color(0xFF00008B)))
            agendaItems.forEach { item ->
                Text(text = "Fecha: ${item.fecha}, Asunto: ${item.asunto}, Actividad: ${item.actividad}")
            }
        }
    }
}

@Composable
fun CustomTextField(label: String, text: String, onTextChange: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            style = TextStyle(color = Color(0xFF8B0000), fontSize = 16.sp),
            modifier = Modifier.width(80.dp)
        )
        BasicTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
                .border(1.dp, Color.Gray)
                .padding(8.dp),
            textStyle = TextStyle(fontSize = 16.sp)
        )
    }
}
