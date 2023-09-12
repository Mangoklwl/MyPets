package com.example.shared.ui.fragments

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.shared.R
import com.example.shared.ui.database.DBHelperApplication
import com.example.shared.ui.database.Mascota
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_insertar.*
import kotlinx.android.synthetic.main.item_mascota.*
import kotlinx.android.synthetic.main.item_mascota.view.*

class ItemMascotaFragment : Fragment() {

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(
            R.layout.item_mascota,
            container,
            false
        )

        val iditem = arguments?.getString("input_id")
        val mascota = DBHelperApplication.dataSource.getMascotaById(Integer.parseInt(iditem))

        if (mascota != null) {
            setData(view, mascota)
            pulsaFav(view, mascota)
            borrar(view, mascota)
        }
        if (mascota != null) {
            test(view, mascota)
        }
        exit(view)
        return view
    }

    private fun test(myview: View, mascota: Mascota) {

        myview.btnEditar.setOnClickListener {
            myview.btnGuardar.visibility = VISIBLE
            myview.nombre.isEnabled = true
            myview.btnGuardar.setOnClickListener {
                val nombre = nombre.text.toString()
                val nomci = nomCient.text.toString()
                val tipo_pelo = tipoPelo.text.toString()
                val clase = clase.text.toString()
                val amor = amorosidad.text.toString()
                val enlace = enlace.text.toString()
                val fot = (foto.drawable as BitmapDrawable).bitmap


                DBHelperApplication.dataSource.updateMascota(mascota.id,
                    nombre,
                    nomci,
                    tipo_pelo,
                    clase,
                    amor,
                    fot,enlace)
            }

            Snackbar.make(root_fragment_item,
                "HAZ CLICK EN UN CAMPO PARA MODIFICARLO",
                Snackbar.LENGTH_LONG).show()

            //myview.nombre.setBackgroundColor(resources.getColor(R.color.teal_200))
        }
    }


    private fun exit(myview: View) {
        myview.btnsalir.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }


    private fun borrar(myview: View, mascota: Mascota) {
        myview.btnBorrar.setOnClickListener {
            DBHelperApplication.dataSource.delMascota(mascota.id)
            exit(myview)
        }

    }


    private fun setData(myview: View, mascota: Mascota) {
        myview.nombre.setText(mascota.nombre, TextView.BufferType.EDITABLE)
        myview.nomCient.setText(mascota.nombre_cientifico, TextView.BufferType.EDITABLE)
        myview.tipoPelo.setText(mascota.tipo_pelaje, TextView.BufferType.EDITABLE)
        myview.clase.setText(mascota.clase, TextView.BufferType.EDITABLE)
        myview.amorosidad.setText(mascota.amorosidad, TextView.BufferType.EDITABLE)
        myview.enlace.setText(mascota.enlace, TextView.BufferType.EDITABLE)
        myview.foto.setImageBitmap(mascota.foto)

        val starOff = android.R.drawable.btn_star_big_off
        val starOn = android.R.drawable.btn_star_big_on
        if (mascota.favorito == 0) {
            myview.favorito.setImageResource(starOff)
        } else if (mascota.favorito == 1) {
            myview.favorito.setImageResource(starOn)
        }

    }


    private fun pulsaFav(myview: View, mascota: Mascota) {
        val StarOn = android.R.drawable.btn_star_big_on
        val StarOF = android.R.drawable.btn_star_big_off
        myview.favorito.setOnClickListener {
            if (mascota.favorito == 0) {
                myview.favorito.setImageResource(StarOn)

                DBHelperApplication.dataSource.updateMascota(mascota.id, favorito = 1)

            } else {
                myview.favorito.setImageResource(StarOF)
                DBHelperApplication.dataSource.updateMascota(mascota.id, favorito = 0)
            }

        }
    }


}