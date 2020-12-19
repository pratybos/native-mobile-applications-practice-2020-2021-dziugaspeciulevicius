package com.dziugaspeciulevicius.to_do.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dziugaspeciulevicius.to_do.Adapters.ItemListAdapter
import com.dziugaspeciulevicius.to_do.R
import com.dziugaspeciulevicius.to_do.ViewModels.ItemViewModel
import com.dziugaspeciulevicius.to_do.ViewModels.TodoViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

//    private val recyclerView: RecyclerView? = null
//    private val floatingActionButton: FloatingActionButton? = null
//
//    private val reference: DatabaseReference? = null
//    private val mAuth: FirebaseAuth? = null
//    private val mUser: FirebaseUser? = null
//    private val onlineUserID: String? = null
//
//    private val loader: ProgressDialog? = null
//
//    private val key = ""
//    private val task: String? = null
//    private val description: String? = null

    // we initialize this later
    private lateinit var itemViewModel: ItemViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // on view created we get view reference
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        // fill recycle view by lazy loading to save memory
        val itemListAdapter: ItemListAdapter by lazy { ItemListAdapter() }
        itemListAdapter.submitList(itemViewModel.getItems().value)
        item_recycleView.apply {
            // set a LinearLayoutManager to handle Android recyclerView behavior
            layoutManager = LinearLayoutManager(this@HomeFragment.context)
            // set custom adapter to the RecyclerView
            adapter = itemListAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}