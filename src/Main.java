import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Please provide a file path as an argument.");
            return;
        }

        String pathProvided = args[0];

        // Process the input file, perform the k operations, and print the result.
        try {
            String result = processSequence(pathProvided);
            System.out.println(result);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

        // If you want to run multiple tests (e.g., from a folder):
        String userDir = System.getProperty("user.dir");
        String inputsPath = userDir + "/src/data/inputs";
        String outputsPath = userDir + "/src/data/outputs";runTests(inputsPath, outputsPath);
    }




    /**
     * Reads the input from the given file, performs the k operations on
     * the sequence using the described logic (ADD / DELETE with cyclic pointer),
     * and returns the final sequence in cyclic order as a String (starting at pointer P).
     */
    public static String processSequence(String filePath) throws IOException {
        int k;
        int[] sequenceArray;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            k = Integer.parseInt(br.readLine().trim());
            String line = br.readLine();
            if (line == null || line.isEmpty()) return "";
            String[] parts = line.split("\\s+");
            sequenceArray = Arrays.stream(parts).mapToInt(Integer::parseInt).toArray();
        }

        Sequence sequence = new Sequence(sequenceArray);

        sequence.performKOperations(k);
        if (sequence.equals(" ")) return " ";

        return sequence.toCyclicString();
    }


    /**
     * Runs multiple tests located in the inputDirPath,
     * compares the results against files in outputDirPath.
     */
    public static void runTests(String inputDirPath, String outputDirPath) {
        File inputDir = new File(inputDirPath);
        File outputDir = new File(outputDirPath);

        if (!inputDir.isDirectory()) {
            System.err.println("Input directory does not exist or is not a directory.");
            return;
        }

        if (!outputDir.isDirectory()) {
            System.err.println("Output directory does not exist or is not a directory.");
            return;
        }

        File[] inputFiles = inputDir.listFiles();
        if (inputFiles == null) {
            System.err.println("No input files found in " + inputDirPath);
            return;
        }

        // Sort input files by name to ensure consistent pairing with output files
        Arrays.sort(inputFiles);

        int passedTests = 0;
        int totalTests = 0;

        for (File inputFile : inputFiles) {
            String inputName = inputFile.getName();
            // Example: from "input1.txt" to "out1.txt"
            String outputName = inputName.replace("input", "out");
            File expectedOutputFile = new File(outputDir, outputName);

            if (!expectedOutputFile.exists()) {
                System.err.println("No corresponding output file for input " + inputName);
                continue;
            }

            totalTests++;

            try {
                // Use processSequence to get the result for the current input
                String result = processSequence(inputFile.getAbsolutePath());

                // Compare to the expected output (read the first line from the expected file)
                String expectedOutput = readFirstLine(expectedOutputFile);
                if (expectedOutput == null) {
                    expectedOutput = " ";
                }

                int areSame = areStrEqualAftSort(expectedOutput, result);

                if (result.equals(expectedOutput)) {
                    System.out.println("Test " + inputName + ": PASSED");
                    passedTests++;
                } else {
                    switch (areSame) {
                        case 1: {
                            System.out.println("Test " + inputName + ": FAILED");
                            System.out.println("======ARE SAME=======");
                        }
                        case -1: {
                            System.out.println("Test " + inputName + ": FAILED");
                            System.out.println("Expected: " + expectedOutput);
                            System.out.println("Got:      " + result);
                        }
                        case 69: {
                            System.out.println("Test " + inputName + ": PASSED");
                            passedTests++;
                        }
                    }


                }
            } catch (IOException e) {
                System.err.println("Error reading files for " + inputName + ": " + e.getMessage());
            }
        }

        System.out.println("Testing Complete. Passed " + passedTests + " out of " + totalTests + " tests.");
    }

    /**
     * Helper method: checks if data contain same numbers.
     * 69 marker for specific case when null or " " is expected/got
     */
    public static int areStrEqualAftSort(String expected, String got) {
        if (expected == null || got == null) {
            return 69;
        }
        if (expected == " " || got == " ") {
            return 69;
        }
        String[] arr1 = expected.trim().split("\\s+");
        String[] arr2 = got.trim().split("\\s+");
        if (arr1.length != arr2.length) {
            return -1;
        }
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        return Arrays.equals(arr1, arr2) ? 1 : -1;
    }

    /**
     * Helper method: read the first line from a file.
     */
    private static String readFirstLine(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return br.readLine();
        }
    }
}
