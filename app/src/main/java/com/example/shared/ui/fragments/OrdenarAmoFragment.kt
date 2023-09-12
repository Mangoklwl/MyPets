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
import kotlinx.android.synthetic.main.fragment_amor.view.*
import kotlinx.android.synthetic.main.fragments_alfabetico.view.*

class OrdenarAmoFragment: Fragment() {
    private lateinit var adapter: Pets_adapter_grid
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_amor,
            container,
            false
        )
        setData(view)
        itemClickedListener(view)
        //setListener(view)
        return view
    }
    private fun setData(view: View) {

        adapter = Pets_adapter_grid(requireContext(), 1)
        view.gridamor.adapter = adapter

    }
    private fun itemClickedListener(myview:View) {
        myview.gridamor.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, l ->
                val gridChild:ViewGroup = myview.gridamor.getChildAt(position) as ViewGroup
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
}