package com.back.wiseSaying;

import java.util.List;

public class WiseSayingService {
    private final WiseSayingRepository repository;

    public WiseSayingService() {
        this.repository = new WiseSayingFileRepository();
    }

    public WiseSaying registerWiseSaying(String wiseSayingContent, String author) {
        WiseSaying wiseSaying = createWiseSaying(wiseSayingContent, author);
        System.out.println(wiseSaying.getId() + "번 명언이 등록되었습니다.");
        return wiseSaying;
    }

    public void showWiseSayingList() {
        List<WiseSaying> wiseSayingList = findAllWiseSayings();
        System.out.println("번호 / 작가 / 명언");
        System.out.println("-----------------------------");
        wiseSayingList.forEach(System.out::println);
    }

    public List<WiseSaying> findAllWiseSayings(){
        return repository.findAll();
    }

    public void deleteWiseSaying(Integer id) {
        System.out.println(repository.deleteById(id));
    }

    public void deleteAllWiseSayings() {
        System.out.println(repository.deleteAll());
    }

    public void updateWiseSaying(int id, String newContent, String newAuthor) {
        WiseSaying wiseSaying = repository.findById(id);
        WiseSaying updated = wiseSaying.update(newContent, newAuthor);
        repository.update(updated);
    }

    public void buildWiseSaying() {
        repository.buildData();
    }

    public boolean exit() {
        System.out.println("== 명언 앱을 종료합니다. ==");
        return false;
    }

    private WiseSaying createWiseSaying(String wiseSayingContent, String author) {
        Integer lastId = repository.getLastId();
        WiseSaying wiseSaying = repository.save(new WiseSaying(++lastId, wiseSayingContent, author));
        repository.saveLastId(lastId);
        return wiseSaying;
    }

    public WiseSaying findById(int id) {
        return repository.findById(id);
    }

    public void searchWiseSayings(String type, String keyword) {
        List<WiseSaying> searchResults;
        switch (type) {
            case "content":
                searchResults = repository.findByContent(keyword);
                break;
            case "author":
                searchResults = repository.findByAuthor(keyword);
                break;
            case "all":
                String[] keywords = keyword.split(" ");
                if (keywords.length == 2) {
                    searchResults = repository.findByContentAndAuthor(keywords[0], keywords[1]);
                } else {
                    System.out.println("검색 형식이 올바르지 않습니다. (예: all 명언내용 작가명)");
                    return;
                }
                break;
            default:
                System.out.println("지원하지 않는 검색 타입입니다. (content, author, all)");
                return;
        }

        if (searchResults.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
        } else {
            System.out.println("번호 / 작가 / 명언");
            System.out.println("-----------------------------");
            searchResults.forEach(System.out::println);
        }
    }
}
