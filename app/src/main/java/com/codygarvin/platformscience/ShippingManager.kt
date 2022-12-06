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

    /**
     * Parse a number of drivers and addresses in order to find a match between the two.
     * Note: This method is a brute force approach. It might be possible to use different data structures
     * such as a max heap or weighted graph in order to optimize the method. This gets us up and running
     * in an effort to explore the valid `secret` algorithm. These data structures may optimize for speed
     * but may reduce readability.
     */
    fun buildDriverIndex() {
        // First calculate the scores for the drivers for each address
        calculateScoresForDrivers()

        // Cycle through each address, removing each driver
        var tempRoutes = mutableListOf<Route>()
        for (address in shippingAddresses) {
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
     * Cycle through each shipping address and calculate the score for each driver for that particular
     * address.
     */
    private fun calculateScoresForDrivers() {
        // Make a copy of the addresses so we can manipulate outside the array
        for (address in shippingAddresses) {

            // Start with empty scores so we can build them up for each driver. We are using a
            // List for the drivers as drivers can have the same score.
            var scores = mutableMapOf<Double, MutableList<Driver>>()

            // Cycle through the available drivers and calculate their score for the address
            for (driver in availableDrivers) {
                val score = getScoreForAddressAndDriver(address, driver)
                val driverList = scores[score]
                if (driverList != null) {
                    driverList.add(driver)
                    scores[score] = driverList
                } else {
                    scores[score] = mutableListOf(driver)
                }
            }

            // Associate scores with address
            address.driverScores = scores
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

    /**
     * Calculate the score for the driver.
     * If the street is even, use the vowels in a driver's name multiply by 1.5
     * If the street is odd, use the consonants in the driver's name and multiply by 1
     * If the street has a factor other than 1, multiply by 50%
     */
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

    /**
     * Find the greatest common denominator to determine the highest factor
     */
    private fun greatestCommonDenominator(driverLength: Int, streetLength: Int): Int {
        if (streetLength < 1) {
            return driverLength
        }
        return greatestCommonDenominator(streetLength, driverLength % streetLength)
    }
}