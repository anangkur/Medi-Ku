package com.anangkur.mediku.data

import com.anangkur.mediku.data.model.BasePagination
import com.anangkur.mediku.data.model.BaseResponse
import com.anangkur.mediku.data.model.Result
import com.anangkur.mediku.data.model.auth.Register
import com.anangkur.mediku.data.model.auth.ResponseLogin
import com.anangkur.mediku.data.model.product.Category
import com.anangkur.mediku.data.model.product.DetailProduct
import com.anangkur.mediku.data.model.product.Product
import com.anangkur.mediku.data.model.profile.ResponseUser

interface DataSource {
    suspend fun postLogin(email: String, password: String): Result<ResponseLogin>{throw Exception()}
    suspend fun postSignup(name: String, email: String, password: String, passwordConfirm: String): Result<BaseResponse<Register>>{throw Exception()}
    suspend fun getProfile(token: String): Result<ResponseUser>{throw Exception()}
    suspend fun getListProduct(token: String, category: Int?, page: Int?): Result<BaseResponse<BasePagination<Product>>>{throw Exception()}
    suspend fun getListCategory(token: String): Result<BaseResponse<List<Category>>>{throw Exception()}
    suspend fun getDetailProduct(token: String, productId: String): Result<BaseResponse<DetailProduct>>{throw Exception()}

    fun saveApiToken(apiToken: String){ throw Exception() }
    fun loadApiToken(): String? { throw Exception() }
    fun deleteApiToken(){ throw Exception() }

}