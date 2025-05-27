package com.back;


import com.back.standard.util.TestUtil;
import com.back.wiseSaying.WiseSayingApp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Scanner;

public class AppTest {
    static String appRoot = "./src/test/resources";

    public static String run(String input) {
        Scanner scanner = TestUtil.genScanner(input);

        ByteArrayOutputStream capturedOutput = TestUtil.setOutToByteArray();

        WiseSayingApp wiseSayingApp = new WiseSayingApp(scanner, appRoot);
        wiseSayingApp.start();

        String outputString = capturedOutput.toString();

        TestUtil.clearSetOutToByteArray(capturedOutput);

        return outputString;
    }

    public static void clear() {
        deleteAll();
    }

    public static void deleteAll() {
        Path directory = Paths.get(appRoot + "/db/wiseSaying");

        try {
            if (Files.exists(directory)) {
                Files.walk(directory)
                        .sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                throw new RuntimeException("Failed to delete: " + path, e);
                            }
                        });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}