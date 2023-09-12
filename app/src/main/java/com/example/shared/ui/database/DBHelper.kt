package com.example.shared.ui.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream


class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION), IDBHelper  {

    /*
    val nombre:String, val nombre_cientifico:String,
    val tipo_pelaje:String,
    val clase:String, val amorosidad:String,
    val foto: Drawable, val enlace:String
    */
    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "mascotas.db"
        val TABLA_MASCOTAS = "mascotas"
        val COLUMNA_ID = "_id"
        val COLUMNA_NOMBRE = "nombre"
        val COLUMNA_NOMCIEN = "nombre_cientifico"
        val COLUMNA_PELAJE = "tipo_pelaje"
        val COLUMNA_CLASE = "clase"
        val COLUMNA_AMOR= "amorosidad"
        val COLUMNA_FOTO= "foto"
        val COLUMNA_ENLACE= "enlace"
        val COLUMNA_FAVORITO= "favorito"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            val createTableMascotas =
                "CREATE TABLE $TABLA_MASCOTAS" +
                        "($COLUMNA_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "$COLUMNA_NOMBRE TEXT , " +
                        "$COLUMNA_NOMCIEN TEXT, "+
                        "$COLUMNA_PELAJE TEXT, "+
                        "$COLUMNA_CLASE TEXT, "+
                        "$COLUMNA_AMOR TEXT, "+
                        "$COLUMNA_FOTO BLOB, "+
                        "$COLUMNA_ENLACE TEXT," +
                        "$COLUMNA_FAVORITO INTEGER) "


            db!!.execSQL(createTableMascotas)
        } catch (e: SQLiteException) {
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    override fun addMascota(
        nombre: String,
        nombre_cientifico: String,
        tipo_pelaje: String,
        clase: String,
        amorosidad: String,
        foto: Bitmap,
        enlace: String,

    ) {
        val data= ContentValues()

        data.put(COLUMNA_NOMBRE, nombre)
        data.put(COLUMNA_NOMCIEN, nombre_cientifico)
        data.put(COLUMNA_PELAJE, tipo_pelaje)
        data.put(COLUMNA_CLASE, clase)
        data.put(COLUMNA_AMOR, amorosidad)
        data.put(COLUMNA_FOTO, getBitmapAsByteArray(foto))
        data.put(COLUMNA_ENLACE, enlace)
        data.put(COLUMNA_FAVORITO, 0)
        val db=this.writableDatabase
        db.insert(TABLA_MASCOTAS,null,data)
    }

    fun getBitmapAsByteArray(bitmap: Bitmap): ByteArray? {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(CompressFormat.PNG, 0, outputStream)
        return outputStream.toByteArray()
    }

    override fun getMascotas(): ArrayList<Mascota> {
        val result = ArrayList<Mascota>()
        val db = this.readableDatabase

        val cursor = db.rawQuery(
            "SELECT $COLUMNA_ID, $COLUMNA_NOMBRE,$COLUMNA_NOMCIEN, $COLUMNA_PELAJE,$COLUMNA_CLASE,$COLUMNA_AMOR,$COLUMNA_FOTO,$COLUMNA_ENLACE,$COLUMNA_FAVORITO" +
            " FROM $TABLA_MASCOTAS",
            null
        )
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val nomcien = cursor.getString(2)
                val pelaje = cursor.getString(3)
                val clase = cursor.getString(4)
                val amor = cursor.getString(5)
                // val fotoByteArray = cursor.getBlob(6)
                // val foto = BitmapFactory.decodeByteArray(fotoByteArray, 0, fotoByteArray.size)
                val foto = BitmapFactory.decodeByteArray(cursor.getBlob(6), 0, cursor.getBlob(6).size)
                val enlace = cursor.getString(7)
                val favorito=cursor.getInt(8)
                val mascota = Mascota(id, name, nomcien, pelaje, clase, amor, foto, enlace, favorito)
                result.add(mascota)
            } while (cursor.moveToNext());

        }
        return result
    }

    override fun getMascotaById(id: Int): Mascota? {

        val db = this.readableDatabase
        var mascota: Mascota? = null

        val cursor = db.rawQuery(
            "SELECT $COLUMNA_ID, $COLUMNA_NOMBRE, $COLUMNA_NOMCIEN, $COLUMNA_PELAJE,$COLUMNA_CLASE,$COLUMNA_AMOR,$COLUMNA_FOTO,$COLUMNA_ENLACE, $COLUMNA_FAVORITO" +
                    " FROM $TABLA_MASCOTAS WHERE $COLUMNA_ID=?", arrayOf(id.toString())
        )
        if (cursor.moveToFirst()) {

            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val nomcien = cursor.getString(2)
            val pelaje = cursor.getString(3)
            val clase = cursor.getString(4)
            val amor = cursor.getString(5)
            val foto = BitmapFactory.decodeByteArray(cursor.getBlob(6), 0, cursor.getBlob(6).size)
            val enlace = cursor.getString(7)
            val favorito=cursor.getInt(8)
            mascota = Mascota(id, name, nomcien, pelaje, clase, amor, foto, enlace, favorito)

        }
        return mascota
    }

    override fun getMascotaByName(name: String): Mascota? {
        val db = this.readableDatabase
        var mascota: Mascota? = null

        val cursor = db.rawQuery(
            "SELECT $COLUMNA_ID, $COLUMNA_NOMBRE, $COLUMNA_NOMCIEN, $COLUMNA_PELAJE, $COLUMNA_CLASE, $COLUMNA_AMOR,$COLUMNA_FOTO,$COLUMNA_ENLACE,$COLUMNA_FAVORITO" +
                    " FROM $TABLA_MASCOTAS WHERE $COLUMNA_NOMBRE = ? ",
            arrayOf(name)
        )

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val nomcien = cursor.getString(2)
            val pelaje = cursor.getString(3)
            val clase = cursor.getString(4)
            val amor = cursor.getString(5)
            val foto = BitmapFactory.decodeByteArray(cursor.getBlob(6), 0, cursor.getBlob(6).size)
            val enlace = cursor.getString(7)
            val favorito=cursor.getInt(8)
            mascota = Mascota(id, name, nomcien, pelaje, clase, amor, foto, enlace, favorito)
        }
        return mascota
    }

    override fun delMascota(id: Int): Int {
        val args = arrayOf(id.toString())//hacemos el array porqye lo requiere el delete
        val db = this.writableDatabase
        val result = db.delete(TABLA_MASCOTAS, "$COLUMNA_ID=?", args)
        db.close()
        return result
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
    }

    override fun updateMascota(
        id: Int,
        nombre: String?,
        nombre_cientifico: String?,
        tipo_pelaje: String?,
        clase: String?,
        amorosidad: String?,
        foto: Bitmap?,
        enlace: String?,
        favorito: Int?
    ): Int {
        val args = arrayOf(id.toString())
        val db= this.writableDatabase
        val data= ContentValues()

        if(nombre != null) { data.put(COLUMNA_NOMBRE, nombre) }
        if(nombre_cientifico != null) { data.put(COLUMNA_NOMCIEN, nombre_cientifico) }
        if(tipo_pelaje != null) { data.put(COLUMNA_PELAJE, tipo_pelaje) }
        if(clase != null) { data.put(COLUMNA_CLASE, clase) }
        if(amorosidad != null) { data.put(COLUMNA_AMOR, amorosidad) }
        if(foto != null) { data.put(COLUMNA_FOTO, getBitmapAsByteArray(foto)) }
        if(enlace != null) { data.put(COLUMNA_ENLACE, enlace) }
        if(favorito != null) { data.put(COLUMNA_FAVORITO, favorito) }



        val res= db.update(TABLA_MASCOTAS, data,"$COLUMNA_ID = ?",args)
        db.close()
        return res
    }


}