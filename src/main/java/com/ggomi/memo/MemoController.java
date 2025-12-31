package com.ggomi.memo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * Restful 네이밍 규칙
 * 1. URL은 명사로 구성
 * 2. 행위는 HTTP Method로 표현
 * 3. _ 대신에 - 사용
 * 4. 소문자만 사용
 * 5. 복수형 사용 권장
 */

/**
 * RequestMapping : 컨트롤러 내부 메소드들의 매핑 URL의 공통된 부분이 있다면 컨트롤러에 RequestMapping을 추가하여 정리할 수 있다.
 */
@RequestMapping("/memos")
@RestController
public class MemoController {
    private final DataSource dataSource;
    
    // 스프링 컨테이너에서 DataSource를 주입받는다.
    public MemoController(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    // C(Create) : 신규 항목 추가
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> memoData) {
        // 클라이언트(화면)로부터 전달받은 body 데이터에서 content 값 추출
        String content = memoData.get("content").toString();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            // DB에 연결
            connection = dataSource.getConnection();

            // SQL 쿼리 작성
            String sql = "INSERT INTO MEMO (CONTENT) VALUES (?)";
            preparedStatement = connection.prepareStatement(sql);

            // 쿼리의 파라미터 세팅
            preparedStatement.setString(1, content);

            // 쿼리 실행
            int insertCount = preparedStatement.executeUpdate();
            if (insertCount == 0) {
                return ResponseEntity.internalServerError().body("신규 항목 추가 실패");
            } else {
                return ResponseEntity.ok().body("신규 항목 추가 성공");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("신규 항목 추가 실패");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // R(Read) : 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.ok().body("상세 조회");
    }

    // R(Read) : 목록 조회
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body("목록 조회");
    }

    // U(Update) : 전체 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable String id, @RequestBody Map<String, Object> memoData) {
        return ResponseEntity.ok().body("전체 수정");
    }

    // U(Update) : 부분 수정
    @PatchMapping("/{id}")
    public ResponseEntity<?> modifyById(@PathVariable String id, @RequestBody Map<String, Object> memoData) {
        return ResponseEntity.ok().body("부분 수정");
    }

    // D(Delete) : 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        return ResponseEntity.ok().body("삭제");
    }
}