package dev.olaore.emotionalfaceview

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import kotlin.math.min

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

    var horizontalSize: Int = 0
    var verticalSize: Int = 0

    var mouthPath = Path()

    var borderWidth = 4.0f
    var size = 320

    var state = 0

    init {
        horizontalSize = paddingLeft + paddingRight + width
        verticalSize = paddingTop + paddingBottom + height

        val typedArray = ctx.obtainStyledAttributes(attributeSet, R.styleable.EmotionalFaceView)

        try {

            faceColor = typedArray.getColor(R.styleable.EmotionalFaceView_faceColor, Color.YELLOW)
            eyesColor = typedArray.getColor(R.styleable.EmotionalFaceView_eyesColor, Color.BLACK)
            mouthColor = typedArray.getColor(R.styleable.EmotionalFaceView_mouthColor, Color.BLACK)
            borderColor = typedArray.getColor(R.styleable.EmotionalFaceView_borderColor, Color.BLACK)

            borderWidth = toDP(typedArray.getDimension(R.styleable.EmotionalFaceView_borderWidth, 4f)).toFloat()
            state = typedArray.getInt(R.styleable.EmotionalFaceView_state, 0)

        } finally {
            typedArray.recycle()
        }

    }

    private fun toDP(value: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value,
            ctx.resources.displayMetrics
        ).toInt()
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
        if (state == 0) {
            mouthPath.moveTo(size * 0.22f, size * 0.7f)
            mouthPath.quadTo(size * 0.5f, size * 0.8f, size * 0.78f, size * 0.7f)
            mouthPath.quadTo(size * 0.5f, size * 0.9f, size * 0.22f, size * 0.7f)

            paint.color = mouthColor

        } else {
            mouthPath.moveTo(size * 0.22f, size * 0.7f)
            mouthPath.quadTo(size * 0.5f, size * 0.5f, size * 0.78f, size * 0.7f)
            mouthPath.quadTo(size * 0.5f, size * 0.6f, size * 0.22f, size * 0.7f)

            paint.color = Color.RED
        }
        paint.style = Paint.Style.FILL
        canvas?.drawPath(mouthPath, paint)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = Math.min(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)
    }

}
