package jakare

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import kotlinx.html.*
import kotlinx.html.stream.createHTML

data class Reptile(
    val order: String,
    val family: String,
    val englishCommonName: String,
    val spanishCommonName: String,
    val portugueseCommonName: String,
    val scientificName: String,
)

fun String.toReptile(): Reptile {
    val rep = this.split(",")
    return Reptile(
        order = rep[0],
        family = rep[1],
        englishCommonName = rep[2],
        spanishCommonName = rep[3],
        portugueseCommonName = rep[4],
        scientificName = rep[5]
    )
}

fun main() {
    val reptiles: List<Reptile> = {}.javaClass.getResource("/reptiles.csv")
        ?.openStream()
        ?.bufferedReader()
        ?.readLines()
        ?.map(String::toReptile)
        .orEmpty()

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = { module(reptiles) })
        .start(wait = true)
}

fun Application.module(reptiles: List<Reptile>) {
    routing {
        get("/") {
            call.respondHtmlTemplate(LayoutTemplate()) {
                content {
                    input(type = InputType.search) {
                        name = "search"
                        placeholder = "Search by name"
                        attributes["hx-get"] = "/list"
                        attributes["hx-trigger"] = "input changed delay:500ms, search"
                        attributes["hx-target"] = "#list"
                    }
                    div { id="list" }
                }
            }
        }

        get("/list") {
            call.respondText { reptiles.search(call.request.queryParameters["search"].orEmpty()) }
        }
    }
}

fun List<Reptile>.search(search: String) =
        createHTML().table {
            id = "search"
            tr {
                th { +"Order" }
                th { +"Family" }
                th { +"English Common Name" }
                th { +"Spanish Common Name" }
                th { +"Portuguese Common Name" }
                th { +"Scientific Name" }
            }
            filter { if (search.isNotBlank()) it.scientificName.contains(search) else true}
                .map {
                    tr {
                        td { +it.order }
                        td { +it.family }
                        td { +it.englishCommonName }
                        td { +it.spanishCommonName }
                        td { +it.portugueseCommonName }
                        td { +it.scientificName }
                    }
                }
        }


class LayoutTemplate : Template<HTML> {
    val content = Placeholder<FlowContent>()
    override fun HTML.apply() {
        head {
            script(type = ScriptType.textJScript, src = "https://unpkg.com/htmx.org@2.0.1") {}
            link(rel = "stylesheet", href = "https://cdn.simplecss.org/simple.min.css")
        }
        body {
            h1 { +"JAKARE" }
            div { insert(content) }
        }
    }
}