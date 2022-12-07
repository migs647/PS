package com.codygarvin.platformscience.shipping_management

import com.google.gson.annotations.SerializedName

data class RawRoute(val shipments: List<String>, val drivers: List<String>) {

}