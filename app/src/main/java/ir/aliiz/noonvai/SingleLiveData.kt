package ir.aliiz.noonvai

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveData<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)

    override fun setValue(value: T) {
        pending.set(true)
        super.setValue(value)
    }


    override fun postValue(value: T) {
        pending.set(true)
        super.postValue(value)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

    override fun getValue(): T? {
        return super.getValue()
    }
}