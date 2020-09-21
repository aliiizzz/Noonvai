package ir.aliiz.noonvai

import androidx.room.*
import java.util.*

@Dao
interface NoonvaDao {
    @Query("select * from bread")
    fun getItems(): List<Bread>
    @Query("select * from bread where id = :id limit 1")
    fun get(id: Int): Bread

    @Insert
    fun add(bread: Bread)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun edit(bread: Bread)

    @Query("delete from bread where id = :id")
    fun delete(id: Int)
}

@Dao
abstract class BasketDao {
    @Insert(entity = Basket::class)
    abstract fun add(basket: Basket): Long

    @Insert
    abstract fun addBasketItem(item: BasketItem)

    @Transaction
    open fun addBasket(items: List<BasketBread>) {
        val id = add(Basket(0, System.currentTimeMillis()))
        items.forEach {
            addBasketItem(BasketItem(0, id.toInt(), it.title, it.price, it.count))
        }
    }
}

@Dao
interface GetBasketDao {
    @Transaction
    @Query("select * from basket")
    fun getBasketWithItems(): List<BasketWithItems>
}