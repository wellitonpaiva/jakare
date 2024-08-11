package jakare

import io.ktor.http.*

data class Reptile(
    val order: String,
    val family: String,
    val englishCommonName: String,
    val spanishCommonName: String,
    val portugueseCommonName: String,
    val scientificName: String,
) {
    val id: String = scientificName.replace(" ", "-")
    fun searchByName(search: String) =
        if (search.isNotBlank()) {
            order.contains(search, ignoreCase = true) ||
            family.contains(search, ignoreCase = true) ||
            englishCommonName.contains(search, ignoreCase = true) ||
            spanishCommonName.contains(search, ignoreCase = true) ||
            portugueseCommonName.contains(search, ignoreCase = true) ||
            scientificName.contains(search, ignoreCase = true)
        } else true
}

fun String.toReptile(): Reptile =
    this.split(",").let {
        Reptile(
            order = it[0],
            family = it[1],
            englishCommonName = it[2],
            spanishCommonName = it[3],
            portugueseCommonName = it[4],
            scientificName = it[5]
        )
    }

fun Parameters.toReptile() = Reptile(
    order = this["order"].orEmpty(),
    family = this["family"].orEmpty(),
    englishCommonName = this["englishCommonName"].orEmpty(),
    spanishCommonName = this["spanishCommonName"].orEmpty(),
    portugueseCommonName = this["portugueseCommonName"].orEmpty(),
    scientificName = this["scientificName"].orEmpty()
)