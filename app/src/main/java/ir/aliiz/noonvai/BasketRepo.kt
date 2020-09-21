package ir.aliiz.noonvai

interface BasketRepo {
    suspend fun addBasket(items: List<BasketBread>)
    suspend fun getBaskets(): List<BasketWithItems>
}