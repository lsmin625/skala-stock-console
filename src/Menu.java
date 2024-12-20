public enum Menu {
    PLAYER_STOCKS(1, "보유 주식 목록"),
    BUY_STOCK(2, "주식 구매"),
    SELL_STOCK(3, "주식 판매"),
    EXIT(0, "프로그램 종료");

    private final int code;
    private final String name;

    Menu(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(code);
        sb.append(". ");
        sb.append(name);
        return sb.toString();
    }

    public static Menu fromCode(int code) {
        for (Menu menu : values()) {
            if (menu.getCode() == code) {
                return menu;
            }
        }
        return null;
    }
}
