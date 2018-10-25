package MazeSolver;


import java.util.Stack;
import java.awt.Point;
import java.io.File;

public class MazeSolver {
  
  private Point currentPosition;
  private Stack<Integer> xStack = new Stack<Integer>();
  private Stack<Integer> yStack = new Stack<Integer>();
  private int[] availableMoves;
  private final int numberOfDifferentMoves = 4;
  private final int correctPath = 2;
  private final int incorrectPath = 5;
  private char[][] convertedMaze;
  
  public int[] checkAvailableMoves(int x, int y, Maze maze) {
    int [] availableMoves = new int[numberOfDifferentMoves];
    if(maze.maze[y-1][x] == maze.path || maze.maze[y-1][x] == maze.end) {
      availableMoves[0] = 1;
    }
    if(maze.maze[y][x+1] == maze.path || maze.maze[y][x+1] == maze.end) {
      availableMoves[1] = 1;
    }
    if(maze.maze[y+1][x] == maze.path || maze.maze[y+1][x] == maze.end) {
      availableMoves[2] = 1;
    }
    if(maze.maze[y][x-1] == maze.path || maze.maze[y][x-1] == maze.end) {
      availableMoves[3] = 1;
    }
    return availableMoves;
  }
  
  public Boolean hasMove(int[] availableMoves) {
    int numMoves = 0;
    for(int i = 0; i<availableMoves.length; i++) {
      if (availableMoves[i] == 1) {
        numMoves++;
      }
    }
    return numMoves>0;
  }
  
  public int[] makeMove(int[] availableMoves, Maze maze) {
    for(int i=0; i<availableMoves.length; i++) {
      if(availableMoves[i]==1) {
        availableMoves[i] = 0;
        switch(i) {
        case 0: currentPosition.y--;
                break;
        case 1: currentPosition.x++;
                break;
        case 2: currentPosition.y++;
                break;
        case 3: this.currentPosition.x--;
                break;
        }
        break;
      }
    }
    return availableMoves;
  }
  
  private static Boolean isStartOrFinishPosition(int x, int y, Maze maze) {
    return (maze.maze[y][x] == maze.start) || (maze.maze[y][x] == maze.end);
  }
  
  private char[][] convertMaze(Maze maze){
    convertedMaze = new char[maze.height][maze.width];
    for(int i=0; i<maze.height; i++) {
      for(int j=0; j<maze.width; j++) {
        switch(maze.maze[i][j]) {
        case 0: convertedMaze[i][j] = ' ';
        break;
        case 1: convertedMaze[i][j] = '#';
        break;
        case 2: convertedMaze[i][j]=  'X';
        break;
        case 3: convertedMaze[i][j] = 'S';
        break;
        case 4: convertedMaze[i][j] = 'E';
        break;
        case 5: convertedMaze[i][j] = ' ';
        break;
        }
      }
    }
    return convertedMaze;
  }
  
  public void printMaze(char[][] array) {
    for (char[] x : array) {
      for (char y : x) {
         System.out.print(y + " ");
      }
      System.out.println();
    }
  }
  
  public char[][] solveMaze(Maze maze){
    currentPosition = maze.startCoordinates;
    while(maze.maze[currentPosition.y][currentPosition.x]!= maze.end) {
      availableMoves = checkAvailableMoves(currentPosition.x, currentPosition.y, maze);
      if(!isStartOrFinishPosition(currentPosition.x, currentPosition.y, maze)) {
        maze.maze[currentPosition.y][currentPosition.x] = correctPath;
      }
      if(hasMove(availableMoves)) {
        xStack.push(currentPosition.x);
        yStack.push(currentPosition.y);
        makeMove(availableMoves, maze);
      }else {
        maze.maze[currentPosition.y][currentPosition.x] = incorrectPath;
        currentPosition.setLocation(xStack.pop(), yStack.pop());      
      }
    }
    convertedMaze = convertMaze(maze);
    return convertedMaze;
  }
  
  public static void main(String args[]) {
    File[] mazeFiles = new File[5];
    mazeFiles[0] = new File("input.txt");
    mazeFiles[1] = new File("small.txt");
    mazeFiles[2] = new File("medium_input.txt");
    mazeFiles[3] = new File("sparse_medium.txt");
    mazeFiles[4] = new File("large_input.txt");
    
    for(File file : mazeFiles) {
      Maze maze = new Maze(file);
      MazeSolver solver = new MazeSolver();
      char[][] solvedMaze = solver.solveMaze(maze);
      solver.printMaze(solvedMaze);      
    }
  }
}
