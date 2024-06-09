package com.dhbw.dhbwsportapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat


class KreisProgressBarView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var progress: Int = 0
    private val paint: Paint = Paint()

    init {
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 30f
        paint.color = ContextCompat.getColor(context, R.color.red_DHBW_app) // Verwende den übergebenen Kontext hier
    }

    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX: Float = width / 2.toFloat()
        val centerY: Float = height / 2.toFloat()
        val radius: Float = (width.coerceAtMost(height) / 2).toFloat() - 30
        val startAngle = -90f
        val sweepAngle = (360 * progress / 100).toFloat()

        canvas.drawArc(centerX - radius, centerY - radius, centerX + radius, centerY + radius,
            startAngle, sweepAngle, false, paint)
    }
}
