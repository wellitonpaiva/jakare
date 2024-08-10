package jakare

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class ReptileTest {

    private val reptile = Reptile(
        order = "order",
        family = "family",
        englishCommonName = "englishName",
        spanishCommonName = "spanishName",
        portugueseCommonName = "portugueseName",
        scientificName = "scientificName"
    )

    @Test
    fun `search by not existing word`() {
        assertFalse(reptile.searchByName("notExisting"))
    }

    @Test
    fun `search by order`() {
        assertTrue(reptile.searchByName("orde"))
    }

    @Test
    fun `search by order with uppercase`() {
        assertTrue(reptile.searchByName("ORDER"))
    }

    @Test
    fun `search by family`() {
        assertTrue(reptile.searchByName("famil"))
    }

    @Test
    fun `search by englishCommonName`() {
        assertTrue(reptile.searchByName("english"))
    }

    @Test
    fun `search by spanishCommonName`() {
        assertTrue(reptile.searchByName("spanish"))
    }

    @Test
    fun `search by portugueseCommonName`() {
        assertTrue(reptile.searchByName("portuguese"))
    }

    @Test
    fun `search by scientificName`() {
        assertTrue(reptile.searchByName("scientific"))
    }

}