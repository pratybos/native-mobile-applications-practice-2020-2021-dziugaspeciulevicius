package com.dziugaspeciulevicius.nativeappstasks.Repositories

import com.dziugaspeciulevicius.nativeappstasks.Models.Item
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

object ShoppingCartRepository {
    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser

    // shopping cart repo functions
    fun addToCart(item: Item): Task<Void> {
        // document is an item in a collection
        // users is collection name
        // !! = not null
        val document = db.collection("users")
            .document(user!!.uid)
            .collection("shopping_cart")
            .document()

        // setting item to the database
        return document.set(item)
    }

    // we need a list from a collection in a database
    fun getShoppingCart():CollectionReference {
        return db.collection("users/${user!!.uid}/shopping_cart")
    }

    fun removeItemFromShoppingCart(item: Item): Task<Void>{
        val document = db.collection("users/${user!!.uid}/shopping_cart").document(item.name)
        return document.delete()
    }
}