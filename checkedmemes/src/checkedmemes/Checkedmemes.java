package checkedmemes;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.Scanner;
import javafx.scene.shape.*;

public class Checkedmemes extends Application {

static Scanner stdin = new Scanner(System.in);
    private static final int BOARD_SIZE  =  8 ;
    private static final int SQUARE_SIZE = 50 ;
    private static final int NUM_PIECES  = 12 ;

    @Override
public void start(Stage primaryStage){
    GridPane checkerBoard = new GridPane();
    configureBoardLayout(checkerBoard);
    addSquaresToBoard(checkerBoard);

    Circle[] redPieces = new Circle[NUM_PIECES];
    Circle[] blackPieces = new Circle[NUM_PIECES];
    addPiecesToBoard(checkerBoard, redPieces, blackPieces);
        
    BorderPane root = new BorderPane(checkerBoard);
    primaryStage.setScene(new Scene(root, 400, 400));
    primaryStage.show();
}    
private void addSquaresToBoard(GridPane board) {
    Color[] squareColors = new Color[] {Color.RED, Color.BLACK};
    for (int row = 0; row < BOARD_SIZE; row++) {
        for (int col = 0; col < BOARD_SIZE; col++) {
            board.add(new Rectangle(SQUARE_SIZE, SQUARE_SIZE, 
                squareColors[(row + col) % 2]), col, row);
        }
    }
}
    
private void addPiecesToBoard(GridPane checkerBoard, Circle[] redPieces,
        Circle[] blackPieces) {
    for (int i=0; i < NUM_PIECES; i++) {
        redPieces[i] = new Circle(SQUARE_SIZE / 2 - 4, Color.RED);
        redPieces[i].setStroke(Color.BLACK);
        checkerBoard.add(redPieces[i],   i % (BOARD_SIZE/2) * 2 + 
            (2 * i / BOARD_SIZE) % 2, 
            BOARD_SIZE - 1 - (i * 2) / BOARD_SIZE);

        blackPieces[i] = new Circle(SQUARE_SIZE/2 - 4, Color.BLACK);
        blackPieces[i].setStroke(Color.RED);
        checkerBoard.add(blackPieces[i], i % (BOARD_SIZE/2) * 2 + 
            (1 + 2 * i / BOARD_SIZE) % 2,
            (i * 2) / BOARD_SIZE);
    }
}

private void configureBoardLayout(GridPane board) {
    for (int i=0; i<BOARD_SIZE; i++) {
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setMinHeight (SQUARE_SIZE);
        rowConstraints.setPrefHeight(SQUARE_SIZE);
        rowConstraints.setMaxHeight (SQUARE_SIZE);
        rowConstraints.setValignment(VPos.CENTER);
        board.getRowConstraints().add(rowConstraints);

        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setMinWidth  (SQUARE_SIZE);
        colConstraints.setMaxWidth  (SQUARE_SIZE);
        colConstraints.setPrefWidth (SQUARE_SIZE);
        colConstraints.setHalignment(HPos.CENTER);
        board.getColumnConstraints().add(colConstraints);
    }
}


public static void main(String[] args) {
    //Creates an array that sets up the spaces that make up the board
    int[][] cb = {{48000000, 49000000, 50000000, 51000000, 52000000, 53000000, 54000000, 55000000, 56000000, 48000000},
                  {49000000,  9000000,  9200011,  9000000,  9200011,  9000000,  9200011,  9000000,  9200011, 56000000},
                  {50000000,  9000011,  9000000,  9000011,  9000000,  9000011,  9000000,  9000011,  9000000, 55000000},
                  {51000000,  9000000,  9000011,  9000000,  9000011,  9000000,  9000011,  9000000,  9000011, 54000000},
                  {52000000,  9000001,  9000000,  9000001,  9000000,  9000001,  9000000,  9000001,  9000000, 53000000},
                  {53000000,  9000000,  9000001,  9000000,  9000001,  9000000,  9000001,  9000000,  9000001, 52000000},
                  {54000000,  9000021,  9000000,  9000021,  9000000,  9000021,  9000000,  9000021,  9000000, 51000000},
                  {55000000,  9000000,  9000021,  9000000,  9000021,  9000000,  9000021,  9000000,  9000021, 50000000},
                  {56000000,  9100021,  9000000,  9100021,  9000000,  9100021,  9000000,  9100021,  9000000, 49000000},
                  {48000000, 49000000, 50000000, 51000000, 52000000, 53000000, 54000000, 55000000, 56000000, 48000000}};
    
    int[][] JumpValue;
    
    boolean RunGame, FirstPlayerTurn;
    RunGame = true;
    FirstPlayerTurn = true;
    
    while(RunGame){
        JumpValue = CheckForPreJump(cb);        
        
        PrintOut(cb);
        
        cb = StartMovingPiece(cb, FirstPlayerTurn, JumpValue);
        FirstPlayerTurn = !FirstPlayerTurn;
                
    }

    if(RunGame == false){
        System.exit(0);
    }
}

public static int[][] CheckForPreJump(int[][] cb){
    int[][] JumpValue = {{0,0,0} , {0,0,0} , {0,0,0}};
    for(int x = 1, y = 1, i = 0; x < 9; y++){
        if(y == 9){
            y = 1;
        }
        
        switch (((cb[x][y] - 1) % 1000000) / 10) {
            case 1:
                System.out.println("1");
                if((cb[x - 1][y - 1] % 1000000) - 1 / 10 == 2 ||
                   (cb[x - 1][y + 1] % 1000000) - 1 / 10 == 2){
                    JumpValue[i][0] = 1;
                    JumpValue[i][1] = x;
                    JumpValue[i][2] = y;
                    i++;
                }
                break;
            case 2:
                System.out.println("2");
                if((cb[x + 1][y - 1] % 1000000) - 1 / 10 == 1 ||
                   (cb[x + 1][y + 1] % 1000000) - 1 / 10 == 1){
                    JumpValue[i][0] = 2;
                    JumpValue[i][1] = x;
                    JumpValue[i][2] = y;
                    i++;
                }
                break;
            default:
                System.out.println("0");
                break;
        }
        
        if(y == 8){
            x = x + 1;
        }
    }
    return JumpValue;
}

public static boolean CheckMoveValid(int[][] cb, int row, int column,
        int RowChange, int ColumnChange, boolean FirstPlayerTurn){
    boolean MustJumpLeft, MustJumpRight;
    int PieceColor, LeftSecondPieceColor, RightSecondPieceColor, RowDifference;
    
    //first checks if requested piece is actually a piece
    if(cb[row][column] % 1000000 == 0){
        return false;
    }
    
    //checks if requested piece belongs to player
    if((((((cb[row][column] % 1000000) - 1) / 10) == 2) 
            && FirstPlayerTurn == false) ||
       (((((cb[row][column] % 1000000) - 1) / 10) == 1) 
            && FirstPlayerTurn == true )) {
        System.out.println("NOT YOUR PIECE");
        return false;
    }
    
    //checks to make sure piece isnt moving backwards
    if(FirstPlayerTurn){
        if(RowChange >= row){
            System.out.println("Can't move backwards");
            return false;
        }
    }else{
        if(RowChange <= row){
            System.out.println("Can't move backwards");
            return false;
        }
    }
    
    //checks piece color and color of any piece that may be directly 
    //ahead of piece
    PieceColor                = (((cb[row][column]         % 1000000) - 1)
            / 10);
    if(FirstPlayerTurn){
        LeftSecondPieceColor  = (((cb[row - 1][column - 1] % 1000000) - 1)
            / 10);
        RightSecondPieceColor = (((cb[row - 1][column + 1] % 1000000) - 1)
            / 10);
    }else{
        LeftSecondPieceColor  = (((cb[row + 1][column - 1] % 1000000) - 1)
            / 10);
        RightSecondPieceColor = (((cb[row + 1][column + 1] % 1000000) - 1)
            / 10);
    }
        
    //checks if there is a piece ahead that must be jumped
    MustJumpRight = (RightSecondPieceColor != PieceColor 
        && RightSecondPieceColor != 0);
    MustJumpLeft  = (LeftSecondPieceColor  != PieceColor 
        && LeftSecondPieceColor  != 0);
    
    if(FirstPlayerTurn){
        RowDifference = -2;
    }else{
        RowDifference =  2;
    }
    
    if((MustJumpRight == false) && (MustJumpLeft == false)){
        if(ColumnChange == column || Math.abs(ColumnChange - column) > 1){
            System.out.println("Can't move there");
            return false;
        }
    }else{
        if(MustJumpRight && !MustJumpLeft){
            if(!(RowChange == row + RowDifference && ColumnChange == column + 2)){
                if(cb[row + RowDifference][column + 2] % 1000000 == 1){
                    System.out.println("Must jump right");
                    return false;
                }
            }
        }else if(MustJumpLeft && !MustJumpRight){
            if(!(RowChange == row + RowDifference && ColumnChange == column - 2)){
                if(cb[row + RowDifference][column - 2] % 1000000 == 1){
                    System.out.println("Must jump left");
                    return false;
                }
            }
        }else if(MustJumpRight && MustJumpLeft){
            if(!(((RowChange == row + RowDifference && ColumnChange == column + 2) &&
                   cb[row + RowDifference][column + 2] % 1000000 == 1            ) || 
                  (RowChange == row + RowDifference && ColumnChange == column - 2) &&
                   cb[row + RowDifference][column - 2] % 1000000 == 1)){
                System.out.println("Must make jump");
                return false;
            }
        }
    }
    
    //checks to see if player is trying to move onto full space
    if(cb[RowChange][ColumnChange] != 9000001){
        System.out.println("Can't move on a full space");
        return false;
    }

    //move is valid
    return true;
}

public static int[][] MovePiece(int[][] cb, int row, int column, int RowChange,
        int ColumnChange, boolean FirstPlayerTurn){
    
    cb[RowChange][ColumnChange] = cb[row][column];
    cb[row][column] = 9000001;
    if((int)Math.abs(RowChange - row) == 2){
        if(FirstPlayerTurn){
            if(ColumnChange - column == 2){
                cb[row - 1][column + 1] = 9000001;
            }else{
                cb[row - 1][column - 1] = 9000001;
            }
        }else{
            if(ColumnChange - column == 2){
                cb[row + 1][column + 1] = 9000001;
            }else{
                cb[row + 1][column - 1] = 9000001;
            }
        }
    }
    return cb;
}

public static int[][] StartMovingPiece(int[][] cb, boolean FirstPlayerTurn,
        int[][] JumpValue){
    int row, column, RowChange, ColumnChange;
    boolean IsValidMove, MovingPiece;
    
    if(JumpValue[0][0] == 0 ||
      (JumpValue[0][0] == 1 && !FirstPlayerTurn) ||
      (JumpValue[0][0] == 2 && FirstPlayerTurn )){
        if(FirstPlayerTurn){
            System.out.println("Player one make your move");
        }else{
            System.out.println("Player two make your move");
        }
    }else if(JumpValue[0][0] == 1 && FirstPlayerTurn){
        System.out.println("Player one make your move "
                + "\nYou must make a jump \n"
                         + "Peice at "  + JumpValue[0][1] + " " 
                + JumpValue[0][2]);
        if(JumpValue[1][0] == 1){
            System.out.println("Or at " + JumpValue[1][1] + " " 
                + JumpValue[1][2]);
        }
    }else if(JumpValue[0][0] == 2 && !FirstPlayerTurn){
        System.out.println("Player two make your move "
                + "\nYou must make a jump \n"
                         + "Peice at "  + JumpValue[0][1] + " " 
                + JumpValue[0][2]);
        if(JumpValue[1][0] == 2){
            System.out.println("Or at " + JumpValue[1][1] + " " 
                + JumpValue[1][2]);
        }
    }
    MovingPiece = true;
    
    while(MovingPiece){
        System.out.println("Select row and column of piece you want to move."
                + " row first then column.");
        row = stdin.nextInt();
        column = stdin.nextInt();
    
        System.out.println("Select row and column of space you want to move "
                + "piece to. row first then column.");
        RowChange = stdin.nextInt();
        ColumnChange = stdin.nextInt();
    
        IsValidMove = CheckMoveValid(cb, row, column, RowChange, 
                ColumnChange, FirstPlayerTurn);
        
        
        if(IsValidMove){
            cb = MovePiece(cb, row, column, RowChange, ColumnChange,
                    FirstPlayerTurn);
            MovingPiece = false;
        }else{
            System.out.println("Invalid move try again");
            MovingPiece = true;
        }
    }
    return cb;
}

public static void PrintOut(int[][] cb){
    
    boolean PrintBoard = true;
    int a, b, c, e;
    int val1, val2, val3;
    char d, val4;
    val1 = 0;
    val2 = 0;
    val3 = 0;
    val4 = 0;
    d    = 0;
    a    = 0;
    b    = 0;
    
    while(PrintBoard){
        if(b == 10){
            a = ++a;
            b = 0;
            System.out.println();
        }
        
        if(a <= 10){
            if(a < 10){
                val1 =  cb[a][b] % 10; //checking movable spaces
                val2 = (cb[a][b] % 100 - val1) / 10; //checking colors
                val3 = cb[a][b] / 1000000;
            }else{
                //a = 0;
                b = 0;
                System.out.println(" ");
            }
            b++;
        }
    
        if(a < 11 && b < 11){
            if(val2 == 0 && val3 == 9){
                if(val1 == 0){
                    val4 = 'X';
                }else{
                    val4 = '_';
                }
            }else{
                val4 = (char)val3;
                }
            if(val2 == 1){
                val4 = 'r';
            }    
            if(val2 == 2){
                val4 = 'b';
            }
            d = val4;
            System.out.print(d + " ");
        }
    
        if(b <= 10 && a < 10){
            PrintBoard = true;
        }else{
            PrintBoard = false;
            a = 0;
            b = 0;
        }
    }        
}
}
