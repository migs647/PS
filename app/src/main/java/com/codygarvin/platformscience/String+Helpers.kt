package com.codygarvin.platformscience

fun String.removeFrontAddress(): String {
    // Assumes numbers are always first, which may not always be true
    // in a real world address
    return this.replace(Regex("^[0-9]* "), "")
}

fun String.removeEndAddress(): String {
    // Assumes numbers always follow Suite and Apt.
    // May need to add a-zA-Z potentially.
    return this.replace(Regex(" Suite [0-9]*$"), "")
               .replace(Regex(" Apt. [0-9]*$"), "")
}

fun String.isEven(): Boolean {
    return ((this.count() % 2) == 0)
}