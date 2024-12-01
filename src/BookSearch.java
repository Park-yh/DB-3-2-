import java.sql.*;
import java.util.Scanner;

public class BookSearch {

    // 책 정보 검색 기능
    public void searchBooksByTitleOrAuthor() {
        Scanner scanner = new Scanner(System.in);

        // 사용자로부터 책 제목 또는 저자 입력받기
        System.out.print("책 제목 또는 저자를 입력하세요: ");
        String searchTerm = scanner.nextLine();  // 책 제목 또는 저자

        // DB 연결 및 책 정보 조회
        try (Connection conn = DBConnection.getConnection()) {
            // SQL 쿼리: 책 제목 또는 저자를 기준으로 책 정보 조회
            String sql = "SELECT 책.일련번호, 책.제목, 책.저자, 등록내역.책상태 " +
                    "FROM 책 " +
                    "JOIN 등록내역 ON 책.일련번호 = 등록내역.일련번호 " +
                    "WHERE UPPER(책.제목) LIKE ? OR UPPER(책.저자) LIKE ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // 입력된 검색어를 대소문자 구분 없이 처리, '%'로 감싸서 LIKE 쿼리 사용
                String searchTermUpper = "%" + searchTerm.toUpperCase().trim() + "%";
                stmt.setString(1, searchTermUpper);  // 책 제목 기준 검색
                stmt.setString(2, searchTermUpper);  // 저자 기준 검색

                // 쿼리 실행
                try (ResultSet rs = stmt.executeQuery()) {
                    System.out.println("일련번호\t제목\t저자\t책 상태");
                    boolean hasResults = false;
                    while (rs.next()) {
                        String bookID = rs.getString("일련번호");
                        String title = rs.getString("제목");
                        String author = rs.getString("저자");
                        String bookCondition = rs.getString("책상태");
                        System.out.println(bookID + "\t" + title + "\t" + author + "\t" + bookCondition);
                        hasResults = true;
                    }

                    // 검색 결과가 없을 경우 메시지 출력
                    if (!hasResults) {
                        System.out.println("검색 결과가 없습니다.");
                    }
                }
            }
        } catch (SQLException e) {
            // 쿼리 실행 오류 처리
            System.out.println("쿼리 실행 오류: " + e.getMessage());
        }
    }
}
