package com.dziugaspeciulevicius.nativeappstasks.ViewModels

import androidx.lifecycle.ViewModel
import com.dziugaspeciulevicius.nativeappstasks.Repositories.ItemRepository

class ItemViewModel : ViewModel() {
    fun getItems() = ItemRepository.getItems()
}