package com.example.money_manager_app.utils

import android.widget.ImageView

fun ImageView.loadImage(resDrawable: Int) {
    Glide.with(this)
        .load(resDrawable)
        .into(this)
}

fun ImageView.loadImage(urlImage: String?) {
    Glide.with(this)
        .load(urlImage)
        .into(this)
}
