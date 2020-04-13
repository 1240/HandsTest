package com.l24o.handstest.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.l24o.handstest.base.AdapterItemModel
import com.l24o.handstest.model.World
import com.l24o.handstest.presentation.adapter.AliveAdapterItem
import com.l24o.handstest.presentation.adapter.DeadAdapterItem
import com.l24o.handstest.presentation.adapter.ResultAdapterItem
import com.l24o.handstest.repo.WorldUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import kotlin.random.Random

class WorldViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val items: MutableLiveData<List<AdapterItemModel>> by lazy {
        MutableLiveData<List<AdapterItemModel>>().also { loadWorld() }
    }

    fun getWorld(): LiveData<List<AdapterItemModel>> = items

    fun onCreateLifeClick() {
        WorldUseCase.createLife(isAlive = Random.nextBoolean())
            .subscribe({
                loadWorld()
            }, {
                // Timber.e(it)
            })
            .addTo(compositeDisposable)
    }

    private fun loadWorld() {
        WorldUseCase.getWorld()
            .map { items ->
                items.mapIndexed { index, item ->
                    when (item) {
                        is World.Cell -> {
                            if (item.isAlive) AliveAdapterItem(index) else DeadAdapterItem(index)
                        }
                        is World.Result -> {
                            ResultAdapterItem(index)
                        }
                    }
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                items.postValue(it)
            }, {
                // Timber.e(it)
            })
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}