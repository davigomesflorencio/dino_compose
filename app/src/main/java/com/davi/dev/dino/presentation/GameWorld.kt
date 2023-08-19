@file:OptIn(ExperimentalComposeUiApi::class)

package com.davi.dev.dino.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import kotlinx.coroutines.launch

@ExperimentalLifecycleComposeApi
@Composable
fun GameWorld(
    viewModel: GameWorldViewModel = viewModel()
) {
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Box(contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0XFFfdfffd))
                .onSizeChanged {
                    viewModel.onCanvasResize(it.width, it.height)
                }
                .pointerInput("screen_taps") {
                    detectTapGestures {
                        viewModel.onReceiveJumpInput()
                    }
                }
                .onRotaryScrollEvent {
                    coroutineScope.launch {
                        viewModel.onReceiveJumpInput()
                    }
                    true
                }
                .focusRequester(focusRequester)
                .focusable(true)
        ) {
            val uiStateValue = uiState.value

            if (uiStateValue != null) {
                drawRect(
                    color = Color(0XFF787878),
                    topLeft = Offset(0f, uiStateValue.groundY),
                )

                for (cloud in uiStateValue.clouds) {
                    translate(left = cloud.left, top = cloud.top) {
//                        Log.d("!!!", "cloud L ${cloud.left} T ${cloud.top}")
                        if (!cloud.isBird) {
                            drawCloud(cloud.type)
                        } else {
                            drawCloud(uiStateValue.gameWorldTicks)
                        }
                    }
                }

                for (obstacle in uiStateValue.obstacles) {
                    translate(left = obstacle.left, top = obstacle.top) {
                        drawDessert(obstacle.type)
                    }
                }

                translate(left = uiStateValue.dino.left, top = uiStateValue.dino.top) {
                    drawDino(uiStateValue.dino.avatarState, uiStateValue.gameWorldTicks)
                }
            }
        }

        Text(
            "SCORE: ${viewModel.score.value}",
            color = Color.White,
            modifier = Modifier
                .padding()
                .offset(0.dp, 40.dp)
        )

        Text(
            " MAX SCORE: ${viewModel.maxscore.value}",
            color = Color.White,
            modifier = Modifier
                .padding()
                .offset(0.dp, 55.dp)
        )

        if (uiState.value?.isPlaying == false) {
            Chip(
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = "play game"
                    )
                },
                colors = ChipDefaults.primaryChipColors(
                    backgroundColor = Color(230, 223, 196, 255),
                    contentColor = Color(65, 65, 65),
                    secondaryContentColor = Color(65, 65, 65),
                    iconColor = Color(65, 65, 65)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = { Text("PLAY GAME") },
                secondaryLabel = uiState.value?.score?.let { score ->
                    if (score > 0) {
                        { Text("SCORE: $score") }
                    } else {
                        null
                    }
                },
                onClick = { viewModel.onStartPressed() }
            )
        }
    }
}
