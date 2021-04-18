package com.manuelcarvalho.speccygraphs.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.core.graphics.get
import androidx.core.graphics.set
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "AppViewModel"

class AppViewModel(application: Application) : BaseViewModel(application) {

    val newImage = MutableLiveData<Bitmap>()
    val imageArray1 = MutableLiveData<ArrayList<ArrayList<Int>>>()
    val imageArray = MutableLiveData<Array<Array<Int>>>()

    fun decodeBitmapZX(bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {

            var byteArray = IntArray(8) { i -> 0 }

            var zxArray = Array(192) { Array(256) { 0 } }
            var changeValue = 0

            val conf = Bitmap.Config.ARGB_8888
            val bmp =
                    Bitmap.createBitmap(bitmap.width, bitmap.height, conf)
            var minimumVal = 0      //      -15768818
            //var maximumVal = 0 //  -1382691
            var maximumVal1 = findBitmapLowest(bitmap) / 2

            var emailString = "picture .byte "
            var hexNum = ""
            var lineNum = 0
            var pixelCount = 0

            Log.d(TAG, "${bitmap.width} ${bitmap.height}")

            var bitIndex = 0

            for (y in 0..bitmap.height - 1) {


                for (x in 0..bitmap.width - 1) {


                    val pix = bitmap.get(x, y)


                    //-15359521 Low -16777216 Good -10359521
                    //-16777216
                    if (pix > (maximumVal1 - 5000000)) {
                        bmp.set(x, y, Color.WHITE)
                        zxArray[y][x] = 1
                        byteArray[bitIndex] = 1
                    } else {
                        bmp.set(x, y, Color.BLACK)
                        zxArray[y][x] = 0
                        byteArray[bitIndex] = 0
                    }

                    bitIndex += 1

                    if (bitIndex > 7) {
                        createAssArray(byteArray)
                        bitIndex = 0

                    }

                }
            }

            viewModelScope.launch(Dispatchers.Main) {
                newImage.value = bmp
                imageArray.value = zxArray
            }

            //createAssArray()

        }

    }

    private fun createAssArray(pixArray: IntArray) {
        for (i in pixArray) {
            Log.d(TAG, "${i}")
        }

        Log.d(TAG, "*******************")


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
        Log.d(TAG, "Lowest ${value}")
        return value / 100
    }


}