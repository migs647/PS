package com.codygarvin.platformscience.shipping_management

/**
 * A driver contains attributes factored into a route
 */
data class Driver(val driverName: String) {
    val driverLength: Int
        get() = driverName.count()

    /**
     * Returns how many vowels and how many consonants are in the drivers name.
     * Vowels are left and consonants are right.
     */
    fun driverAttributes(yConsonant: Boolean = false): Pair<Int, Int> {
        val fullDriver = this.driverName.lowercase()
        var vowelsCount = 0
        var consonantsCount = 0

        // Note: Assume y isn't a vowel by default, maybe find a way to auto-detect
        val vowelString = if (!yConsonant) "aeiou" else "aeiouy"
        val vowels = vowelString.toList()
        for (char in fullDriver) {
            if (char in vowels) {
                vowelsCount++
            } else if (char in 'a'..'z') {
                consonantsCount++
            }
        }

        return Pair(vowelsCount, consonantsCount)
    }
}