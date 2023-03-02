package com.jaygee.lessonschedule.util

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log

/**
 *  create on 27/2/2023
 **/

private var _drawLength = 0f
private var _drawLines = 0
private var _drawStartY = 0f

fun Paint.drawMultiLineText(
    canvas: Canvas?,
    word: List<String?>,
    startX: Float,
    startY: Float,
    maxWidth: Float,
    maxHeight: Float,
    drawTextOffsetY: Float,
    textMeasureHeight: Float
) {
    if (word.isNotEmpty()) {
        if (word.size == 1) {
            canvas?.drawText(
                word[0] ?: "",
                startX + maxWidth / 2,
                (startY + maxHeight / 2 + drawTextOffsetY),
                this
            )
        } else {
            _drawStartY = (maxHeight - word.size * textMeasureHeight) / 2
            word.forEachIndexed { index, s ->
                canvas?.drawText(
                    s ?: "",
                    startX + maxWidth / 2,
                    startY + _drawStartY + drawTextOffsetY * 2 + index * textMeasureHeight,
                    this
                )
            }
        }
    }
}

fun Paint.drawMultiLineText(
    canvas: Canvas?,
    word: List<String?>,
    rectF: RectF,
    rectWidth: Float,
    rectHeight: Float,
    drawTextOffsetY: Float,
    textMeasureHeight: Float
) {
    drawMultiLineText(
        canvas,
        word,
        rectF.left,
        rectF.top,
        rectWidth,
        rectHeight,
        drawTextOffsetY,
        textMeasureHeight
    )
}

private val _sb = StringBuilder()
fun String.generateMultiLineString(paint: Paint, maxWidth: Float): List<String> {
    _sb.setLength(0)
    _drawLines = 0
    return if (paint.measureText(this) > maxWidth) {
        this.forEach {
            _sb.append(it)
            _drawLength += paint.measureText(it.toString())
            if (_drawLength > maxWidth) {
                _sb.append("\n")
                _drawLength = 0f
                _drawLines += 1
            }
        }
        _sb.toString().split("\n").filter { it.isNotEmpty() }
    } else {
        listOf(this)
    }
}
