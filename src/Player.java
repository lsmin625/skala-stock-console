class Player {
    private String name;
    private int userGold;

    public Player(String name, int userGold) {
        this.name = name;
        this.userGold = userGold;
    }

    public String getName() {
        return name;
    }

    public int getuserGold() {
        return userGold;
    }

    public void setuserGold(int userGold) {
        this.userGold = userGold;
    }

    public void display() {
        System.out.println(name + ": " + userGold + "골드");
    }

    public String toFileString() {
        return name + "," + userGold; // 파일에 저장할 형식
    }

    public static Stock fromFileString(String line) {
        String[] parts = line.split(",");
        return new Stock(parts[0], Integer.parseInt(parts[1]));
    }
}
