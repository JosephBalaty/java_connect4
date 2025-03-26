import java.util.InputMismatchException;
import java.util.Scanner;

interface Connect4Gameplay {
   public void play();
}

class Connect4Board implements Connect4Gameplay {
   final private char[][] board;
   private int emptySpaces;
   final private int[] lastPosPlayed; // [row, column]
   final private Scanner scanner;
   private char curPlayer;

   public Connect4Board() { 
      board = new char[6][7];
      lastPosPlayed = new int[2];
      scanner = new Scanner(System.in);
      emptySpaces = 42;

      for(int i = 0; i < 6; i++){
         for(int j = 0; j < 7; j++){
         board[i][j] = ' ';
         }
      }
   }


   @Override
   public void play() {
      System.out.println("Welcome to Connect 4!");

      while (true) {
         this.playGame();
         if (this.playAgain() == false) {return;}
   }}

   private void playGame() {
      this.firstPlayer();

      while (true) {
         this.playerMove();
         if (this.gameOver() == true) {return;}
         else {this.nextPlayerTurn();}
      }}


private boolean playAgain() {
   char playAgain;
   while (true) {
      System.out.println("Do you want to play again? (y/n)");
      playAgain = this.scanner.next().charAt(0);
      if (playAgain == 'n') {
         System.out.println("Thanks for playing!");
         return false;
      } else if (playAgain == 'y') {
         System.out.println("Restarting board....");
         this.newGame();
         return true;
      } else {
         System.out.println("Invalid input. Please try again.");
      }
   }
}

   private void firstPlayer() {
      int firstPlayer;

      while (true) {
         System.out.println("Who will go first? (1 or 2)");
         try {
            firstPlayer = this.scanner.nextInt();
            if (firstPlayer != 1 && firstPlayer != 2) {
               System.out.println("Invalid player number. Please try again.");
            } else {
               this.curPlayer = (firstPlayer == 1) ? 'X' : 'O';
               break;
            }
         } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please try again.");
            this.scanner.next();
            }
      }
   }

   private void playerMove() {
      int nextMove;

      while (true){
         System.out.format("Player %d, it's your turn! Enter a column number between 1 and 7: \n", ((curPlayer == 'X') ? 1 : 2)); 
         this.display();
         try {
            nextMove = this.scanner.nextInt();
         } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please try again.");
            this.scanner.next();
            continue;
         }
         if (this.isValidMove(nextMove - 1) == false){
            System.out.println("Invalid move. Try again.");
         } else break;
            
      }

      this.makeMove(nextMove - 1);
   }

   private boolean gameOver() {
      if (this.isWin() == true){
         this.display();
         System.out.format("Player %d wins!\n", ((this.curPlayer == 'X') ? 1 : 2));
         return true;
      }

      else if (this.isDraw() == true){
         this.display();
         System.out.println("It's a draw!");
         return true;
      }
      return false;
   }

   private void nextPlayerTurn() {
      this.curPlayer = (this.curPlayer == 'X') ? 'O' : 'X';
   }

   private void display(){
         System.out.println("\n  1   2   3   4   5   6   7  ");
         for(int i = 0; i < 6; i++){
            System.out.println(" --- --- --- --- --- --- --- ");

               for(int j = 0; j < 7; j++){
                  System.out.print("| " + this.board[i][j] + " ");
               }
               System.out.println("|");
         }
         System.out.println(" --- --- --- --- --- --- --- ");
   }

   private void newGame(){
         for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
               this.board[i][j] = ' ';
            }
         }
         emptySpaces = 42;
   }

   private void makeMove(int column){
         emptySpaces--;
         for (int i = 5; i >= 0; i--){
               if(this.board[i][column] == ' '){
                  this.board[i][column] = this.curPlayer;
                  lastPosPlayed[0] = i;
                  lastPosPlayed[1] = column;
                  break;
               }
         }
   }

   private boolean isDraw() {
         return (emptySpaces == 0);
   }

   private boolean isVerticalWin(int column) {
      int count = 0;

      for (int i = 0; i < this.board.length; i++) {
            if (this.board[i][column] == this.curPlayer) {
               count++;
               if (count == 4) {
                  return true;
               }
            } else {
               count = 0;
            }
      }
      return false;
   }

   private boolean isHorizontalWin(int row) {
      int count = 0;

      for (int j = 0; j < this.board[row].length; j++) {
            if (this.board[row][j] == this.curPlayer) {
               count++;
               if (count == 4) {
                  return true;
               }
            } else {
               count = 0;
            }
      }
      return false;
   }
   private boolean isLeftDiagonalWin(int row, int column) {
      int count = 1;

      int i = row;
      int j = column;
      while (i > 0 && j > 0) {
         i--;
         j--;
         if (this.board[i][j] == this.curPlayer) {
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
         if (this.board[i][j] == this.curPlayer) {
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

   private boolean isRightDiagonalWin(int row, int column) {
      int count = 1;

      int i = row;
      int j = column;
      while (i > 0 && j < 6) {
         i--;
         j++;
         if (this.board[i][j] == this.curPlayer) {
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
      while (i < 5 && j > 0) {
         i++;
         j--;
         if (this.board[i][j] == this.curPlayer) {
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
   private boolean isDiagonalWin(int row, int column) {
      if (this.isLeftDiagonalWin(row, column) || this.isRightDiagonalWin(row, column)) {
         return true;
      } else { return false;}
   }


   private boolean isWin() {
         int row = this.lastPosPlayed[0];
         int column = this.lastPosPlayed[1];

         if (this.isVerticalWin(column) || this.isHorizontalWin(row) || this.isDiagonalWin(row, column)) {
            return true;
         } else {return false;}
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
         Connect4Board board = new Connect4Board();
         board.play();
}}