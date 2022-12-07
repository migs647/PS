package com.codygarvin.platformscience

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets.UTF_8

object Utils {
    fun getJsonFromAssets(context: Context, fileName: String?): String? {

        val fileName = fileName ?: return null

        val jsonString = try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }
}