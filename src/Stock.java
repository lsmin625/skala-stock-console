class Stock {
    private String name;
    private int price;

    public Stock(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void display() {
        System.out.println(name + ": " + price + "골드");
    }

    public String toFileString() {
        return name + "," + price; // 파일에 저장할 형식
    }

    public static Stock fromFileString(String line) {
        String[] parts = line.split(",");
        return new Stock(parts[0], Integer.parseInt(parts[1]));
    }
}
