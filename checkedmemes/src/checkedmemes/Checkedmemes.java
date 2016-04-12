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

static int row, column, RowChange, ColumnChange;
    
static Scanner stdin = new Scanner(System.in);
    private static final int BOARD_SIZE  =  8;
    private static final int SQUARE_SIZE = 50;
    private static final int NUM_PIECES  = 12;

    @Override
public void start(Stage primaryStage){
    GridPane checkerBoard = new GridPane();
    configureBoardLayout(checkerBoard);
    addSquaresToBoard(checkerBoard);

    Circle[] redPieces   = new Circle[NUM_PIECES];
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
        checkerBoard.add(redPieces[i],   i % (BOARD_SIZE / 2) * 2 + 
            (2 * i / BOARD_SIZE) % 2, 
            BOARD_SIZE - 1 - (i * 2) / BOARD_SIZE);

        blackPieces[i] = new Circle(SQUARE_SIZE / 2 - 4, Color.BLACK);
        blackPieces[i].setStroke(Color.RED);
        checkerBoard.add(blackPieces[i], i % (BOARD_SIZE / 2) * 2 + 
            (1 + 2 * i / BOARD_SIZE) % 2,
            (i * 2) / BOARD_SIZE);
    }
}

private void configureBoardLayout(GridPane board) {
    for (int i = 0; i < BOARD_SIZE; i++) {
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
  {49000000,  9000000,  9200001,  9000000,  9200001, 9000000,  9200001,  9000000,  9200001, 49000000},  //Row 1
  {50000000,  9000001,  9000000,  9000001,  9000000, 9000001,  9000000,  9000001,  9000000, 50000000},  //Row 2
  {51000000,  9000000,  9000001,  9000000,  9000011, 9000000,  9000011,  9000000,  9000001, 51000000},  //Row 3
  {52000000,  9000001,  9000000,  9000001,  9000000, 9000001,  9000000,  9000001,  9000000, 52000000},  //Row 4
  {53000000,  9000000,  9000001,  9000000,  9000011, 9000000,  9000001,  9000000,  9000001, 53000000},  //Row 5
  {54000000,  9000001,  9000000,  9000021,  9000000, 9000001,  9000000,  9000001,  9000000, 54000000},  //Row 6
  {55000000,  9000000,  9000001,  9000000,  9000001, 9000000,  9000001,  9000000,  9000001, 55000000},  //Row 7
  {56000000,  9100001,  9000000,  9100001,  9000000, 9100001,  9000000,  9100001,  9000000, 56000000},  //Row 8
  {48000000, 49000000, 50000000, 51000000, 52000000, 53000000, 54000000, 55000000, 56000000, 48000000}};
    
    int[][] JumpValue;
    
    boolean RunGame, FirstPlayerTurn, CheckingDoubleJump;
    
    RunGame = true;
    FirstPlayerTurn = true;
    
    while(RunGame){
        PrintOut(cb);
        JumpValue = CheckPreJump(cb, FirstPlayerTurn); 
        CheckingDoubleJump = true;
        cb = StartMovingPiece(cb, FirstPlayerTurn, JumpValue);
        if(cb[0][0] != 999) PrintOut(cb);
        while(CheckingDoubleJump){
            if(cb[0][0] != 999){
                CheckingDoubleJump = CheckDoubleJump(cb, FirstPlayerTurn, JumpValue);
            }else if(cb[0][0] == 999){
                CheckingDoubleJump = false;
            }
            if(CheckingDoubleJump){
                cb = MovePiece(cb, FirstPlayerTurn);
            }
        }
        
        if(cb[0][0] == 999){
            RunGame = false;
        }else{
            FirstPlayerTurn = !FirstPlayerTurn;
        }    
    }
    if(RunGame == false){
        System.exit(0);
    }
}

public static boolean CheckDoubleJump(int[][] cb, boolean FirstPlayerTurn, 
        int[][] JumpValue){
    int playerValue;
    int playerRowChange;
    int[] JumpingPiece;
    JumpingPiece = new int[2];
    boolean IsValidMove;
    boolean DoubleJumpPresent;
    
    if(FirstPlayerTurn){
        playerRowChange = -2;
    }else{
        playerRowChange = 2;
    }
    
    if(RowChange == (row + playerRowChange) && 
      (ColumnChange == (column + 2) || ColumnChange == (column - 2))){
        row = RowChange;
        column = ColumnChange;
        
        JumpValue = CheckPreJump(cb, FirstPlayerTurn);
        for(int i = 0; i < 4; i++){
            System.out.println(JumpValue[i][0] + " " + JumpValue[i][1] + " " + JumpValue[i][2]);
        }
        if(FirstPlayerTurn){
            playerValue = 1;
        }else{
            playerValue = 2;
        }
        if(JumpValue[0][0] == playerValue ||
           JumpValue[1][0] == playerValue ||
           JumpValue[2][0] == playerValue ||
           JumpValue[3][0] == playerValue){
            if(JumpValue[0][1] == row && JumpValue[0][2] == column){
                DoubleJumpPresent = true;
                JumpingPiece[0] = JumpValue[0][1];
                JumpingPiece[1] = JumpValue[0][2];
            }else if(JumpValue[1][1] == row && JumpValue[1][2] == column){
                DoubleJumpPresent = true;
                JumpingPiece[0] = JumpValue[1][1];
                JumpingPiece[1] = JumpValue[1][2];
            }else if(JumpValue[2][1] == row && JumpValue[2][2] == column){
                DoubleJumpPresent = true;
                JumpingPiece[0] = JumpValue[2][1];
                JumpingPiece[1] = JumpValue[2][2];
            }else if(JumpValue[3][1] == row && JumpValue[3][2] == column){
                DoubleJumpPresent = true;
                JumpingPiece[0] = JumpValue[3][1];
                JumpingPiece[1] = JumpValue[3][2];
            }else{
                DoubleJumpPresent = false;
            }
            if(DoubleJumpPresent){
                System.out.println("The piece you moved, " + JumpingPiece[0] +
                        " " + JumpingPiece[1] + " has to make another jump");
                System.out.println("Select row and column to move piece to");
                RowChange = stdin.nextInt();
                ColumnChange = stdin.nextInt();
                IsValidMove = CheckMoveValid(cb, FirstPlayerTurn);
                while(IsValidMove == false){
                    System.out.println("Invalid Move try again");
                    IsValidMove = CheckMoveValid(cb, FirstPlayerTurn);
                }
                return true;
            }
        }
    }
    return false;
}

public static int[][] CheckPreJump(int[][] cb, boolean FirstPlayerTurn){
    int[][] JumpValue;
    JumpValue = new int[4][3];
    
    if(FirstPlayerTurn == true){
        for(int x = 1, y = 1, i = 0; x < 9; y++){
            if(y == 9){
                y = 1;
            }
            if((cb[    x][    y] % 1000000) % 100000 / 10 == 2){
                if(((((cb[x - 1][y + 1] % 1000000) % 100000 / 10 == 1)&&
                      (cb[x - 2][y + 2] % 1000000) % 100000 / 10 == 0)&&
                      (cb[x - 2][y + 2] % 1000000) % 100000 % 10 == 1)|| 
                   ((((cb[x - 1][y - 1] % 1000000) % 100000 / 10 == 1)&&
                      (cb[x - 2][y - 2] % 1000000) % 100000 / 10 == 0)&&
                      (cb[x - 2][y - 2] % 1000000) % 100000 % 10 == 1)){
                    JumpValue[i][0] = 1;
                    JumpValue[i][1] = x;
                    JumpValue[i][2] = y;
                    i++;
                }
            }
            if(y == 8){
                x = x + 1;
            }
        }
    }
    if(FirstPlayerTurn == false){
        for(int x = 1, y = 1, i = 0; x < 9; y++){
            if(y == 9){
                y = 1;
            }
            if((cb[    x][    y] % 1000000) % 100000 / 10 == 1){
                if(((((cb[x + 1][y + 1] % 1000000) % 100000 / 10 == 2)&&
                      (cb[x + 2][y + 2] % 1000000) % 100000 / 10 == 0)&&
                      (cb[x + 2][y + 2] % 1000000) % 100000 % 10 == 1)|| 
                   ((((cb[x + 1][y - 1] % 1000000) % 100000 / 10 == 2)&&
                      (cb[x + 2][y - 2] % 1000000) % 100000 / 10 == 0)&&
                      (cb[x + 2][y - 2] % 1000000) % 100000 % 10 == 1)){
                    JumpValue[i][0] = 2;
                    JumpValue[i][1] = x;
                    JumpValue[i][2] = y;
                    i++;
                }
            }
            if(y == 8){
                x = x + 1;
            }
        }
    }
    return JumpValue;
}

public static boolean CheckMoveValid(int[][] cb, boolean FirstPlayerTurn){
    boolean MustJumpLeft, MustJumpRight;
    int PieceColor, LeftSecondPieceColor, RightSecondPieceColor, RowDifference;
    
    //first checks if requested piece is actually a piece
    if(cb[row][column] % 1000000 == 0){
        return false;
    }
    //checks if requested piece belongs to player
    if((((((cb[row][column] % 1000000) - 1) % 100 / 10) == 2) 
            && FirstPlayerTurn == false) ||
       (((((cb[row][column] % 1000000) - 1) % 100 / 10) == 1) 
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
    PieceColor                = (((cb[row]    [column]     % 1000000) % 100000 % 100 - 1)
            / 10);
    if(FirstPlayerTurn){
        LeftSecondPieceColor  = (((cb[row - 1][column - 1] % 1000000) % 100000 % 100 - 1)
            / 10);
        RightSecondPieceColor = (((cb[row - 1][column + 1] % 1000000) % 100000 % 100 - 1)
            / 10);
    }else{
        LeftSecondPieceColor  = (((cb[row + 1][column - 1] % 1000000) % 100000 % 100 - 1)
            / 10);
        RightSecondPieceColor = (((cb[row + 1][column + 1] % 1000000) % 100000 % 100 - 1)
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
            if(!(RowChange == row + RowDifference && 
                    ColumnChange == column + 2)){
                if(cb[row + RowDifference][column + 2] % 1000000 == 1){
                    System.out.println("Must jump right");
                    return false;
                }
            }
        }else if(MustJumpLeft && !MustJumpRight){
            if(!(RowChange == row + RowDifference && 
                    ColumnChange == column - 2)){
                if(cb[row + RowDifference][column - 2] % 1000000 == 1){
                    System.out.println("Must jump left");
                    return false;
                }
            }
        }else if(MustJumpRight && MustJumpLeft){
            if(!(((RowChange    == row + RowDifference && ColumnChange == column + 2)
                && cb[row + RowDifference][column + 2] % 1000000 == 1) || 
                  (RowChange    == row + RowDifference && ColumnChange == column - 2) &&
                   cb[row + RowDifference][column - 2] % 1000000 == 1)){
                System.out.println("Must make jump");
                return false;
            }
        }
    }
    //checks to see if player is trying to move onto full space
    if((int)cb[RowChange][ColumnChange] % 100 / 10 != 0){
        System.out.println("Can't move on a full space");
        return false;
    }
    //move is valid
    return true;
}

public static int[][] MovePiece(int[][] cb, boolean FirstPlayerTurn){
    if(cb[RowChange][ColumnChange] % 1000000 / 100000 == 2){
        cb[row][column] = cb[row][column] + 100;
    }
    
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
    boolean IsValidMove, MovingPiece;
    
    if(FirstPlayerTurn){
        System.out.println("Player one make your move");
    }else{
        System.out.println("Player two make your move");
    }
    MovingPiece = true;

    while(MovingPiece){
        System.out.println("Select row and column of piece you want to move."
                + " row first then column. Or type 999 to exit");
        row = stdin.nextInt();
    
        if(row == 999){
            cb[0][0] = 999;
            MovingPiece = false;
        }else{
            column = stdin.nextInt();

            if((JumpValue[0][0] == 1 &&  FirstPlayerTurn) || 
               (JumpValue[0][0] == 2 && !FirstPlayerTurn)) {
                while((row != JumpValue[0][1] || column != JumpValue[0][2]) &&
                      (row != JumpValue[1][1] || column != JumpValue[1][2]) &&
                      (row != JumpValue[2][1] || column != JumpValue[2][2]) &&
                      (row != JumpValue[3][1] || column != JumpValue[3][2])){
                    System.out.println("There is a jump to be made at ");
                    for(int i = 0; i < 4; i++){
                        if(i > 0 && JumpValue[i][0] != 0){
                            System.out.println("OR AT");
                        }
                        if(JumpValue[i][0] != 0){
                            System.out.print(JumpValue[i][1]);
                            System.out.println(" " + JumpValue[i][2]);
                        }
                    }
                    System.out.println("Select row and column of a piece that has"
                                  + "to make a jump");
                    row = stdin.nextInt();
                    column = stdin.nextInt();
                }
            }
       
            System.out.println("Select row and column of space you want to move "
                    + "piece to. row first then column.");
            RowChange = stdin.nextInt();
            ColumnChange = stdin.nextInt();
                 IsValidMove = CheckMoveValid(cb, FirstPlayerTurn);
        
            if(IsValidMove){
                cb = MovePiece(cb, FirstPlayerTurn);
                MovingPiece = false;
            }else{
                System.out.println("Invalid move try again");
                MovingPiece = true;
            }
        }
    }
    return cb;
}
public static void PrintOut(int[][] cb){
    
    boolean PrintBoard = true;
    int a, b;
    int val1, val2, val3, val4;
    char d, val5;
    val1 = 0;
    val2 = 0;
    val3 = 0;
    val4 = 0;
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
                val3 =  cb[a][b] / 1000000;
                val4 =  cb[a][b] % 1000 / 100;
            }else{
                //a = 0;
                b = 0;
                System.out.println(" ");
            }
            b++;
        }
        if(a < 10 && b < 11){
            if(val2 == 0 && val3 == 9){
                if(val1 == 0){
                    val5 = 'X';
                }else{
                    val5 = '_';
                }
            }else{
                val5 = (char)val3;
                }
            if(val2 == 1){
                if(val4 == 0){
                    val5 = 'r';
                }else{
                    val5 = 'R';
                }
            }    
            if(val2 == 2){
                if(val4 == 0){
                    val5 = 'b';
                }else{
                    val5 = 'B';
                }
            }
            d = val5;
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
    System.out.println(cb[1][4]);
}
}
