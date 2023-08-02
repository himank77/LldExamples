import java.util.ArrayList;
import java.util.List;


public class Game {

    private Player player1;
    private Player player2;
    private Grid grid = new Grid();
    private Player currentPlayer;
    private GameStatus status;
    private List<Move> moves;

    public GameStatus getStatus(){
        return status;
    }

    public boolean isEnded(){
        if(status == GameStatus.CORSS_WIN || status == GameStatus.ZERO_WIN){
            return true;
        }
        return false;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static class Player{
        private String name;
        private boolean isCross;
        private boolean humanPlayer;
        public Player( String name,boolean isCross, boolean humanPlayer){
            this.name = name;
            this.humanPlayer = humanPlayer;
            this.isCross = isCross;
        }

        public String getName() {
            return name;
        }
    }

    public class Grid{
        Cell[][] boxes;
        public Grid(){
            boxes = new Cell[3][3];
            reset();
        }
        public Cell[][] getBoxes(){
            return boxes;
        }
        public void reset() {
            for(int i=0;i<3;i++)
                for(int j=0;j<3;j++)
                    boxes[i][j] = new Cell(i,j);
        }
    }

    public class Cell {
        public Cell(int i,int j){
            x=i;
            y=j;
            state = CellState.EMPTY;
        }
        private CellState state;
        private int x;
        private int y;
    }

    public class Move {
        public Move(Cell cell, Player player){
            this.cell = cell;
            this.player = player;
        }
        Cell cell;
        Player player;
    }

    public enum GameStatus {
        YET_TO_START,IN_PROGRESS,CORSS_WIN,ZERO_WIN
    }

    public enum CellState{
        EMPTY(" "), CROSS("X"), ZERO("0");
        String pritable;
        CellState(String s) {
            pritable = s;
        }
    }

    public void initialize(Player p1, Player p2){
        player1 = p1;
        player2 = p2;
        currentPlayer = p1;
        this.grid.reset();
        this.status = GameStatus.YET_TO_START;
        moves = new ArrayList<>();
    }

    public boolean makeMove(Player player, int x, int y) {
        this.status = GameStatus.IN_PROGRESS;
        Cell currentCell = this.grid.getBoxes()[x][y];
        if(currentCell.state != CellState.EMPTY || player!=this.currentPlayer){
            return false;
        }
        if(player.isCross)
            currentCell.state = CellState.CROSS;
        else
            currentCell.state = CellState.ZERO;

        moves.add(new Move(currentCell,player));
        if(this.currentPlayer == player1)
            this.currentPlayer = player2;
        else
            this.currentPlayer = player1;

        return computeState();
    }

    private boolean computeState() {
        for(int i=0;i<3;i++) {
            if (this.grid.getBoxes()[i][0].state != CellState.EMPTY &&
                    this.grid.getBoxes()[i][0].state == this.grid.getBoxes()[i][1].state &&
                    this.grid.getBoxes()[i][1].state == this.grid.getBoxes()[i][2].state) {
                this.status = this.grid.getBoxes()[i][0].state == CellState.CROSS ? GameStatus.CORSS_WIN : GameStatus.ZERO_WIN;
                return true;
            }
            if (this.grid.getBoxes()[0][i].state != CellState.EMPTY &&
                    this.grid.getBoxes()[0][i].state == this.grid.getBoxes()[1][i].state &&
                    this.grid.getBoxes()[1][i].state == this.grid.getBoxes()[2][i].state) {
                this.status = this.grid.getBoxes()[0][i].state == CellState.CROSS ? GameStatus.CORSS_WIN : GameStatus.ZERO_WIN;
                return true;
            }
        }
        if (this.grid.getBoxes()[0][0].state != CellState.EMPTY &&
                this.grid.getBoxes()[0][0].state == this.grid.getBoxes()[1][1].state &&
                this.grid.getBoxes()[1][1].state == this.grid.getBoxes()[2][2].state) {
            this.status = this.grid.getBoxes()[0][0].state == CellState.CROSS ? GameStatus.CORSS_WIN : GameStatus.ZERO_WIN;
            return true;
        }
        if (this.grid.getBoxes()[0][2].state != CellState.EMPTY &&
                this.grid.getBoxes()[0][2].state == this.grid.getBoxes()[1][1].state &&
                this.grid.getBoxes()[1][1].state == this.grid.getBoxes()[2][0].state) {
            this.status = this.grid.getBoxes()[0][2].state == CellState.CROSS ? GameStatus.CORSS_WIN : GameStatus.ZERO_WIN;
            return true;
        }
        return true;
    }

    public void print(){
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                System.out.print(grid.boxes[i][j].state.pritable + " | ");
            }
            System.out.println();
            System.out.println("___________");
        }
    }
}