package com.sofia.miformacionctma.ui.state
import com.sofia.miformacionctma.domain.Prioridad
data class FormularioActividadUiState(val titulo:String="",val descripcion:String="",val fecha:String="",val prioridad:Prioridad=Prioridad.MEDIA,val progreso:String="0",val errorTitulo:String?=null,val errorDescripcion:String?=null,val errorFecha:String?=null,val errorProgreso:String?=null,val puedeGuardar:Boolean=false)
