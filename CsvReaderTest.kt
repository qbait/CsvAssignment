package com.CSV.AddressProcessing

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.junit.Test
import java.io.BufferedReader
import java.io.IOException

class CsvReaderTest {

    private val bufferedReader = mock<BufferedReader>()
    private val reader = CsvReader(bufferedReader)

    @Test
    fun read() {
        whenever(bufferedReader.readLine()).thenReturn("Jakub\tSzwiec")
        reader.read() shouldEqual listOf("Jakub", "Szwiec")
    }

    @Test
    fun readWhenEmpty() {
        whenever(bufferedReader.readLine()).thenReturn("")
        reader.read() shouldEqual listOf("")
    }

    @Test
    fun throwIOExceptionWhenReadingProblems() {
        whenever(bufferedReader.readLine()).thenThrow(IOException::class.java)

        val read = { reader.read() }
        read shouldThrow IOException::class
    }
}