package com.example.shared.ui.database

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.text.Editable


interface IDBHelper {
    fun addMascota(nombre:String, nombre_cientifico:String, tipo_pelaje:String,
                   clase:String, amorosidad:String, foto: Bitmap, enlace:String)
    fun delMascota(id: Int):Int
    fun getMascotas(): ArrayList<Mascota>
    fun getMascotaById(id: Int):Mascota?
    fun getMascotaByName(name: String):Mascota?
    fun updateMascota(
        id:Int,
        nombre: String? = null,
        nombre_cientifico:String? = null,
        tipo_pelaje:String? = null,
        clase:String? = null,
        amorosidad:String? = null,
        foto: Bitmap? = null,
        enlace:String? = null,
        favorito:Int? = null ): Int




}