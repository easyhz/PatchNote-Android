package com.easyhz.patchnote.core.designSystem.util.extension

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.easyhz.patchnote.ui.theme.SubText

inline fun Modifier.noRippleClickable(
    interactionSource: MutableInteractionSource? = null,
    enabled: Boolean = true,
    crossinline onClick: () -> Unit,
): Modifier = composed {
    clickable(
        indication = null,
        enabled = enabled,
        interactionSource = interactionSource ?: remember { MutableInteractionSource() }) {
        onClick()
    }
}

inline fun Modifier.circleClickable(
    enabled: Boolean = true,
    bounded: Boolean = false,
    radius: Dp = 24.dp,
    color: Color = SubText,
    crossinline onClick: () -> Unit,
): Modifier = composed {
    clickable(
        indication = ripple(bounded = bounded, radius = radius, color = color),
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}


/**
 * Figma DropShadow
 *
 * @param shape : 그림자의 모양 지정
 * @param color : 그림자의 색 지정
 * @param blur : 그림자의 세기 지정
 * @param offsetY : Y 축 offset 지정
 * @param offsetX : X 축 offset 지정
 * @param spread : 그림자의 퍼진 정도 지정
 */
fun Modifier.dropShadow(
    shape: Shape,
    color: Color = Color.Black.copy(0.25f),
    blur: Dp = 4.dp,
    offsetY: Dp = 4.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp
) = this.drawBehind {
    val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
    val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

    val paint = Paint().apply {
        this.color = color
    }

    if (blur.toPx() > 0) {
        paint.asFrameworkPaint().apply {
            maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
    }

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.translate(offsetX.toPx(), offsetY.toPx())
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}


/**
 * 그림자 효과 [Modifier] 확장 함수 - 버튼 기준
 *
 * @param shadowColor 그림자 색
 * @param borderRadius 버튼 radius
 * @param blurRadius 블러 radius
 * @param offsetY y 오프셋
 * @param offsetX x 오프셋
 * @param spread 얼마나 퍼질건지
 * @param modifier Modifier
 *
 * @return [Modifier]
 */
fun Modifier.buttonShadowEffect(
    shadowColor: Color = Color.Black,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0f.dp,
    modifier: Modifier = Modifier
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val spreadPixel = spread.toPx()
            val leftPixel = (0f - spreadPixel) + offsetX.toPx()
            val topPixel = (0f - spreadPixel) + offsetY.toPx()
            val rightPixel = (this.size.width + spreadPixel)
            val bottomPixel = (this.size.height + spreadPixel)

            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }

            frameworkPaint.color = shadowColor.toArgb()
            it.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = borderRadius.toPx(),
                radiusY = borderRadius.toPx(),
                paint
            )
        }
    }
)