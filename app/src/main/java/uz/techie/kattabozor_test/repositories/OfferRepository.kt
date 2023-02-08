package uz.techie.kattabozor_test.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.techie.kattabozor_test.api.OffersApi
import uz.techie.kattabozor_test.models.Offer
import javax.inject.Inject

class OfferRepository @Inject constructor(private val offersApi: OffersApi) {
    suspend fun getCountries(): Result<List<Offer>> = withContext(Dispatchers.IO) {
        kotlin.runCatching { offersApi.getOffers().offers }
    }
}