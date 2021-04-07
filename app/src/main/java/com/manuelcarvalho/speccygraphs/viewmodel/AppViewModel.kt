package com.manuelcarvalho.speccygraphs.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData


private const val TAG = "AppViewModel"

class AppViewModel(application: Application) : BaseViewModel(application) {

    val newImage = MutableLiveData<Bitmap>()


}