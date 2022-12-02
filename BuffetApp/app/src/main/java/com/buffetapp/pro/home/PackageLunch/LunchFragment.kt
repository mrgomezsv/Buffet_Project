package com.buffetapp.pro.home.PackageLunch

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
import com.buffetapp.pro.home.PackageLunch.Adapter.LunchAdapter
import com.buffetapp.pro.home.PackageLunch.Model.LunchViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LunchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private lateinit var viewModel: ViewModel
private lateinit var lunchRecyclerView: RecyclerView
lateinit var adapter: LunchAdapter

class LunchFragment : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)


        }
        //createData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lunch, container, false)
    }
    /**********************************************************************/
    //Declaramos el recyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lunchRecyclerView = view.findViewById(R.id.lunch_recyclerview)
        lunchRecyclerView.layoutManager = LinearLayoutManager(context)
        lunchRecyclerView.setHasFixedSize(true)
        adapter = LunchAdapter()
        lunchRecyclerView.adapter = adapter

        viewModel =  ViewModelProvider(this).get(LunchViewModel::class.java)

        (viewModel as LunchViewModel).allLunchs.observe(viewLifecycleOwner, Observer {
            adapter.updateLunchList(it)
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance():LunchFragment = LunchFragment()
    }
}