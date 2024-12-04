package com.example.money_manager_app.selecticon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.money_manager_app.base.BaseViewModel
import com.example.money_manager_app.data.model.CategoryData
import com.example.money_manager_app.di.AppDispatchers
import com.example.money_manager_app.di.Dispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : BaseViewModel() {

    private val _categories = MutableLiveData<List<CategoryData.Category>>()
    val categories: LiveData<List<CategoryData.Category>> = _categories

    fun setCategory(category: List<CategoryData.Category>) {
        _categories.value = category
    }
}