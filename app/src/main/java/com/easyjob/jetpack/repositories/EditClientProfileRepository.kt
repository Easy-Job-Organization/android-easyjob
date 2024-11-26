package com.easyjob.jetpack.repositories

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import com.easyjob.jetpack.models.Client
import com.easyjob.jetpack.services.EditClientProfileService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

interface EditClientProfileRepository {

    suspend fun editClient(
        id: String,
        name: String,
        last_name: String,
        phone_number: String,
        client_image: Uri,
        contentResolver: ContentResolver
    ): Response<Unit>?

    suspend fun getCurrentClient(id: String): Response<Client>
}

class EditClientProfileRepositoryImpl @Inject constructor(
    private val service : EditClientProfileService
): EditClientProfileRepository{
    override suspend fun editClient(
        id: String,
        name: String,
        last_name: String,
        phone_number: String,
        client_image: Uri,
        contentResolver: ContentResolver
    ): Response<Unit>? {

        // Convert fields to RequestBody
        val namePart = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
        val lastNamePart = RequestBody.create("text/plain".toMediaTypeOrNull(), last_name)
        val phoneNumberPart = RequestBody.create("text/plain".toMediaTypeOrNull(), phone_number)

        val imageName = "client_image"

        val imageStream = contentResolver.openInputStream(client_image)
        val imagePart = imageStream?.let {
            MultipartBody.Part.createFormData(
                imageName,
                File(client_image.path!!).name,
                it.readBytes().toRequestBody("image/*".toMediaTypeOrNull())
            )
        }

        val res = imagePart?.let {
            service.editClient(id, namePart, lastNamePart, phoneNumberPart,
                it)
        }

        Log.d("EditProfessionalProfileRepository", ">>> Response - $res")
        return res
    }

    override suspend fun getCurrentClient(id: String): Response<Client>{
        val res = service.getCurrentClient(id)

        return res
    }

}