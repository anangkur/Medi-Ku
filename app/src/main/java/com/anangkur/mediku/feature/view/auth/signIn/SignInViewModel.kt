package com.anangkur.mediku.feature.view.auth.signIn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.base.BaseFirebaseListener
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.auth.User
import com.anangkur.mediku.feature.mapper.UserMapper
import com.anangkur.mediku.feature.model.auth.UserIntent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: Repository): ViewModel() {

    private val userMapper = UserMapper.getInstance()

    val progressSignInLive = MutableLiveData<Boolean>()
    val resultSignInLive = MutableLiveData<FirebaseUser>()
    val errorSignInLive = MutableLiveData<String>()
    fun firebaseSignIn(email: String, password: String){
        CoroutineScope(Dispatchers.IO).launch{
            repository.signInWithEmail(email, password, object: BaseFirebaseListener<FirebaseUser?>{
                override fun onLoading(isLoading: Boolean) {
                    progressSignInLive.postValue(isLoading)
                }
                override fun onSuccess(data: FirebaseUser?) {
                    resultSignInLive.postValue(data)
                }
                override fun onFailed(errorMessage: String) {
                    errorSignInLive.postValue(errorMessage)
                }
            })
        }
    }

    val progressSignInGoogleLive = MutableLiveData<Boolean>()
    fun firebaseSignInWithGoogle(acct: GoogleSignInAccount?) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.signInWithGoogle(acct, object: BaseFirebaseListener<FirebaseUser>{
                override fun onLoading(isLoading: Boolean) {
                    progressSignInGoogleLive.postValue(isLoading)
                }
                override fun onSuccess(data: FirebaseUser) {
                    getUser(data)
                }
                override fun onFailed(errorMessage: String) {
                    errorSignInLive.postValue(errorMessage)
                }
            })
        }
    }

    val successCreateUser = MutableLiveData<UserIntent>()
    private fun createUser(user: FirebaseUser){
        CoroutineScope(Dispatchers.IO).launch {
            repository.createUser(user, loadFirebaseToken(), object: BaseFirebaseListener<User>{
                override fun onLoading(isLoading: Boolean) {
                    progressSignInGoogleLive.postValue(isLoading)
                }
                override fun onSuccess(data: User) {
                    successCreateUser.postValue(userMapper.mapToIntent(data))
                }
                override fun onFailed(errorMessage: String) {
                    errorSignInLive.postValue(errorMessage)
                }
            })
        }
    }

    private fun getUser(user: FirebaseUser){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getUser(user, object: BaseFirebaseListener<User?>{
                override fun onLoading(isLoading: Boolean) {
                    progressSignInGoogleLive.postValue(isLoading)
                }
                override fun onSuccess(data: User?) {
                    if (data == null){
                        createUser(user)
                    }else{
                        successCreateUser.postValue(userMapper.mapToIntent(data))
                    }
                }
                override fun onFailed(errorMessage: String) {
                    errorSignInLive.postValue(errorMessage)
                }
            })
        }
    }

    private fun loadFirebaseToken(): String {
        return repository.loadFirebaseToken()
    }
}