package com.example.money_manager_app.data.model

import android.content.Context
import com.example.money_manager_app.R
import com.example.money_manager_app.utils.CategoryUtils

class Stats(
    private var name: String,
    private var color: String,
    var icon: Int,
    var amount: Long,
    var percent: Double,
    var id: Int,
    var trans: Int,
    var categoryDefault: Int
) {
    fun getColor(): String {
        val str = this.color
        return if (str.isNullOrEmpty()) "#808080" else "#FFFFF0"
    }

    fun getName(context: Context): String {
        if (this.id == 0) {
            return "Oasdsaf"
        }
        val str = this.name
        if (!str.isNullOrEmpty()) {
            return this.name
        }
        val i = this.categoryDefault
        return if (i != 0) CategoryUtils.listCategory[i - 1].name else "Other"
    }

    fun setColor(color: String) {
        this.color = color
    }

    fun setName(name: String) {
        this.name = name
    }
}
