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

public void start(Stage primarystage){

}    

public static void main(String[] args) {
Scanner stdin = new Scanner(System.in);
    
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
        
            if(a <= 9){
                if(a < 9){
                    val1 = cb[a][b] % 10; //checking movable spaces
                    val2 = (cb[a][b] % 100 - val1) / 10; //checking colors
                    if(val1 == 0){
                        val3 = cb[a][b] / 1000000;
                    }
                    }
                else{
                    //a = 0;
                    b = 0;
                    System.out.println(" ");
                    }
                b++;
                }
        
            if(a < 9 && b < 9){
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
                }
            else{
                printboard = false;
                a = 0;
                b = 0;
                }
            }
        
        
        
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
