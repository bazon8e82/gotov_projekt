package com.example.aseefm.networking

import com.example.aseefm.networking.model.ArtistImage
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface MyApiService {
    @POST("upload")
    suspend fun addImageForStation(@Body requestBody: RequestBody): Response<ResponseBody>

    @GET("download")
    suspend fun getArtistImages(@Query("artistName") artistName: String): List<ArtistImage>

    @DELETE("images/{imageId}")
    suspend fun deleteImage(@Path("imageId") imageId: String): Response<ResponseBody>
}