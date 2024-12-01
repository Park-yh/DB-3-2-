import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 기능 클래스 인스턴스화
        TransactionSearch transactionSearch = new TransactionSearch();
        BookSearch bookSearch = new BookSearch();
        BookSort bookSort = new BookSort();
        PurchaseRequestSearch purchaseRequestSearch = new PurchaseRequestSearch();
        SaleRequestSearch saleRequestSearch = new SaleRequestSearch();
        TransactionInsert transactionInsert = new TransactionInsert();  // 거래 삽입 기능 클래스 인스턴스화
        BookRegister bookRegister = new BookRegister();  // 책 등록 기능 클래스 인스턴스화

        // 메뉴 출력
        while (true) {
            System.out.println("1. 거래 기록 조회");
            System.out.println("2. 책 정보 검색");
            System.out.println("3. 책 정보 정렬");
            System.out.println("4. 구매 신청 내역 검색");
            System.out.println("5. 판매 신청 내역 검색");
            System.out.println("6. 거래 삽입");
            System.out.println("7. 책 등록");
            System.out.println("8. 종료");
            System.out.print("원하는 기능의 번호를 선택하세요: ");
            String choice = scanner.nextLine();  // 숫자가 아닌 문자열로 입력 받기

            try {
                int choiceNum = Integer.parseInt(choice);  // 문자열을 숫자로 변환

                switch (choiceNum) {
                    case 1:
                        // 거래 기록 조회 기능 실행
                        transactionSearch.searchTransactionsByDateRange();
                        break;
                    case 2:
                        // 책 정보 검색 기능 실행
                        bookSearch.searchBooksByTitleOrAuthor();
                        break;
                    case 3:
                        // 책 정보 정렬 기능 실행
                        bookSort.sortBooks();
                        break;
                    case 4:
                        // 구매 신청 내역 검색 기능 실행
                        purchaseRequestSearch.searchPurchaseRequests();
                        break;
                    case 5:
                        // 판매 신청 내역 검색 기능 실행
                        saleRequestSearch.searchSaleRequests();
                        break;
                    case 6:
                        // 거래 삽입 기능 실행
                        transactionInsert.insertTransaction();
                        break;
                    case 7:
                        // 책 등록 기능 실행
                        bookRegister.registerBook();
                        break;
                    case 8:
                        System.out.println("프로그램을 종료합니다.");
                        return;  // 프로그램 종료
                    default:
                        System.out.println("잘못된 선택입니다. 다시 선택하세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자만 입력해 주세요.");
            }
        }
    }
}
