package com.anangkur.mediku.data

import android.content.Context
import android.content.SharedPreferences
import com.anangkur.mediku.data.local.LocalRepository
import com.anangkur.mediku.data.local.room.AppDao
import com.anangkur.mediku.data.remote.RemoteRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

object Injection {
    fun provideRepository(
        context: Context,
        preferences: SharedPreferences,
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore,
        storage: FirebaseStorage,
        dao: AppDao
    ) = Repository.getInstance(
        RemoteRepository.getInstance(firebaseAuth, firestore, storage),
        LocalRepository.getInstance(context, preferences, dao)
    )
}