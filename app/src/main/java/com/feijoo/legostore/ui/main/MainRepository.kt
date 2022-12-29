package com.feijoo.legostore.ui.main

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import com.feijoo.legostore.R
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor() {
    /** Methods **/
    fun logIn(activity: Activity, liveData: MutableLiveData<MainResponse.SessionStarted>, email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(activity) { task ->
            val message = if(task.isSuccessful) {
                 activity.getString(R.string.logged_in_successfully)

            } else {
                activity.getString(R.string.user_not_exist)

            }

            liveData.postValue(MainResponse.SessionStarted(task.isSuccessful, message))

        }

    }

}