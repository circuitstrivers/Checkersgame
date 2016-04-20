package checkedmemes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.Scanner;
import javafx.scene.shape.*;
import java.io.*;
import java.io.File;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Checkedmemes extends Application {

    static Scanner fileReader;
    static int row, column, RowChange, ColumnChange;

    EventHandler handler;
    
    static Scanner stdin = new Scanner(System.in);
    private static final int BOARD_SIZE  = 8;
    private static final int SQUARE_SIZE = 50;
    private static final int NUM_PIECES  = 12;

    public static void main(String[] args) throws Exception{
        Application.launch(args);
    }
    
    private void addSquaresToBoard(GridPane board) {

        Color[] squareColors = new Color[]{Color.RED, Color.BLACK};
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Rectangle rect = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, squareColors[(row + col) % 2]);

                board.add(rect, col, row);
                rect.setMouseTransparent(true);
            }
        }
    }

    /*private void addPiecesToBoard(GridPane board, Circle[] redPieces,
            Circle[] blackPieces) {
        for (int i=0; i<NUM_PIECES; i++) {
            redPieces[i] = new Circle(SQUARE_SIZE/2-4, Color.RED);
            redPieces[i].setStroke(Color.BLACK);
            board.add(redPieces[i], i%(BOARD_SIZE/2) * 2 + (2*i/BOARD_SIZE)%2, BOARD_SIZE - 1 - (i*2)/BOARD_SIZE);

            Circle c = new Circle(SQUARE_SIZE/2 -4, Color.BLACK);
            c.setMouseTransparent(true); //Mouse events not registered on the circle that overlays the rectangle. Event then gets handled at rectangle level
            blackPieces[i] = c;
            blackPieces[i].setStroke(Color.RED);
            checkerBoard.add(blackPieces[i], i%(BOARD_SIZE/2) * 2 + (1 + 2*i/BOARD_SIZE)%2, (i*2)/BOARD_SIZE);
        }
    }*/
    private void configureBoardLayout(GridPane board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(SQUARE_SIZE);
            rowConstraints.setPrefHeight(SQUARE_SIZE);
            rowConstraints.setMaxHeight(SQUARE_SIZE);
            rowConstraints.setValignment(VPos.CENTER);
            board.getRowConstraints().add(rowConstraints);

            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setMinWidth(SQUARE_SIZE);
            colConstraints.setMaxWidth(SQUARE_SIZE);
            colConstraints.setPrefWidth(SQUARE_SIZE);
            colConstraints.setHalignment(HPos.CENTER);
            board.getColumnConstraints().add(colConstraints);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String savename = "null";
        int saving, screenSelection;
        boolean RunGame, FirstPlayerTurn, CheckingDoubleJump, GameOver, loading;
        GameOver = false;
        RunGame = true;
        FirstPlayerTurn = true;
        
        Group board = new Group();
        GridPane checkerBoard = new GridPane();
        configureBoardLayout(checkerBoard);
        addSquaresToBoard(checkerBoard);

        board.getChildren().add(checkerBoard);

        Circle[] redPieces = new Circle[NUM_PIECES];
        Circle[] blackPieces = new Circle[NUM_PIECES];
        //addPiecesToBoard(checkerBoard, redPieces, blackPieces);

        BorderPane root = new BorderPane(board);
        Scene scene = new Scene(root, 400, 400);
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                row = (int) (event.getX() / 50 + 1);
                column = (int) (event.getY() / 50 + 1);
                System.out.println("Mouse x: " + row + " Mouse y: " + column);
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();

        String selectGame = JOptionPane.showInputDialog(null, "New Game: 1\nLoad Game: 2");
        screenSelection = Integer.parseInt(selectGame);
        while (screenSelection != 1 && screenSelection != 2) {
            selectGame = JOptionPane.showInputDialog(null, "Invalid selection. enter again");
            screenSelection = Integer.parseInt(selectGame);
        }

        loading = (screenSelection == 2);
        //if(loading == false){
        int[][] cb = {{48000000, 49000000, 50000000, 51000000, 52000000, 53000000, 54000000, 55000000, 56000000, 48000000},
        {49000000, 9000000, 9200001, 9000000, 9200001, 9000000, 9200001, 9000000, 9200001, 49000000}, //Row 1
        {50000000, 9000121, 9000000, 9000121, 9000000, 9000001, 9000000, 9000001, 9000000, 50000000}, //Row 2
        {51000000, 9000000, 9000001, 9000000, 9000011, 9000000, 9000011, 9000000, 9000001, 51000000}, //Row 3
        {52000000, 9000001, 9000000, 9000001, 9000000, 9000001, 9000000, 9000001, 9000000, 52000000}, //Row 4
        {53000000, 9000000, 9000001, 9000000, 9000011, 9000000, 9000001, 9000000, 9000001, 53000000}, //Row 5
        {54000000, 9000001, 9000000, 9000001, 9000000, 9000001, 9000000, 9000001, 9000000, 54000000}, //Row 6
        {55000000, 9000000, 9000021, 9000000, 9000001, 9000000, 9000001, 9000000, 9000001, 55000000}, //Row 7
        {56000000, 9100001, 9000000, 9100001, 9000000, 9100001, 9000000, 9100001, 9000000, 56000000}, //Row 8
        {48000000, 49000000, 50000000, 51000000, 52000000, 53000000, 54000000, 55000000, 56000000, 48000000}};
        /*}else{
        File = JOptionPane.showInputDialog(null, "Enter name of file you want to load");
        int[][]cb = ReadFile(File);
        FirstPlayerTurn = (cb[11][10] == 1);
    }*/
        
        int[][] JumpValue;

        while (RunGame) {
            PrintOut(cb);
            JumpValue = CheckPreJump(cb, FirstPlayerTurn);
            CheckingDoubleJump = true;
            cb = StartMovingPiece(cb, FirstPlayerTurn, JumpValue);
            if (cb[0][0] != 999) {
                PrintOut(cb);
            }
            if (cb[0][0] == 999) {
                GameOver = true;
            }
            while (CheckingDoubleJump && !GameOver) {
                GameOver = CheckForEndGame(cb, FirstPlayerTurn);
                if (!GameOver) {
                    if (cb[0][0] != 999) {
                        CheckingDoubleJump = CheckDoubleJump(cb, FirstPlayerTurn, JumpValue);
                    } else if (cb[0][0] == 999) {
                        CheckingDoubleJump = false;
                    }
                    if (CheckingDoubleJump) {
                        cb = MovePiece(cb, FirstPlayerTurn);
                    }
                }
            }
            FirstPlayerTurn = !FirstPlayerTurn;
            if (cb[0][0] == 999 || GameOver) {
                cb[0][0] = 48000000;
                RunGame = false;
            }
            WriteFile(cb, savename, FirstPlayerTurn);
        }
        if (RunGame == false) {
            String selectSave = JOptionPane.showInputDialog(null, "Would you like to save?\nyes: 1\nno: 0");
            saving = Integer.parseInt(selectSave);
            if (saving == 1) {
                savename = JOptionPane.showInputDialog(null, "Enter the name of your save.");
                WriteFile(cb, savename, FirstPlayerTurn);
            }
            JOptionPane.showMessageDialog(null, "Exiting Game");
            System.exit(0);
        }
    }

    public static boolean CheckDoubleJump(int[][] cb, boolean FirstPlayerTurn, int[][] JumpValue) {
        int playerValue;
        int playerRowChange;
        int[] JumpingPiece;
        JumpingPiece = new int[2];
        boolean IsValidMove, DoubleJumpPresent, isKing;

        isKing = (cb[RowChange][ColumnChange] % 1000 / 100 == 1);

        if (FirstPlayerTurn) {
            playerRowChange = -2;
        } else {
            playerRowChange = 2;
        }

        if (((RowChange == (row + playerRowChange) && !isKing) || isKing)
                && (ColumnChange == (column + 2) || ColumnChange == (column - 2))) {
            row = RowChange;
            column = ColumnChange;

            JumpValue = CheckPreJump(cb, FirstPlayerTurn);

            if (FirstPlayerTurn) {
                playerValue = 1;
            } else {
                playerValue = 2;
            }

            if (JumpValue[0][0] == playerValue
                    || JumpValue[1][0] == playerValue
                    || JumpValue[2][0] == playerValue
                    || JumpValue[3][0] == playerValue) {
                if (JumpValue[0][1] == row && JumpValue[0][2] == column) {
                    DoubleJumpPresent = true;
                    JumpingPiece[0] = JumpValue[0][1];
                    JumpingPiece[1] = JumpValue[0][2];
                } else if (JumpValue[1][1] == row && JumpValue[1][2] == column) {
                    DoubleJumpPresent = true;
                    JumpingPiece[0] = JumpValue[1][1];
                    JumpingPiece[1] = JumpValue[1][2];
                } else if (JumpValue[2][1] == row && JumpValue[2][2] == column) {
                    DoubleJumpPresent = true;
                    JumpingPiece[0] = JumpValue[2][1];
                    JumpingPiece[1] = JumpValue[2][2];
                } else if (JumpValue[3][1] == row && JumpValue[3][2] == column) {
                    DoubleJumpPresent = true;
                    JumpingPiece[0] = JumpValue[3][1];
                    JumpingPiece[1] = JumpValue[3][2];
                } else {
                    DoubleJumpPresent = false;
                }
                if (DoubleJumpPresent) {
//                    JOptionPane.showMessageDialog(null, "The piece you moved (" + JumpingPiece[0]
//                           + "," + JumpingPiece[1] + ") has to make another jump");
//                    System.out.println("Select row and column to move piece to");
                    RowChange = stdin.nextInt();
                    ColumnChange = stdin.nextInt();
                    IsValidMove = CheckMoveValid(cb, FirstPlayerTurn);
                    while (IsValidMove == false) {
                        JOptionPane.showMessageDialog(null, "Invalid Move try again", "Alert", JOptionPane.ERROR_MESSAGE);
                        RowChange = stdin.nextInt();
                        ColumnChange = stdin.nextInt();
                        IsValidMove = CheckMoveValid(cb, FirstPlayerTurn);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static int[][] CheckPreJump(int[][] cb, boolean FirstPlayerTurn) {
        int[][] JumpValue;
        boolean isKing;
        JumpValue = new int[4][3];

        if (FirstPlayerTurn) {
            for (int x = 1, y = 1, i = 0; x < 9; y++) {
                if (y == 9) {
                    y = 1;
                }
                isKing = (cb[x][y] % 1000 / 100 == 1);
                if (cb[x][y] % 100 / 10 == 2 && !isKing) {
                    if (((cb[x - 1][y + 1] % 100 / 10 == 1)
                            && (cb[x - 2][y + 2] % 100 / 10 == 0)
                            && (cb[x - 2][y + 2] % 10 == 1))
                            || ((cb[x - 1][y - 1] % 100 / 10 == 1)
                            && (cb[x - 2][y - 2] % 100 / 10 == 0)
                            && (cb[x - 2][y - 2] % 10 == 1))) {
                        JumpValue[i][0] = 1;
                        JumpValue[i][1] = x;
                        JumpValue[i][2] = y;
                        i++;
                    }
                } else if (cb[x][y] % 100 / 10 == 2 && isKing) {
                    if (((cb[x + 1][y + 1] % 100 / 10 == 1)
                            && (cb[x + 2][y + 2] % 100 / 10 == 0)
                            && (cb[x + 2][y + 2] % 10 == 1))
                            || ((cb[x + 1][y - 1] % 100 / 10 == 1)
                            && (cb[x + 2][y - 2] % 100 / 10 == 0)
                            && (cb[x + 2][y - 2] % 10 == 1))
                            || ((cb[x - 1][y + 1] % 100 / 10 == 1)
                            && (cb[x - 2][y + 2] % 100 / 10 == 0)
                            && (cb[x - 2][y + 2] % 10 == 1))
                            || ((cb[x - 1][y - 1] % 100 / 10 == 1)
                            && (cb[x - 2][y - 2] % 100 / 10 == 0)
                            && (cb[x - 2][y - 2] % 10 == 1))) {
                        JumpValue[i][0] = 1;
                        JumpValue[i][1] = x;
                        JumpValue[i][2] = y;
                        i++;
                    }
                }
                if (y == 8) {
                    x = x + 1;
                }
            }
        } else {
            for (int x = 1, y = 1, i = 0; x < 9; y++) {
                if (y == 9) {
                    y = 1;
                }
                isKing = (cb[x][y] % 1000 / 100 == 1);
                if (cb[x][y] % 100 / 10 == 1) {
                    if (((cb[x + 1][y + 1] % 100 / 10 == 2)
                            && (cb[x + 2][y + 2] % 100 / 10 == 0)
                            && (cb[x + 2][y + 2] % 10 == 1))
                            || ((cb[x + 1][y - 1] % 100 / 10 == 2)
                            && (cb[x + 2][y - 2] % 100 / 10 == 0)
                            && (cb[x + 2][y - 2] % 10 == 1))) {
                        JumpValue[i][0] = 2;
                        JumpValue[i][1] = x;
                        JumpValue[i][2] = y;
                        i++;
                    }
                } else if (cb[x][y] % 100 / 10 == 1 && isKing) {
                    if (((cb[x + 1][y + 1] % 100 / 10 == 1)
                            && (cb[x + 2][y + 2] % 100 / 10 == 0)
                            && (cb[x + 2][y + 2] % 10 == 1))
                            || ((cb[x + 1][y - 1] % 100 / 10 == 1)
                            && (cb[x + 2][y - 2] % 100 / 10 == 0)
                            && (cb[x + 2][y - 2] % 10 == 1))
                            || ((cb[x - 1][y + 1] % 100 / 10 == 1)
                            && (cb[x - 2][y + 2] % 100 / 10 == 0)
                            && (cb[x - 2][y + 2] % 10 == 1))
                            || ((cb[x - 1][y - 1] % 100 / 10 == 1)
                            && (cb[x - 2][y - 2] % 100 / 10 == 0)
                            && (cb[x - 2][y - 2] % 10 == 1))) {
                        JumpValue[i][0] = 2;
                        JumpValue[i][1] = x;
                        JumpValue[i][2] = y;
                        i++;
                    }
                }
                if (y == 8) {
                    x = x + 1;
                }
            }
        }
        return JumpValue;
    }

    public static boolean CheckMoveValid(int[][] cb, boolean FirstPlayerTurn) {
        boolean MustJumpLeft, MustJumpRight, mustJumpBackLeft, mustJumpBackRight, isKing;
        int PieceColor, LeftSecondPieceColor, RightSecondPieceColor, backRight, backLeft, RowDifference, jumpVal;
        mustJumpBackLeft = false;
        mustJumpBackRight = false;
        backRight = 0;
        backLeft = 0;
        jumpVal = 0;

        //first checks if requested piece is actually a piece
        if (cb[row][column] % 1000000 == 0) {
            return false;
        }
        //checks if requested piece belongs to player
        if ((((((cb[row][column] % 1000000) - 1) % 100 / 10) == 2)
                && FirstPlayerTurn == false)
                || (((((cb[row][column] % 1000000) - 1) % 100 / 10) == 1)
                && FirstPlayerTurn == true)) {
            JOptionPane.showMessageDialog(null, "NOT YOUR PIECE", "Alert", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        //checks to see f piece is a king
        isKing = (cb[row][column] % 1000 / 100 == 1);

        //checks to make sure piece isnt moving backwards
        if (FirstPlayerTurn) {
            if (RowChange >= row && isKing == false) {
                JOptionPane.showMessageDialog(null, "Can't move backwards", "Alert", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else if (RowChange <= row && isKing == false) {
            JOptionPane.showMessageDialog(null, "Can't move backwards", "Alert", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        //checks piece color and color of any piece that may be directly 
        //ahead of piece
        PieceColor = ((cb[row][column] % 100 - 1) / 10);
        if (FirstPlayerTurn) {
            LeftSecondPieceColor = ((cb[row - 1][column - 1] % 100 - 1) / 10);
            RightSecondPieceColor = ((cb[row - 1][column + 1] % 100 - 1) / 10);
        } else {
            LeftSecondPieceColor = ((cb[row + 1][column - 1] % 100 - 1) / 10);
            RightSecondPieceColor = ((cb[row + 1][column + 1] % 100 - 1) / 10);
        }
        if (isKing) {
            if (FirstPlayerTurn) {
                backLeft = ((cb[row + 1][column - 1] % 100 - 1) / 10);
                backRight = ((cb[row + 1][column + 1] % 100 - 1) / 10);
            } else {
                backLeft = ((cb[row - 1][column - 1] % 100 - 1) / 10);
                backRight = ((cb[row - 1][column + 1] % 100 - 1) / 10);
            }
        }

        //checks if there is a piece ahead that must be jumped
        MustJumpRight = (RightSecondPieceColor != PieceColor
                && RightSecondPieceColor != 0);
        MustJumpLeft = (LeftSecondPieceColor != PieceColor
                && LeftSecondPieceColor != 0);
        if (isKing) {
            mustJumpBackRight = (backRight != PieceColor && backRight != 0);
            mustJumpBackLeft = (backLeft != PieceColor && backLeft != 0);
        }
        if (MustJumpRight) {
            jumpVal = jumpVal + 1;
        }
        if (MustJumpLeft) {
            jumpVal = jumpVal + 1;
        }
        if (mustJumpBackRight) {
            jumpVal = jumpVal + 1;
        }
        if (mustJumpBackLeft) {
            jumpVal = jumpVal + 1;
        }

        if (FirstPlayerTurn) {
            RowDifference = -2;
        } else {
            RowDifference = 2;
        }
        if (!MustJumpRight && !MustJumpLeft && !mustJumpBackRight && !mustJumpBackLeft) {
            if (ColumnChange == column || Math.abs(ColumnChange - column) > 1) {
                JOptionPane.showMessageDialog(null, "Can't move there", "Alert", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else if (MustJumpRight && !MustJumpLeft && !mustJumpBackRight && !mustJumpBackLeft) {
            if (!(RowChange == row + RowDifference
                    && ColumnChange == column + 2)) {
                if (cb[row + RowDifference][column + 2] % 1000000 == 1) {
                    JOptionPane.showMessageDialog(null, "Must jump right", "Alert", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        } else if (MustJumpLeft && !MustJumpRight && !mustJumpBackRight && !mustJumpBackLeft) {
            if (!(RowChange == row + RowDifference
                    && ColumnChange == column - 2)) {
                if (cb[row + RowDifference][column - 2] % 1000000 == 1) {
                    JOptionPane.showMessageDialog(null, "Must jump left", "Alert", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        } else if (mustJumpBackRight && !mustJumpBackLeft && !MustJumpLeft && !MustJumpRight) {
            if (!(RowChange == row - RowDifference
                    && ColumnChange == column + 2)) {
                if (cb[row - RowDifference][column + 2] % 1000000 == 1) {
                    JOptionPane.showMessageDialog(null, "Must jump back right", "Alert", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        } else if (mustJumpBackLeft && !mustJumpBackRight && !MustJumpLeft && !MustJumpRight) {
            if (!(RowChange == row - RowDifference
                    && ColumnChange == column - 2)) {
                if (cb[row - RowDifference][column - 2] % 1000000 == 1) {
                    JOptionPane.showMessageDialog(null, "Must jump back left", "Alert", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        } else if (jumpVal > 1) {
            if (!(((RowChange == row + RowDifference && ColumnChange == column + 2)
                    && cb[row + RowDifference][column + 2] % 1000000 == 1)
                    || ((RowChange == row + RowDifference && ColumnChange == column - 2)
                    && cb[row + RowDifference][column - 2] % 1000000 == 1)
                    || ((RowChange == row - RowDifference && ColumnChange == column + 2)
                    && cb[row - RowDifference][column + 2] % 1000000 == 1)
                    || ((RowChange == row - RowDifference && ColumnChange == column - 2)
                    && cb[row - RowDifference][column - 2] % 1000000 == 1))) {
                JOptionPane.showMessageDialog(null, "Must make jump", "Alert", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        //checks to see if player is trying to move onto full space
        if ((int) cb[RowChange][ColumnChange] % 100 / 10 != 0) {
            JOptionPane.showMessageDialog(null, "Can't move on a full space", "Alert", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        //move is valid
        return true;
    }

    public static int[][] MovePiece(int[][] cb, boolean FirstPlayerTurn) {
        boolean isKing;
        int rowDif, columnDif;
        isKing = (cb[row][column] % 1000 / 100 == 1);
        rowDif = (RowChange - row) / 2;
        columnDif = (ColumnChange - column) / 2;

        if (cb[RowChange][ColumnChange] % 1000000 / 100000 == 2) {
            cb[row][column] = cb[row][column] + 100;
        }

        cb[RowChange][ColumnChange] = cb[row][column];

        if (row != 1 && row != 8) {
            cb[row][column] = 9000001;
        } else {
            cb[row][column] = 9200001;
        }

        if (!isKing) {
            if ((int) Math.abs(RowChange - row) == 2) {
                if (FirstPlayerTurn) {
                    if (ColumnChange - column == 2) {
                        cb[row - 1][column + 1] = 9000001;
                    } else {
                        cb[row - 1][column - 1] = 9000001;
                    }
                } else if (ColumnChange - column == 2) {
                    cb[row + 1][column + 1] = 9000001;
                } else {
                    cb[row + 1][column - 1] = 9000001;
                }
            }
        } else {
            cb[row + rowDif][column + columnDif] = 9000001;
        }
        return cb;
    }

    public static int[][] StartMovingPiece(int[][] cb, boolean FirstPlayerTurn, int[][] JumpValue) {
        boolean IsValidMove, MovingPiece;

        if (FirstPlayerTurn) {
            JOptionPane.showMessageDialog(null, "Player one make your move");
        } else {
            JOptionPane.showMessageDialog(null, "Player two make your move");
        }
        MovingPiece = true;

        while (MovingPiece) {
//            System.out.println("Select row and column of piece you want to move."
//                    + " row first then column. Or type 999 to exit");
            row = stdin.nextInt();

            if (row == 999) {
                cb[0][0] = 999;
                MovingPiece = false;
            } else {
                column = stdin.nextInt();

                if ((JumpValue[0][0] == 1 && FirstPlayerTurn)
                        || (JumpValue[0][0] == 2 && !FirstPlayerTurn)) {
                    while ((row != JumpValue[0][1] || column != JumpValue[0][2])
                            && (row != JumpValue[1][1] || column != JumpValue[1][2])
                            && (row != JumpValue[2][1] || column != JumpValue[2][2])
                            && (row != JumpValue[3][1] || column != JumpValue[3][2])) {
/*                        System.out.println("There is a jump to be made at ");
                        for (int i = 0; i < 4; i++) {
                            if (i > 0 && JumpValue[i][0] != 0) {
                                System.out.println("OR AT");
                            }
                            if (JumpValue[i][0] != 0) {
                                System.out.print(JumpValue[i][1]);
                                System.out.println(" " + JumpValue[i][2]);
                            }
                        }
                        System.out.println("Select row and column of a piece that has"
                                + "to make a jump");
*/                      row = stdin.nextInt();
                        column = stdin.nextInt();
                    }
                }
//                System.out.println("Select row and column of space you want to move "
//                      + "piece to. row first then column.");
                RowChange = stdin.nextInt();
                ColumnChange = stdin.nextInt();
                IsValidMove = CheckMoveValid(cb, FirstPlayerTurn);

                if (IsValidMove) {
                    cb = MovePiece(cb, FirstPlayerTurn);
                    MovingPiece = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalide move, try again", "Alert", JOptionPane.ERROR_MESSAGE);
                    MovingPiece = true;
                }
            }
        }
        return cb;
    }

    public static boolean CheckForEndGame(int[][] cb, boolean FirstPlayerTurn) {
        boolean RedLeft, BlackLeft;
        int RedAmntLeft, BlackAmntLeft;
        BlackAmntLeft = 0;
        RedAmntLeft = 0;
        BlackLeft = false;
        RedLeft = false;

        //check if only one color is left on board
        for (int x = 1, y = 1; x < 8; y++) {
            if (y == 9) {
                y = 1;
            }

            if (!RedLeft) {
                if (cb[x][y] % 100 / 10 == 1) {
                    RedLeft = true;
                }
            }
            if (!BlackLeft) {
                if (cb[x][y] % 100 / 10 == 2) {
                    BlackLeft = true;
                }
            }
            if (y == 8) {
                x = x + 1;
            }
        }
        if (RedLeft && !BlackLeft) {
            JOptionPane.showMessageDialog(null, "Red Wins");
            return true;
        }
        if (BlackLeft && !RedLeft) {
            JOptionPane.showMessageDialog(null, "Black Wins");
            return true;
        }
        //checks how many pieces of each color are left
        for (int x = 1, y = 1; x < 8; y++) {
            if (y == 9) {
                y = 1;
            }
            if (cb[x][y] % 100 / 10 == 1) {
                RedAmntLeft = RedAmntLeft + 1;
            } else if (cb[x][y] % 100 / 10 == 2) {
                BlackAmntLeft = BlackAmntLeft + 1;
            }
            if (y == 8) {
                x = x + 1;
            }
        }
        //check if any pieces can still move
        for (int x = 1, y = 1; x < 8; y++) {
            if (y == 9) {
                y = 1;
            }
            if (FirstPlayerTurn) {
                if (cb[x][y] % 100 / 10 == 2) {
                    if (cb[x][y] % 1000 / 100 == 1) {
                        if ((cb[x - 1][y - 1] % 100 == 1
                                || cb[x - 1][y + 1] % 100 == 1)
                                || (cb[x + 1][y - 1] % 100 == 1
                                || cb[x + 1][y + 1] % 100 == 1)
                                || (cb[x - 2][y - 2] % 100 == 1
                                || cb[x - 2][y + 2] % 100 == 1)
                                || (cb[x + 2][y - 2] % 100 == 1
                                || cb[x + 2][y + 2] % 100 == 1)) {
                            if (RedAmntLeft > BlackAmntLeft) {
                                JOptionPane.showMessageDialog(null, "Stalemate. Red has more pieces");
                                return true;
                            } else if (BlackAmntLeft > RedAmntLeft) {
                                JOptionPane.showMessageDialog(null, "Stalemate. Black has more pieces");
                                return true;
                            }
                        }
                    } else {
                        if (cb[x - 1][y - 1] % 100 == 1
                                || cb[x - 1][y + 1] % 100 == 1) {
                            return false;
                        }
                        if (x > 2) {
                            if (y > 2) {
                                if (cb[x - 2][y - 2] % 100 == 1) {
                                    return false;
                                }
                                if (y < 8) {
                                    if (cb[x - 2][y + 2] % 100 == 1) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                if (cb[x][y] % 100 / 10 == 1) {
                    if (cb[x][y] % 1000 / 100 == 1) {
                        if ((cb[x - 1][y - 1] % 100 == 2
                                || cb[x - 1][y + 1] % 100 == 2)
                                || (cb[x + 1][y - 1] % 100 == 2
                                || cb[x + 1][y + 1] % 100 == 2)
                                || (cb[x - 2][y - 2] % 100 == 2
                                || cb[x - 2][y + 2] % 100 == 2)
                                || (cb[x + 2][y - 2] % 100 == 2
                                || cb[x + 2][y + 2] % 100 == 2)) {
                            if (RedAmntLeft > BlackAmntLeft) {
                                JOptionPane.showMessageDialog(null, "Stalemate. Red has more pieces");
                                return true;
                            } else if (BlackAmntLeft > RedAmntLeft) {
                                JOptionPane.showMessageDialog(null, "Stalemate. Black has more pieces");
                                return true;
                            }
                        }
                    } else {
                        if (cb[x + 1][y - 1] % 100 == 2
                                || cb[x + 1][y + 1] % 100 == 2) {
                            return false;
                        }
                        if (x < 8) {
                            if (y > 2) {
                                if (cb[x + 2][y - 2] % 100 == 2) {
                                    return false;
                                }
                                if (y < 8) {
                                    if (cb[x + 2][y + 2] % 100 == 2) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
                if (y == 8) {
                    x = x + 1;
                }
            }
            return false;
        }
        return false;
    }

    public static void PrintOut(int[][] cb) {

        boolean PrintBoard = true;
        int a, b;
        int val1, val2, val3, val4;
        char d, val5;
        val1 = 0;
        val2 = 0;
        val3 = 0;
        val4 = 0;
        a = 0;
        b = 0;

        while (PrintBoard) {
            if (b == 10) {
                a = ++a;
                b = 0;
                System.out.println();
            }
            if (a <= 10) {
                if (a < 10) {
                    val1 = cb[a][b] % 10; //checking movable spaces
                    val2 = (cb[a][b] % 100 - val1) / 10; //checking colors
                    val3 = cb[a][b] / 1000000;
                    val4 = cb[a][b] % 1000 / 100;
                } else {
                    //a = 0;
                    b = 0;
                    System.out.println(" ");
                }
                b++;
            }
            if (a < 10 && b < 11) {
                if (val2 == 0 && val3 == 9) {
                    if (val1 == 0) {
                        val5 = 'X';
                    } else {
                        val5 = '_';
                    }
                } else {
                    val5 = (char) val3;
                }
                if (val2 == 1) {
                    if (val4 == 0) {
                        val5 = 'r';
                    } else {
                        val5 = 'R';
                    }
                }
                if (val2 == 2) {
                    if (val4 == 0) {
                        val5 = 'b';
                    } else {
                        val5 = 'B';
                    }
                }
                d = val5;
                System.out.print(d + " ");
            }
            if (b <= 10 && a < 10) {
                PrintBoard = true;
            } else {
                PrintBoard = false;
                a = 0;
                b = 0;
            }
        }
    }

    public static void WriteFile(int[][] cb, String savename, boolean FirstPlayerTurn) throws Exception {
        File File = new File("gamestate");
        File File2 = new File(savename);
        PrintStream Print;
        Print = new PrintStream(File);

        String boardValue;

        try {
            for (int x = 1, y = 1; x < 8; y++) {
                if (y == 9) {
                    y = 1;
                }
                boardValue = Integer.toString(cb[x][y]);
                Print.print(boardValue + "\t");
                if (y == 8) {
                    Print.print("\n");
                    x = x + 1;
                }
            }
            Print.print(FirstPlayerTurn);
            if (!savename.equals("null")) {
                File.renameTo(File2);
                Print.close();
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static int[][] ReadFile(String File, String boardValue) throws Exception {
        int[][] cb;
        cb = new int[11][10];
        int i;
        i = 0;

        for (int n = 48, x = 0, y = 0; x < 11; y++) {
            if (y == 11) {
                y = 0;
            }
            
            if (y == 10) {
                x = x + 1;
            }
        }
        try {
            fileReader = new Scanner(new File(File));
            fileReader.useDelimiter("\t|\n");
            while (fileReader.hasNext()) {
                boardValue = fileReader.next();
                i = i + 1;
            }
        } 
        catch (Exception NoSuchElementException) {
        }
        return cb;
    }
}
