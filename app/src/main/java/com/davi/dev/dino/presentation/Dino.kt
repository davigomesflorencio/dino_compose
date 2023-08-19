package com.davi.dev.dino.presentation

import android.content.res.Resources
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.imageResource
import com.davi.dev.dino.R

sealed interface Image {
    class SingleFrameImage(val frame: ImageBitmap) : Image
    class DualFrameImage(val frameOne: ImageBitmap, val frameTwo: ImageBitmap) : Image
}

object DinoImages {
    private lateinit var waiting: Image
    private lateinit var running: Image
    private lateinit var jumping: Image
    private lateinit var crashed: Image

    fun initialize(resources: Resources) {
        waiting = Image.SingleFrameImage(
            ImageBitmap.imageResource(
                resources,
                R.drawable.dino_wait
            )
        )
        running = Image.DualFrameImage(
            ImageBitmap.imageResource(resources, R.drawable.dino_1),
            ImageBitmap.imageResource(resources, R.drawable.dino_2)
        )
        crashed = Image.SingleFrameImage(
            ImageBitmap.imageResource(
                resources,
                R.drawable.dino_dead
            )
        )
        jumping = Image.DualFrameImage(
            ImageBitmap.imageResource(resources, R.drawable.dino_stop_1),
            ImageBitmap.imageResource(resources, R.drawable.dino_stop_2)
        )
    }

    fun imagesFor(avatarState: AvatarState): Image {
        return when (avatarState) {
            AvatarState.RUNNING -> running
            AvatarState.CRASHED -> crashed
            AvatarState.WAITING -> waiting
            AvatarState.JUMPING -> jumping
        }
    }
}

fun DrawScope.drawDino(avatarState: AvatarState, gameTimeTicks: Long) {
    val flippedBit = gameTimeTicks.toInt() % 5 == 0
    when (val images = DinoImages.imagesFor(avatarState)) {
        is Image.DualFrameImage -> {
            if (flippedBit) {
                drawImage(images.frameOne)
            } else {
                drawImage(images.frameTwo)
            }
        }

        is Image.SingleFrameImage -> drawImage(images.frame)
    }
}

enum class AvatarState {
    WAITING,
    RUNNING,
    CRASHED,
    JUMPING
}
