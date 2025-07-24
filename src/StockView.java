import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

/**
 * 콘솔 입출력과 관련된 모든 것을 처리합니다.
 */
public class StockView {
    private final Scanner scanner;

    public StockView() {
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }

    public String promptForPlayerId() {
        System.out.print("\n▶ 플레이어 ID를 입력하세요: ");
        return scanner.nextLine();
    }

    public int promptForInitialMoney() {
        System.out.print("▶ 초기 투자금을 입력하세요: ");
        int money = scanner.nextInt();
        scanner.nextLine();
        return money;
    }

    public int showMenuAndGetSelection() {
        System.out.println("\n======= 스칼라 주식 시장 =======");
        System.out.println("  1. 💵 나의 자산 확인");
        System.out.println("  2. 📈 주식 구매");
        System.out.println("  3. 📉 주식 판매");
        System.out.println("  0. 🔚 프로그램 종료");
        System.out.println("=============================");
        System.out.print("▶ 선택: ");
        int selection = scanner.nextInt();
        scanner.nextLine();
        return selection;
    }

    public void displayPlayerInfo(Player player) {
        System.out.println("\n======= 👤 플레이어 정보 =======");
        System.out.println("  ID: " + player.getId());
        System.out.println("  보유 현금: " + String.format("%,d", player.getMoney()));
        System.out.println("-----------------------------");
        System.out.println("  보유 주식 목록:");
        PortfolioFormatter formatter = new MenuPortfolioFormatter();
        String formattedStocks = formatter.format(player.getPortfolio());
        if (formattedStocks.isEmpty()) {
            System.out.println("    (보유 주식이 없습니다)");
        } else {
            System.out.print(formattedStocks);
        }
        System.out.println("=============================");
    }

    public void displayStockList(List<Stock> stockList) {
        System.out.println("\n======= 📊 현재 주식 시세 =======");
        for (int i = 0; i < stockList.size(); i++) {
            Stock stock = stockList.get(i);
            System.out.println(
                    "  " + (i + 1) + ". " + stock.getName() + " - " + String.format("%,d", stock.getPrice()) + "원");
        }
        System.out.println("=============================");
    }

    public int getStockIndexFromUser() {
        System.out.print("▶ 주식 번호를 선택하세요: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine();
        return index;
    }

    public int getQuantityFromUser() {
        System.out.print("▶ 수량을 입력하세요: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        return quantity;
    }

    public void showMessage(String message) {
        System.out.println("📢 " + message);
    }

    public void close() {
        scanner.close();
    }
}