package com.easyhz.patchnote.data.di

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseApp(): FirebaseApp {
        return FirebaseApp.getInstance()
    }

    @Provides
    @Singleton
    fun provideFireStore(
        firebaseApp: FirebaseApp
    ): FirebaseFirestore {
        return FirebaseFirestore.getInstance(firebaseApp)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(
        firebaseApp: FirebaseApp
    ): FirebaseAuth {
        return FirebaseAuth.getInstance(firebaseApp)
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(
        firebaseApp: FirebaseApp
    ): FirebaseStorage {
        return FirebaseStorage.getInstance(firebaseApp)
    }
}