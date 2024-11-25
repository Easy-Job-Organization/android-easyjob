package com.easyjob.jetpack.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.repositories.SearchScreenRepository
import com.easyjob.jetpack.repositories.SearchScreenRepositoryImpl
import com.easyjob.jetpack.services.ProfessionalCardResponse
import com.easyjob.jetpack.services.ProfessionalCardResponseWithoutCity
import com.easyjob.jetpack.services.ProfessionalSearchScreenResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val repo: SearchScreenRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {
    val professionalCards = MutableLiveData<List<ProfessionalCardResponse>>()
    val searchResult = MutableLiveData<ProfessionalSearchScreenResponse?>()
    val searchResult2 = MutableLiveData<List<ProfessionalCardResponseWithoutCity>?>()
    val userName = MutableLiveData<String?>();

    init {
        loadUserName() // Carga el nombre del usuario al inicializar el ViewModel
    }

    private fun loadUserName() {
        viewModelScope.launch {
            userPreferencesRepository.nameFlow.collect { name ->
                userName.value = name // Asigna el nombre del usuario a LiveData
            }
        }
    }

    fun loadProfessionalCards(){
        viewModelScope.launch(Dispatchers.IO) {
            val professionalCardsList = repo.fetchProfesionalCards()
            withContext(Dispatchers.Main) {
                professionalCards.value = professionalCardsList
            }
        }
    }

    fun loadSearchResults(city:String, speciality:String){
        viewModelScope.launch(Dispatchers.IO) {
            val results = repo.fetchProfesionalCardsSearch(city,speciality)
            withContext(Dispatchers.Main){
                searchResult.value = results
            }
        }
    }

    fun loadSearch(speciality: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val results = repo.fetchProfesionalSearch(speciality)
            withContext(Dispatchers.Main) {
                searchResult2.value = results
            }
        }
    }
}