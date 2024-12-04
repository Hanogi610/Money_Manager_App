package com.example.money_manager_app.fragment.wallet.add_budget

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.money_manager_app.base.BaseViewModel
import com.example.money_manager_app.data.model.CategoryData
import com.example.money_manager_app.data.model.entity.Budget
import com.example.money_manager_app.data.model.entity.BudgetCategoryCrossRef
import com.example.money_manager_app.data.model.entity.Category
import com.example.money_manager_app.data.model.entity.enums.CategoryType
import com.example.money_manager_app.data.repository.BudgetRepository
import com.example.money_manager_app.di.AppDispatchers
import com.example.money_manager_app.di.Dispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBudgetViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val budgetRepository: BudgetRepository
): BaseViewModel() {

    fun insertBudget(budget: Budget, listCategory: List<Category>, listCategoryData: List<CategoryData.Category>) {
        var budgetCategoryCrossRefs = mutableListOf<BudgetCategoryCrossRef>()
        if(listCategoryData[0].isCheck){
            for(i in listCategory.indices){
                if(listCategory[i].type == CategoryType.EXPENSE){
                    budgetCategoryCrossRefs.add(BudgetCategoryCrossRef(budget.id,listCategory[i].id))
                }
            }
        } else {
            for(i in listCategoryData){
               if(i.isCheck){
                     budgetCategoryCrossRefs.add(BudgetCategoryCrossRef(budget.id,i.icon.toLong()))
               }
            }
        }
        Log.d("AddBudgetFragment",budgetCategoryCrossRefs.toString())
        Log.d("AddBudgetFragment",budget.toString())
        viewModelScope.launch(ioDispatcher) {
            budgetRepository.insertBudget(budget, budgetCategoryCrossRefs)
        }
    }

}