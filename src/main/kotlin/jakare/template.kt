package jakare

import kotlinx.html.*
import kotlinx.html.stream.createHTML

fun TR.rowEdit(reptile: Reptile) {
    id = reptile.id
    td { inputField("order", reptile.order) }
    td { inputField("family", reptile.family) }
    td { inputField("englishCommonName", reptile.englishCommonName) }
    td { inputField("spanishCommonName", reptile.spanishCommonName) }
    td { inputField("portugueseCommonName", reptile.portugueseCommonName) }
    td { inputField("scientificName", reptile.scientificName) }
    td {
        button {
            attributes["hx-post"] = "/reptile/${reptile.id}/edit"
            attributes["hx-target"] = "#${reptile.id}"
            attributes["hx-swap"] = "outerHTML"
            attributes["hx-include"] = ".editing"
            +"Save"
        }
    }
}

private fun TD.inputField(name: String, value: String) {
    input {
        classes = setOf("editing")
        type = InputType.text
        this.name = name
        this.value = value
    }
}

fun Map<String, Reptile>.search(search: String) =
    createHTML().table {
        id = "search"
        tr {
            th { +"Order" }
            th { +"Family" }
            th { +"English Common Name" }
            th { +"Spanish Common Name" }
            th { +"Portuguese Common Name" }
            th { +"Scientific Name" }
            th {}
        }
        filter { it.value.searchByName(search) }
            .map {
                tr {
                    rowView(it.value)
                }
            }
    }

fun TR.rowView(reptile: Reptile) {
    id = reptile.id
    td { +reptile.order }
    td { +reptile.family }
    td { +reptile.englishCommonName }
    td { +reptile.spanishCommonName }
    td { +reptile.portugueseCommonName }
    td { +reptile.scientificName }
    td {
        button(classes = "btn danger") {
            attributes["hx-get"] = "/reptile/${reptile.id}/edit"
            attributes["hx-target"] = "#${reptile.id}"
            attributes["hx-swap"] = "outerHTML"
            +"Edit"
        }
    }
}