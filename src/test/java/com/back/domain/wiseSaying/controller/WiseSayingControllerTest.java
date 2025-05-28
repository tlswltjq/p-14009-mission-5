package com.back.domain.wiseSaying.controller;

import com.back.AppTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingControllerTest {
    @BeforeEach
    void beforeEach() {
        AppTest.clear();
    }

    @Test
    @DisplayName("등록")
    void t3() {
        // 종료 안넣으면 무한루프, 넣을시 실패말고 결과에 ignored뜸
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                종료
                """);

        assertThat(out)
                .contains("명언 :")
                .contains("작가 :")
                .containsPattern("\\d+번 명언이 등록되었습니다");
    }

    @Test
    @DisplayName("목록")
    void t4() {
        final String out = AppTest.run("""
                등록
                목록기능 테스트용 명언
                목록기능 테스트용 작가
                목록
                종료
                """);

        assertThat(out)
                .contains("번호 / 작가 / 명언")
                .contains("목록기능 테스트용 명언")
                .contains("목록기능 테스트용 작가");
    }

    @Test
    @DisplayName("삭제")
    void t5() {
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                삭제?id=1
                종료
                """);

        assertThat(out)
                .containsPattern("\\d+번 명언 삭제");

    }

    @Test
    @DisplayName("삭제 - 존재하지 않는 명언은 삭제할 수 없다.")
    void t6() {
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                삭제?id=2
                종료
                """);

        assertThat(out)
                .containsPattern("\\d+번 명언은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("수정")
    void t7() {
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                수정?id=1
                수정된 명언
                수정된 작가
                목록
                종료
                """);

        assertThat(out)
                .contains("1 / 수정된 작가 / 수정된 명언");
    }

    @Test
    @DisplayName("수정 - 존재하지 않는 명언은 수정할 수 없다.")
    void t8() {
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                수정?id=2
                종료
                """);

        assertThat(out)
                .containsPattern("\\d+번 명언은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("빌드")
    void t9() {
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                빌드
                종료
                """);

        assertThat(out).contains("data.json 파일의 내용이 갱신되었습니다.");
    }

    @Test
    @DisplayName("종료")
    void t10() {
        final String out = AppTest.run("""
                종료
                """);
        assertThat(out).contains("== 명언 앱을 종료합니다. ==");
    }

    @Test
    @DisplayName("목록 작가로 검색")
    void t11() {
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                미래를 사랑하라.
                작자미상
                목록?type=author&keyword=작자미상
                종료
                """);

        assertThat(out)
                .contains("번호 / 작가 / 명언")
                .contains("현재를 사랑하라.")
                .contains("미래를 사랑하라.");
    }

    @Test
    @DisplayName("목록 명언으로 검색")
    void t12() {
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                미래를 사랑하라.
                작자미상
                목록?type=content&keyword=사랑하라
                종료
                """);

        assertThat(out)
                .contains("번호 / 작가 / 명언")
                .contains("현재를 사랑하라.")
                .contains("미래를 사랑하라.");
    }

    @Test
    @DisplayName("목록 명언과 작가로 검색")
    void t13() {
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                미래를 사랑하라.
                작자미상
                목록?type=content&keyword=사랑하라&type=author&keyword=작자미상
                종료
                """);

        assertThat(out)
                .contains("번호 / 작가 / 명언")
                .contains("현재를 사랑하라.")
                .contains("미래를 사랑하라.");
    }
}
