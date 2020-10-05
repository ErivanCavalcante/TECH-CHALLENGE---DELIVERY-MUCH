package com.erivan.santos.deliverymuchtest.presentation.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.erivan.santos.deliverymuchtest.R
import com.squareup.picasso.Picasso

object ImageViewAdapter {
    @JvmStatic
    @BindingAdapter("app:srcAvatar", requireAll = false)
    fun setImage(img: ImageView, src: String?) {
        src?.let {
            Picasso.get()
                .load(it)
                .placeholder(R.drawable.ic_image_black)
                .error(R.drawable.ic_broken_image_black)
                .centerInside()
                .fit()
                .into(img)
        }
    }
}