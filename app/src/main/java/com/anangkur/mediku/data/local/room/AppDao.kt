package com.anangkur.mediku.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anangkur.mediku.data.local.model.news.ArticleLocalModel
import com.anangkur.mediku.data.model.newCovid19.NewCovid19DataCountry
import com.anangkur.mediku.data.model.newCovid19.NewCovid19Summary

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataSummary(data: List<NewCovid19Summary>)

    @Query("SELECT * FROM newcovid19summary")
    fun getNewCovid19SummaryAll(): LiveData<List<NewCovid19Summary>>

    @Query("SELECT * FROM newcovid19summary ORDER BY TotalConfirmed DESC LIMIT 10")
    fun getNewCovid19SummaryTopCountry(): LiveData<List<NewCovid19Summary>>

    @Query("SELECT * FROM newcovid19summary WHERE Country = :country LIMIT 1")
    fun getNewCovid19SummaryByCountry(country: String): LiveData<List<NewCovid19Summary>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataCountry(data: List<NewCovid19DataCountry>)

    @Query("SELECT * FROM newcovid19datacountry WHERE Country = :country ORDER BY Date ASC")
    fun getNewCovid19CountryByCountry(country: String): LiveData<List<NewCovid19DataCountry>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataNews(data: List<ArticleLocalModel>)

    @Query("SELECT * FROM ArticleLocalModel WHERE category = :category")
    fun getAllDataByCategory(category: String): LiveData<List<ArticleLocalModel>>
}