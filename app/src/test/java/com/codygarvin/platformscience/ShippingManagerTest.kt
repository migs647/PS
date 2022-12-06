package com.codygarvin.platformscience

import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class ShippingManagerTest {
    @Before
    fun setup() {

    }

    @After
    fun teardown() {
        // May not be needed
    }

    @Test
    fun `test basic calculation`() {
        // Arrange
        val shippingManager = ShippingManager()

        // Act
        shippingManager.buildDriverIndex()

        // Assert
        assert((shippingManager.filledAddresses?.count() ?: 0) == 10)
    }

    @Test
    fun `test match driver`() {
        // Arrange
        val shippingManager = ShippingManager()

        // Act
        shippingManager.buildDriverIndex()

        // Assert
        assert((shippingManager.filledAddresses?.count() ?: 0) >= 3)
        assert(shippingManager.filledAddresses?.get(2)?.driver?.driverName == "Noemie Murphy")
        assert(shippingManager.filledAddresses?.get(2)?.address?.address == "7127 Kathlyn Ferry")
    }
}