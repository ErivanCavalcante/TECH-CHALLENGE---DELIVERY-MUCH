package com.erivan.santos.deliverymuchtest.presentation.viewmodel

import android.graphics.*
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.erivan.santos.deliverymuchtest.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation


object ImageViewAdapter {
    @JvmStatic
    @BindingAdapter("app:srcAvatar", requireAll = false)
    fun setImage(img: ImageView, src: String?) {
        src?.let {
            if (it.isBlank())
                return@let

            Picasso.get()
                .load(it)
                .placeholder(R.drawable.ic_image_black)
                .error(R.drawable.ic_broken_image_black)
                .centerInside()
                .fit()
                .transform(CircleTransform())
                .into(img)
        }
    }

    class CircleTransform : Transformation {
        override fun transform(source: Bitmap?): Bitmap {
            val size = Math.min(source!!.width, source.height)

            val x = (source.width - size) / 2
            val y = (source.height - size) / 2

            val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
            if (squaredBitmap != source) {
                source.recycle()
            }

            val bitmap = Bitmap.createBitmap(size, size, source.config)

            val canvas = Canvas(bitmap)
            val paint = Paint()
            val shader = BitmapShader(
                squaredBitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP
            )
            paint.setShader(shader)
            paint.setAntiAlias(true)

            val r = size / 2f
            canvas.drawCircle(r, r, r, paint)

            squaredBitmap.recycle()
            return bitmap
        }

        override fun key(): String {
            return "circle"
        }

    }
}