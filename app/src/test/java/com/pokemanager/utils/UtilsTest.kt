package com.pokemanager.utils

import com.google.common.truth.Truth.*
import com.pokemanager.utils.Utils.getTotalStepsAtDownloadingAll
import org.junit.Test

class UtilsTest {

    //getTotalStepsAtDownloadingAll
    @Test
    fun `getTotalStepsAtDownloadingAll correct result`() {
        val actual = getTotalStepsAtDownloadingAll()
        val expected = 16
        assertThat(expected).isEqualTo(actual)
    }

}