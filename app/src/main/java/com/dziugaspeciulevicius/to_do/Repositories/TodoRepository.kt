package com.dziugaspeciulevicius.to_do.Repositories

import com.dziugaspeciulevicius.to_do.Models.Item
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

object TodoRepository {
    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser

    fun addTask(item: Item): Task<Void> {
        val document = db.collection("users")
            .document(user!!.uid)   // if user is not null
            .collection("todoList")
            .document("${item.id}")
//            .document()

        // save to database
        return document.set(item)
    }

    fun getTasks(): CollectionReference {
        return db.collection("users/${user!!.uid}/todoList")
    }

    fun removeItemFromTodoList(item: Item):Task<Void>{
        val document = db.
        collection("users/${user!!.uid}/todoList").
        document("${item.id}")

        // delete document
        return document.delete()
    }
}