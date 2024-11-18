package com.easyjob.jetpack.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.repositories.RecoverPassRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecoverPassViewModel @Inject constructor(
    private val repo: RecoverPassRepository
) : ViewModel() {

    fun recoverPass(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.recoverPass(email)
            Log.e("RecoverPassViewModel", "$response")
        }
    }
}