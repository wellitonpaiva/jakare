package jakare

import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.impl.HttpJdkSolrClient
import org.apache.solr.common.params.MapSolrParams
import org.http4k.routing.routes
import org.http4k.server.JettyLoom
import org.http4k.server.asServer

fun main() {
    val solr = HttpJdkSolrClient
        .Builder("http://localhost:8983/solr")
        .withDefaultCollection("species")
        .build()
    val service = Service(solr)
    routes(
        initial(),
        list(service.list())
    ).asServer(JettyLoom(8080)).start()
}

class Service(private val solr: SolrClient) {
    fun list(): List<Specie> =
        solr.query(MapSolrParams(mapOf("q" to "*:*", "rows" to "1000")))
            .getBeans(SpecieSolr::class.java)
            .map(SpecieSolr::toReptile)
}

