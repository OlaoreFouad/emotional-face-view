package dev.olaore.emotionalfaceview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * TODO: document your custom view class.
 */
class EmotionalFaceView @JvmOverloads
    constructor(var ctx: Context, var attributeSet: AttributeSet? = null, var defStyleAttr: Int = 0, var defStyleRes: Int = 0)
    : View(ctx, attributeSet, defStyleAttr, defStyleRes) {

    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var faceColor = Color.YELLOW
    var eyesColor = Color.BLACK
    var mouthColor = Color.BLACK
    var borderColor = Color.BLACK

    var borderWidth = 4.0f
    var size = 320

    init {

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawFaceBackground(canvas)
        drawEyes(canvas)
        drawMouth(canvas)
    }

    private fun drawFaceBackground(canvas: Canvas?) {
        paint.color = faceColor
        paint.style = Paint.Style.FILL

        val radius = size / 2f
        canvas?.let {
            it.drawCircle(size / 2f, size / 2f, radius, paint)
        }

        val strokeRadius = radius - borderWidth / 2
        paint.color = borderColor
        paint.style = Paint.Style.STROKE

        canvas?.let {
            it.drawCircle(size / 2f, size / 2f, strokeRadius, paint)
        }

    }

    private fun drawEyes(canvas: Canvas?) {

        paint.color = eyesColor
        paint.style = Paint.Style.FILL

        canvas?.let {
            val leftEyeRect = RectF(size * 0.32f, size* 0.23f, size * 0.43f, size * 0.50f)
            it.drawOval(leftEyeRect, paint)
            val rightEyeRect = RectF(size * 0.57f, size * 0.23f, size * 0.68f, size * 0.5f)
            it.drawOval(rightEyeRect, paint)
        }

    }

    private fun drawMouth(canvas: Canvas?) {

    }

}
