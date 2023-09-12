package com.example.shared.ui.helpers

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity

import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class PermissionManager {
    companion object {
        val cameraCode = 123
        val galeriaCode = 345
        val internetCode=678

        fun onAlertDialog(activity: Activity) {
            //Instantiate builder variable
            val builder = AlertDialog.Builder(activity)
            // set title
            builder.setTitle("Permisos denegados")

            //set content area
            builder.setMessage("blbalblablablalbalba")

            //set neutral button
            builder.setNeutralButton("OK") { dialog, id ->
                // User Click on reminder me latter
            }
            builder.show()
        }

        fun  checkForPermission(activity: Activity, permission: String, requestCode: Int): Int {
            // Comprobamos si se ha concedido permisos
            var has_permission = 0

            val permissionResult = ContextCompat.checkSelfPermission(activity, permission) // para preguntar si tenemos el permiso
            if (permissionResult != PackageManager.PERMISSION_GRANTED) { // comprobamos si el permiso esta en garantizado
                // No se han concedido permisos y le preguntamos si deberíamos mostrar el dialogo (o la UI) pidiendo los permisos
                if(ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)){
                    // el usuario NO ha marcado la opción de "No volver a preguntar"
                    // pedirle que nos de permisos
                    ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)

                } else {
                    // el usuario SI ha marcado la opción de "No volver a preguntar"
                    // decirle que necesita activar los permisos porque ha seleccionado la opcion de "no me vuelvas a preguntar"
                    //onAlertDialog(activity)
                    has_permission = 1
                }

            } else {
                // Si se han concedido permisos
                has_permission = 2
                //activity.startActivity(intent)
            }

            return has_permission
        }


    }





}