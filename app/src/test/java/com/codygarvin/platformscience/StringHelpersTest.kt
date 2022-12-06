package com.codygarvin.platformscience

import org.junit.Test

import org.junit.Assert.*

class StringHelpersTest {

    @Test
    fun `verify front removed success`() {
        // Arrange
        val address = "8725 Aufderhar River Suite 859"

        // Act
        val updatedAddress = address.removeFrontAddress()

        // Assert
        assertFalse(updatedAddress.contains("8725 "))
    }

    @Test
    fun `verify front not removed success`() {
        // Arrange
        val address = "Aufderhar River Suite 859"

        // Act
        val updatedAddress = address.removeFrontAddress()

        // Assert
        assert(updatedAddress.contains("Aufderhar"))
    }

    @Test
    fun `verify front not removed with numbers`() {
        // Arrange
        val address = "8725Aufderhar River Suite 859"

        // Act
        val updatedAddress = address.removeFrontAddress()

        // Assert
        assert(updatedAddress.contains("8725"))
    }

    @Test
    fun `verify back removed with numbers`() {
        // Arrange
        val address = "8725 Aufderhar River Suite 859"

        // Act
        val updatedAddress = address.removeEndAddress()

        // Assert
        assertFalse(updatedAddress.contains("Suite 859"))
    }

    @Test
    fun `verify address is odd`() {
        // Arrange
        val address = "8725 Aufderhar River Suite 859"

        // Act
        val filteredAddress = address.removeFrontAddress().removeEndAddress()

        // Assert
        assertFalse(filteredAddress.isEven())
    }

    @Test
    fun `verify address is even`() {
        // Arrange
        val address = "2431 Lindgren Corners"

        // Act
        val filteredAddress = address.removeFrontAddress().removeEndAddress()

        // Assert
        assert(filteredAddress.isEven())
    }
}