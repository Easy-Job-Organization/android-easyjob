package com.easyjob.jetpack.repositories

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.easyjob.jetpack.services.AuthService
import com.easyjob.jetpack.services.AuthServiceImpl
import com.easyjob.jetpack.services.LoginRequest
import com.easyjob.jetpack.services.LoginResponse
import com.easyjob.jetpack.services.RegisterResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File


interface AuthRepository {
    suspend fun signIn(email: String, password: String): Response<LoginResponse>

    suspend fun signUp(
        name: String,
        last_name: String,
        email: String,
        phone_number: String,
        password: String,
        option: String,
        imageUri: Uri,
        contentResolver: ContentResolver
    ): Response<RegisterResponse>
}

class AuthRepositoryImpl(
    val authService: AuthService = AuthServiceImpl()
) : AuthRepository {
    override suspend fun signIn(email: String, password: String): Response<LoginResponse> {
        val res = authService.loginWithEmailAndPasswordClient(LoginRequest(email, password))
        Log.e(">>>", "The response is: ${res.body()}")
        return res
    }

    override suspend fun signUp(
        name: String,
        last_name: String,
        email: String,
        phone_number: String,
        password: String,
        option: String,
        imageUri: Uri,
        contentResolver: ContentResolver // Recibe ContentResolver
    ): Response<RegisterResponse> {

        // Convert fields to RequestBody
        val namePart = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
        val lastNamePart = RequestBody.create("text/plain".toMediaTypeOrNull(), last_name)
        val emailPart = RequestBody.create("text/plain".toMediaTypeOrNull(), email)
        val phoneNumberPart = RequestBody.create("text/plain".toMediaTypeOrNull(), phone_number)
        val passwordPart = RequestBody.create("text/plain".toMediaTypeOrNull(), password)

        val imageName = if (option == "Cliente") "client_image" else "professional_image"

        // Abrir el InputStream para la imagen
        val imageStream = contentResolver.openInputStream(imageUri)
        val imagePart = imageStream?.let {
            MultipartBody.Part.createFormData(
                imageName,
                File(imageUri.path!!).name,
                it.readBytes().toRequestBody("image/*".toMediaTypeOrNull())
            )
        }

        Log.e(">>> Uri", "$imageUri")
        Log.e(">>> imageName", "$imageName")
        Log.e(">>> imagePart", "$imagePart")

        val res: Response<RegisterResponse>
        if (option == "Cliente") {
            res = imagePart?.let {
                authService.registerClient(
                    namePart,
                    lastNamePart,
                    emailPart,
                    phoneNumberPart,
                    passwordPart,
                    it
                )
            }!!
        } else {
            res = imagePart?.let {
                authService.registerProfessional(
                    namePart,
                    lastNamePart,
                    emailPart,
                    phoneNumberPart,
                    passwordPart,
                    it
                )
            }!!
        }

        Log.e(">>>", "The response is: ${res.body()}")
        return res
    }

}