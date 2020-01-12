package com.anangkur.mediku.data.remote

import com.anangkur.mediku.base.BaseDataSource
import com.anangkur.mediku.data.DataSource
import com.anangkur.mediku.data.model.BasePagination
import com.anangkur.mediku.data.model.BaseResponse
import com.anangkur.mediku.data.model.Result
import com.anangkur.mediku.data.model.auth.Register
import com.anangkur.mediku.data.model.auth.ResponseLogin
import com.anangkur.mediku.data.model.product.Category
import com.anangkur.mediku.data.model.product.DetailProduct
import com.anangkur.mediku.data.model.product.Product
import com.anangkur.mediku.data.model.profile.ResponseUser

class RemoteRepository: DataSource, BaseDataSource() {

    override suspend fun getListProduct(token: String, category: Int?, page: Int?): Result<BaseResponse<BasePagination<Product>>> {
        return getResult { ApiService.getApiService.getListProduct(token, category, page) }
    }

    override suspend fun getListCategory(token: String): Result<BaseResponse<List<Category>>> {
        return getResult { ApiService.getApiService.getListCategory(token) }
    }

    override suspend fun getDetailProduct(token: String, productId: String): Result<BaseResponse<DetailProduct>> {
        return getResult { ApiService.getApiService.getDetailProduct(token, productId) }
    }

    override suspend fun postLogin(email: String, password: String): Result<ResponseLogin> {
        return getResult { ApiService.getApiService.postLogin(email, password) }
    }

    override suspend fun postSignup(
        name: String,
        email: String,
        password: String,
        passwordConfirm: String
    ): Result<BaseResponse<Register>> {
        return getResult { ApiService.getApiService.postRegister(name, email, password, passwordConfirm) }
    }

    override suspend fun getProfile(token: String): Result<ResponseUser> {
        return getResult { ApiService.getApiService.getUserProfile(token) }
    }

    companion object{
        private var INSTANCE: RemoteRepository? = null
        fun getInstance() = INSTANCE ?: RemoteRepository()
    }
}