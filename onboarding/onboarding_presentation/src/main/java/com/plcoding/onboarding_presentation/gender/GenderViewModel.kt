package com.plcoding.onboarding_presentation.gender

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.core.domain.model.Gender
import com.plcoding.core.domain.preferences.Preferences
import com.plcoding.core.navigation.Route
import com.plcoding.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenderViewModel @Inject constructor(
    private val preference: Preferences
) : ViewModel() {
    var selectedGender = mutableStateOf<Gender>(Gender.Male)
        private set

    private val _uiEvent= Channel<UiEvent>()
    val uiEvent=_uiEvent.receiveAsFlow()

    fun onGenderClick(gender: Gender){
        selectedGender.value=gender
    }
    fun onNextClick(){
        viewModelScope.launch {
            preference.saveGender(selectedGender.value)
            _uiEvent.send(UiEvent.Navigate(Route.AGE))
        }
    }
}