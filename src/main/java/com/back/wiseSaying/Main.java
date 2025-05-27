package com.back.wiseSaying;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        WiseSayingApp newApp = new WiseSayingApp(new Scanner(System.in));
        newApp.start();
    }
}