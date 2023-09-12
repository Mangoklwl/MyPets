package com.example.shared.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.example.shared.R

import com.example.shared.ui.fragments.InsertmascotaFragment
import com.example.shared.ui.fragments.OrdenarAlfaFragment
import com.example.shared.ui.fragments.OrdenarAmoFragment
import com.example.shared.ui.fragments.OrdenarFavFragment
import com.example.shared.ui.helpers.Pets_adapter_grid


import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configView()
        setListener()
        //setFragments(InsertmascotaFragment())

    }

    private fun setFragments(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        //val fragment = SettingsFragment()
        transaction.replace(R.id.myViewPager2, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    private fun setListener() {
        // Control sobre la opción seleccionada.
        myNavigationView.setNavigationItemSelectedListener {
            it.isChecked = true
            when (it.itemId) {
                R.id.item11 -> {
                    // Se carga el fragment en el ViewPager2.
                    setFragments(InsertmascotaFragment())
                    // Se cierra el Drawer Layout.
                    myDrawerLayout.close()
                    true
                }
                R.id.item21 -> {
                    setFragments(OrdenarAlfaFragment())
                    myDrawerLayout.close()
                    true
                }
                R.id.item22 -> {
                    setFragments(OrdenarAmoFragment())
                    myDrawerLayout.close()
                    true
                }

                R.id.item23 -> {
                    setFragments(OrdenarFavFragment())
                    myDrawerLayout.close()

//                    Toast.makeText(
//                        applicationContext,
//                        getString(R.string.txtMenu22),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    myDrawerLayout.close()

                    true
                }

                R.id.item31->{
                    setFragments(InsertmascotaFragment())
                    myDrawerLayout.close()
                    true
                }
                R.id.item32->{
                    setFragments(InsertmascotaFragment())
                    myDrawerLayout.close()
                    true
                }


                else -> false
            }
        }
    }
    private fun configView() {
        setSupportActionBar(myToolBar) //Cargar toolbar la barra de arriba

        val toggle = ActionBarDrawerToggle(this,
            myDrawerLayout,
            myToolBar,
            R.string.txt_open,
            R.string.txt_close
        )
        myDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }
}