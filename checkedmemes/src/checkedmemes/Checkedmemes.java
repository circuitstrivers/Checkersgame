package checkedmemes;
//Brent isn't a cool kid
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Scanner;
public class Checkedmemes extends Application{

public void start(Stage primarystage){

}    

public static void main(String[] args) {
Scanner stdin = new Scanner(System.in);
    //brent is gay
    //Creates an array that sets up the spaces that make up the board
    int[][] cb = {{0000000, 1000000, 2000000, 3000000, 4000000, 5000000, 6000000, 7000000, 8000000},
                  {1000000, 9000000, 9200011, 9000000, 9200011, 9000000, 9200011, 9000000, 9200011},
                  {2000000, 9000011, 9000000, 9000011, 9000000, 9000011, 9000000, 9000011, 9000000},
                  {3000000, 9000000, 9000011, 9000000, 9000011, 9000000, 9000011, 9000000, 9000011},
                  {4000000, 9000001, 9000000, 9000001, 9000000, 9000001, 9000000, 9000001, 9000000},
                  {5000000, 9000000, 9000001, 9000000, 9000001, 9000000, 9000001, 9000000, 9000001},
                  {6000000, 9000021, 9000000, 9000021, 9000000, 9000021, 9000000, 9000021, 9000000},
                  {7000000, 9000000, 9000021, 9000000, 9000021, 9000000, 9000021, 9000000, 9000021},
                  {8000000, 9100021, 9000000, 9100021, 9000000, 9100021, 9000000, 9100021, 9000000},
                 };
    
    int[][] CheckerBoard;
    
    boolean printboard = true;
    boolean runprint = true;
    int a, b, c, e;
    int val1, val2, val3;
    char d, val4;
    val1 = 0;
    val2 = 0;
    val3 = 0;
    val4 = 0;
    d = 0;
    a = 0;
    b = 0;
    
    while(runprint){
        
        System.out.println("Player 1: Make your move");
        
    
        while(printboard){
            if(b == 8){
                a = ++a;
                b = 0;
                System.out.println();
                }
        
            if(a <= 8){
                if(a < 8){
                    val1 = cb[a][b] % 10; //checking movable spaces
                    val2 = (cb[a][b] % 100 - val1) / 10; //checking colors
                    }
                else{
                    //a = 0;
                    b = 0;
                    System.out.println(" ");
                    }
                b++;
                }
        
            if(a < 8 && b < 8){
                if(val2 == 0){
                    if(val1 == 0){
                        val4 = 'X';
                        }
                    else{
                        val4 = '_';
                        }
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
        
            if(b <= 8 && a < 8){
                printboard = true;
                }
            else{
                printboard = false;
                a = 0;
                b = 0;
                }
            }
        
        //Math to change position of peices here
        
        System.out.println("would you like to print again? \n yes(1) \n no(0)");
        c = stdin.nextInt();
        if(c == 1){
            runprint = true;
            printboard = true;
            System.out.println("running again");
            }
        else{
            runprint = false;
            System.out.println("exiting");
            }
        }

    if(runprint == false){
        System.exit(0);
        }
}
}