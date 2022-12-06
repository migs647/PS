package com.codygarvin.platformscience

import java.util.*

data class ShippingAddress(val address: String) {
    val streetName: String
        get() = address.removeFrontAddress().removeEndAddress()

    var sortedDriverScores: SortedMap<Double, List<Driver>>? = null
    private set

    var driverScores: Map<Double, List<Driver>>? = null
    set(value) {
        sortedDriverScores = value?.toSortedMap(compareByDescending { it })
        field = value
    }
}

