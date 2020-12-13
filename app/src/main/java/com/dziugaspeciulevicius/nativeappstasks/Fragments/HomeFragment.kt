package com.dziugaspeciulevicius.nativeappstasks.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.dziugaspeciulevicius.nativeappstasks.Adapters.ItemListAdapter
import com.dziugaspeciulevicius.nativeappstasks.Models.Item
import com.dziugaspeciulevicius.nativeappstasks.R
import com.dziugaspeciulevicius.nativeappstasks.ViewModels.ItemViewModel
import com.dziugaspeciulevicius.nativeappstasks.ViewModels.ShoppingCartViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(R.layout.fragment_home), ItemListAdapter.Interaction {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var itemViewModel: ItemViewModel
    private lateinit var shoppingCartViewModel: ShoppingCartViewModel
    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)

        shoppingCartViewModel = ViewModelProvider(this).get(ShoppingCartViewModel::class.java)

        val itemListAdapter: ItemListAdapter by lazy { ItemListAdapter(this) }
        // populated list with items


        shoppingCartViewModel.addToCart(Item(Random().nextLong(),"Women Jeans", 29.99, "https://image.shutterstock.com/image-photo/3-shot-woman-blue-jeans-260nw-690960433.jpg"))

        if(user != null) {
            // when something changes in database, we update our UI
            shoppingCartViewModel.getShoppingCart().observe( viewLifecycleOwner, Observer {
                itemListAdapter.submitList(it)  // it = return list of items
            })
        }
        //        itemListAdapter.submitList(itemViewModel.getItems().value)

        item_recycleView.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(context)
            // set the custom adapter to the RecyclerView
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun click_item(item: Item) {
        shoppingCartViewModel.removeItemFromShoppingCart(item)
        Toast.makeText(context, "CLICKED $item", Toast.LENGTH_LONG).show()
    }
}