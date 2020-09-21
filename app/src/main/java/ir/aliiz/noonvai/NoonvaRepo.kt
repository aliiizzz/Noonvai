package ir.aliiz.noonvai

interface NoonvaRepo {
    suspend fun getItems(): List<Bread>
    suspend fun add(bread: Bread)
    suspend fun remove(id: Int)
    suspend fun update(bread: Bread)
    suspend fun getItem(id: Int): Bread
    suspend fun delete(id: Int)
}