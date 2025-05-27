package com.back.wiseSaying;

import java.util.Scanner;

public class WiseSayingApp {
    private Scanner scanner;
    static String APP_ROOT;
    private final WiseSayingController controller;
    private boolean isRunning = true;

    public WiseSayingApp(Scanner scanner) {
        APP_ROOT = "./src/main/resources";
        this.scanner = scanner;
        this.controller = new WiseSayingController(scanner);
    }

    public WiseSayingApp(Scanner scanner, String appRoot) {
        APP_ROOT = appRoot;
        this.scanner = scanner;
        this.controller = new WiseSayingController(scanner);
    }

    public void start() {
        while (isRunning) {
            String cmd;
            System.out.print("명령) ");
            cmd = scanner.nextLine().trim();
            isRunning = controller.executeCommand(cmd);
        }
    }
}
