package uz.techie.kattabozor_test.models

data class Offer(
    val id: Long,
    val name: String,
    val brand: String,
    val category: String,
    val merchant: String,
    val attributes: List<Attribute>,
    val image: Image,
)
