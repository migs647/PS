package com.codygarvin.platformscience

import com.codygarvin.platformscience.shipping_management.Driver
import com.codygarvin.platformscience.shipping_management.ShippingAddress
import com.codygarvin.platformscience.shipping_management.ShippingManager
import org.junit.After
import org.junit.Test

import org.junit.Before

class ShippingManagerTest {

    var shippingManager: ShippingManager? = null
    @Before
    fun setup() {
        shippingManager = ShippingManager(null)
        shippingManager?.availableDrivers = mutableListOf<Driver>(
            Driver("Everardo Welch"),
            Driver("Orval Mayert"),
            Driver("Howard Emmerich"),
            Driver("Izaiah Lowe"),
            Driver("Monica Hermann"),
            Driver("Ellis Wisozk"),
            Driver("Noemie Murphy"),
            Driver("Cleve Durgan"),
            Driver("Murphy Mosciski"),
            Driver("Kaiser Sose")
        )

        shippingManager?.shippingAddresses = listOf<ShippingAddress>(
            ShippingAddress("215 Osinski Manors"),
            ShippingAddress("9856 Marvin Stravenue"),
            ShippingAddress("7127 Kathlyn Ferry"),
            ShippingAddress("987 Champlin Lake"),
            ShippingAddress("63187 Volkman Garden Suite 447"),
            ShippingAddress("75855 Dessie Lights"),
            ShippingAddress("1797 Adolf Island Apt. 744"),
            ShippingAddress("2431 Lindgren Corners"),
            ShippingAddress("8725 Aufderhar River Suite 859"),
            ShippingAddress("79035 Shanna Light Apt. 322")
        )
    }

    @After
    fun teardown() {
        // May not be needed
    }

    @Test
    fun `test basic calculation`() {
        // Arrange

        // Act
        shippingManager?.buildDriverIndex()

        // Assert
        assert((shippingManager?.filledAddresses?.count() ?: 0) == 10)
    }

    @Test
    fun `test match driver`() {
        // Arrange

        // Act
        shippingManager?.buildDriverIndex()

        // Assert
        assert((shippingManager?.filledAddresses?.count() ?: 0) >= 3)
        assert(shippingManager?.filledAddresses?.get(2)?.driver?.driverName == "Noemie Murphy")
        assert(shippingManager?.filledAddresses?.get(2)?.address?.address == "7127 Kathlyn Ferry")
    }
}