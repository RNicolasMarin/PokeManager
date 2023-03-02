package com.pokemanager.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.pokemanager.R

object AndroidUtils {

    fun loadImage(root: View, imageUrl: String, pokeSpecieDetailImage: ImageView) {
        Glide.with(root).load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)//image while the image is loading
            //.diskCacheStrategy(DiskCacheStrategy.ALL)//in case it's having problems when loading the image
            .into(pokeSpecieDetailImage)
    }
}