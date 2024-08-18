package jakare

import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind

fun initial() = "/" bind Method.GET to {
    Response(Status.OK).body(
        content {
            input(type = InputType.search) {
                name = "search"
                placeholder = "Search by name"
                attributes["hx-get"] = "/list"
                attributes["hx-trigger"] = "input changed delay:500ms, search"
                attributes["hx-target"] = "#list"
            }
            div { id = "list" }
        }
    )
}

fun list(species: List<Specie>) = "/solr/list" bind Method.GET to {
    Response(Status.OK).body(
        content {
            table {
                id = "search"
                tr {
                    th { +"Order" }
                    th { +"Family" }
                    th { +"English Common Name" }
                    th { +"Spanish Common Name" }
                    th { +"Portuguese Common Name" }
                    th { +"Scientific Name" }
                }
                species.map { doc ->
                    tr {
                        td { +doc.order }
                        td { +doc.family }
                        td { +doc.englishCommonName }
                        td { +doc.spanishCommonName }
                        td { +doc.portugueseCommonName }
                        td { +doc.scientificName }
                    }
                }
            }
        }
    )
}

fun content(block: DIV.() -> Unit) =
    createHTML().html {
        head {
            script(type = ScriptType.textJScript, src = "https://unpkg.com/htmx.org@2.0.1") {}
            link(rel = "stylesheet", href = "https://cdn.simplecss.org/simple.min.css")
        }
        body {
            h1 { +"JAKARE" }
            div { block() }
        }
    }