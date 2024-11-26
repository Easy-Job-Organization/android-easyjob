package com.easyjob.jetpack.viewmodels

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.models.City
import com.easyjob.jetpack.models.SpecialitiesResponse
import com.easyjob.jetpack.repositories.EditProfessionalProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfessionalProfileViewModel @Inject constructor(
    private val repository: EditProfessionalProfileRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    // Estados del formulario
    private val _name = MutableLiveData("")
    val name: LiveData<String> get() = _name

    private val _lastName = MutableLiveData("")
    val lastName: LiveData<String> get() = _lastName

    private val _phoneNumber = MutableLiveData("")
    val phoneNumber: LiveData<String> get() = _phoneNumber

    private val _cityId = MutableLiveData("")
    val cityId: LiveData<String> get() = _cityId

    private val _specialityId = MutableLiveData("")
    val specialityId: LiveData<String> get() = _specialityId

    private val _professionalImage = MutableLiveData<Uri?>()
    val professionalImage: LiveData<Uri?> get() = _professionalImage

    // Datos adicionales
    private val _specialities = MutableLiveData<List<SpecialitiesResponse>>()
    val specialities: LiveData<List<SpecialitiesResponse>> get() = _specialities

    private val _cities = MutableLiveData<List<City>>()
    val cities: LiveData<List<City>> get() = _cities

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

    fun onCityIdChange(newCityId: String) {
        _cityId.value = newCityId
    }

    fun onSpecialityIdChange(newSpecialityId: String) {
        _specialityId.value = newSpecialityId
    }

    fun onProfessionalImageChange(newImageUri: Uri?) {
        _professionalImage.value = newImageUri
    }

    // Cargar especialidades desde el repositorio
    fun fetchSpecialities() {
        viewModelScope.launch {
            val response = repository.getSpecialities()
            if (response.isSuccessful) {
                _specialities.value = response.body()
            } else {
                Log.e("EditProfessionalVM", "Error al obtener especialidades: ${response.errorBody()?.string()}")
            }
        }
    }

    // Cargar ciudades desde el repositorio
    fun fetchCities() {
        viewModelScope.launch {
            val response = repository.getCities()
            if (response.isSuccessful) {
                _cities.value = response.body()
            } else {
                Log.e("EditProfessionalVM", "Error al obtener ciudades: ${response.errorBody()?.string()}")
            }
        }
    }

    fun fetchCurrentProfessional() {
        viewModelScope.launch {
            val userId = getUserId()
            if (userId == null) {
                Log.e("EditProfessionalVM", "No se pudo obtener el ID del usuario")
                return@launch
            }

            try {
                val response = repository.getCurrentProfessional(userId)
                if (response.isSuccessful) {
                    val professional = response.body()
                    professional?.let {
                        _name.value = it.name
                        _lastName.value = it.last_name
                        _phoneNumber.value = it.phone_number
                        _cityId.value = it.cities[0].id
                        _specialityId.value = it.specialities[0].id
                        _professionalImage.value = Uri.parse(it.photo_url)
                    }
                } else {
                    Log.e("EditProfessionalVM", "Error al obtener datos del profesional: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("EditProfessionalVM", "Excepción al obtener datos del profesional: ${e.message}")
            }
        }
    }

    // Editar perfil del profesional
    fun editProfessionalProfile(imageUri: Uri?, contentResolver: ContentResolver) {
        viewModelScope.launch {
            val name = _name.value.orEmpty()
            val lastName = _lastName.value.orEmpty()
            val phoneNumber = _phoneNumber.value.orEmpty()
            val city = _cityId.value.orEmpty()
            val speciality = _specialityId.value.orEmpty()

            if (name.isBlank() || lastName.isBlank() || phoneNumber.isBlank() || city.isBlank() || speciality.isBlank() || imageUri == null) {
                Log.e("EditProfessionalVM", "Campos vacíos o imagen no seleccionada")
                _updateResult.value = false
                return@launch
            }

            val userId = getUserId()
            if (userId == null) {
                Log.e("EditProfessionalVM", "No se pudo obtener el ID del usuario")
                _updateResult.value = false
                return@launch
            }

            try {
                val response = repository.editProfessional(
                    userId, name, lastName, phoneNumber, imageUri, city, speciality, contentResolver
                )
                _updateResult.value = response?.isSuccessful == true
            } catch (e: Exception) {
                Log.e("EditProfessionalVM", "Error al actualizar perfil: ${e.message}")
                _updateResult.value = false
            }
        }
    }

    fun resetUpdateResult() {
        _updateResult.value = null
    }

}
