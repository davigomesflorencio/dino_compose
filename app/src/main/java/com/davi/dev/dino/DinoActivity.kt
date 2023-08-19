/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.davi.dev.dino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.davi.dev.dino.presentation.CloudImages
import com.davi.dev.dino.presentation.DessertImages
import com.davi.dev.dino.presentation.DinoImages
import com.davi.dev.dino.presentation.GameWorld

@ExperimentalLifecycleComposeApi
class DinoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CloudImages.initialize(resources)
        DinoImages.initialize(resources)
        DessertImages.initialize(resources)
        setContent {
            GameWorld()
        }
    }
}


