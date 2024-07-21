package com.example.wishlistapp.Data

import kotlinx.coroutines.flow.Flow

class WishRepository(private val wishDao : WishDao) {

    suspend fun addWish(wish : Wish)
    {
        wishDao.addWish(wish)
    }

    fun getWishes(): Flow<List<Wish>> = wishDao.getAllWishes()

    fun getaWishbyId(id:Long): Flow<Wish>
    {
        return wishDao.getAWishbyId(id)
    }

    suspend fun upDateWish(wish : Wish)
    {
        wishDao.updateWish(wish)
    }

    suspend fun deleteWish(wish : Wish)
    {
        wishDao.deleteWish(wish)
    }

}