package com.example.wishlistapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wishlistapp.Data.Wish
import com.example.wishlistapp.Data.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishViewModel(private val wishRepository: WishRepository = Graph.wishRepository) : ViewModel() {

    var WishTitleState by mutableStateOf("")
    var WishDescriptionState by mutableStateOf("")

    fun onWishTitleChange(newString : String)
    {
        WishTitleState = newString
    }

    fun onWishDescriptionChange(newString : String)
    {
        WishDescriptionState = newString
    }

    lateinit var getAllWishes : Flow<List<Wish>>

    init {
        viewModelScope.launch {
            getAllWishes = wishRepository.getWishes()
        }
    }

    fun addWish(wish:Wish)
    {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.addWish(wish = wish)
        }
    }

    fun getWishbyId(id:Long) : Flow<Wish>
    {
        return wishRepository.getaWishbyId(id)
    }

    fun updateWish(wish:Wish)
    {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.upDateWish(wish = wish)
        }
    }

    fun deleteWish(wish : Wish)
    {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.deleteWish(wish = wish)
        }
    }

}