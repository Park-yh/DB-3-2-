import java.sql.*;
import java.util.Scanner;

public class BookSort {

    // 책 정보 정렬 기능
    public void sortBooks() {
        Scanner scanner = new Scanner(System.in);

        // 사용자로부터 정렬 기준을 입력받기
        System.out.println("정렬 기준을 선택하세요:");
        System.out.println("1. 책 제목");
        System.out.println("2. 책 판매 글 등록일");
        System.out.println("3. 제시 금액");
        System.out.print("원하는 정렬 기준의 번호를 선택하세요: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // 버퍼 비우기

        // 사용자로부터 정렬 순서를 입력받기
        System.out.println("정렬 순서를 선택하세요:");
        System.out.println("1. 오름차순 (ASC)");
        System.out.println("2. 내림차순 (DESC)");
        System.out.print("원하는 정렬 순서의 번호를 선택하세요: ");
        int orderChoice = scanner.nextInt();
        scanner.nextLine();  // 버퍼 비우기

        // DB 연결 및 책 정보 조회
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "";
            String order = (orderChoice == 1) ? "ASC" : "DESC";  // 오름차순 또는 내림차순 선택

            // 선택한 정렬 기준에 맞춰 SQL 쿼리 작성
            switch (choice) {
                case 1:
                    sql = "SELECT 책.일련번호, 책.제목, 등록내역.등록일자, 등록내역.등록가격 " +
                            "FROM 책 " +
                            "JOIN 등록내역 ON 책.일련번호 = 등록내역.일련번호 " +
                            "ORDER BY 책.제목 " + order;  // 책 제목 기준 정렬
                    break;
                case 2:
                    sql = "SELECT 책.일련번호, 책.제목, 등록내역.등록일자, 등록내역.등록가격 " +
                            "FROM 책 " +
                            "JOIN 등록내역 ON 책.일련번호 = 등록내역.일련번호 " +
                            "ORDER BY 등록내역.등록일자 " + order;  // 책 판매 글 등록일 기준 정렬
                    break;
                case 3:
                    sql = "SELECT 책.일련번호, 책.제목, 등록내역.등록일자, 등록내역.등록가격 " +
                            "FROM 책 " +
                            "JOIN 등록내역 ON 책.일련번호 = 등록내역.일련번호 " +
                            "ORDER BY 등록내역.등록가격 " + order;  // 제시 금액 기준 정렬
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
                    return;
            }

            // 쿼리 실행
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    System.out.println("일련번호\t제목\t등록일자\t등록가격");
                    boolean hasResults = false;
                    while (rs.next()) {
                        String bookID = rs.getString("일련번호");
                        String title = rs.getString("제목");
                        Date registrationDate = rs.getDate("등록일자");
                        double price = rs.getDouble("등록가격");
                        System.out.println(bookID + "\t" + title + "\t" + registrationDate + "\t" + price);
                        hasResults = true;
                    }

                    // 결과가 없을 경우 메시지 출력
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
