package jakare

import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.extractIt
import it.skrape.fetcher.skrape
import it.skrape.selects.html5.*
import org.apache.solr.client.solrj.impl.HttpJdkSolrClient

fun main() {
    val solr = HttpJdkSolrClient.Builder("http://localhost:8983/solr").build()

    for (i in 13..60) {
        println("starting page $i")
        extract("https://www.ecoregistros.org/site_en/ordenalfabetico.php?&page=$i")
            .map { specie ->
                if (specie.scientificName.isNotEmpty())
                    println("adding ${specie.scientificName} from page $i")
                solr.addBean("species", specie)
                solr.commit("species")
            }
    }


    /*solr.addBean("species", Specie(order, family, english, spanish, portuguese, scientificName))
    solr.commit("species")*/
}

private fun extract(urlScrape: String): List<Specie> =
    skrape(HttpFetcher) {
        request { url = urlScrape }
        extractIt<ScrapingResult> { result ->
            htmlDocument {
                table(".tablainvisible") {
                    tr {
                        findAll { this }
                    }

                }.drop(2)  // Remove the first two elements; these are just the table header and subheader
                    .map {
                        // Define variables to hold name and population
                        val order = it.td { findByIndex(1) { text } }
                        val family = it.td { findByIndex(2) { text } }
                        val english = it.td { findByIndex(3) { text } }
                        val spanish = it.td { findByIndex(4) { text } }
                        val portuguese = it.td { findByIndex(5) { text } }
                        val scientificName = it.td { findByIndex(6) { text } }
                        result.species.add(Specie(order, family, english, spanish, portuguese, scientificName))
                    }

            }
        }
    }.species

data class ScrapingResult(val species: MutableList<Specie>) {
    constructor() : this(mutableListOf())
}
