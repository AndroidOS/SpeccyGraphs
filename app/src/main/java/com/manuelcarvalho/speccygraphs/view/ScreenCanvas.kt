package com.manuelcarvalho.speccygraphs.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.manuelcarvalho.speccygraphs.R


private const val TAG = "Canvas"

class ScreenCanvas(context: Context) : View(context) {

    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap

    private var touchX = 0.0f
    private var touchY = 0.0f

    private var canvasHeight = 0
    private var canvasWidth = 0

    private var zxArray = Array(192) { Array(256) { 0 } }


    private val backgroundColor = ResourcesCompat.getColor(resources, R.color.canvasBackground, null)
    private val drawColor = ResourcesCompat.getColor(resources, R.color.canvasColor, null)


    private val paint = Paint().apply {
        color = drawColor
        style = Paint.Style.STROKE
        strokeWidth = 5f
        textSize = 20f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        canvasWidth = w
        canvasHeight = h

//        zxArray[20][30] = 1
//        zxArray[191][255] = 1

        Log.d(TAG, "${w / 256} ${h / 192}")
//        zxArray[90][128] = 1
//        zxArray[191][254] = 1
//        zxArray[191][253] = 1

//        for (x in 0..255) {
//            zxArray[50][x] = 1
//        }


        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(backgroundColor)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val xStep = canvasWidth / 256
        val yStep = canvasHeight / 192

        var Xcanvas = 0.0f
        var Ycanvas = 0.0f
        for (y1 in 0..191) {
            for (x1 in 0..255) {
                val pix = zxArray[y1][x1]
                if (pix == 1) {
                    //extraCanvas.drawPoint(Xcanvas, Ycanvas, paint)
                    val selectPaint = paint
                    selectPaint.style = Paint.Style.FILL
                    extraCanvas.drawRect(
                            x1.toFloat() * xStep,
                            y1.toFloat() * yStep,
                            (x1.toFloat() * xStep + xStep),
                            (y1.toFloat() * yStep + yStep),
                            selectPaint
                    )
                }
            }
        }

        canvas?.drawBitmap(extraBitmap, 0f, 0f, null)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        touchX = event.x
        touchY = event.y

//        for (x in -canvasWidth..canvasWidth) {
//            Log.d(TAG, " $x")
//            val x1 = x * touchX / 10
        extraCanvas.drawPoint(touchX.toFloat(), touchY.toFloat(), paint)


        // }

        invalidate()

        return true
    }
}