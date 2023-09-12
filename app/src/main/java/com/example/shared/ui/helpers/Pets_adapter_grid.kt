package com.example.shared.ui.helpers

import android.R.attr.bitmap
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.shared.R
import com.example.shared.ui.database.DBHelperApplication
import com.example.shared.ui.database.Mascota
import kotlinx.android.synthetic.main.adapter_item_mascotas_grid.view.*
import java.util.*


class Pets_adapter_grid : BaseAdapter{

    var context: Context?=null
    var items:ArrayList<Mascota>

    constructor(context: Context, filtro: Int){
        this.context=context
        this.items= DBHelperApplication.dataSource.getMascotas()

        when (filtro) {
            0 ->{
                this.items.sortBy { it.nombre }//filtro para ordenar alfabeticamente
            }

            1 -> {
                this.items.sortBy { it.amorosidad }
            }

            2 -> {
                // evitar los que no estén marcados como favoritos.
                var newlist = ArrayList<Mascota>()


                for (item in items ) {
                    if(item.favorito==1) {
                        newlist.add(item);
                    }
                }
                this.items = newlist

            }
        }

    }
    override fun getCount(): Int {
        return this.items.size
    }

    override fun getItem(position: Int): Mascota {
        return this.items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        val item=this.items[position]
        val inflater= LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.adapter_item_mascotas_grid, p2, false)

        val amorosidad: String = "Amorosidad: " + item.amorosidad
        view.txtnombre.text = item.nombre
        view.txtamorosidad.text = amorosidad
        view.idItem.text = item.id.toString()
        //view.textView.text = item.nombre_cientifico

        view.imageView.setImageBitmap(item.foto)

        return view
    }

}