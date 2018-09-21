package com.CSV.AddressProcessing

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.anyList
import org.mockito.ArgumentMatchers.anyString
import java.io.*

class CsvWriterTest {

    private val bufferedWriter = mock<BufferedWriter>()
    private val writer = CsvWriter(bufferedWriter)

    @Test
    fun write() {
        val argument = ArgumentCaptor.forClass(String::class.java)

        writer.write(listOf("Jakub", "Szwiec"))

        verify(bufferedWriter).write(argument.capture())
        argument.value shouldEqual "Jakub\tSzwiec"
    }

    @Test
    fun writeWhenEmpty() {
        val argument = ArgumentCaptor.forClass(String::class.java)

        writer.write(emptyList())

        verify(bufferedWriter).write(argument.capture())
        argument.value shouldEqual ""
    }

    @Test
    fun throwIOExceptionWhenWritingProblems() {
        whenever(bufferedWriter.write(anyString())).thenThrow(IOException::class.java)

        val write = { writer.write(anyList()) }
        write shouldThrow IOException::class
    }
}

