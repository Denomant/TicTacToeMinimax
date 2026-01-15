
import simpleIO.*;
import TicTacToe.board.*;
import TicTacToe.boardprinter.*;
import TicTacToe.player.*;
import TicTacToe.input.*;
import TicTacToe.model.CellValue;


/**
 * @author sDenis Zaltsberg
 * Date: 13/1/26
 * Course: ICS3U
 * App.java
 * The main application class.
 */

public class App {
    private static TicTacToeBoard<?> initialBoard;
    private static TicTacToeBoard<?> board;       
    private static BoardPrinter printer;
    private static IntInputReader inputReader= new ConsoleIntInputReader();
    private static TicTacToePlayer[] players = new TicTacToePlayer[2];

    public static void main(String[] args) { 
        boolean running = true;
        Console.print("Welcome to Tic-Tac-Toe!\n");

        config();

        Console.print();
        while (running){
            board = initialBoard;

            game();

            int userChoice = inputReader.readInt("\nDo you want to play again?\n1) Yes\n2) No (exit)\n");

            switch (userChoice) {
                case 1:
                    board = board.getClass().equals(Board3x3.class) ? new Board3x3() : new Board4x4();
                    break;
                case 2:
                    running = false;
                    Console.print("Thanks for playing Tic-Tac-Toe! Goodbye!");
                    break;
                default:
                    Console.print("Invalid option, exiting the game.");
                    running = false;
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

        // Printer
        while (true) {
            Console.print("What kind of printing style do you prefer?");
            userChoice = inputReader.readInt("1) Simple\n2) Fancy (box-drawing characters might not be supported by all terminals)\n");

            switch (userChoice) {
                case 1:
                    printer = new SimplePrinter();
                    break;
                case 2:
                    printer = new BoxCharacterPrinter();
                    break;
                default:
                    continue;
            };
            break;
        }

        // Player and opponents.
        players[0] = new User(inputReader);
        while (true) {
            Console.print("Who do you want to play against?");
            userChoice = inputReader.readInt("1) Another Player (turn-based local multiplayer using the same PC)\n2) Simple AI\n3) Impossible AI\n");

            switch (userChoice) {
                case 1:
                    players[1] = new User(inputReader);
                    break;
                case 2:
                    players[1] = new Random();
                    break;
                case 3:
                    players[1] = new Minimax();
                    Console.print("Preparing AI...\nThis might take up to a minute depending on the board size.");
                    players[1].makeMove(initialBoard); // Explore some of the branches
                    break;
                default:
                    continue;
            };
            break;
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
}
