package org.example.moreinone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.moreinone.model.entities.MoreSettings
import org.example.moreinone.repository.MoreInOneRepository
import javax.inject.Inject

@HiltViewModel
class MoreInOneViewModel @Inject constructor(private val moreInOneRepository: MoreInOneRepository): ViewModel() {

    val getSettings = moreInOneRepository.getSettings()

    fun insertSettings(moreSettings: MoreSettings) {
        viewModelScope.launch(Dispatchers.IO) {
            moreInOneRepository.insertSettings(moreSettings)
        }
    }
}
