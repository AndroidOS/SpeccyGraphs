package com.manuelcarvalho.speccygraphs.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.get
import androidx.core.graphics.set
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "AppViewModel"

class AppViewModel(application: Application) : BaseViewModel(application) {

    val newImage = MutableLiveData<Bitmap>()

    fun decodeBitmapZX(bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            var changeValue = 0

            val conf = Bitmap.Config.ARGB_8888
            val bmp =
                    Bitmap.createBitmap(bitmap.width, bitmap.height, conf)
            var minimumVal = 0      //      -15768818
            //var maximumVal = 0 //  -1382691
            var maximumVal = findBitmapLowest(bitmap) / 2

            var emailString = "picture .byte "
            var hexNum = ""
            var lineNum = 0
            var pixelCount = 0

            for (y in 0..bitmap.height - 1) {

                for (x in 0..bitmap.width - 1) {
                    val pix = bitmap.get(x, y)
                    if (minimumVal > pix) {
                        maximumVal = pix
                    }
                    if (maximumVal < pix) {
                        maximumVal = pix
                    }
                }
            }


            var vzByte = arrayListOf(1, 2, 3, 4)
            //Log.d(TAG, "${minimumVal}  ${maximumVal}")

            for (y in 0..bitmap.height - 1) {

                var bitcount = 0

                for (x in 0..bitmap.width - 1) {

                    val pix = bitmap.get(x, y)
                    lineNum += 1
                    pixelCount += 1


                    if (pix < changeValue) {       //-6768818
                        bmp.set(x, y, Color.BLACK)
                        hexNum = "0"
                        vzByte[bitcount] = 15
                    } else {
                        bmp.set(x, y, Color.WHITE)
                        hexNum = "15"
                        vzByte[bitcount] = 0
                    }

                    bitcount += 1
                    if (bitcount > 3) {
                        bitcount = 0
                        hexNum = createByte(vzByte)
                        if (lineNum > 20) {
                            lineNum = 0
                            emailString += "\n    .byte " + hexNum + ","
                        } else if (lineNum > 19) {
                            emailString += hexNum
                            //lineNum = 0
                        } else {
                            emailString += hexNum + ","
                        }
                    }
                }

            }

        }

    }


    private fun createByte(list: List<Int>): String {

        var newByte = 0

        if (list[0] == 15) {
            newByte += 128
            newByte += 64
        }

        if (list[1] == 15) {
            newByte += 32
            newByte += 16
        }

        if (list[2] == 15) {
            newByte += 8
            newByte += 4
        }

        if (list[3] == 15) {
            newByte += 2
            newByte += 1
        }
        // Log.d(TAG, "${list}   ${newByte}")
        return newByte.toString()
    }

    private fun findBitmapLowest(bitmap: Bitmap): Int {
        var value = 0
        for (y in 0..bitmap.height - 1) {
            for (x in 0..bitmap.width - 1) {
                val pix = bitmap.get(x, y)
                if (pix < value) {
                    value = pix
                }
            }
        }
        return value / 100
    }


}