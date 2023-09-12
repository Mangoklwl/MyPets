package com.example.shared.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.shared.R
import com.example.shared.ui.helpers.Pets_adapter_grid
import kotlinx.android.synthetic.main.fragments_alfabetico.*
import kotlinx.android.synthetic.main.fragments_alfabetico.view.*


class OrdenarAlfaFragment : Fragment() {
    private lateinit var adapter: Pets_adapter_grid
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragments_alfabetico,
            container,
            false
        )
        setData(view)
        itemClickedListener(view)

        return view
    }


    private fun itemClickedListener(myview:View) {
        myview.gridalfabetico.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, l ->
                val gridChild:ViewGroup = myview.gridalfabetico.getChildAt(position) as ViewGroup
                val item: TextView = gridChild.getChildAt(3) as TextView
                Log.d("INFO", item.text.toString());

                val bundle = Bundle()
                bundle.putString("input_id", item.text.toString())
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val fragment = ItemMascotaFragment()
                fragment.arguments = bundle

                transaction.replace(R.id.myViewPager2, fragment)
                transaction.addToBackStack(null)
                transaction.commit()


            }

    }

    private fun setData(view: View) {

        adapter = Pets_adapter_grid(requireContext(), 0)
        view.gridalfabetico.adapter = adapter

    }


/*
    override fun onBackPressed() {
        // La clase Preferences se encarga de editar y guardar en la asignación.
        if (!et_name.text.isEmpty()){
            Application.preferences.name = et_name.text.toString()
        }
        super.onBackPressed()
    }*/
}