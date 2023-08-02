import java.security.PublicKey;
import java.util.List;
import java.util.Stack;

public class Chess {
    Player player1;
    Player player2;
    Player currentPlayer;
    GameStatus status;
    Board board;

    private void initialize(Player p1, Player p2)
    {
        player1 = p1;
        player2 = p2;
        status = GameStatus.ACTIVE;
        board = new Board();

        if (p1.isWhite()) {
            this.currentPlayer = p1;
        }
        else {
            this.currentPlayer = p2;
        }
    }
    public boolean isEnd()
    {
        return this.getStatus() != GameStatus.ACTIVE;
    }

    public GameStatus getStatus()
    {
        return this.status;
    }

    public void setStatus(GameStatus status)
    {
        this.status = status;
    }

    public boolean playerMove(Player player, int startX,
                              int startY, int endX, int endY) throws Exception {
        Cell startBox = board.getCell(startX, startY);
        Cell endBox = board.getCell(startY, endY);

        if(currentPlayer == player && startBox.getPiece().isValidMove(startBox,endBox)){
            if(endBox.getPiece()!=null) {
                endBox.getPiece().isKilled = true;
            }
            if (endBox.getPiece() != null && endBox.getPiece() instanceof King) {
                if (player.isWhite()) {
                    this.setStatus(GameStatus.WHITE_WIN);
                }
                else {
                    this.setStatus(GameStatus.BLACK_WIN);
                }
            }
            endBox.setPiece(startBox.getPiece());
            startBox.setPiece(null);
            if (this.currentPlayer == player1) {
                this.currentPlayer = player2;
            }
            else {
                this.currentPlayer = player1;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean isCheck(){
        return false;
    }


    public enum GameStatus{
        ACTIVE,
        BLACK_WIN,
        WHITE_WIN,
        FORFEIT,
        STALEMATE,
        RESIGNATION
    }

    public class Player{
        String name;
        Boolean isWhite;
        boolean humanPlayer;

        public boolean isWhite() {
            return isWhite;
        }
    }

    public class Board {
        Cell[][] layout;
        public Cell getCell(int x, int y) throws Exception {
            if (x < 0 || x > 7 || y < 0 || y > 7) {
                throw new Exception("Index out of bound");
            }
            return layout[x][y];
        }
        public Board() {
            layout = new Cell[8][8];
            layout[0][0] = new Cell(0, 0, new Rook(true));
            layout[0][1] = new Cell(0, 1, new Knight(true));
            layout[0][2] = new Cell(0, 2, new Bishop(true));
            //...
            layout[1][0] = new Cell(1, 0, new Pawn(true));
            layout[1][1] = new Cell(1, 1, new Pawn(true));
            //...

            // initialize black pieces
            layout[7][0] = new Cell(7, 0, new Rook(false));
            layout[7][1] = new Cell(7, 1, new Knight(false));
            layout[7][2] = new Cell(7, 2, new Bishop(false));
            //...
            layout[6][0] = new Cell(6, 0, new Pawn(false));
            layout[6][1] = new Cell(6, 1, new Pawn(false));
            //...

            // initialize remaining boxes without any piece
            for (int i = 2; i < 6; i++) {
                for (int j = 0; j < 8; j++) {
                    layout[i][j] = new Cell(i, j, null);
                }
            }
        }
    }

    public class Cell{
        int x;
        int y;
        Piece piece;

        public Piece getPiece() {
            return piece;
        }

        public void setPiece(Piece piece) {
            this.piece = piece;
        }

        public Cell(int x, int y, Piece piece){
            this.x = x;
            this.y = y;
            this.piece = piece;
        }
    }

    public abstract class Piece {
        Boolean isWhite;
        Boolean isKilled;
        List<MovementBehaviour> movementBehaviour;
        public Piece(List<MovementBehaviour> movementBehaviour, Boolean isWhite, Boolean isKilled) {
            this.movementBehaviour = movementBehaviour;
            this.isKilled = isKilled;
            this.isWhite = isWhite;
        }

        public boolean isValidMove(Cell src, Cell dest) {
            return movementBehaviour.stream().anyMatch(m -> m.isValid(src, dest));
        }
    }

    public abstract class MovementBehaviour {
        public abstract boolean isValid(Cell src, Cell dest);
    }

    public class StraightMovement extends MovementBehaviour{

        @Override
        public boolean isValid(Cell src, Cell dest) {
            return false;
        }
    }

    public class DiagonalMovement extends MovementBehaviour{

        @Override
        public boolean isValid(Cell src, Cell dest) {
            return false;
        }
    }

    public class OneStepStraightMovement extends MovementBehaviour{

        @Override
        public boolean isValid(Cell src, Cell dest) {
            return false;
        }
    }

    public class OneStepDiagonalMovement extends MovementBehaviour{

        @Override
        public boolean isValid(Cell src, Cell dest) {
            return false;
        }
    }

    public class Castling extends MovementBehaviour{

        @Override
        public boolean isValid(Cell src, Cell dest) {
            return false;
        }
    }

    public class PawnFirstStep extends MovementBehaviour{

        @Override
        public boolean isValid(Cell src, Cell dest) {
            return false;
        }
    }

    public class DhaiMovement extends MovementBehaviour{

        @Override
        public boolean isValid(Cell src, Cell dest) {
            return false;
        }
    }



    public class King extends Piece {

        boolean castlingDone = false;

        public King(Boolean isWhite) {
            super(List.of(new Castling(),new OneStepStraightMovement(), new OneStepDiagonalMovement()), isWhite, false);
        }
    }

    public class Queen extends Piece{

        public Queen(Boolean isWhite) {
            super(List.of(new StraightMovement(), new DiagonalMovement()), isWhite, false);
        }
    }
    public class Rook extends Piece{

        public Rook(Boolean isWhite) {
            super(List.of(new StraightMovement()), isWhite, false);
        }
    }
    public class Bishop extends Piece{

        public Bishop(Boolean isWhite) {
            super(List.of(new DiagonalMovement()), isWhite, false);
        }
    }
    public class Pawn extends Piece{

        public Pawn(Boolean isWhite) {
            super(List.of(new OneStepStraightMovement(), new PawnFirstStep()), isWhite, false);
        }
    }
    public class Knight extends Piece{

        public Knight(Boolean isWhite) {
            super(List.of(new DhaiMovement()), isWhite, false);
        }
    }
}
