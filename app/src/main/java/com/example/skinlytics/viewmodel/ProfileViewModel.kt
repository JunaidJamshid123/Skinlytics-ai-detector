 package com.example.skinlytics.viewmodel

import androidx.lifecycle.ViewModel
import com.example.skinlytics.model.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {
    private val _userName = MutableStateFlow(repository.getUserName())
    val userName: StateFlow<String> = _userName

    private val _userEmail = MutableStateFlow(repository.getUserEmail())
    val userEmail: StateFlow<String> = _userEmail
}