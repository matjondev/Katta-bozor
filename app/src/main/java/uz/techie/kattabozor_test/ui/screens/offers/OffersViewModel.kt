package uz.techie.kattabozor_test.ui.screens.offers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.techie.kattabozor_test.models.Offer
import uz.techie.kattabozor_test.repositories.OfferRepository
import javax.inject.Inject

@HiltViewModel
class OffersViewModel @Inject constructor(
    private val offerRepository: OfferRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(OffersUIState())
    val uiState = _uiState.asStateFlow()

    private val _selectedOffer = MutableStateFlow<Offer?>(null)
    val selectedOffer = _selectedOffer.asStateFlow()

    init {
        execute(RefreshOffersAction)
    }

    fun execute(action: OffersAction) {
        when (action) {
            is RefreshOffersAction -> execute(action)
            is SelectOfferAction -> execute(action)
        }
    }

    private fun execute(action: RefreshOffersAction) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            offerRepository.getCountries().apply {
                onSuccess { offers ->
                    _uiState.update {
                        it.copy(isLoading = false, message = null, offers = offers)
                    }
                }
                onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, message = error.message) }
                }
            }
        }
    }

    private fun execute(action: SelectOfferAction) {
        _selectedOffer.update { action.offer }
    }

}

sealed interface OffersAction
object RefreshOffersAction : OffersAction
data class SelectOfferAction(val offer: Offer) : OffersAction

data class OffersUIState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val offers: List<Offer> = emptyList()
)