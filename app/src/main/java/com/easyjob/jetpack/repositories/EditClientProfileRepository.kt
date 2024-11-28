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

        val namePart = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
        val lastNamePart = RequestBody.create("text/plain".toMediaTypeOrNull(), last_name)
        val phoneNumberPart = RequestBody.create("text/plain".toMediaTypeOrNull(), phone_number)

        val imagePart: MultipartBody.Part? = if (client_image.scheme == "content" || client_image.scheme == "file") {
            val imageStream = contentResolver.openInputStream(client_image)
            imageStream?.let {
                MultipartBody.Part.createFormData(
                    "client_image",
                    File(client_image.path!!).name,
                    it.readBytes().toRequestBody("image/*".toMediaTypeOrNull())
                )
            }
        } else {
            // Si la imagen no es local (ejemplo: URI remota), no se incluye en el multipart
            Log.d("EditClientProfileRepository", "La imagen no es local, omitiendo el envío")
            null
        }

        return imagePart?.let {
            service.editClient(id, namePart, lastNamePart, phoneNumberPart, it)
        } ?: service.editClient(id, namePart, lastNamePart, phoneNumberPart, null)
    }

    override suspend fun getCurrentClient(id: String): Response<Client>{
        val res = service.getCurrentClient(id)

        return res
    }

}