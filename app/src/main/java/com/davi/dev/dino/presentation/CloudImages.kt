package com.davi.dev.dino.presentation

import android.content.res.Resources
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.imageResource
import com.davi.dev.dino.R

object CloudImages {
    lateinit var one: ImageBitmap
    lateinit var two: ImageBitmap
    lateinit var three: ImageBitmap
    lateinit var birds: Image.DualFrameImage

    fun initialize(resources: Resources) {
        one = ImageBitmap.imageResource(
            resources,
            R.drawable.cloud
        )
        two = ImageBitmap.imageResource(
            resources,
            R.drawable.cloud
        )
        three = ImageBitmap.imageResource(
            resources,
            R.drawable.cloud
        )
        birds = Image.DualFrameImage(
            ImageBitmap.imageResource(resources, R.drawable.bird_1),
            ImageBitmap.imageResource(resources, R.drawable.bird_2)
        )
    }

}

fun DrawScope.drawCloud(type: CloudType) {
    when (type) {
        CloudType.CLOUD_ONE -> drawImage(CloudImages.one)
        CloudType.CLOUD_TWO -> drawImage(CloudImages.two)
        CloudType.CLOUD_THREE -> drawImage(CloudImages.three)
    }
}

fun DrawScope.drawCloud(gameTimeTicks: Long) {
    val flippedBit = gameTimeTicks.toInt() % 5 == 0
    val images = CloudImages.birds
    if (flippedBit) {
        drawImage(images.frameOne)
    } else {
        drawImage(images.frameTwo)
    }
}

enum class CloudType {
    CLOUD_ONE,
    CLOUD_TWO,
    CLOUD_THREE
}
