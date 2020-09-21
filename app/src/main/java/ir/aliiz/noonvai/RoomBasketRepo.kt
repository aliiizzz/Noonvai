package ir.aliiz.noonvai

class RoomBasketRepo(
    private val basketDao: BasketDao,
    private val getBasketDao: GetBasketDao
) : BasketRepo {
    override suspend fun addBasket(items: List<BasketBread>) {
        basketDao.addBasket(items)
    }

    override suspend fun getBaskets(): List<BasketWithItems> = getBasketDao.getBasketWithItems()
}