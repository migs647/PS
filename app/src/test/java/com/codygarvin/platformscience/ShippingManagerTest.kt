package com.codygarvin.platformscience

import org.junit.Test

import org.junit.Assert.*

class ShippingManagerTest {
    @Test
    fun `test basic calculation`() {
        // Arrange
        val shippingManager = ShippingManager()

        // Act
        shippingManager.buildDriverIndex()

        // Assert
        assert((shippingManager.filledAddresses?.count() ?: 0) > 1)
    }
}