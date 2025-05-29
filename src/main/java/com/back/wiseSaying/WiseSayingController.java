package com.back.wiseSaying;

import java.util.Scanner;
import java.util.stream.IntStream;

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
        if (service.findAllWiseSayings().isEmpty()) {
            initSampleData();
        }
    }

    private void initSampleData() {
        IntStream.range(1, 11)
                .forEach(i -> service.registerWiseSaying("명언 " + i, "작자미상 " + i));
    }

    public boolean executeCommand(Request request) {
        String content;
        String author;

        switch (request.getCommand()) {
            case "등록":
                System.out.print("명언 : ");
                content = scanner.nextLine().trim();
                System.out.print("작가 : ");
                author = scanner.nextLine().trim();
                service.registerWiseSaying(content, author);
                break;
            case "목록":

                if (request.getParams().isEmpty()) {
                    service.showWiseSayingList();
                } else {
                    String type = request.getParams().get("type");
                    String keyword = request.getParams().get("keyword");
                    if (type != null && keyword != null) {
                        service.searchWiseSayings(type, keyword);
                    } else {
                        System.out.println("목록 명령어가 올바르지 않습니다. (예: 목록?type=content&keyword=안녕)");
                    }
                }
                break;
            case "삭제":
                service.deleteWiseSaying(request.getParams().get("id") != null ? Integer.parseInt(request.getParams().get("id")) : null);
                break;
            case "수정":
                Integer id = request.getParams().get("id") != null ? Integer.parseInt(request.getParams().get("id")) : null;
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
