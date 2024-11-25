package com.easyjob.jetpack.repositories

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import com.easyjob.jetpack.models.City
import com.easyjob.jetpack.models.Professional
import com.easyjob.jetpack.models.SpecialitiesResponse
import com.easyjob.jetpack.services.EditProfessionalProfileService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

interface EditProfessionalProfileRepository {

    suspend fun editProfessional(
        id: String,
        name: String,
        last_name: String,
        phone_number: String,
        professional_image: Uri,
        city: String,
        speciality: String,
        contentResolver: ContentResolver
    ): Response<Unit>?

    suspend fun getSpecialities(): Response<List<SpecialitiesResponse>>

    suspend fun getCities(): Response<List<City>>

    suspend fun getCurrentProfessional(id: String): Response<Professional>
}

class EditProfessionalProfileRepositoryImpl @Inject constructor(
    private val service : EditProfessionalProfileService
): EditProfessionalProfileRepository{
    override suspend fun editProfessional(
        id: String,
        name: String,
        last_name: String,
        phone_number: String,
        professional_image: Uri,
        city: String,
        speciality: String,
        contentResolver: ContentResolver
    ): Response<Unit>? {

        // Convert fields to RequestBody
        val namePart = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
        val lastNamePart = RequestBody.create("text/plain".toMediaTypeOrNull(), last_name)
        val phoneNumberPart = RequestBody.create("text/plain".toMediaTypeOrNull(), phone_number)
        val cityPart = RequestBody.create("text/plain".toMediaTypeOrNull(), city)
        val specialityPart = RequestBody.create("text/plain".toMediaTypeOrNull(), speciality)

        val imageName = "professional_image"

        val imageStream = contentResolver.openInputStream(professional_image)
        val imagePart = imageStream?.let {
            MultipartBody.Part.createFormData(
                imageName,
                File(professional_image.path!!).name,
                it.readBytes().toRequestBody("image/*".toMediaTypeOrNull())
            )
        }

        val res = imagePart?.let {
            service.editProfessional(id, namePart, lastNamePart, phoneNumberPart,
                it, cityPart, specialityPart)
        }

        Log.d("EditProfessionalProfileRepository", ">>> Response - $res")
        return res
    }

    override suspend fun getCurrentProfessional(id: String): Response<Professional>{
        val res = service.getCurrentProfessional(id)

        return res
    }

    override suspend fun getSpecialities(): Response<List<SpecialitiesResponse>> {
        val res = service.getSpecialities()
        return res
    }

    override suspend fun getCities(): Response<List<City>> {
        val res = service.getCities()
        return res
    }

}