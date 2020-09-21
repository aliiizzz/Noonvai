package ir.aliiz.noonvai

class RoomBreadRepo(private val noonvaDao: NoonvaDao) : BreadRepo {
    override suspend fun getItems(): List<Bread> = noonvaDao.getItems()

    override suspend fun add(bread: Bread) {
        noonvaDao.add(bread)
    }

    override suspend fun remove(id: Int) {
    }

    override suspend fun update(bread: Bread) {
        noonvaDao.edit(bread)
    }

    override suspend fun getItem(id: Int): Bread = noonvaDao.get(id)

    override suspend fun delete(id: Int) = noonvaDao.delete(id)
}