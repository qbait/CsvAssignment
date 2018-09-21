package com.CSV.AddressProcessing;

/*
A junior developer was tasked with writing a reusable implementation for a mass mailing application to read and write text files that hold tab separated data. He proceeded and as a result produced the CSVReaderWriter class.

His implementation, although it works and meets the needs of the application, is of very low quality.

Your task:
     - Identify and annotate the shortcomings in the current implementation as if you were doing a code review, using comments in the CSVReaderWriter.java file.
     - Refactor the CSVReaderWriter implementation into clean, elegant, rock-solid & well performing code.
     - Provide evidence that the code is working as expected.
     - Where you make trade offs, comment & explain.
     - Assume this code is in production and backwards compatibility must be maintained. Therefore if you decide to change the public interface,
       please deprecate the existing methods. Feel free to evolve the code in other ways though.
*/

import java.io.*;

// We can refactor it to have 2 classes with single responsibilities.
// This is my favorite explanation why single responsibility is beneficial https://youtu.be/llGgO74uXMI?t=3359
@Deprecated
public class CSVReaderWriter {
    // Android Studio higlight fields, we don't need to write underscores in front (my personal preference, at the end it depends on the team's naming convention)
    private BufferedReader _bufferedReader = null;
    private BufferedWriter _bufferedWriter = null;

    public enum Mode {
        Read (1), Write(2);

        private int _mode;
        Mode(int mode) {
            this._mode = mode;
        }
        public int getMode() {
            return _mode;
        }
    }

    // It's not the best API design when other developers need to remember about opening and closing.
    // In Java we should use try-with-resources statement instead
    // https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
    public void open(String fileName, Mode mode) throws Exception {
        if (mode == Mode.Read)
        {
            _bufferedReader = new BufferedReader(new FileReader(fileName));
        }
        else if (mode == Mode.Write)
        {
            FileWriter fileWriter = new FileWriter(fileName);
            _bufferedWriter = new BufferedWriter(fileWriter);
        }
        else
        {
            throw new Exception("Unknown file mode for " + fileName);
        }
    }

    // Keep it simple, there is join function in TextUtils or Java 8
    // https://developer.android.com/reference/android/text/TextUtils#join(java.lang.CharSequence
    // https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#join-java.lang.CharSequence-java.lang.CharSequence...-
    public void write(String... columns) throws IOException {
        String outPut = "";

        for (int i = 0; i < columns.length; i++)
        {
            outPut += columns[i];
            if ((columns.length - 1) != i)
            {
                outPut += "\t";
            }
        }

        writeLine(outPut);
    }

    // It should return the result of reading for us, shouldn't it? Why are you passing columns?
    // Also this function is not robust, no array size and null checks.
    public boolean read(String[] columns) throws IOException {
        final int FIRST_COLUMN = 0;
        final int SECOND_COLUMN = 1;

        String line;
        String[] splitLine;

        String separator = "\t";

        line = readLine();
        splitLine = line.split(separator);

        if (splitLine.length == 0)
        {
            columns[0] = null;
            columns[1] = null;

            return false;
        }
        else
        {
            columns[0] = splitLine[FIRST_COLUMN];
            columns[1] = splitLine[SECOND_COLUMN];

            return true;
        }
    }

    // This function duplicates code of read(String[] columns), we could solve it with the one robust method
    public boolean read(String column1, String column2) throws IOException {
        final int FIRST_COLUMN = 0;
        final int SECOND_COLUMN = 1;

        String line;
        String[] splitLine;

        String separator = "\t";

        line = readLine();

        if (line == null)
        {
            column1 = null;
            column2 = null;

            return false;
        }

        splitLine = line.split(separator);

        if (splitLine.length == 0)
        {
            column1 = null;
            column2 = null;

            return false;
        }
        else
        {
            column1 = splitLine[FIRST_COLUMN];
            column2 = splitLine[SECOND_COLUMN];

            return true;
        }
    }

    private void writeLine(String line) throws IOException {
        _bufferedWriter.write(line);
    }

    private String readLine() throws IOException {
        return _bufferedReader.readLine();
    }

    // You are closing here writer twice and forgot to close the reader
    public void close() throws IOException {
        if (_bufferedWriter != null)
        {
            _bufferedWriter.close();
        }

        if (_bufferedWriter != null)
        {
            _bufferedWriter.close();
        }
    }
}