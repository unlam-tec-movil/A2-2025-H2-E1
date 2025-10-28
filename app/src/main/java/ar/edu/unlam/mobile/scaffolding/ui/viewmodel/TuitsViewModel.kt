package ar.edu.unlam.mobile.scaffolding.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit
import ar.edu.unlam.mobile.scaffolding.data.repositories.TuitsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TuitsViewModel @Inject constructor(
    private val tuitsRepo: TuitsRepository
) : ViewModel() {

    private val _tuitState = MutableStateFlow(emptyList<Tuit>())
    val tuitState: StateFlow<List<Tuit>> = _tuitState

    init {
        viewModelScope.launch {
            _tuitState.value = tuitsRepo.getTuits()
        }
    }
}