package com.codygarvin.platformscience

class Route(val driver: Driver, val address: ShippingAddress) {
    fun goToRoute() {
        print("Driver: ${driver.driverName}\nAddress: ${address.address}")
    }
}