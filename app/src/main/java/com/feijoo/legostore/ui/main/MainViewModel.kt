package com.feijoo.legostore.ui.main

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository): ViewModel() {
    /** Attributes **/
    val sessionStarted: LiveData<MainResponse.SessionStarted> get() = _sessionStarted
    private val _sessionStarted = MutableLiveData<MainResponse.SessionStarted>()

    /** Methods **/
    fun logIn(activity: Activity, email: String, password: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.logIn(activity, _sessionStarted, email, password)

            }

        } catch (e: Exception) {
            e.printStackTrace()

        }

    }

}