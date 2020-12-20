package com.dziugaspeciulevicius.to_do.Fragments

import DeleteDialogFragment
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dziugaspeciulevicius.to_do.Adapters.ItemListAdapter
import com.dziugaspeciulevicius.to_do.Models.Item
import com.dziugaspeciulevicius.to_do.R
import com.dziugaspeciulevicius.to_do.ViewModels.ItemViewModel
import com.dziugaspeciulevicius.to_do.ViewModels.TodoViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_input_box.*
import kotlinx.android.synthetic.main.fragment_input_box.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*


class HomeFragment : Fragment(), ItemListAdapter.Interaction {

    // we initialize this later
    private lateinit var itemViewModel: ItemViewModel
    private lateinit var todoViewModel: TodoViewModel
    private val user = FirebaseAuth.getInstance().currentUser
//    private var mAuth: FirebaseAuth? = null
//    private val mAuth = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // on view created we get view reference
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        todoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)
        val floatingActionButton: FloatingActionButton? = activity?.findViewById(R.id.floating_create_button)
        val deleteTask: ImageButton? = activity?.findViewById(R.id.delete_task)

        // fill recycle view by lazy loading to save memory
        val itemListAdapter: ItemListAdapter by lazy { ItemListAdapter(this) }

        // display to-do list
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            todoViewModel.getTasks().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                itemListAdapter.submitList(it)  // it return list of items
            })
        }

        item_recycleView.apply {
            // set a LinearLayoutManager to handle Android recyclerView behavior
            layoutManager = LinearLayoutManager(this@HomeFragment.context)
            // set custom adapter to the RecyclerView
            adapter = itemListAdapter
        }

        // add task button
        floatingActionButton?.setOnClickListener {
            addTask()
        }

//        // delete task button
//        deleteTask?.setOnClickListener {
////            deleteTask()
//            Toast.makeText(context, "Delete clicked", Toast.LENGTH_SHORT).show()
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @Override
    fun addTask() {
        // inflate dialog with custom view
        val mDialogView = LayoutInflater.from(this@HomeFragment.context)
            .inflate(R.layout.fragment_input_box, null)

        // alert dialog builder
        val mBuilder = AlertDialog.Builder(context).setView(mDialogView)
        // show dialog
        val  mAlertDialog = mBuilder.show()


        // get dialog buttons and fields
        val saveButton: Button? = activity?.findViewById(R.id.buttonSave)
        val cancelButton: Button? = activity?.findViewById(R.id.buttonCancel)
        val taskField: EditText? = activity?.findViewById(R.id.task)
        val additionalInfoField: EditText? = activity?.findViewById(R.id.additional_information)


        // button save to-do
        mAlertDialog.buttonSave.setOnClickListener{
            val name = mDialogView.task.text.toString()
            val description = mDialogView.additional_information.text.toString()

            if (name.isEmpty() && description.isEmpty() || name.isEmpty() || description.isEmpty()){
                Toast.makeText(context,"Fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                todoViewModel.addTask(Item(Random().nextLong(), name, description))
                taskField?.text?.clear()
                additionalInfoField?.text?.clear()
                Toast.makeText(context, "Task added.", Toast.LENGTH_SHORT).show()
            }
            // close dialog
            mAlertDialog.dismiss()
        }

        // button cancel to-do
        mAlertDialog.buttonCancel.setOnClickListener{
            // dismiss dialog
            mAlertDialog.dismiss()
        }
    }

//    @Override
//    fun deleteTask(item: Item) {
//        todoViewModel.removeItemFromTodoList(item)
//    }

    override fun click_item(item: Item) {
        var customDialog = DeleteDialogFragment(todoViewModel, item)
        customDialog.show(activity?.supportFragmentManager!!, "customDialog")
    }
}

