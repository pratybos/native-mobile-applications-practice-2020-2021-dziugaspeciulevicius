package com.dziugaspeciulevicius.nativeappstasks.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dziugaspeciulevicius.nativeappstasks.Models.Item
import com.dziugaspeciulevicius.nativeappstasks.Repositories.ShoppingCartRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference

const val TAG = "SHOPPINGCARTVIEWMODEL"

class ShoppingCartViewModel :ViewModel() {

    private val repository = ShoppingCartRepository
    private val shoppingCart: MutableLiveData<List<Item>> = MutableLiveData()

    fun addToCart(item: Item) {
        repository.addToCart(item).addOnSuccessListener {
            Log.w(TAG, "ITEM ADDED")
        }.addOnFailureListener{
            Log.w(TAG, "ERROR ADDING TO CART: $it")
        }

    }

    fun getShoppingCart(): LiveData<List<Item>> {
        repository.getShoppingCart().addSnapshotListener{ value, error ->
            val tempList: MutableList<Item> = mutableListOf()
            value!!.forEach { document ->
                val item = document.toObject(Item::class.java)
                tempList.add(item)
            }
            shoppingCart.value = tempList
        }
        return shoppingCart
    }

    fun removeItemFromShoppingCart(item: Item){
        repository.removeItemFromShoppingCart(item).addOnFailureListener{
            Log.w(TAG, "ERROR REMOVING FROM  CART: $it")
        }
    }
}