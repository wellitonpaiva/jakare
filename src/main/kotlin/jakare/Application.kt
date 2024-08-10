package jakare

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.html.*
import kotlinx.html.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    routing {
        get("/") {
            call.respondHtml {
                head {
                    script(type = ScriptType.textJScript, src= "https://unpkg.com/htmx.org@2.0.1" ) {}
                    link(rel = "stylesheet", href = "https://cdn.simplecss.org/simple.min.css")
                }
                body("jakare")

            }
        }
        post("/pressed") {
            call.respondHtml { body("jakared") }
        }
    }
}

private fun HTML.body(h1: String) {
    body {
        id="theBody"
        div {
            h1 {
                id = "title"
                +h1
            }
            button {
                attributes["hx-post"] = "/pressed"
                attributes["hx-target"] = "#theBody"
                +"Don't press this button"
            }
        }
    }
}