package com.pokemanager.utils

import com.google.common.truth.Truth.assertThat
import com.pokemanager.data.remote.responses.OfficialArtworkNetwork
import com.pokemanager.data.remote.responses.SpriteNetwork
import com.pokemanager.data.remote.responses.SpriteOtherNetwork
import com.pokemanager.utils.UrlUtils.getIdAtEndFromUrl
import com.pokemanager.utils.UrlUtils.getImageUrl
import org.junit.Test

class UrlUtilsTest {

    companion object {
        const val OFFICIAL_ARTWORK_FRONT_DEFAULT = "OFFICIAL_ARTWORK_FRONT_DEFAULT"
        const val OFFICIAL_ARTWORK_FRONT_SHINY = "OFFICIAL_ARTWORK_FRONT_SHINY"
        const val FRONT_DEFAULT = "FRONT_DEFAULT"
        const val FRONT_SHINY = "FRONT_SHINY"
    }

    //getIdAtEndFromUrl
    @Test
    fun `getIdAtEndFromUrl empty url`() {
        val url = ""
        val actual = getIdAtEndFromUrl(url)
        val expected = 0
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getIdAtEndFromUrl no slash`() {
        val url = "dgvgfgxdvxd"
        val actual = getIdAtEndFromUrl(url)
        val expected = 0
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getIdAtEndFromUrl no id after slash with slash at end`() {
        val url = "dgvgfgxdvxd//"
        val actual = getIdAtEndFromUrl(url)
        val expected = 0
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getIdAtEndFromUrl no id after slash`() {
        val url = "dgvgfgxdvxd/"
        val actual = getIdAtEndFromUrl(url)
        val expected = 0
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getIdAtEndFromUrl end with slash`() {
        val url = "https://pokeapi.co/api/v2/pokemon-species/6/"
        val actual = getIdAtEndFromUrl(url)
        val expected = 6
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getIdAtEndFromUrl end with number`() {
        val url = "https://pokeapi.co/api/v2/pokemon/100"
        val actual = getIdAtEndFromUrl(url)
        val expected = 100
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getIdAtEndFromUrl no numeric id, end with slash`() {
        val url = "https://pokeapi.co/api/v2/pokemon/earf/"
        val actual = getIdAtEndFromUrl(url)
        val expected = 0
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getIdAtEndFromUrl no numeric id, end with number`() {
        val url = "https://pokeapi.co/api/v2/pokemon/earf"
        val actual = getIdAtEndFromUrl(url)
        val expected = 0
        assertThat(expected).isEqualTo(actual)
    }


    //getImageUrl
    @Test
    fun `getImageUrl official artwork, front default`() {
        val sprites = SpriteNetwork(
            other = SpriteOtherNetwork(
                OfficialArtworkNetwork(
                    frontDefault = OFFICIAL_ARTWORK_FRONT_DEFAULT
                )
            )
        )
        val actual = getImageUrl(sprites)
        val expected = OFFICIAL_ARTWORK_FRONT_DEFAULT
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getImageUrl official artwork, front shiny`() {
        val sprites = SpriteNetwork(
            other = SpriteOtherNetwork(
                OfficialArtworkNetwork(
                    frontDefault = null,
                    frontShiny = OFFICIAL_ARTWORK_FRONT_SHINY
                )
            )
        )
        val actual = getImageUrl(sprites)
        val expected = OFFICIAL_ARTWORK_FRONT_SHINY
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getImageUrl front default`() {
        val sprites = SpriteNetwork(
            other = SpriteOtherNetwork(
                OfficialArtworkNetwork(
                    frontDefault = null,
                    frontShiny = null
                )
            ),
            frontDefault = FRONT_DEFAULT
        )
        val actual = getImageUrl(sprites)
        val expected = FRONT_DEFAULT
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getImageUrl front shiny`() {
        val sprites = SpriteNetwork(
            other = SpriteOtherNetwork(
                OfficialArtworkNetwork(
                    frontDefault = null,
                    frontShiny = null
                )
            ),
            frontDefault = null,
            frontShiny = FRONT_SHINY
        )
        val actual = getImageUrl(sprites)
        val expected = FRONT_SHINY
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getImageUrl no image`() {
        val sprites = SpriteNetwork(
            other = SpriteOtherNetwork(
                OfficialArtworkNetwork(
                    frontDefault = null,
                    frontShiny = null
                )
            ),
            frontDefault = null,
            frontShiny = null
        )
        val actual = getImageUrl(sprites)
        val expected = ""
        assertThat(expected).isEqualTo(actual)
    }

}