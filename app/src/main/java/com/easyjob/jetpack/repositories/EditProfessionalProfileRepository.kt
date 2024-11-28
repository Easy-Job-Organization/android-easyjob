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
        professional_image: Uri?,
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
        professional_image: Uri?,
        city: String,
        speciality: String,
        contentResolver: ContentResolver
    ): Response<Unit>? {
        val namePart = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val lastNamePart = last_name.toRequestBody("text/plain".toMediaTypeOrNull())
        val phoneNumberPart = phone_number.toRequestBody("text/plain".toMediaTypeOrNull())
        val cityPart = city.toRequestBody("text/plain".toMediaTypeOrNull())
        val specialityPart = speciality.toRequestBody("text/plain".toMediaTypeOrNull())

        val imagePart = if (professional_image != null &&
            (professional_image.scheme == "content" || professional_image.scheme == "file")) {
            val imageStream = contentResolver.openInputStream(professional_image)
            imageStream?.let {
                MultipartBody.Part.createFormData(
                    "professional_image",
                    File(professional_image.path!!).name,
                    it.readBytes().toRequestBody("image/*".toMediaTypeOrNull())
                )
            }
        } else {
            null
        }

        return try {
            imagePart?.let {
                service.editProfessional(
                    id, namePart, lastNamePart, phoneNumberPart, it, cityPart, specialityPart
                )
            } ?: service.editProfessional(id, namePart, lastNamePart, phoneNumberPart, null, cityPart, specialityPart)
        } catch (e: Exception) {
            Log.e("EditProfessionalRepo", "Error al enviar solicitud: ${e.message}")
            null
        }
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