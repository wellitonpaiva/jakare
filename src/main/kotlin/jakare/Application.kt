package jakare

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.html.*
import kotlinx.html.stream.createHTML

fun main() {
    val reptiles: MutableMap<String, Reptile> = {}.javaClass.getResource("/reptiles.csv")!!
        .openStream()
        .bufferedReader()
        .readLines()
        .associate {
            val reptile = it.toReptile()
            reptile.id to reptile
        }
        .toMutableMap()

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = { module(reptiles) })
        .start(wait = true)
}

fun Application.module(reptiles: MutableMap<String, Reptile>) {
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
                    div { id = "list" }
                }
            }
        }
        get("/list") {
            call.respondText { reptiles.search(call.request.queryParameters["search"].orEmpty()) }
        }
        get("/reptile/{id}/edit") {
            val reptile = reptiles[call.parameters["id"]!!]!!
            call.respondText {
                createHTML().tr { rowEdit(reptile) }
            }
        }
        post("/reptile/{id}/edit") {
            val id = call.parameters["id"]!!
            val newReptile = call.receiveParameters().toReptile()
            reptiles[id] = newReptile
            call.respondText {
                createHTML().tr { rowView(reptiles[id]!!) }
            }
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