package com.dziugaspeciulevicius.to_do.ViewModels

import androidx.lifecycle.ViewModel
import com.dziugaspeciulevicius.to_do.Repositories.ItemRepository

class ItemViewModel : ViewModel() {
    // get updated list
    fun getItems() = ItemRepository.getItems()
}