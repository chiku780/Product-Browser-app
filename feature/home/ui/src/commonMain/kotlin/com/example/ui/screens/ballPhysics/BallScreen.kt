package com.example.ui.screens.ballPhysics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.sqrt
import kotlin.random.Random
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.Alignment
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateListOf
import com.example.ui.screens.ballPhysics.viewModel.BallScreenViewmodel
import com.example.ui.screens.home.viewModel.HomeScreenViewmodel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BallScreen() {

    val viewModel = koinViewModel<BallScreenViewmodel>()

    val balls = remember {

        mutableStateListOf<Ball>().apply {

            repeat(12) {

                add(
                    Ball(
                        x = Random.nextFloat() * 800f + 100f,
                        y = Random.nextFloat() * 1200f + 100f,
                        radius = Random.nextFloat() * 40f + 30f,
                        velocityX = Random.nextFloat() * 600f - 300f,
                        velocityY = Random.nextFloat() * 600f - 300f,
                        color = Color(
                            Random.nextFloat(),
                            Random.nextFloat(),
                            Random.nextFloat(),
                            1f
                        ),
                        mass = Random.nextFloat() * 3f + 1f
                    )
                )
            }
        }
    }

    var canvasSize by remember {
        mutableStateOf(IntSize.Zero)
    }

    val gravity = 900f
    val friction = 0.999f
    val bounceDamping = 0.92f

    LaunchedEffect(Unit) {

        var lastFrameTime = 0L

        while (true) {

            withFrameNanos { currentTime ->

                if (lastFrameTime != 0L) {

                    val delta =
                        (currentTime - lastFrameTime) / 1_000_000_000f

                    updatePhysics(
                        balls = balls,
                        delta = delta,
                        width = canvasSize.width.toFloat(),
                        height = canvasSize.height.toFloat(),
                        gravity = gravity,
                        friction = friction,
                        bounceDamping = bounceDamping
                    )
                }

                lastFrameTime = currentTime
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF111111))
            .onSizeChanged {
                canvasSize = it
            }
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {

            balls.forEach { ball ->

                drawCircle(
                    color = ball.color,
                    radius = ball.radius,
                    center = Offset(ball.x, ball.y)
                )
            }
        }

        Text(
            text = "Physics Engine Demo",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

fun updatePhysics(
    balls: MutableList<Ball>,
    delta: Float,
    width: Float,
    height: Float,
    gravity: Float,
    friction: Float,
    bounceDamping: Float
) {

    balls.forEach { ball ->

        // Gravity
        ball.velocityY += gravity * delta

        // Friction
        ball.velocityX *= friction
        ball.velocityY *= friction

        // Position Update
        ball.x += ball.velocityX * delta
        ball.y += ball.velocityY * delta

        // Left Wall
        if (ball.x - ball.radius <= 0f) {

            ball.x = ball.radius
            ball.velocityX *= -bounceDamping
        }

        // Right Wall
        if (ball.x + ball.radius >= width) {

            ball.x = width - ball.radius
            ball.velocityX *= -bounceDamping
        }

        // Top Wall
        if (ball.y - ball.radius <= 0f) {

            ball.y = ball.radius
            ball.velocityY *= -bounceDamping
        }

        // Bottom Wall
        if (ball.y + ball.radius >= height) {

            ball.y = height - ball.radius
            ball.velocityY *= -bounceDamping
        }
    }

    // Ball Collision
    for (i in balls.indices) {

        for (j in i + 1 until balls.size) {

            val ball1 = balls[i]
            val ball2 = balls[j]

            resolveCollision(ball1, ball2)
        }
    }
}

fun resolveCollision(
    ball1: Ball,
    ball2: Ball
) {

    val dx = ball2.x - ball1.x
    val dy = ball2.y - ball1.y

    val distance = sqrt(dx * dx + dy * dy)

    val minDistance = ball1.radius + ball2.radius

    if (distance < minDistance && distance > 0f) {

        // Normalize
        val nx = dx / distance
        val ny = dy / distance

        // Push Apart
        val overlap = minDistance - distance

        ball1.x -= overlap / 2 * nx
        ball1.y -= overlap / 2 * ny

        ball2.x += overlap / 2 * nx
        ball2.y += overlap / 2 * ny

        // Relative velocity
        val rvx = ball2.velocityX - ball1.velocityX
        val rvy = ball2.velocityY - ball1.velocityY

        // Velocity along normal
        val velocityAlongNormal =
            rvx * nx + rvy * ny

        if (velocityAlongNormal > 0f) return

        val restitution = 1f

        val impulse =
            -(1 + restitution) * velocityAlongNormal /
                    (1 / ball1.mass + 1 / ball2.mass)

        val impulseX = impulse * nx
        val impulseY = impulse * ny

        ball1.velocityX -= impulseX / ball1.mass
        ball1.velocityY -= impulseY / ball1.mass

        ball2.velocityX += impulseX / ball2.mass
        ball2.velocityY += impulseY / ball2.mass
    }
}

class Ball(
    x: Float,
    y: Float,
    val radius: Float,
    velocityX: Float,
    velocityY: Float,
    val color: Color,
    val mass: Float = 1f
) {

    var x by mutableFloatStateOf(x)
    var y by mutableFloatStateOf(y)

    var velocityX by mutableFloatStateOf(velocityX)
    var velocityY by mutableFloatStateOf(velocityY)
}
