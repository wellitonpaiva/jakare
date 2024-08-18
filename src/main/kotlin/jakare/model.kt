package jakare

import org.apache.solr.client.solrj.beans.Field

class SpecieSolr {
    @Field("Order") val order: ArrayList<String> = ArrayList()
    @Field("Family") val family: ArrayList<String> = ArrayList()
    @Field("English_Common_Name") val englishCommonName: ArrayList<String> = ArrayList()
    @Field("Spanish_Common_Name") val spanishCommonName: ArrayList<String> = ArrayList()
    @Field("Portuguese_Common_Name") val portugueseCommonName: ArrayList<String> = ArrayList()
    @Field("Scientific_Name") val scientificName: ArrayList<String> = ArrayList()

    fun toReptile() = Specie(
        order = order.joinToString(),
        family = family.joinToString(),
        englishCommonName = englishCommonName.joinToString(),
        spanishCommonName = spanishCommonName.joinToString(),
        portugueseCommonName = portugueseCommonName.joinToString(),
        scientificName = scientificName.joinToString()
    )
}

data class Specie(
    @Field("Order") val order: String,
    @Field("Family") val family: String,
    @Field("English_Common_Name") val englishCommonName: String,
    @Field("Spanish_Common_Name") val spanishCommonName: String,
    @Field("Portuguese_Common_Name") val portugueseCommonName: String,
    @Field("Scientific_Name") val scientificName: String,
) {
}

