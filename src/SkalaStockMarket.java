import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SkalaStockMarket {
    private PlayerRepository playerRepository = new PlayerRepository();
    private StockRepository stockRepository = new StockRepository();
    private Player player = null;

    public void start() {

        // 주식 레파지토리에서 주식 정보를 불러옴
        stockRepository.loadStockList();

        // 플레이어 레파지토리에서 플레이어 정보를 불러옴
        playerRepository.loadPlayerList();

        // 콘솔로 입력을 받을 수 있도록 스캐너 설정
        Scanner scanner = new Scanner(System.in);

        System.out.print("플레이어 ID를 입력하세요: ");
        String playerId = scanner.nextLine();
        player = playerRepository.findPlayer(playerId);
        if (player == null) { // 새로운 플레이어
            player = new Player(playerId);

            System.out.print("초기 투자금을 입력하세요 (스칼라): ");
            int money = scanner.nextInt();
            player.setPlayerMoney(money);
            playerRepository.addPlayer(player);
        }

        System.out.println("\n환영합니다, " + playerId + "!");
        boolean running = true;

        // 게임 루프
        while (running) {
            System.out.println("\n=== 스칼라 주식 시장 ===");
            System.out.println("1. 보유 주식 목록");
            System.out.println("2. 주식 구매");
            System.out.println("3. 주식 판매");
            System.out.println("4. 다음 날");
            System.out.println("5. 프로그램 종료");
            System.out.print("선택: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayStockList();
                    break;
                case 2:
                    buyStock(scanner);
                    break;
                case 3:
                    sellStock(scanner);
                    break;
                case 4:
                    simulateNextDay();
                    break;
                case 5:
                    saveStocksToFile();
                    System.out.println("게임을 종료합니다. 최종 자산: " + userGold + "골드");
                    running = false;
                    break;
                default:
                    System.out.println("올바른 번호를 선택하세요.");
            }
        }

        scanner.close();
    }

    // 주식 목록 표시
    private void displayStockList() {
        System.out.println("\n=== 주식 목록 ===");
        for (Stock stock : stockList) {
            stock.display();
        }
        System.out.println("보유 골드: " + userGold + "골드");
    }

    // 주식 구매
    private void buyStock(Scanner scanner) {
        System.out.println("\n구매할 주식 번호를 선택하세요:");
        displayStockList();
        int stockIndex = scanner.nextInt() - 1;

        if (stockIndex >= 0 && stockIndex < stockList.size()) {
            Stock selectedStock = stockList.get(stockIndex);
            System.out.print("구매할 수량을 입력하세요: ");
            int quantity = scanner.nextInt();

            int totalCost = selectedStock.getPrice() * quantity;

            if (totalCost <= userGold) {
                userGold -= totalCost;
                System.out.println(quantity + "주를 구매했습니다! 남은 골드: " + userGold + "골드");
            } else {
                System.out.println("골드가 부족합니다.");
            }
        } else {
            System.out.println("잘못된 선택입니다.");
        }
    }

    // 주식 판매
    private void sellStock(Scanner scanner) {
        System.out.println("\n판매할 주식 번호를 선택하세요:");
        displayStockList();
        int stockIndex = scanner.nextInt() - 1;

        if (stockIndex >= 0 && stockIndex < stockList.size()) {
            Stock selectedStock = stockList.get(stockIndex);
            System.out.print("판매할 수량을 입력하세요: ");
            int quantity = scanner.nextInt();

            int totalGain = selectedStock.getPrice() * quantity;

            userGold += totalGain;
            System.out.println(quantity + "주를 판매했습니다! 현재 골드: " + userGold + "골드");
        } else {
            System.out.println("잘못된 선택입니다.");
        }
    }

    // 다음 날 시뮬레이션
    private void simulateNextDay() {
        System.out.println("\n다음 날이 시작되었습니다!");
        for (Stock stock : stockList) {
            int priceChange = (int) (Math.random() * 21) - 10; // -10에서 +10 사이의 변화
            stock.setPrice(stock.getPrice() + priceChange);

            if (stock.getPrice() < 0) {
                stock.setPrice(0); // 주식 가격은 0 이하로 내려가지 않음
            }
        }
        System.out.println("주식 가격이 업데이트되었습니다.");
        displayStockList();
    }

    // 주식 정보를 파일에서 읽어옴
    private void loadStockList() {
        try (BufferedReader reader = new BufferedReader(new FileReader(STOCK_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stockList.add(Stock.fromFileString(line));
            }
        } catch (IOException e) {
            System.out.println("파일을 불러오는 중 오류가 발생했습니다. 새로운 게임을 시작합니다.");
            // 파일이 없으면 기본 데이터 추가
            stockList.add(new Stock("TechCorp", 100));
            stockList.add(new Stock("GreenEnergy", 80));
            stockList.add(new Stock("HealthPlus", 120));
        }
    }

    // 주식 정보를 파일에 저장
    private void saveStocksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STOCK_FILE))) {
            for (Stock stock : stockList) {
                writer.write(stock.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("파일에 저장하는 중 오류가 발생했습니다.");
        }
    }
}
