package com.manuelcarvalho.speccygraphs.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.core.graphics.get
import androidx.core.graphics.set
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.manuelcarvalho.speccygraphs.utils.formatString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "AppViewModel"

class AppViewModel(application: Application) : BaseViewModel(application) {

    var byteCount = 0

    val newImage = MutableLiveData<Bitmap>()
    val imageArray1 = MutableLiveData<ArrayList<ArrayList<Int>>>()
    val imageArray = MutableLiveData<Array<Array<Int>>>()
    val imageContrast = MutableLiveData<Int>()
    val progressInt = MutableLiveData<Int>()

    val progress = MutableLiveData<Boolean>()
    val inProgress = MutableLiveData<Boolean>()

    fun decodeBitmapZX(bitmap: Bitmap, contrast: Int) {
        progress.value = true
        inProgress.value = true
        viewModelScope.launch(Dispatchers.IO) {

            var contrast2 = contrast * 0.025

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


            var strIndex = 0
            val progressStep = (bitmap.height.toFloat() / 120.0).toInt()
            Log.d(TAG, "ProgressStep $progressStep")
            var progressIndex = 0
            for (y in 0..bitmap.height - 1) {
                progressIndex += progressStep
                viewModelScope.launch(Dispatchers.Main) {

                    progressInt.value = progressIndex
                    //Log.d(TAG, "ProgressIndex  ${bitmap.height}   $progressIndex $progressStep")
                }

                var bitIndex = 0
                for (x in 0..bitmap.width - 1) {


                    val pix = bitmap.get(x, y)


                    //-15359521 Low -16777216 Good -10359521
                    //-16777216
                    if (pix > (maximumVal1 - 5000000 * contrast2)) {
                        bmp.set(x, y, Color.WHITE)
                        zxArray[y][x] = 1
                        byteArray[bitIndex] = 1
                        byteArray.set(bitIndex, 1)
                    } else {
                        bmp.set(x, y, Color.BLACK)
                        zxArray[y][x] = 0
                        byteArray[bitIndex] = 0
                        byteArray.set(bitIndex, 0)
                    }

                    bitIndex += 1

                    if (bitIndex > 7) {
                        var numStr = createAssArray(byteArray)
                        //Log.d(TAG, "${numStr}")
                        if (strIndex < 10) {
                            numStr += ","
                        }

                        formatString += numStr
                        strIndex += 1

                        if (strIndex > 10) {
                            formatString += "\n"
                            formatString += "DB "
                            strIndex = 0
                        }
                        bitIndex = 0

                    }

                }

                //Log.d(TAG, "${formatString}")
            }

            viewModelScope.launch(Dispatchers.Main) {
                newImage.value = bmp
                imageArray.value = zxArray
                progress.value = false
                inProgress.value = false
            }

            Log.d(TAG, "${byteCount}")

        }

    }


    private fun createAssArray(list: IntArray): String {
        byteCount += 1
        for (i in 0..7) {
            //Log.d(TAG,"$i ${list[i]}")
        }
        //Log.d(TAG, "${pixArray}")
        //Log.d(TAG, "*******************")

        var newByte = 0

        if (list[0] == 1) {
            newByte += 1
        }

        if (list[1] == 1) {
            newByte += 2
        }

        if (list[2] == 1) {
            newByte += 4
        }

        if (list[3] == 1) {
            newByte += 8
        }

        if (list[4] == 1) {
            newByte += 16
        }

        if (list[5] == 1) {
            newByte += 32
        }

        if (list[6] == 1) {
            newByte += 64
        }

        if (list[7] == 1) {
            newByte += 128
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
        //Log.d(TAG, "Lowest ${value}")
        return value / 100
    }


}