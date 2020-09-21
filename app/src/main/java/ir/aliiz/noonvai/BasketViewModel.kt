package ir.aliiz.noonvai

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BasketViewModel(
    private val noonvaRepo: NoonvaRepo,
    private val basketRepo: BasketRepo,
    private val dispatchers: Dispatchers
) : ViewModel() {
    private val _items: MutableLiveData<List<BasketBread>> = MutableLiveData()
    val basketItems: LiveData<List<BasketBread>> = _items

    private val _basketSubmit: MutableLiveData<Loadable<Unit>> = MutableLiveData()
    val basketSubmit: LiveData<Loadable<Unit>> = _basketSubmit

    private val _allBaskets: MutableLiveData<Loadable<List<BasketWithItems>>> = MutableLiveData()
    val allBaskets: LiveData<Loadable<List<BasketWithItems>>> = _allBaskets

    fun getAllBaskets() {
        viewModelScope.launch {
            _allBaskets.value = Loadable.Loading
            kotlin.runCatching {
                withContext(dispatchers.io) {
                    basketRepo.getBaskets()
                }
            }.onSuccess {
                _allBaskets.value = Loadable.Loaded(it)
            }.onFailure {
                _allBaskets.value = Loadable.Failed(it)
            }
        }
    }
    fun plus(id: Int) {
        val item = _items.value?.firstOrNull { it.id ==  id }
        if (item == null) {
            viewModelScope.launch {
                kotlin.runCatching {
                    withContext(dispatchers.io) {
                        noonvaRepo.getItem(id)
                    }
                }.onSuccess { bread ->
                    _items.value = (_items.value?.toMutableList() ?: mutableListOf()).apply {
                        add(bread.let {
                            BasketBread(it.id, it.title, it.price, 1)
                        })
                    }
                }.onFailure {
                    it.printStackTrace()
                }
            }
        } else {
            val copy = item.copy(count = item.count + 1)
            _items.value = _items.value?.toMutableList()?.apply {
                val index = indexOf(item)
                remove(item)
                add(index, copy)
            }
        }
    }

    fun minus(id: Int) {
        val item = _items.value?.firstOrNull { it.id ==  id } ?: return
        val count = item.count
        if (count < 2) {
            _items.value = _items.value?.toMutableList()?.apply {
                remove(item)
            }
            return
        }
        val copy = item.copy(count = item.count - 1)
        _items.value = _items.value?.toMutableList()?.apply {
            val index = indexOf(item)
            remove(item)
            add(index, copy)
        }
    }

    fun clearBasket() {
        val value = _items.value ?: return
        _items.value = listOf()
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(dispatchers.io) {
                    basketRepo.addBasket(value)
                }
            }.onSuccess {
                _basketSubmit.value = Loadable.Loaded(Unit)
            }.onFailure {
                _basketSubmit.value = Loadable.Failed(it)
            }
        }
    }
}

data class BasketBread(
    val id: Int,
    val title: String,
    val price: Int,
    val count: Int = 0
)