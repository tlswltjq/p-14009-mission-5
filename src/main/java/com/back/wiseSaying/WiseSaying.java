package com.back.wiseSaying;

public class WiseSaying {
    private Integer id;
    private String content;
    private String author;

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public WiseSaying(Integer id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public WiseSaying update(String content, String author) {
        this.content = content;
        this.author = author;
        return this;
    }

    public static WiseSaying fromJson(String content) {
        int idStart = content.indexOf("\"id\":") + 5;
        int idEnd = content.indexOf(",", idStart);
        Integer id = Integer.parseInt(content.substring(idStart, idEnd).trim());

        int contentStart = content.indexOf("\"content\":\"") + 11;
        int contentEnd = content.indexOf("\"", contentStart);
        String wiseSayingContent = content.substring(contentStart, contentEnd);

        int authorStart = content.indexOf("\"author\":\"") + 10;
        int authorEnd = content.indexOf("\"", authorStart);
        String author = content.substring(authorStart, authorEnd);
        return new WiseSaying(id, wiseSayingContent, author);
    }

    public String toJson() {
        return "{\"id\":" + this.id + ",\"content\":\"" + this.content + "\",\"author\":\"" + this.author + "\"}";
    }

    @Override
    public String toString() {
        return id + " / " + author + " / " + content;
    }
}
