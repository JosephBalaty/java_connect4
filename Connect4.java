/* Create a Connect 4 board game that can be played between two human players.
 * The game should be played on a 6x7 grid.
 * The game should be able to detect a win or a draw.
 * The game should be able to detect an invalid move.
 * 
 * When it's a player's turn, the state of the board will be displayed, 
 * and they can make a move, and the game will update the board. Player 1's
 * moves will be represented by 'X' and Player 2's moves will be represented
 * by 'O'.
 */
import java.util.InputMismatchException;
import java.util.Scanner;


 class Connect4Board {
   private char[][] board;
   private int emptySpaces;
   private int[] lastPosPlayed; // [row, column]
   private boolean gameOver;

   public Connect4Board() { 
      board = new char[6][7];
      lastPosPlayed = new int[2];
      gameOver = true;

      emptySpaces = 42;
      for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
            board[i][j] = ' ';
            }
      }
   }

   public void display(){

         for(int i = 0; i < 6; i++){
            System.out.println(" --- --- --- --- --- --- --- ");

               for(int j = 0; j < 7; j++){
                  System.out.print("| " + this.board[i][j] + " ");
               }
               System.out.println("|");
         }
         System.out.println(" --- --- --- --- --- --- --- ");
   }

   public void newGame(){
         for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
               this.board[i][j] = ' ';
            }
         }
         emptySpaces = 42;
   }

   public void makeMove(int column, char player){
         emptySpaces--;
         for (int i = 5; i >= 0; i--){
               if(this.board[i][column] == ' '){
                  this.board[i][column] = player;
                  lastPosPlayed[0] = i;
                  lastPosPlayed[1] = column;
                  break;
               }
         }
   }

   public boolean isDraw() {
         return (emptySpaces == 0);
   }

   public boolean isWin(char curPlayer) {
         // check vertical, horizontal, and diagonal from the last position played.
         int row = this.lastPosPlayed[0];
         int column = this.lastPosPlayed[1];
         int count = 0;

         // if the sum of the current player's symbols totals 4, they win.
         for (int i = 0; i < this.board.length; i++) {
               if (this.board[i][column] == curPlayer) {
                  count++;
                  if (count == 4) {
                     return true;
                  }
               } else {
                  count = 0;
               }
         } count = 0;
         for (int j = 0; j < this.board[row].length; j++) {
            if (this.board[row][j] == curPlayer) {
               count++;
               if (count == 4) {
                  return true;
               }
            } else {
               count = 0;
            }
      } 
      
      count = 1;
      // check diagonal
      int i = row;
      int j = column;
      while (i > 0 && j > 0) {
         i--;
         j--;
         if (this.board[i][j] == curPlayer) {
            count++;
            if (count == 4) {
               return true;
            }
         } else {
            break;
         }
      }

      i = row;
      j = column;
      while (i < 5 && j < 6) {
         i++;
         j++;
         if (this.board[i][j] == curPlayer) {
            count++;
            if (count == 4) {
               return true;
            }
         } else {
            break;
         }
      }

      return false;
   }

   public boolean isValidMove(int column) {
         if (column < 0 || column > 6) {
               return false;
         }
         else if (this.board[0][column] != ' ') {
               return false;
         }
         return true;
   }
}

public class Connect4 {
   public static void main(String[] args){
         // 6 rows by 7 column grid
         System.out.println("Welcome to Connect 4!");
         Scanner scanner = new Scanner(System.in);
         Connect4Board board = new Connect4Board();

         System.out.println("Who will go first? (1 or 2)");
         int firstPlayer = scanner.nextInt();
         char curPlayer = (firstPlayer == 1) ? 'X' : 'O';

         int nextMove;
         while (true) {
            while(true){
               System.out.format("Player %d, it's your turn! Enter a column number between 1 and 7: \n", ((curPlayer == 'X') ? 1 : 2)); 
               board.display(); 
               try {
                  nextMove = scanner.nextInt();
               } catch (InputMismatchException e) {
                  System.out.println("Invalid input. Please try again.");
                  scanner.next();
                  continue;
               }
               if (board.isValidMove(nextMove - 1) == false){
                  System.out.println("Invalid move. Try again.");
               } else break;
                  
            }
         board.makeMove(nextMove - 1, curPlayer);
         boolean gameOver = board.isWin(curPlayer);
         if (gameOver == true){
            board.display();
            System.out.format("Player %d wins!\n", ((curPlayer == 'X') ? 1 : 2));}

         else if (board.isDraw() == true){
            gameOver = true;
            board.display();
            System.out.println("It's a draw!");
         }
         while (gameOver == true){
            System.out.println("Do you want to play again? (y/n)");
            char playAgain = scanner.next().charAt(0);
            if (playAgain == 'n') {
               System.out.println("Thanks for playing!");
               return;
            } else if (playAgain == 'y') {
               System.out.println("Restarting board....");
               board.newGame();

               System.out.println("Who will go first? (1 or 2)");
               firstPlayer = scanner.nextInt();
               curPlayer = (firstPlayer == 1) ? 'X' : 'O';
               break;
            } else {
               System.out.println("Invalid input. Please try again.");
            }
         }
         curPlayer = (curPlayer == 'X') ? 'O' : 'X';
   }}
}