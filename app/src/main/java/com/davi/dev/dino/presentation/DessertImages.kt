package com.davi.dev.dino.presentation

import android.content.res.Resources
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.imageResource
import com.davi.dev.dino.R

object DessertImages {
    lateinit var cake: ImageBitmap
    lateinit var donut: ImageBitmap
    lateinit var sundae: ImageBitmap

    fun initialize(resources: Resources) {
        cake = ImageBitmap.imageResource(
            resources,
            R.drawable.chrome_dino_cactus
        )
        donut = ImageBitmap.imageResource(
            resources,
            R.drawable.chrome_dino_cactus
        )
        sundae = ImageBitmap.imageResource(
            resources,
            R.drawable.chrome_dino_cactus
        )
    }
}

fun DrawScope.drawDessert(type: DessertType) {
    when (type) {
        DessertType.CAKE -> drawImage(DessertImages.cake)
        DessertType.DONUT -> drawImage(DessertImages.donut)
        DessertType.SUNDAE -> drawImage(DessertImages.sundae)
    }
}

enum class DessertType {
    CAKE,
    DONUT,
    SUNDAE
}
