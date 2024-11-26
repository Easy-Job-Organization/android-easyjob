package com.easyjob.jetpack.viewmodels

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.models.Client
import com.easyjob.jetpack.repositories.EditClientProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class EditClientProfileViewModel @Inject constructor(
    private val repository: EditClientProfileRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    // Estados del formulario
    private val _name = MutableLiveData("")
    val name: LiveData<String> get() = _name

    private val _lastName = MutableLiveData("")
    val lastName: LiveData<String> get() = _lastName

    private val _phoneNumber = MutableLiveData("")
    val phoneNumber: LiveData<String> get() = _phoneNumber

    private val _clientImage = MutableLiveData<Uri?>()
    val clientImage: LiveData<Uri?> get() = _clientImage

    private val _updateResult = MutableLiveData<Boolean?>(null)
    val updateResult: LiveData<Boolean?> get() = _updateResult

    // Obtener el ID del usuario desde UserPreferencesRepository
    private suspend fun getUserId(): String? {
        return userPreferencesRepository.userIdFlow.firstOrNull()
    }

    // Métodos para actualizar los valores de los campos
    fun onNameChange(newName: String) {
        _name.value = newName
    }

    fun onLastNameChange(newLastName: String) {
        _lastName.value = newLastName
    }

    fun onPhoneNumberChange(newPhoneNumber: String) {
        _phoneNumber.value = newPhoneNumber
    }

    fun onClientImageChange(newImageUri: Uri?) {
        _clientImage.value = newImageUri
    }

    // Cargar datos del cliente desde el repositorio
    fun fetchCurrentClient() {
        viewModelScope.launch {
            val userId = getUserId()
            if (userId == null) {
                Log.e("EditClientVM", "No se pudo obtener el ID del usuario")
                return@launch
            }

            try {
                val response = repository.getCurrentClient(userId)
                if (response.isSuccessful) {
                    val client = response.body()
                    client?.let {
                        _name.value = it.name
                        _lastName.value = it.last_name
                        _phoneNumber.value = it.phone_number
                        if (it.photo_url.isNotBlank()) {
                            _clientImage.value = Uri.parse(it.photo_url)
                        } else {
                            Log.e("EditClientVM", "URL de la imagen es nula o vacía")
                        }
                        Log.d("_clientImage.value", _clientImage.value.toString())
                    }
                } else {
                    Log.e(
                        "EditClientVM",
                        "Error al obtener datos del cliente: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("EditClientVM", "Excepción al obtener datos del cliente: ${e.message}")
            }
        }
    }

    // Editar perfil del cliente
    fun editClientProfile(imageUri: Uri?, contentResolver: ContentResolver) {
        viewModelScope.launch {
            val name = _name.value.orEmpty()
            val lastName = _lastName.value.orEmpty()
            val phoneNumber = _phoneNumber.value.orEmpty()

            if (name.isBlank() || lastName.isBlank() || phoneNumber.isBlank() || imageUri == null) {
                Log.e("EditClientVM", "Campos vacíos o imagen no seleccionada")
                _updateResult.value = false
                return@launch
            }

            val userId = getUserId()
            if (userId == null) {
                Log.e("EditClientVM", "No se pudo obtener el ID del usuario")
                _updateResult.value = false
                return@launch
            }

            val response = repository.editClient(
                userId,
                name,
                lastName,
                phoneNumber,
                imageUri,
                contentResolver
            )
            _updateResult.value = response?.isSuccessful == true

            if (response?.isSuccessful == false) {
                Log.e(
                    "EditClientVM",
                    "Error al actualizar perfil: ${response.errorBody()?.string()}"
                )
            }
        }
    }

    fun resetUpdateResult() {
        _updateResult.value = null
    }
}
