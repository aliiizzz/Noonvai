package ir.aliiz.noonvai

import androidx.room.*
import java.util.*

@Entity
data class Bread(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "price") val price: Int
)

@Entity(foreignKeys = [ForeignKey(entity = Basket::class, parentColumns = ["id"], childColumns = ["basketId"], onDelete = ForeignKey.CASCADE)])
data class BasketItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "basketId") val basketId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "count") val count: Int
)

@Entity
data class Basket(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "createdAt") val createdAt: Long
)

data class BasketWithItems(
    @Embedded val basket: Basket,
    @Relation(
        parentColumn = "id",
        entityColumn = "basketId",
        entity = BasketItem::class
    ) val items: List<BasketItem>
)