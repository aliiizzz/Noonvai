package ir.aliiz.noonvai

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BreadViewModel(
    private val breadRepo: BreadRepo,
    private val dispatchers: Dispatchers
) : ViewModel() {

    private val _items: MutableLiveData<Loadable<List<Bread>>> = MutableLiveData()
    val items: LiveData<Loadable<List<Bread>>> = _items

    private val _addBread: SingleLiveData<Loadable<Unit>> = SingleLiveData()
    val addBread: LiveData<Loadable<Unit>> = _addBread

    private val _editBread: SingleLiveData<Loadable<Unit>> = SingleLiveData()
    val editBread: LiveData<Loadable<Unit>> = _editBread

    private val _bread: MutableLiveData<Loadable<Bread>> = MutableLiveData()
    val bread: LiveData<Loadable<Bread>> = _bread
    fun checkItems() {
        if (_items.value == Loadable.Loading) return
        viewModelScope.launch {
            _items.value = Loadable.Loading
            kotlin.runCatching {
                withContext(dispatchers.io) {
                    breadRepo.getItems()
                }
            }.onSuccess {
                _items.value = Loadable.Loaded(it)
            }.onFailure {
                _items.value = Loadable.Failed(it)
            }
        }
    }

    fun add(title: String?, price: String?) {
        if (title == null || title.isEmpty()) return
        if (price == null || !price.matches(Regex("\\d+"))) return
        if (_addBread.value == Loadable.Loading) return
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(dispatchers.io) {
                    breadRepo.add(Bread(0, title.toString(), price = price.toString().toInt()))
                }
            }.onSuccess {
                _addBread.setValue(Loadable.Loaded(Unit))
            }.onFailure {
                _addBread.setValue(Loadable.Failed(it))
                it.printStackTrace()
            }
        }
    }

    fun edit(title: Editable?, price: Editable?, id: Int) {
        if (title == null || title.isEmpty()) return
        if (price == null || !price.matches(Regex("\\d+"))) return
        if (_editBread.value == Loadable.Loading) return
        viewModelScope.launch {
            kotlin.runCatching {
                breadRepo.update(Bread(id, title.toString(), price = price.toString().toInt()))
            }.onSuccess {
                _editBread.setValue(Loadable.Loaded(Unit))
            }.onFailure {
                _editBread.setValue(Loadable.Failed(it))
                it.printStackTrace()
            }
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(dispatchers.io) {
                    breadRepo.delete(id)
                    breadRepo.getItems()
                }
            }.onSuccess {
                _items.value = Loadable.Loaded(it)
            }.onFailure {
                _items.value = Loadable.Failed(it)
            }
        }
    }

    fun checkBread(id: Int) {
        if (id == -1) return
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(dispatchers.io) {
                    breadRepo.getItem(id)
                }
            }.onSuccess {
                _bread.value = Loadable.Loaded(it)
            }.onFailure {
                _bread.value = Loadable.Failed(it)
            }
        }
    }
}

sealed class Loadable<out T> {
    data class Loaded<T>(val data: T): Loadable<T>()
    data class Failed(val throwable: Throwable): Loadable<Nothing>()
    object Loading: Loadable<Nothing>()
}