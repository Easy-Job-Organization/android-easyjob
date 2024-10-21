package com.easyjob.jetpack.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.repositories.SearchScreenRepository
import com.easyjob.jetpack.repositories.SearchScreenRepositoryImpl
import com.easyjob.jetpack.services.ProfessionalCardResponse
import com.easyjob.jetpack.services.ProfessionalSearchScreenResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchScreenViewModel(
    private val repo: SearchScreenRepository = SearchScreenRepositoryImpl()
): ViewModel() {
    val professionalCards = MutableLiveData<List<ProfessionalCardResponse>>()
    val searchResult = MutableLiveData<ProfessionalSearchScreenResponse?>()

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
}