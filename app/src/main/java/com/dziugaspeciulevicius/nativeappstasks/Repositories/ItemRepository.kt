package com.dziugaspeciulevicius.nativeappstasks.Repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dziugaspeciulevicius.nativeappstasks.Models.Item

object ItemRepository {
    var db = mutableListOf<Item>()
    val mutableList = MutableLiveData<List<Item>>()

    fun getItems(): LiveData<List<Item>> {
        db.clear()
//        loadFromAnySource()
        mutableList.value = db
        return mutableList
    }

//    fun loadFromAnySource() {
//        db.add(Item("Women Jeans", 29.99, "https://image.shutterstock.com/image-photo/3-shot-woman-blue-jeans-260nw-690960433.jpg"))
//        db.add(Item("White Shirt", 19.99, "https://www.pakaitashop.lt/assets/files/2019/01/IMG_3864_resize.jpg"))
//        db.add(Item("White sneakers", 59.99, "https://gearmoose.com/wp-content/uploads/2017/04/best-all-white-sneakers-for-men.jpg"))
//        db.add(Item("Socks", 3.99, "https://myer-media.com.au/wcsstore/MyerCatalogAssetStore/images/25/101/1334/400/3/678650950/678650950_1_720x928.jpg"))
//    }
}