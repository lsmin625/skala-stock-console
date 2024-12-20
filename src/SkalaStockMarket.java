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

            System.out.print("초기 투자금을 입력하세요: ");
            int money = scanner.nextInt();
            player.setPlayerMoney(money);
            playerRepository.addPlayer(player);
        }

        System.out.println("\n환영합니다, " + playerId + "!");
        boolean running = true;

        // 프로그램 루프
        while (running) {
            System.out.println("\n=== 스칼라 주식 시장 ===");
            System.out.println(Menu.PLAYER_STOCKS.toString());
            System.out.println(Menu.BUY_STOCK.toString());
            System.out.println(Menu.SELL_STOCK.toString());
            System.out.println(Menu.EXIT.toString());

            System.out.print("선택: ");
            int code = scanner.nextInt();

            switch (Menu.fromCode(code)) {
                case Menu.PLAYER_STOCKS:
                    displayPlayerStocks();
                    break;
                case Menu.BUY_STOCK:
                    buyStock(scanner);
                    break;
                case Menu.SELL_STOCK:
                    sellStock(scanner);
                    break;
                case Menu.EXIT:
                    System.out.println("프로그램을 종료합니다. 최종 자산: ");
                    running = false;
                    break;
                default:
                    System.out.println("올바른 번호를 선택하세요.");
            }
        }

        scanner.close();
    }

    // 플레이어의 보유 주식 목록 표시
    private void displayPlayerStocks() {
        System.out.println("\n=== 보유 주식 목록 ===");
        System.out.println(player.getPlayerStocksForMenu());
    }

    // 주식 목록 표시
    private void displayStockList() {
        System.out.println("\n=== 주식 목록 ===");
        System.out.println(stockRepository.getStockListForMenu());
    }

    // 주식 구매
    private void buyStock(Scanner scanner) {
        System.out.println("\n구매할 주식 번호를 선택하세요:");
        displayStockList();

        System.out.print("선택: ");
        int index = scanner.nextInt() - 1;

        Stock selectedStock = stockRepository.finStock(index);
        if (selectedStock != null) {
            System.out.print("구매할 수량을 입력하세요: ");
            int quantity = scanner.nextInt();

            int totalCost = selectedStock.getStockPrice() * quantity;
            int playerMoney = player.getPlayerMoney();
            if (totalCost <= playerMoney) {
                player.setPlayerMoney(playerMoney - totalCost);
                player.addStock(new PlayerStock(selectedStock, quantity));
                System.out.println(quantity + "주를 구매했습니다! 남은 금액: " + player.getPlayerMoney());

                // 변경된 내용을 파일로 저장
                playerRepository.savePlayerList();
            } else {
                System.out.println("금액이 부족합니다.");
            }
        } else {
            System.out.println("잘못된 선택입니다.");
        }
    }

    // 주식 판매
    private void sellStock(Scanner scanner) {
        System.out.println("\n판매할 주식 번호를 선택하세요:");
        displayPlayerStocks();

        System.out.print("선택: ");
        int index = scanner.nextInt() - 1;

        PlayerStock playerStock = player.finStock(index);
        if (playerStock != null) {
            System.out.print("판매할 수량을 입력하세요: ");
            int quantity = scanner.nextInt();

            // 어얼리 리턴
            if (quantity > playerStock.getStockQuantity()) {
                System.out.println("수량이 부족합니다.");
                return;
            }

            Stock baseStock = stockRepository.finStock(playerStock.getStockName());
            int playerMoney = player.getPlayerMoney() + baseStock.getStockPrice() * quantity;
            player.setPlayerMoney(playerMoney);

            playerStock.setStockQuantity(playerStock.getStockQuantity() - quantity);
            player.updatePlayerStock(playerStock);

            // 변경된 내용을 파일로 저장
            playerRepository.savePlayerList();

        } else {
            System.out.println("잘못된 선택입니다.");
        }
    }
}