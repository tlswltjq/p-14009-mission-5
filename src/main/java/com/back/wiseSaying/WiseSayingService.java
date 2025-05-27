package com.back.wiseSaying;

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
        System.out.println("번호 / 작가 / 명언");
        System.out.println("-----------------------------");
        repository.findAll().forEach(System.out::println);
    }

    public void deleteWiseSaying(Integer id) {
        System.out.println(repository.deleteById(id));
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
}
