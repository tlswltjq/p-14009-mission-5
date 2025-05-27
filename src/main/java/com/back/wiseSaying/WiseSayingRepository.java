package com.back.wiseSaying;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class WiseSayingRepository {
    private String dbPath;

    public WiseSayingRepository() {
        dbPath = WiseSayingApp.APP_ROOT + "/db/wiseSaying";
    }

    public WiseSaying save(WiseSaying wiseSaying) {
        Path filePath = Paths.get(dbPath, wiseSaying.getId() + ".json");
        try {
            Files.createDirectories(filePath.getParent());
            Files.writeString(filePath, wiseSaying.toJson());
            return wiseSaying;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveWiseSayings(List<WiseSaying> wiseSayings) {
        if (wiseSayings.isEmpty()) {
            return;
        }
        Path filePath = Paths.get(dbPath, "data.json");
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("[\n\t");
            wiseSayings.forEach(w -> sb.append(w.toJson() + ",\n\t"));
            sb.delete(sb.length() - 3, sb.length());
            sb.append("\n]");
            String data = sb.toString();
            Files.createDirectories(filePath.getParent());
            Files.writeString(filePath, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }

    public Integer getLastId() {
        Path filePath = Paths.get(dbPath, "lastId.txt");

        if (!Files.exists(filePath)) {
            return saveLastId(0);
        } else {
            try {
                return Integer.parseInt(Files.readString(filePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Integer saveLastId(Integer lastId) {
        Path filePath = Paths.get(dbPath, "lastId.txt");

        if (!Files.exists(filePath)) {
            try {
                Files.createDirectories(filePath.getParent());
                Files.writeString(filePath, 0 + "");
                return 0;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                Files.createDirectories(filePath.getParent());
                Files.writeString(filePath, lastId + "");
                return lastId;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public WiseSaying findById(Integer id) {
        try {
            String content = Files.readString(Paths.get(dbPath, id + ".json"));
            return WiseSaying.fromJson(content);
        } catch (IOException e) {
            return null;
        }
    }

    public List<WiseSaying> findAll() {
        try (Stream<Path> stream = Files.list(Paths.get(dbPath))) {
            return stream
                    .filter(file -> {
                        String fileName = file.getFileName().toString();
                        return fileName.matches("\\d+\\.json");
                    })
                    .map(file -> {
                        try {
                            String jsonContent = Files.readString(file);
                            return WiseSaying.fromJson(jsonContent);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .sorted(Comparator.comparing(WiseSaying::getId).reversed())
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public WiseSaying update(WiseSaying wiseSaying) {
        return save(wiseSaying);
    }

    public String delete(WiseSaying wiseSaying) {
        try {
            Files.deleteIfExists(Paths.get(dbPath, wiseSaying.getId() + ".json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return wiseSaying.getId() + "번 명언 삭제";
    }

    public String deleteById(Integer id) {
        WiseSaying wiseSaying = findById(id);
        return wiseSaying != null ? delete(wiseSaying) : id + "번 명언은 존재하지 않습니다.";
    }

    public String deleteAll() {
        findAll().forEach(this::delete);
        return "모두 삭제";
    }

    public void buildData() {
        List<WiseSaying> wiseSayingList = findAll();
        saveWiseSayings(wiseSayingList);
    }
}
