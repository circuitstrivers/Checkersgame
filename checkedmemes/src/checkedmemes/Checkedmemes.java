package checkedmemes;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.Scanner;
public class Checkedmemes extends Application{

static Scanner stdin = new Scanner(System.in);
    
public void start(Stage primarystage){
//Gui
}    

public static boolean checkmovevalid(int[][] cb, int row, int column, int rowchange, int columnchange, boolean firstplayerturn){
    boolean mustjumpleft, mustjumpright;
    int piececolor, leftsecondpiececolor, rightsecondpiececolor;
    
    //first checks if requested piece is actually a piece
    if(cb[row][column] % 1000000 == 0){
        return false;
    }
    
    //checks if requested piece belongs to player
    if((((((cb[row][column] % 1000000) - 1) / 10) == 2) && firstplayerturn == false) ||
       (((((cb[row][column] % 1000000) - 1) / 10) == 1) && firstplayerturn == true )) {
        System.out.println("NOT YOUR PIECE");
        return false;
    }
    
    //checks to make sure piece isnt moving backwards
    if(firstplayerturn){
        if(rowchange >= row){
            System.out.println("Can't move backwards");
            return false;
        }
    }else{
        if(rowchange <= row){
            System.out.println("Can't move backwards");
            return false;
        }
    }
    
    //checks to make sure piece is moving onto valid space
    if(columnchange == column || Math.abs(columnchange - column) > 1){
        System.out.println("Can't move there");
        return false;
    }
    
    //checks piece color and color of any piece that may be directly ahead of piece
    piececolor                = (((cb[row][column]         % 1000000) - 1) / 10);
    if(firstplayerturn){
        leftsecondpiececolor  = (((cb[row + 1][column - 1] % 1000000) - 1) / 10);
        rightsecondpiececolor = (((cb[row + 1][column + 1] % 1000000) - 1) / 10);
    }else{
        leftsecondpiececolor  = (((cb[row - 1][column - 1] % 1000000) - 1) / 10);
        rightsecondpiececolor = (((cb[row - 1][column + 1] % 1000000) - 1) / 10);
    }
    
    //checks if there is a piece ahead that must be jumped
    if(firstplayerturn){
        if(rightsecondpiececolor == 1){
            mustjumpright = true;
        }else{
            mustjumpright = false;
        }
        if(leftsecondpiececolor == 1){
            mustjumpleft  = true;
        }else{
            mustjumpleft = false;
        }
    }else{
        if(rightsecondpiececolor == 2){
            mustjumpright = true;
        }else{
            mustjumpright = false;
        }
        if(leftsecondpiececolor == 2){
            mustjumpleft = true;
        }else{
            mustjumpleft = false;
        }
    }
    
    //checks if player is making a required jump
    if(firstplayerturn){
        if(mustjumpright || mustjumpleft){
            if(!((mustjumpright && (cb[rowchange][columnchange] == cb[row + 2][column + 2])) ||
                 (mustjumpleft  && (cb[rowchange][columnchange] == cb[row + 2][column - 2])))){
                System.out.println("must jump piece");
                return false;
            }
        }
    }else{
        if(mustjumpright || mustjumpleft){
            if(!((mustjumpright && (cb[rowchange][columnchange] == cb[row - 2][column + 2])) ||
                 (mustjumpleft  && (cb[rowchange][columnchange] == cb[row - 2][column - 2])))){
                System.out.println("must jump piece");
                return false;
            }
        }
    }
    
    //move is valid
    return true;
}

public static int[][] movepiece(int[][] cb, int row, int column, int rowchange, int columnchange){
    
    cb[rowchange][columnchange] = cb[row][column];
    cb[row][column] = 9000001;
    
    return cb;
}

public static int[][] startmovingpiece(int[][] cb, boolean firstplayerturn){
    int row, column, rowchange, columnchange;
    boolean isvalidmove, movingpiece;
    
    if(firstplayerturn){
        System.out.println("Player one make your move");
    }else{
        System.out.println("Player two make your move");
    }
    movingpiece = true;
    
    while(movingpiece){
        System.out.println("Select row and column of piece you want to move. row first then column.");
        row = stdin.nextInt();
        column = stdin.nextInt();
    
        System.out.println("Select row and column of space you want to move piece to. row first then column.");
        rowchange = stdin.nextInt();
        columnchange = stdin.nextInt();
    
        isvalidmove = checkmovevalid(cb, row, column, rowchange, columnchange, firstplayerturn);
        if(isvalidmove){
            cb = movepiece(cb, row, column, rowchange, columnchange);
            movingpiece = false;
        }else{
            System.out.println("Invalid move try again");
            movingpiece = true;
        }
    }
    return cb;
}

public static void printout(int[][] cb){
    
    boolean printboard = true;
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
    
    while(printboard){
        if(b == 9){
            a = ++a;
            b = 0;
            System.out.println();
        }
        
        if(a <= 9){
            if(a < 9){
                val1 = cb[a][b] % 10; //checking movable spaces
                val2 = (cb[a][b] % 100 - val1) / 10; //checking colors
                val3 = cb[a][b] / 1000000;
            }
            else{
                //a = 0;
                b = 0;
                System.out.println(" ");
            }
            b++;
        }
    
        if(a < 10 && b < 10){
            if(val2 == 0 && val3 == 9){
                if(val1 == 0){
                    val4 = 'X';
                }
                else{
                    val4 = '_';
                }
            }
            else{
                val4 = (char)val3;
                }
            if(val2 == 1){
                val4 = 'B';
            }    
            if(val2 == 2){
                val4 = 'R';
            }
            d = val4;
            System.out.print(d + " ");
        }
    
        if(b <= 9 && a < 9){
            printboard = true;
        }else{
            printboard = false;
            a = 0;
            b = 0;
        }
    }        
}

public static void main(String[] args) {
    //Creates an array that sets up the spaces that make up the board
    int[][] cb = {{48000000, 49000000, 50000000, 51000000, 52000000, 53000000, 54000000, 55000000, 56000000},
                  {49000000,  9000000,  9200011,  9000000,  9200011,  9000000,  9200011,  9000000,  9200011},
                  {50000000,  9000011,  9000000,  9000011,  9000000,  9000011,  9000000,  9000011,  9000000},
                  {51000000,  9000000,  9000011,  9000000,  9000011,  9000000,  9000011,  9000000,  9000011},
                  {52000000,  9000001,  9000000,  9000001,  9000000,  9000001,  9000000,  9000001,  9000000},
                  {53000000,  9000000,  9000001,  9000000,  9000001,  9000000,  9000001,  9000000,  9000001},
                  {54000000,  9000021,  9000000,  9000021,  9000000,  9000021,  9000000,  9000021,  9000000},
                  {55000000,  9000000,  9000021,  9000000,  9000021,  9000000,  9000021,  9000000,  9000021},
                  {56000000,  9100021,  9000000,  9100021,  9000000,  9100021,  9000000,  9100021,  9000000},
                 };
    
    boolean rungame, firstplayerturn;
    rungame = true;
    firstplayerturn = true;
    
    while(rungame){
        
        printout(cb);
        
        cb = startmovingpiece(cb, firstplayerturn);
        firstplayerturn = !firstplayerturn;
                
    }

    if(rungame == false){
        System.exit(0);
    }
}
}
