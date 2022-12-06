package com.codygarvin.platformscience

import java.util.*

class ShippingManager {

    var filledAddresses: List<Route>? = null

    private var availableDrivers = mutableListOf<Driver>(
        Driver("Everardo Welch"),
        Driver("Orval Mayert"),
        Driver("Howard Emmerich"),
        Driver("Izaiah Lowe"),
        Driver("Monica Hermann"),
        Driver("Ellis Wisozk"),
        Driver("Noemie Murphy"),
        Driver("Cleve Durgan"),
        Driver("Murphy Mosciski"),
        Driver("Kaiser Sose"))

    private var shippingAddresses = listOf<ShippingAddress>(
        ShippingAddress("215 Osinski Manors"),
        ShippingAddress("9856 Marvin Stravenue"),
        ShippingAddress("7127 Kathlyn Ferry"),
        ShippingAddress("987 Champlin Lake"),
        ShippingAddress("63187 Volkman Garden Suite 447"),
        ShippingAddress("75855 Dessie Lights"),
        ShippingAddress("1797 Adolf Island Apt. 744"),
        ShippingAddress("2431 Lindgren Corners"),
        ShippingAddress("8725 Aufderhar River Suite 859"),
        ShippingAddress("79035 Shanna Light Apt. 322"))

    fun buildDriverIndex() {
        var tempAddresses = shippingAddresses
        for (addressIndex in shippingAddresses.indices) {
            val address = shippingAddresses[addressIndex]

            // build the scores for all the drivers for this address
            var scores = mutableMapOf<Double, MutableList<Driver>>()

            print("\n-----------\nBuilding scores for: ${address.address}")
            for (driver in availableDrivers) {
                val score = getScoreForAddressAndDriver(address, driver)
                val driverList = scores[score]
                if (driverList != null) {
                    driverList.add(driver)
                    scores[score] = driverList
                } else {
                    scores[score] = mutableListOf(driver)
                }
                print("\n$score - ${driver.driverName}")
            }

            // Associate scores with address
            tempAddresses[addressIndex].driverScores = scores
        }

        // Cycle through each address, removing each driver
        var tempRoutes = mutableListOf<Route>()
        for (address in tempAddresses) {
            // Get driver with highest score, if driver is available. If no driver is found, try the next address
            val returnedDriver = getDriverForAddress(address, availableDrivers) ?: continue

            // Assign driver to route
            tempRoutes.add(Route(returnedDriver, address))

            // Remove driver from available drivers
            availableDrivers.remove(returnedDriver)
        }

        // Only fill out the routes if it isn't empty
        filledAddresses = tempRoutes.ifEmpty {
            null
        }
    }

    /**
     * Find the next driver available with a list of available drivers left. Starts from a descending
     * score in order to find the best matched driver.
     */
    private fun getDriverForAddress(address: ShippingAddress, driversLeft: List<Driver>): Driver? {
        var returnDriver: Driver? = null

        val sortedScores = address.sortedDriverScores ?: return null
        for ((score, driverList) in sortedScores) {
            println("Score: $score")
            // Iterate potential duplicate scores
            for (potentialDriver in driverList) {
                if (driversLeft.contains(potentialDriver)) {
                    returnDriver = potentialDriver
                    break
                }
            }
            if (returnDriver != null) {
                break
            }
        }

        return returnDriver
    }

    private fun getScoreForAddressAndDriver(address: ShippingAddress, driver: Driver): Double {
        var score = 0.0
        val driverAttributes = driver.driverAttributes()
        score = if (address.streetName.isEven()) {
            driverAttributes.first.toDouble() * 1.5
        } else {
            driverAttributes.second.toDouble() * 1
        }

        // Check the factors for a score boost
        if (greatestCommonDenominator(driver.driverLength, address.streetName.length) != 1) {
            score *= 1.5
        }

        return score
    }

    private fun greatestCommonDenominator(driverLength: Int, streetLength: Int): Int {
        if (streetLength < 1) {
            return driverLength
        }
        return greatestCommonDenominator(streetLength, driverLength % streetLength)
    }
}