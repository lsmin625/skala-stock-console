import java.util.ArrayList;

class Player {
    private String playerId;
    private int playerMoney;
    private ArrayList<PlayerStock> stocks = new ArrayList<>();

    public Player() {
    }

    public Player(String id) {
        this.playerId = id;
        this.playerMoney = 10000;
    }

    public void setplayerId(String id) {
        this.playerId = id;
    }

    public String getplayerId() {
        return this.playerId;
    }

    public int getPlayerMoney() {
        return this.playerMoney;
    }

    public void setPlayerMoney(int money) {
        this.playerMoney = money;
    }

    public void addStock(PlayerStock stock) {
        boolean stockExists = false;

        for (PlayerStock existingStock : stocks) {
            if (existingStock.getStockName().equals(stock.getStockName())) {
                existingStock.setStockPrice(stock.getStockPrice());
                existingStock.setStockQuantity(existingStock.getStockQuantity());
                stockExists = true;
                break;
            }
        }

        if (!stockExists) {
            stocks.add(stock);
        }
    }

    public String getStocksAsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stocks.size(); i++) {
            if (i > 0) {
                sb.append("|");
            }
            sb.append(stocks.get(i));
        }
        return sb.toString();
    }

    public String getStocksString() {
        StringBuilder sb = new StringBuilder();
        for (PlayerStock stock : stocks) {
            sb.append(stock.toString());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public void setStockList(ArrayList<PlayerStock> stocks) {
        this.stocks = stocks;
    }
}
