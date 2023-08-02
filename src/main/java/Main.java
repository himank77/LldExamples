import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        //Scanner myInput = new Scanner( System.in );
        int count = 5;
        int zeroWin = 0;
        int crossWin = 0;
        //List<String> result = new ArrayList<>();
        while(count-- > 0) {
            Game.Player p1 = new Game.Player("alice-X", true, true);
            Game.Player p2 = new Game.Player("bob-0", false, true);
            Game game = new Game();
            game.initialize(p1, p2);
            while (!game.isEnded()) {
                game.print();
                while (true) {
                    System.out.println(game.getCurrentPlayer().getName() + " play your turn for match"+count);
                    int x = new Random().nextInt(3);//myInput.nextInt();
                    int y = new Random().nextInt(3);//myInput.nextInt();
                    if (game.makeMove(game.getCurrentPlayer(), x, y)) {
                        break;
                    }
                }
                System.out.println(game.getStatus());
                if(game.getStatus().equals(Game.GameStatus.CORSS_WIN))
                    crossWin++;
                else if(game.getStatus().equals(Game.GameStatus.ZERO_WIN))
                    zeroWin++;

                }
            }
        System.out.println("CROSS:" + crossWin + " ZERO: " + zeroWin);
    }
}
