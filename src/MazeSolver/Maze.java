package MazeSolver;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Maze {

  protected int height;
  protected int width;
  protected Point startCoordinates;
  protected Point endCoordinates;
  protected int[][] maze;
  private final int mazeDimensionsFileLine = 1;
  private final int startCoordinatesFileLine = 2;
  private final int endCoordinatesFileLine = 3;
  private final int mazeDataFileLine = 4;
  protected final int path = 0;
  protected final int wall = 1;
  protected final int start = 3;
  protected final int end = 4;
  
  public ArrayList<String> fileToArrayList(File mazeFile) {
    ArrayList<String> fileArrayList = new ArrayList<String>();
    Scanner fileScanner = null;
    try {
      fileScanner = new Scanner(mazeFile);
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
    while(fileScanner.hasNextLine()) {
      String x = fileScanner.nextLine();
      fileArrayList.add(x);
    }
    return fileArrayList;
  }
  
  public LinkedList<Integer> stringToIntLinkedList(String input) {
    Scanner scanner = new Scanner(input);
    LinkedList<Integer> list = new LinkedList<Integer>();
    while (scanner.hasNextInt()) {
      list.add(scanner.nextInt());
    }
    scanner.close();
    return list; 
  }
  
  public void setDimensions(ArrayList<String> fileArrayList) {
    LinkedList<Integer> dimensions = stringToIntLinkedList(fileArrayList.get(mazeDimensionsFileLine-1));
    this.width = dimensions.remove();
    this.height = dimensions.remove();
  }
  
  public void setCoordinates(ArrayList<String> fileArrayList) {
    LinkedList<Integer> startCoordinatesList = stringToIntLinkedList(fileArrayList.get(startCoordinatesFileLine-1));
    this.startCoordinates = new Point(startCoordinatesList.remove(),startCoordinatesList.remove());
    LinkedList<Integer> endCoordinatesList= stringToIntLinkedList(fileArrayList.get(endCoordinatesFileLine-1));
    this.endCoordinates = new Point(endCoordinatesList.remove() , endCoordinatesList.remove());
  }
  
  public void populateMaze(ArrayList<String> fileReadByLine) {
    for(int i = (mazeDataFileLine-1); i < fileReadByLine.size(); i++) {
      LinkedList<Integer> mazeValues = stringToIntLinkedList(fileReadByLine.get(i));
      for(int j = 0; j < this.width; j++ ) {
        this.maze[i-(mazeDataFileLine-1)][j] = mazeValues.remove();
      }
    }
  }
  
  Maze(File mazeFile) {
    ArrayList<String> fileReadByLine = fileToArrayList(mazeFile);
    setCoordinates(fileReadByLine);
    setDimensions(fileReadByLine);
    this.maze = new int[this.height][this.width];
    populateMaze(fileReadByLine);
    this.maze[this.startCoordinates.y][this.startCoordinates.x] = start;
    this.maze[this.endCoordinates.y][this.endCoordinates.x] = end;
  }
}
