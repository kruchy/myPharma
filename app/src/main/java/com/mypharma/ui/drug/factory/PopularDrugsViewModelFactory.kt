package com.mypharma.ui.drug.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.j256.ormlite.dao.Dao
import com.mypharma.model.Drug
import com.mypharma.ui.drug.PopularDrugsViewModel

class PopularDrugsViewModelFactory(private val drugDao: Dao<Drug, Long>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PopularDrugsViewModel::class.java)) {
            return PopularDrugsViewModel(drugDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}