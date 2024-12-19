import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class PlayerRepository {

    // 플레이어 정보를 저장할 파일
    private final String PLAYER_FILE = "data/players.txt";

    // 플레이어 정보 목록 (메모리)
    private ArrayList<Player> playerList = new ArrayList<>();

    // 플레이어 정보를 파일에서 읽어옴
    public void loadPlayerList() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PLAYER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Player player = parseLineToPlayer(line);
                if (player != null) {
                    playerList.add(player);
                }
            }
        } catch (IOException e) {
            System.out.println("플레이어 정보가 없습니다.");
        }
    }

    // 플레이어 정보를 파일에 저장
    public void savePlayerList() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PLAYER_FILE))) {
            for (Player player : playerList) {
                writer.write(player.getplayerId() + "," + player.getPlayerMoney() + "," + player.getStocksAsString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("파일에 저장하는 중 오류가 발생했습니다.");
        }
    }

    // 파일 라인을 Player 객체로 변환
    private Player parseLineToPlayer(String line) {
        String fileds[] = line.split(",");
        if (fileds.length > 1) {
            Player player = new Player();
            player.setplayerId(fileds[0]);
            player.setPlayerMoney(Integer.parseInt(fileds[1]));
            return player;
        } else {
            System.out.println("라인을 분석할 수 없습니다. line=" + line);
            return null;
        }
    }

    // 플레이어 검색
    public Player findPlayer(String id) {
        for (Player player : playerList) {
            if (player.getplayerId().equals(id)) {
                return player;
            }
        }
        return null;
    }

    // 플레이어 추가 후 파일에 저장
    public void addPlayer(Player player) {
        playerList.add(player);
        savePlayerList();
    }
}
