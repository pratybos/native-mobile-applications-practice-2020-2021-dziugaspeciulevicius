package com.dziugaspeciulevicius.to_do.Repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dziugaspeciulevicius.to_do.Models.Item

object ItemRepository {
    val db = mutableListOf<Item>()
    val mutableList = MutableLiveData<List<Item>>()

    fun getItems(): LiveData<List<Item>> {
        db.clear()
//        loadFromAnySource()
        mutableList.value = db
        return mutableList
    }

//    fun loadFromAnySource() {
//        db.add(Item("Take out trash1", "take out trash in detail1"))
//        db.add(Item("Take out trash2", "take out trash in detail2"))
//        db.add(Item("Take out trash3", "take out trash in detail3 take out trash in detail3take out trash in detail3take out trash in detail3"))
//    }

}