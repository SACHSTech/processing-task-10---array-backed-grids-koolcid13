import processing.core.PApplet;

/**
* A program Sketch.java that demonstrates the application of 2D arrays.
* @author: Avin A.
*
*/

public class Sketch extends PApplet {
	
  int CELL_WIDTH = 20, CELL_HEIGHT = 20, ROW_COUNT = 10, COLUMN_COUNT = 10, MARGIN = 5;
  int SCREEN_WIDTH = (CELL_WIDTH + MARGIN) * COLUMN_COUNT + MARGIN;
  int SCREEN_HEIGHT = (CELL_HEIGHT + MARGIN) * ROW_COUNT + MARGIN;
  int intGrid[][] = new int[ROW_COUNT][COLUMN_COUNT];
  int rowCellCnt[] = new int[ROW_COUNT], colCellCnt[] = new int[COLUMN_COUNT];
  int selectedCnt = 0;

  /**
   * Initial settings happens in it
   */
  public void settings() {
    size(SCREEN_WIDTH, SCREEN_HEIGHT);
  }

  /**
   * Is called once and to set the initial environment code is to happen in
   */
  public void setup() {
    background (0);
    for (int row = 0; row < ROW_COUNT; row ++) {
      for (int col = 0; col < COLUMN_COUNT; col ++) {
        if (random (10) < 5) {
          intGrid[row][col] = 1;
          selectedCnt ++;
          rowCellCnt[row] ++;
          colCellCnt[col] ++;
        }
      }
    }
  }

  /**
   * Is called continuously and executes the codes within it infinite times
   */
  public void draw() {
    for (int row = 0; row < ROW_COUNT; row ++) {
      for (int col = 0; col < COLUMN_COUNT; col ++) {
        // colour selection
        if (intGrid[row][col] == 0) {
          fill (255);
          stroke (255);
        }
        else {
          fill (81, 196, 131);
          stroke (81, 196, 131);
        }
        rect ((col + 1) * MARGIN + col * CELL_WIDTH, (row + 1) * MARGIN + row * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
      }
    } 
  }

  /**
   * Is called every time a mouse button is pressed
   */
  public void mousePressed(){
    System.out.println ("u clicked; mouse coordinates: mouseX = " + mouseX + ", mouseY = " + mouseY + " u are currently on row " + (mouseY / (MARGIN + CELL_HEIGHT) + 1) + " and on column " + (mouseX / (MARGIN + CELL_WIDTH) + 1));
    int intTempRow = mouseY / (MARGIN + CELL_HEIGHT), intTempCol = mouseX / (MARGIN + CELL_WIDTH);
    colourThis(intTempRow, intTempCol);
    
    // colours neighbouring blocks, ifs to check if the block is on the edge or wall
    if (intTempCol + 1 < COLUMN_COUNT) {
      colourThis (intTempRow, intTempCol + 1);
    }
    if (intTempCol - 1 >= 0) {
      colourThis(intTempRow, intTempCol - 1);
    }
    if (intTempRow + 1 < ROW_COUNT) {
      colourThis(intTempRow + 1, intTempCol);
    }
    if (intTempRow - 1 >= 0) {
      colourThis(intTempRow - 1, intTempCol);
    }
    System.out.println ("number of selected cells: " + selectedCnt);

    for (int row = 0; row < ROW_COUNT; row ++) {
      System.out.println ("Row " + row + " has " + rowCellCnt[row] + " cells");
    }
    for (int col = 0; col < COLUMN_COUNT; col ++) {
      System.out.println ("Column " + col + " has " + colCellCnt[col] + " cells");
    }
    findContinuous();
  }

  /**
   * changes the colour identifier value according to the cell being selected or not
   *
   * @param row  row number
   * @param col  column number
   */
  private void colourThis (int row, int col) {
    if (intGrid[row][col] == 0) {
      intGrid[row][col] = 1;
      selectedCnt ++;
      rowCellCnt[row] ++;
      colCellCnt[col] ++;
    }
    else {
      intGrid[row][col] = 0;
      selectedCnt --;
      rowCellCnt[row] --;
      colCellCnt[col] --;
    }
  }

  /**
   * finds max number of continuous cells in each row
   */
  private void findContinuous () {
    for (int row = 0; row < ROW_COUNT; row ++) {
      int MaxChainSoFar = 0;
      int col = 0;
      while (col < COLUMN_COUNT) {
        int chainLength = 0;
        if (intGrid[row][col] == 1) {
          // if the block is selected, a loop starts from that block to check the chain for continuous blocks
          for (int subcol = col; subcol < COLUMN_COUNT; subcol ++) {
            if (intGrid[row][subcol] == 1) {
              chainLength ++;
            }
            else {
              break;
            }
          }
          if (chainLength > 2) {
            MaxChainSoFar = max (MaxChainSoFar, chainLength);
          }
        }
        col ++;
      }

      if (MaxChainSoFar > 2) {
        System.out.println ("Row " + row + " has " + MaxChainSoFar + " continuous blocks selected.");
      }
    }
  }
}