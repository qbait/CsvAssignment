package com.CSV.AddressProcessing

import java.io.BufferedReader

class CsvReader(private val bufferedReader: BufferedReader, private val separator: String = "\t") {

    fun read(): List<String> {
        val line = bufferedReader.use { it.readLine() } // `use` is like try-with-resources in Java
        return parse(line)
    }

    private fun parse(line: String): List<String> {
        return line.split(separator)
    }
}