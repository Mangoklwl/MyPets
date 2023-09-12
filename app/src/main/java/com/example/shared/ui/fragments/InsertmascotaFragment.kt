package com.example.shared.ui.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.shared.R
import com.example.shared.ui.helpers.PermissionManager
import kotlinx.android.synthetic.main.fragment_insertar.*
import kotlinx.android.synthetic.main.fragment_insertar.view.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.shared.ui.database.DBHelperApplication
import com.example.shared.ui.database.Mascota
import com.google.android.material.snackbar.Snackbar


class InsertmascotaFragment() : Fragment() {
    lateinit var myView: View;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(
            R.layout.fragment_insertar,
            container,
            false
        )
        anyadirMascotaListener(myView)
        sacarFotoListener(myView)
        initSpinnerClase(myView)
        galerialistener()
        return myView
    }

    private fun initSpinnerClase(view:View) {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            view.context, R.array.amorosidad_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            view.spnamorosidad.adapter = adapter
        }
    }

    private fun sacarFotoListener(view: View) {
        view.imgButton.setOnClickListener {
            val intent = Intent(Intent(MediaStore.ACTION_IMAGE_CAPTURE))

            // PermissionManager.checkForPermission(this, Manifest.permission.CAMERA, PermissionManager.cameraCode, intent)

            if (PermissionManager.checkForPermission(
                    requireActivity(),
                    Manifest.permission.CAMERA,
                    PermissionManager.cameraCode
                ) == 2
            ) {
                System.out.println("TIENE PERMISOS")
                if (intent.resolveActivity(view.context.packageManager) != null) {
                    startActivityForResult(intent, PermissionManager.cameraCode)
                }
            } else if (PermissionManager.checkForPermission(
                    requireActivity(),
                    Manifest.permission.CAMERA,
                    PermissionManager.cameraCode
                ) == 1
            ) {
                PermissionManager.onAlertDialog(requireActivity())
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {//hacer algo cuando obtienes el resultado de una acitvidad
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PermissionManager.cameraCode && resultCode == AppCompatActivity.RESULT_OK) {
            val thumbnail: Bitmap = data?.getParcelableExtra("data")!!
            myView.colocarFoto.setImageBitmap(thumbnail)

        }
        else if(requestCode == PermissionManager.galeriaCode && resultCode == AppCompatActivity.RESULT_OK){
            val imageURI = data?.data
            myView.colocarFoto.setImageURI(imageURI)
        }
    }


    private fun galerialistener() {
        myView.btngaleria.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            if (PermissionManager.checkForPermission(
                    requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    PermissionManager.galeriaCode
                ) == 2
            ) {
                if (intent.resolveActivity(myView.context.packageManager) != null) {
                    startActivityForResult(intent, PermissionManager.galeriaCode)
                }
            } else if (PermissionManager.checkForPermission(
                    requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    PermissionManager.galeriaCode
                ) == 1
            ) {

                PermissionManager.onAlertDialog(requireActivity())
            }
        }

    }

    override fun onRequestPermissionsResult(//se lanza cuando da el resultado del permiso
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PermissionManager.cameraCode -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                    if (intent.resolveActivity(myView.context.packageManager) != null) {
                        startActivityForResult(intent, PermissionManager.cameraCode)
                    }

                }
            }
            PermissionManager.galeriaCode -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val intent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                    if (intent.resolveActivity(myView.context.packageManager) != null) {
                        startActivityForResult(intent, PermissionManager.galeriaCode)//lanza la actividad esperando un resultado ( se va al onActivityResult)
                    }
                }
            }

        }
    }


    private fun anyadirMascotaListener(view:View) {
        view.btnañadir.setOnClickListener {

            // Le pido a la base de datos que me saque la mascota con el nombre 'view.txtnombre.text.toString()'
            val existe: Mascota? = DBHelperApplication.dataSource.getMascotaByName(view.txtnombre.text.toString())

            // Si lo que me ha devuelto la peticion es 'null' es que no existe
            if(existe != null) { // si es distinto de 'null' quiere decir que existe una mascota con ese nombre

                // Como ya existe voy a preguntarle si quiere añadirlo igualmente con un alertDialog
                val builder = AlertDialog.Builder(requireActivity())
                // Coloco el titulo del dialogo
                builder.setTitle("Ya existe una mascota con ese nombre")

                // Establezco el mensaje
                builder.setMessage("Quieres agregarla igualmente?")
                // Coloco el boton 'positive' que si el usuario le hace click agregará la mascota
                builder.setPositiveButton("Agregar") {dialog, id ->
                    DBHelperApplication.dataSource.addMascota( view.txtnombre.text.toString(),
                        view.txtnombrecientifico.text.toString(),
                        view.txttipopelo.text.toString(),
                        view.txtclase.text.toString(),
                        view.spnamorosidad.selectedItem.toString(),
                        (view.colocarFoto.drawable as BitmapDrawable).bitmap,
                        view.txtenlace.text.toString())

                    view.txtnombre.text.clear()
                    view.txtnombrecientifico.text.clear()
                    view.txttipopelo.text.clear()
                    view.txtclase.text.clear()
                    view.txtenlace.text.clear()

                    Snackbar.make(root_fragment_insertar, "Añadido correctamente", Snackbar.LENGTH_LONG).show()

                }

                //Coloco el boton 'negative' que si el usuario hace click NO añadirá la mascota
                builder.setNegativeButton("Cancelar") { dialog, id ->
                    // Le muestro al usuario un snackbar confirmando que no lo ha añadido
                    Snackbar.make(root_fragment_insertar, "La mascota no se ha añadido", Snackbar.LENGTH_LONG).show()
                }
                builder.show()
            } else { // Esto se ejecuta si no encuentra ninguna mascota con el nombre 'view.txtnombre.text.toString()'

                DBHelperApplication.dataSource.addMascota( view.txtnombre.text.toString(),
                    view.txtnombrecientifico.text.toString(),
                    view.txttipopelo.text.toString(),
                    view.txtclase.text.toString(),
                    view.spnamorosidad.selectedItem.toString(),
                    (view.colocarFoto.drawable as BitmapDrawable).bitmap,
                    view.txtenlace.text.toString())

                view.txtnombre.text.clear()
                view.txtnombrecientifico.text.clear()
                view.txttipopelo.text.clear()
                view.txtclase.text.clear()
                view.txtenlace.text.clear()

                Snackbar.make(root_fragment_insertar, "Añadido correctamente", Snackbar.LENGTH_LONG).show()

            }






        }

    }

}