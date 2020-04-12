package com.anangkur.mediku.feature.profile.editProfile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.auth.User
import com.anangkur.mediku.util.Const
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfileViewModel(private val repository: Repository): ViewModel() {

    var user: User? = null

    val progressEditProfile = MutableLiveData<Boolean>()
    val successEditProfile = MutableLiveData<Void>()
    val errorEditProfile = MutableLiveData<String>()
    fun editProfile(user: User){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                progressEditProfile.postValue(true)
                val userFirebase = repository.remoteRepository.firebaseAuth.currentUser
                repository.remoteRepository.firestore
                    .collection(Const.COLLECTION_USER)
                    .document(userFirebase?.uid?:"")
                    .set(user)
                    .addOnSuccessListener { result ->
                        progressEditProfile.postValue(false)
                        successEditProfile.postValue(result)
                    }
                    .addOnFailureListener { exeption ->
                        progressEditProfile.postValue(false)
                        errorEditProfile.postValue(exeption.message)
                    }
            }catch (e: Exception){
                progressEditProfile.postValue(false)
                errorEditProfile.postValue(e.message)
            }
        }
    }

    val progressGetProfile = MutableLiveData<Boolean>()
    val successGetProfile = MutableLiveData<User>()
    val errorGetProfile = MutableLiveData<String>()
    fun getUserProfile(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = repository.remoteRepository.firebaseAuth.currentUser
                progressGetProfile.postValue(true)
                repository.remoteRepository.firestore
                    .collection(Const.COLLECTION_USER)
                    .document(user?.uid?:"")
                    .get()
                    .addOnSuccessListener { result ->
                        progressGetProfile.postValue(false)
                        successGetProfile.postValue(result.toObject<User>())
                    }
                    .addOnFailureListener { exception ->
                        progressGetProfile.postValue(false)
                        errorGetProfile.postValue(exception.message)
                    }
            }catch (e: Exception){
                progressGetProfile.postValue(false)
                errorGetProfile.postValue(e.message)
            }
        }
    }

    val progressUploadImage = MutableLiveData<Boolean>()
    val successUploadImage = MutableLiveData<Uri>()
    fun uploadImage(image: Uri){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                progressUploadImage.postValue(true)
                val fileName = image.lastPathSegment?:""
                val extension = fileName.substring(fileName.lastIndexOf("."))
                val user = repository.remoteRepository.firebaseAuth.currentUser
                val storageReference = repository.remoteRepository.storage.reference
                    .child(Const.STORAGE_PROFILE_PHOTO)
                    .child(user?.uid?:"")
                    .child("${user?.uid}$extension")
                storageReference.putFile(image)
                    .addOnProgressListener {
                        progressUploadImage.postValue(true)
                    }.continueWithTask { task ->
                        if (!task.isSuccessful) {
                            throw task.exception!!
                        }
                        storageReference.downloadUrl
                    }.addOnSuccessListener {
                        progressUploadImage.postValue(false)
                        successUploadImage.postValue(it)
                    }
                    .addOnFailureListener {
                        progressUploadImage.postValue(false)
                        errorEditProfile.postValue(it.message)
                    }
            }catch (e: Exception){
                progressUploadImage.postValue(false)
                errorEditProfile.postValue(e.message)
            }
        }
    }
}