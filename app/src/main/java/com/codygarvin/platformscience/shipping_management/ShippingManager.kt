package com.codygarvin.platformscience.shipping_management

import android.content.Context
import android.util.Log
import com.codygarvin.platformscience.Utils
import com.codygarvin.platformscience.isEven
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ShippingManager(private val appContext: Context?) {

    var filledAddresses: List<Route>? = null

    internal var availableDrivers = mutableListOf<Driver>()

    internal var shippingAddresses = listOf<ShippingAddress>()

    init {
        // Fetch JSON
        try {
            fetchJson()
        } catch (exception: Exception) {
            print("Exception: {${exception.toString()}")
        }
    }

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

    /**
     * Note: Probably a good idea to make sure this is on a background thread
     */
    private fun fetchJson() {
        val appContext = appContext ?: return // probably good idea to throw an error here
        val jsonFileString = Utils.getJsonFromAssets(appContext, "shipment_driver_data.json") ?: return
        val gson = Gson()
        var rawRouteModel = gson.fromJson(jsonFileString.toString(), RawRoute::class.java)
        parseRawRoute(rawRouteModel)

    }

    private fun parseRawRoute(rawRoute: RawRoute) {
        val tempShipments = mutableListOf<ShippingAddress>()
        for (address in rawRoute.shipments) {
            tempShipments.add(ShippingAddress(address))
        }
        shippingAddresses = tempShipments

        val tempDrivers = mutableListOf<Driver>()
        for (driver in rawRoute.drivers) {
            tempDrivers.add(Driver(driver))
        }
        availableDrivers = tempDrivers
    }
}