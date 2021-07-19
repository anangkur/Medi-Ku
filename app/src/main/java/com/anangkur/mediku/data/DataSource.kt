package com.anangkur.mediku.data

import android.net.Uri
import androidx.lifecycle.LiveData
import com.anangkur.mediku.base.BaseFirebaseListener
import com.anangkur.mediku.data.model.BaseResult
import com.anangkur.mediku.data.model.auth.User
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.data.model.menstrual.MenstrualPeriodMonthly
import com.anangkur.mediku.data.model.menstrual.MenstrualPeriodResume
import com.anangkur.mediku.data.model.newCovid19.NewCovid19Country
import com.anangkur.mediku.data.model.newCovid19.NewCovid19Summary
import com.anangkur.mediku.data.model.news.Article
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

interface DataSource {

    /**
     * Firebase
     */
    // auth
    suspend fun getUser(user: FirebaseUser, listener: BaseFirebaseListener<User?>) {}
    suspend fun getUser(listener: BaseFirebaseListener<User?>) {}
    suspend fun createUser(user: FirebaseUser, firebaseToken: String, listener: BaseFirebaseListener<User>) {}
    suspend fun signInWithGoogle(acct: GoogleSignInAccount?, listener: BaseFirebaseListener<FirebaseUser>) {}
    suspend fun signInEmail(email: String, password: String, listener: BaseFirebaseListener<FirebaseUser?>) {}
    suspend fun signUp(name: String, email: String, password: String, listener: BaseFirebaseListener<FirebaseUser>) {}
    suspend fun resetPassword(email: String, listener: BaseFirebaseListener<String>) {}
    suspend fun editPassword(newPassword: String, listener: BaseFirebaseListener<Boolean>) {}
    suspend fun reAuthenticate(oldPassword: String, listener: BaseFirebaseListener<Boolean>) {}
    suspend fun logout(listener: BaseFirebaseListener<Boolean>) {}
    suspend fun editProfile(user: User, listener: BaseFirebaseListener<User>) {}
    suspend fun uploadImage(image: Uri, listener: BaseFirebaseListener<Uri>) {}
    suspend fun checkUserLogin(listener: BaseFirebaseListener<Boolean>) {}

    // medicalRecord
    suspend fun getMedicalRecords(listener: BaseFirebaseListener<List<MedicalRecord>>) {}
    suspend fun addMedicalRecord(medicalRecord: MedicalRecord, listener: BaseFirebaseListener<MedicalRecord>) {}
    suspend fun uploadDocument(document: Uri, listener: BaseFirebaseListener<Uri>) {}

    // menstrualPeriod
    suspend fun getMenstrualPeriod(year: String, listener: BaseFirebaseListener<MenstrualPeriodMonthly>) {}
    suspend fun addMenstrualPeriod(menstrualPeriodResume: MenstrualPeriodResume, listener: BaseFirebaseListener<MenstrualPeriodResume>) {}

    /**
     * Preferences
     */
    // firebase token
    fun saveFirebaseToken(firebaseToken: String) { throw Exception() }
    fun loadFirebaseToken(): String { throw Exception() }

    // country
    fun saveCountry(country: String){}
    fun loadCountry(): String { throw Exception() }

    /**
     * Room
     */
    // news
    suspend fun insertDataNews(data: List<Article>) { throw Exception() }
    fun getAllDataByCategory(category: String): LiveData<List<Article>> { throw Exception() }

    // NewCovid19Summary
    suspend fun insertDataSummary(data: List<NewCovid19Summary>) {}
    fun getNewCovid19SummaryAll(): LiveData<List<NewCovid19Summary>> { throw Exception() }
    fun getNewCovid19SummaryTopCountry(): LiveData<List<NewCovid19Summary>> { throw Exception() }
    fun getNewCovid19SummaryByCountry(country: String): LiveData<List<NewCovid19Summary>> { throw Exception() }

    // NewCovid19DataCountry
    suspend fun insertDataCountry(data: List<NewCovid19Country>) {}
    fun getNewCovid19CountryByCountry(country: String): LiveData<List<NewCovid19Country>> { throw Exception() }

    /**
     * Retrofit
     */
    // news
    suspend fun getTopHeadlinesNews(apiKey: String?, country: String?, category: String?, sources: String?, q: String?): BaseResult<List<Article>?> { throw Exception() }

    // NewCovid19Summary
    suspend fun getSummary(): BaseResult<List<NewCovid19Summary>> { throw Exception() }
    suspend fun getDataCovid19ByCountry(country: String, status: String): BaseResult<List<NewCovid19Country>> { throw Exception() }
}