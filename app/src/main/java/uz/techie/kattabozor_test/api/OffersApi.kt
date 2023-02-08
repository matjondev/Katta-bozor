package uz.techie.kattabozor_test.api

import retrofit2.http.GET
import uz.techie.kattabozor_test.models.OfferResponse

interface OffersApi {
    @GET("offers")
    suspend fun getOffers(): OfferResponse
}