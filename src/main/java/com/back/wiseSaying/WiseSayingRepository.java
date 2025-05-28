package com.back.wiseSaying;

import java.util.List;

public interface WiseSayingRepository {
    WiseSaying save(WiseSaying wiseSaying);

    Integer getLastId();

    Integer saveLastId(Integer lastId);

    WiseSaying findById(Integer id);

    List<WiseSaying> findByContent(String content);

    List<WiseSaying> findByAuthor(String author);

    List<WiseSaying> findByContentAndAuthor(String content, String author);

    List<WiseSaying> findAll();

    WiseSaying update(WiseSaying wiseSaying);

    String delete(WiseSaying wiseSaying);

    String deleteById(Integer id);

    String deleteAll();

    void buildData();
}
