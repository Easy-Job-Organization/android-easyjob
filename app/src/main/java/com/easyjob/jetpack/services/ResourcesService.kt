package com.easyjob.jetpack.services

import android.util.Log
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class City(val id: String, val city_name: String)
data class Language(val id: String, val language_name: String)
data class Service(val id: String, val title: String, val description: String, val price: Double)
data class Speciality(val id: String, val speciality_name: String)

//data class CitiesResponse(val cities: List<City>)
//data class LanguagesResponse(val languages: List<Language>)
//data class ServicesResponse(val services: List<Service>)
//data class SpecialityResponse(val specialities: List<Speciality>)

typealias CitiesResponse = List<City>
typealias LanguagesResponse = List<Language>
typealias ServicesResponse = List<Service>
typealias SpecialityResponse = List<Speciality>


interface ResourcesService {
    @GET("city")
    suspend fun getCities(): Response<CitiesResponse>

    @GET("language")
    suspend fun getLanguages(): Response<LanguagesResponse>

    @GET("services")
    suspend fun getServices(): Response<ServicesResponse>

    @GET("specialities")
    suspend fun getSpecialities(): Response<SpecialityResponse>

}

class ResourcesServiceImpl : ResourcesService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.easyjob.com.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ResourcesService = retrofit.create(ResourcesService::class.java)

    override suspend fun getCities(): Response<CitiesResponse> {
        val res = apiService.getCities()
        Log.d("getCities Response", "$res")
        return res
    }

    override suspend fun getLanguages(): Response<LanguagesResponse> {
        val res = apiService.getLanguages()
        Log.d("getLanguages Response", "$res")
        return res
    }

    override suspend fun getServices(): Response<ServicesResponse> {
        val res = apiService.getServices()
        Log.d("getServices Response", "$res")
        return res
    }

    override suspend fun getSpecialities(): Response<SpecialityResponse> {
        val res = apiService.getSpecialities()
        Log.d("getSpecialities Response", "$res")
        return res
    }
}