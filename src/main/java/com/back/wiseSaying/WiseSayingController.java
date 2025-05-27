package com.back.wiseSaying;

import java.util.Scanner;

public class WiseSayingController {
    private final Scanner scanner;
    private final WiseSayingService service;

    public WiseSayingController(Scanner scanner) {
        this.scanner = scanner;
        this.service = new WiseSayingService();
        sayHello();
    }

    public void sayHello() {
        System.out.println("== 명언 앱 ==");
    }

    public boolean executeCommand(String command) {
        Integer id = null;
        String content;
        String author;
        switch (command) {
            case "등록":
                System.out.print("명언 : ");
                content = scanner.nextLine().trim();
                System.out.print("작가 : ");
                author = scanner.nextLine().trim();
                service.registerWiseSaying(content, author);
                break;
            case "목록":
                service.showWiseSayingList();
                break;
            case "삭제":
                System.out.print("?id = ");
                id = Integer.parseInt(scanner.nextLine().trim());
                service.deleteWiseSaying(id);
                break;
            case "수정":
                System.out.print("?id = ");
                id = Integer.parseInt(scanner.nextLine().trim());
                WiseSaying wiseSaying = service.findById(id);
                if (wiseSaying != null) {

                    System.out.println("명언(기존) : " + wiseSaying.getContent());
                    System.out.print("명언 : ");
                    content = scanner.nextLine().trim();

                    System.out.println("작가(기존) : " + wiseSaying.getAuthor());
                    System.out.print("작가 : ");
                    author = scanner.nextLine().trim();

                    service.updateWiseSaying(wiseSaying.getId(), content, author);
                } else {
                    System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
                }
                break;
            case "빌드":
                service.buildWiseSaying();
                break;
            case "종료":
                return service.exit();
//            default:
//                System.out.println("잘못된 명령어입니다.");
        }
        return true;
    }
}
