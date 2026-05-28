package com.example.ui.screens.ballPhysics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.sp
import com.example.ui.screens.ballPhysics.viewModel.BallScreenViewmodel
import com.example.ui.screens.ballPhysics.viewModel.component.createBottlePath
import com.example.ui.screens.ballPhysics.viewModel.component.updateBottlePhysics
import kotlinx.coroutines.isActive
import org.koin.compose.viewmodel.koinViewModel
import kotlin.random.Random

@Composable
fun BallScreenGyro() {

    val viewModel = koinViewModel<BallScreenViewmodel>()

    val uiState by viewModel.uiState.collectAsState()

    var canvasSize by remember {
        mutableStateOf(IntSize.Zero)
    }

    val balls = remember {

        mutableStateListOf<Ball>().apply {

            repeat(160) {

                add(
                    Ball(
                        x = Random.nextFloat() * 120f + 350f,
                        y = Random.nextFloat() * 300f + 350f,
                        radius =  Random.nextFloat() * 7f + 12f,
                        velocityX = 0f,
                        velocityY = 0f,
                        color = Color(
                            Random.nextFloat(),
                            Random.nextFloat(),
                            Random.nextFloat(),
                            1f
                        ),
                        mass = 1f
                    )
                )
            }
        }
    }

    val friction = 0.992f

    LaunchedEffect(Unit) {

        var lastFrameTime = 0L

        while (isActive) {

            withFrameNanos { currentTime ->

                if (lastFrameTime != 0L) {

                    val delta =
                        (currentTime - lastFrameTime) / 1_000_000_000f

                    updateBottlePhysics(
                        balls = balls,
                        delta = delta,
                        width = canvasSize.width.toFloat(),
                        height = canvasSize.height.toFloat(),
                        friction = friction,
                        gravityX = uiState.gravityX,
                        gravityY = uiState.gravityY
                    )
                }

                lastFrameTime = currentTime
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
            .onSizeChanged {
                canvasSize = it
            }
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {

            val bottlePath = createBottlePath(
                width = size.width,
                height = size.height
            )

            // Bottle Fill
            drawPath(
                path = bottlePath,
                color = Color.White.copy(alpha = 0.05f)
            )

            // Balls
            balls.forEach { ball ->

                drawCircle(
                    color = ball.color,
                    radius = ball.radius,
                    center = Offset(ball.x, ball.y)
                )
            }

            // Bottle Border
            drawPath(
                path = bottlePath,
                color = Color.White.copy(alpha = 0.85f),
                style = Stroke(width = 8f)
            )
        }

        Text(
            text = """
                X: ${uiState.gravityX.toInt()}
                Y: ${uiState.gravityY.toInt()}
            """.trimIndent(),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}