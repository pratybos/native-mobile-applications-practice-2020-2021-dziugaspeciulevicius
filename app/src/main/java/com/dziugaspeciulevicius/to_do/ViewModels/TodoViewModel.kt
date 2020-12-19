package com.dziugaspeciulevicius.to_do.ViewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dziugaspeciulevicius.to_do.Models.Item
import com.dziugaspeciulevicius.to_do.Repositories.TodoRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference

const val TAG = "VIEW_MODEL_TAG_TODO"


class TodoViewModel: ViewModel() {

    private val repository = TodoRepository
    private val todoList: MutableLiveData<List<Item>> = MutableLiveData()

    fun addTask(item: Item) {
        repository.addTask(item).addOnSuccessListener {
            Log.w(TAG, "ITEM ADDED!")
        }.addOnFailureListener{
            Log.w(TAG, "SOME PROBLEMS: $it")
        }
    }

    fun getTasks(): LiveData<List<Item>> {
        repository.getTasks().addSnapshotListener{ value, error ->
            val tempList: MutableList<Item> = mutableListOf()
            value!!.forEach { document ->
                val item = document.toObject(Item::class.java)
                tempList.add(item)
            }
            todoList.value = tempList
        }

        return todoList
    }

    fun removeItemFromTodoList(item: Item){
        repository.removeItemFromTodoList(item).addOnFailureListener{
            Log.w(TAG, "SOME PROBLEMS: $it")
        }
    }
}