class PlayerStock extends Stock {
    private int stockCount;

    public PlayerStock(Stock stock) {
        this.stockName = stock.getStockName();
        this.stockPrice = stock.getStockPrice();
        this.stockCount = 0;
    }

    public PlayerStock(String name, String price, String count) {
        this.stockName = name;
        this.stockPrice = Integer.parseInt(price);
        this.stockCount = Integer.parseInt(count);
    }

    public int getStockCount() {
        return this.stockCount;
    }

    public void setStockCount(int count) {
        this.stockCount = count;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(":");
        sb.append(this.stockCount);
        return sb.toString();
    }
}
