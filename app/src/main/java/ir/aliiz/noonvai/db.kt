package ir.aliiz.noonvai

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Bread::class, Basket::class, BasketItem::class], version = 1)
abstract class NoonvaDatabse: RoomDatabase() {
    abstract fun noonvaDao(): NoonvaDao
    abstract fun basketDao(): BasketDao
    abstract fun getBasketDao(): GetBasketDao
}