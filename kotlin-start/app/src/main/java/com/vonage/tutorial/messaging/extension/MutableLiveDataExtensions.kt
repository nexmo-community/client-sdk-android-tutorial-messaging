package com.vonage.tutorial.messaging.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

@Suppress("UnsafeCast")
fun <T> MutableLiveData<T>.toLiveData() = this as LiveData<T>
