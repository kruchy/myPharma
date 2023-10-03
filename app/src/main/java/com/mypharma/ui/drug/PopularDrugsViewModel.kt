package com.mypharma.ui.drug

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.j256.ormlite.dao.Dao
import com.mypharma.model.Drug
import com.mypharma.model.DrugView
import kotlinx.coroutines.launch

class PopularDrugsViewModel(private val drugDao: Dao<Drug, Long>) : ViewModel() {
    private val _popularDrugs = MutableLiveData<List<DrugView>>()
    val popularDrugs: LiveData<List<DrugView>> = _popularDrugs

    init {
        fetchAllDrugs()
    }

    private fun fetchAllDrugs() = viewModelScope.launch {
        _popularDrugs.postValue(getAllDrugs())
    }

    fun getDrugById(id: Long): Drug? {
        return drugDao.queryForId(id)
    }

    private fun getAllDrugs(): MutableList<DrugView> {
        return drugDao.queryForAll().map {
            DrugView(it.id, it.popularName, it.entityResponsible, it.substance)
        }.toMutableList()
    }

}