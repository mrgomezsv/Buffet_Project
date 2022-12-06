package com.buffetapp.pro.home.PackageBuffet2022

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buffetapp.pro.R
import com.buffetapp.pro.WhatsAppActivity
import com.buffetapp.pro.home.PackageBuffet2022.Adapter.Model.Repository.BuffetAdapter
import com.buffetapp.pro.home.PackageBuffet2022.Adapter.Model.Repository.BuffetViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BuffetNavidenoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private lateinit var viewModel: ViewModel
private lateinit var buffetRecyclerView: RecyclerView
lateinit var adapter: BuffetAdapter

class BuffetNavidenoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buffet_navideno, container, false)
    }
    /**********************************************************************/
    //Declaramos el recyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buffetRecyclerView = view.findViewById(R.id.buffet_recyclerview)
        buffetRecyclerView.layoutManager = LinearLayoutManager(context)
        buffetRecyclerView.setHasFixedSize(true)
        adapter = BuffetAdapter()
        buffetRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : BuffetAdapter.onitemClickListener{
            override fun onItemClick(position: Int) {

                /*val intent = Intent(getActivity(), WhatsAppActivity::class.java)
                startActivity(intent)*/
                //Toast.makeText(this@BuffetNavidenoFragment, "Tu Clickeaste en un item. $position",Toast.LENGTH_SHORT).show()

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=+50378736282&text=Hola,%20necesito%20informaci%C3%B3n%20de%20buffetproevent.com%20me%20comunico%20desde%20Buffet%20App"))
                startActivity(intent)
            }

        })



        viewModel =  ViewModelProvider(this).get(BuffetViewModel::class.java)

        (viewModel as BuffetViewModel).allBuffet.observe(viewLifecycleOwner, Observer {
            adapter.updateBuffetList(it)
        })
    }

    

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BuffetNavidenoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance():BuffetNavidenoFragment = BuffetNavidenoFragment()
    }

    private fun getUserData(){

    }
}