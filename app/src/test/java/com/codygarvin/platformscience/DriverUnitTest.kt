package com.codygarvin.platformscience

import com.codygarvin.platformscience.shipping_management.Driver
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DriverUnitTest {
    @Test
    fun `verify vowels are correct`() {
        // Arrange
        val driver = Driver("Cody Garvin")

        // Act
        val driverData = driver.driverAttributes()

        // Assert
        assertEquals(3, driverData.first)
    }

    @Test
    fun `verify consonants are correct`() {
        // Arrange
        val driver = Driver("Cody Garvin")

        // Act
        val driverData = driver.driverAttributes()

        // Assert
        assertEquals(7, driverData.second)
    }
}