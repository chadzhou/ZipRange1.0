package com.company;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ZipCodeProcessor zipCodeProcessor = new ZipCodeProcessor();

        /*
         2 modes of zipCOdeProcesser are defined here, and they are not supposed to do at same time
          */

        // mode 1. to prcess a random collection of zip-code input, this is mostly for testing purposes, we can see the input and output
        RandomRangeGenerator randomRangeGenerator = new RandomRangeGenerator();
        executeRandomZip(zipCodeProcessor, randomRangeGenerator, 4);

        // mode 2. to process user defined input, this is for client use, we can only see the output
        String user_input = "[94133,94133] [94200,94299] [94226,94399]";
        zipCodeProcessor.generateOutput(user_input);

    }

    /*
        This method takes the zipCodeProcessor, randomRangeGenerator and the number of pairs we want to generate randomly,
        and prints out the input generated, as well as the result after the process
     */

    private static void executeRandomZip(ZipCodeProcessor zipCodeProcessor, RandomRangeGenerator randomRangeGenerator, int pairs) {
        final PrintStream originalOut = System.out;
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ArrayList<RangeNode> nodes = randomRangeGenerator.generateNode(pairs);
        for (RangeNode node: nodes) {
            System.out.print("[" + fillZip(node.getMin()) + "," + fillZip(node.getMax()) + "] ");
        }

        String inputString = outContent.toString();
        System.setOut(originalOut);
        System.out.println(inputString);
        zipCodeProcessor.generateOutput(inputString);
        System.out.println();
    }
    /*
        This method is to fill the zip into 5 digits if it starts with 0;
        it takes a zip as integer and returns the zip as String
     */
    private static String fillZip(int zip) {
        String zipString = Integer.toString(zip);
        if (zipString.length() < 5) {
            StringBuilder sb_min = new StringBuilder(zipString);
            for (int i = 0; i < 5 - zipString.length(); i++) {
                sb_min.insert(0, "0");
            }
            zipString = sb_min.toString();
        }
        return zipString;
    }
}
