package com.fb.weathertest.ui.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers

abstract class BaseViewModel() : ViewModel() {

    val coroutineExceptionHandler = CoroutineExceptionHandler { Context, throwable ->
        throwable.printStackTrace()
    }

    val coroutineContext = coroutineExceptionHandler + Dispatchers.IO
}
