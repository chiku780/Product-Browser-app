package com.example.ui.screens.ballPhysics.viewModel.component

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import com.example.ui.screens.ballPhysics.Ball
import kotlin.math.sqrt


data class BottleWall(
    val center: Offset,
    val radius: Float
)


//fun createBottleWalls(
//    width: Float,
//    height: Float
//): List<BottleWall> {
//
//    val walls = mutableListOf<BottleWall>()
//
//    val centerX = width / 2f
//
//    // Bottom Curve
//    for (i in 0..20) {
//
//        val angle = Math.PI + (Math.PI * i / 20.0)
//
//        val x = centerX + kotlin.math.cos(angle).toFloat() * 250f
//        val y = height - 220f + kotlin.math.sin(angle).toFloat() * 250f
//
//        walls.add(
//            BottleWall(
//                center = Offset(x, y),
//                radius = 28f
//            )
//        )
//    }
//
//    // Left Body
//    for (i in 0..12) {
//
//        walls.add(
//            BottleWall(
//                center = Offset(
//                    centerX - 250f,
//                    height - 220f - i * 80f
//                ),
//                radius = 28f
//            )
//        )
//    }
//
//    for (i in 0..12) {
//
//        walls.add(
//            BottleWall(
//                center = Offset(
//                    centerX + 250f,
//                    height - 220f - i * 80f
//                ),
//                radius = 28f
//            )
//        )
//    }
//
//    // Left Neck
//    for (i in 0..6) {
//
//        walls.add(
//            BottleWall(
//                center = Offset(
//                    centerX - 120f,
//                    260f + i * 55f
//                ),
//                radius = 24f
//            )
//        )
//    }
//
//    // Right Neck
//    for (i in 0..6) {
//
//        walls.add(
//            BottleWall(
//                center = Offset(
//                    centerX + 120f,
//                    260f + i * 55f
//                ),
//                radius = 24f
//            )
//        )
//    }
//
//    // Top Cap
//    for (i in 0..8) {
//
//        walls.add(
//            BottleWall(
//                center = Offset(
//                    centerX - 120f + i * 30f,
//                    240f
//                ),
//                radius = 24f
//            )
//        )
//    }
//
//    return walls
//}


fun createBottlePath(
    width: Float,
    height: Float
): Path {

    val centerX = width / 2f

    val neckWidth = width * 0.22f
    val bodyWidth = width * 0.92f

    val neckLeft = centerX - neckWidth / 2f
    val neckRight = centerX + neckWidth / 2f

    val bodyLeft = centerX - bodyWidth / 2f
    val bodyRight = centerX + bodyWidth / 2f

    val topY = 180f
    val neckBottomY = 420f
    val bodyTopY = 520f
    val bottomY = height - 120f

    return Path().apply {

        moveTo(neckLeft, topY)

        lineTo(neckLeft, neckBottomY)

        cubicTo(
            neckLeft,
            bodyTopY,
            bodyLeft,
            bodyTopY,
            bodyLeft,
            bodyTopY + 180f
        )

        lineTo(bodyLeft, bottomY - 180f)

        quadraticBezierTo(
            centerX,
            bottomY + 120f,
            bodyRight,
            bottomY - 180f
        )

        lineTo(bodyRight, bodyTopY + 180f)

        cubicTo(
            bodyRight,
            bodyTopY,
            neckRight,
            bodyTopY,
            neckRight,
            neckBottomY
        )

        lineTo(neckRight, topY)

        close()
    }
}


fun updateBottlePhysics(
    balls: MutableList<Ball>,
    delta: Float,
    width: Float,
    height: Float,
    friction: Float,
    gravityX: Float,
    gravityY: Float
) {

    val centerX = width / 2f

    val neckWidth = width * 0.22f
    val bodyWidth = width * 0.92f

    val neckLeft = centerX - neckWidth / 2f
    val neckRight = centerX + neckWidth / 2f

    val bodyLeft = centerX - bodyWidth / 2f
    val bodyRight = centerX + bodyWidth / 2f

    val topY = 180f
    val neckBottomY = 420f
    val bottomY = height - 120f

    val bottomCenterX = centerX
    val bottomCenterY = bottomY - 40f
    val bottomRadius = bodyWidth / 2f

    balls.forEach { ball ->

        // Gravity
        ball.velocityX += gravityX * delta
        ball.velocityY += gravityY * delta

        // Friction
        ball.velocityX *= friction
        ball.velocityY *= friction

        // Velocity Clamp
        val maxVelocity = 2200f

        ball.velocityX =
            ball.velocityX.coerceIn(
                -maxVelocity,
                maxVelocity
            )

        ball.velocityY =
            ball.velocityY.coerceIn(
                -maxVelocity,
                maxVelocity
            )

        // Move
        ball.x += ball.velocityX * delta
        ball.y += ball.velocityY * delta

        // Neck Collision
        if (ball.y < neckBottomY) {

            if (ball.x - ball.radius < neckLeft) {

                ball.x = neckLeft + ball.radius
                ball.velocityX *= -0.35f
            }

            if (ball.x + ball.radius > neckRight) {

                ball.x = neckRight - ball.radius
                ball.velocityX *= -0.35f
            }
        }

        // Body Collision
        else {

            if (ball.x - ball.radius < bodyLeft) {

                ball.x = bodyLeft + ball.radius
                ball.velocityX *= -0.35f
            }

            if (ball.x + ball.radius > bodyRight) {

                ball.x = bodyRight - ball.radius
                ball.velocityX *= -0.35f
            }
        }

        // Top Collision
        if (ball.y - ball.radius < topY) {

            ball.y = topY + ball.radius
            ball.velocityY *= -0.35f
        }

        // Bottom Curve Collision
        val dx = ball.x - bottomCenterX
        val dy = ball.y - bottomCenterY

        val distance =
            sqrt(dx * dx + dy * dy)

        if (
            ball.y > bottomCenterY &&
            distance > bottomRadius - ball.radius
        ) {

            val nx = dx / distance
            val ny = dy / distance

            ball.x =
                bottomCenterX +
                        nx * (bottomRadius - ball.radius)

            ball.y =
                bottomCenterY +
                        ny * (bottomRadius - ball.radius)

            val dot =
                ball.velocityX * nx +
                        ball.velocityY * ny

            ball.velocityX -= 2 * dot * nx
            ball.velocityY -= 2 * dot * ny

            ball.velocityX *= 0.4f
            ball.velocityY *= 0.4f
        }
    }

    // Ball Collisions
    for (i in balls.indices) {

        for (j in i + 1 until balls.size) {

            resolveCollision(
                balls[i],
                balls[j]
            )
        }
    }
}

fun resolveCollision(
    ball1: Ball,
    ball2: Ball
) {

    val dx = ball2.x - ball1.x
    val dy = ball2.y - ball1.y

    val distance =
        sqrt(dx * dx + dy * dy)

    val minDistance =
        ball1.radius + ball2.radius

    if (
        distance < minDistance &&
        distance > 0f
    ) {

        val nx = dx / distance
        val ny = dy / distance

        val overlap =
            minDistance - distance

        ball1.x -= overlap / 2 * nx
        ball1.y -= overlap / 2 * ny

        ball2.x += overlap / 2 * nx
        ball2.y += overlap / 2 * ny

        val rvx =
            ball2.velocityX - ball1.velocityX

        val rvy =
            ball2.velocityY - ball1.velocityY

        val velocityAlongNormal =
            rvx * nx + rvy * ny

        if (velocityAlongNormal > 0f) return

        val restitution = 0.5f

        val impulse =
            -(1 + restitution) *
                    velocityAlongNormal /
                    (1 / ball1.mass + 1 / ball2.mass)

        val impulseX = impulse * nx
        val impulseY = impulse * ny

        ball1.velocityX -= impulseX / ball1.mass
        ball1.velocityY -= impulseY / ball1.mass

        ball2.velocityX += impulseX / ball2.mass
        ball2.velocityY += impulseY / ball2.mass
    }
}