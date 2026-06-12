package main;

import simpleIO.*;
import TicTacToe.board.*;
import TicTacToe.boardprinter.*;
import TicTacToe.player.*;
import TicTacToe.input.*;
import TicTacToe.javafx.*;
import TicTacToe.model.CellValue;
import javafx.application.Application;
import javafx.application.Platform;


/**
 * The main application class for the Tic Tac Toe game. <br>
 * Handles the configuration and the game loop. <br>
 * App.java
 * @author Denis Zaltsberg
 * @date 8/06/2026
*/

public class App {
    private static TicTacToeBoard<?> initialBoard;
    private static TicTacToeBoard<?> board;       
    private static BoardPrinter printer;
    private static IntInputReader inputReader= new ConsoleIntInputReader();
    private static TicTacToePlayer[] players = new TicTacToePlayer[2];
    private static boolean isJavaFX = false;

    public static void main(String[] args) { 
        boolean running = true;
        Console.print("Welcome to Tic-Tac-Toe!\n");

        config();

        Console.print();
        while (running){
            board = initialBoard;

            game();

            int userChoice;
            if (isJavaFX){
                userChoice = JavaFXApp.askYesNoQuestion("Do you want to play again?") ? 1 : 2;
            } else {
                userChoice = inputReader.readInt("\nDo you want to play again?\n1) Yes\n2) No (exit)\n");
            }
             
            switch (userChoice) {
                case 1:
                    board = board.getClass().equals(Board3x3.class) ? new Board3x3() : new Board4x4();
                    break;
                case 2:
                    running = false;
                    Console.print("Thanks for playing Tic-Tac-Toe! Goodbye!");
                    if (players[1] instanceof PersistentMinimax){
                        ((PersistentMinimax) players[1]).saveMemory();
                    }
                    close();
                    break;
                default:
                    Console.print("Invalid option, exiting the game.");
                    running = false;
                    if (players[1] instanceof PersistentMinimax){
                        ((PersistentMinimax) players[1]).saveMemory();
                    }
                    break;
            };
        }
    }

    private static void config(){
        Console.print("Enter the option number to configure your experience.");
        int userChoice = 0;
        
        // Board
        while (true) {
            Console.print("What kind of board do you want to play on?");
            userChoice = inputReader.readInt("1) 3x3\n2) 4x4\n");

            switch (userChoice) {
                case 1:
                    initialBoard = new Board3x3();
                    break;
                case 2:
                    initialBoard = new Board4x4();
                    break;
                default:
                    continue;
            };
            break;
        }

        // Printer + main player
        while (true) {
            Console.print("What kind of printing style do you prefer?");
            userChoice = inputReader.readInt("1) Simple\n2) Fancy (box-drawing characters might not be supported by all terminals)\n3) JavaFX (graphical)\n");

            switch (userChoice) {
                case 1:
                    printer = new SimplePrinter();
                    players[0] = new User(inputReader);
                    break;
                case 2:
                    printer = new BoxCharacterPrinter();
                    players[0] = new User(inputReader);
                    break;
                case 3:
                    printer = new JavaFXPrinter();
                    players[0] = new JavaFXPlayer();
                    JavaFXApp.setDependencies((JavaFXPlayer) players[0], (JavaFXPrinter) printer, initialBoard);
                    // Launch JavaFX after an opponent is chosen
                    isJavaFX = true;
                    break;
                default:
                    continue;
            };
            break;
        }

        // Opponent.
        while (true) {
            Console.print("Who do you want to play against?");
            userChoice = inputReader.readInt("1) Another Player (turn-based local multiplayer using the same PC)\n2) Simple AI\n3) Impossible AI\n");

            switch (userChoice) {
                case 1:
                    if (isJavaFX){
                        players[1] = players[0]; // Point to the same player object to communicate
                    } else {
                        players[1] = new User(inputReader);
                    }
                    break;
                case 2:
                    players[1] = new Random();
                    break;
                case 3:
                    players[1] = new PersistentMinimax();
                    Console.print("Preparing AI...\nThis might take up to a minute depending on the board size.");
                    players[1].makeMove(initialBoard); // Explore some of the branches
                    break;
                default:
                    continue;
            };
            break;
        }

        if (isJavaFX){
            new Thread(() -> Application.launch(JavaFXApp.class, new String[0])).start();

            try {
                JavaFXApp.startupLatch.await(); // Wait for the JavaFX application to signal that it's ready
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void game(){
        int currentPlayer = Math.random() < 0.5 ? 0 : 1; // Randomly choose starting player

        Console.print("Player " + (currentPlayer+1) + " will be X.");

        while (!board.isTerminal()){

            Console.print("Player " + (currentPlayer + 1) + "'s Turn. This is the board (you are " + board.getCurrentPlayer().getCharacter() + "):");
            Console.print(printer.render(board));

            board = board.moveResult(players[currentPlayer].makeMove(board));
            Console.print("Player " + (currentPlayer + 1) + " has made their move.\n");

            currentPlayer = (currentPlayer + 1) % 2;
        }

        currentPlayer = (currentPlayer + 1) % 2;

        if (board.getWinner() != CellValue.EMPTY){
            Console.print("Player " + (currentPlayer + 1) + " has WON!!!");
        } else {
            Console.print("The game ended with a tie...");
        }
        
        Console.print("\nThis is the final board");
        Console.print(printer.render(board), false);
    }

    public static void close(){
        Console.print("Thanks for playing Tic-Tac-Toe! Goodbye!");
        Platform.exit();
        System.exit(0);
    }
}
