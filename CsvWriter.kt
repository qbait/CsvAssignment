package com.CSV.AddressProcessing

import java.io.BufferedWriter

class CsvWriter(private val bufferedWriter: BufferedWriter, private val separator: String = "\t") {

    fun write(line: List<String>) {
        bufferedWriter.use { out ->
            out.write( join(line) ) // `use` is like try-with-resources in Java
        }
    }

    private fun join(list: List<String>): String {
        return list.joinToString(separator)
    }

}