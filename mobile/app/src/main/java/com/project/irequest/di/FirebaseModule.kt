package com.project.irequest.di

import com.google.firebase.firestore.FirebaseFirestore

object FirebaseModule {

    private var firestoreInstance: FirebaseFirestore? = null

    fun getFirestore(): FirebaseFirestore {
        return firestoreInstance ?: synchronized(this) {
            firestoreInstance ?: FirebaseFirestore.getInstance().also {
                firestoreInstance = it
            }
        }
    }
}
