import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class SudokuSolver {
	
	bigGrid puzzle;
	File theInitialPuzzle;
	
	int btGridX= 0;
	int btGridY= 0;
	SmallGrid previousGrid;
	
	public SudokuSolver(){
		LoadPuzzle newP = new LoadPuzzle();
		puzzle = (bigGrid) newP.getPuzzle();
		previousGrid = puzzle.getSmallGrid(0,0);
	}
	
	public void setPuzzle(File f) throws IOException{
		theInitialPuzzle=f;
		BufferedReader br = null;
		   
			br = new BufferedReader(new FileReader(theInitialPuzzle));
			for(int i=0; i<9; i++){
				String currentLine = br.readLine();
				String[] lineArray = currentLine.split(",");
				for(int j=0; j<9; j++){
					puzzle.getSmallGrid((int)Math.floor(i/3), (int)Math.floor(j/3)).setValue(i%3,j%3,Integer.parseInt(lineArray[j]));
				}
			}

	}
	/*Solving methods*/
	public boolean checkRow (int value , int rows, int smGridX){
		
		int row = rows;
		int val = value;
		int gridX = smGridX;
		boolean goodValue = true;
		
		for(int i = 0; i < 3; i++){
			for (int j = 0; j<3; j++){
				if(puzzle.game[gridX][i].gridArray[row][j].getValue() == val){
				goodValue = false;
				}
			}
		}
		return goodValue;
	}
	
	public boolean checkCol (int value , int column, int smGridY){
		
		int col = column;
		int val = value;
		int gridY = smGridY;
		boolean goodValue = true;
		
		for(int i = 0; i < 3; i++){
			for (int j = 0; j<3; j++){
				if(puzzle.game[i][gridY].gridArray[j][col].getValue() == val){
				goodValue = false;
				}
			}
		}
		return goodValue;
	}
	
	public boolean checkGrid (int value, SmallGrid mini){//notpossiblenums
		int val =  value;
		SmallGrid sub = mini;  
		boolean goodValue = true;
		for(int i = 0; i < 3; i++){
			for (int j = 0; j<3; j++){
				if(puzzle.game[sub.getGridX()][sub.GetGridY()].gridArray[i][j].getValue() == val)	{
					boolean goodValue = false;
				}
			}
		}
		return goodValue; 
	}
	
	public void solve(int row, int col){
		int x = row;
		int y = col;
					
		if (puzzleSolved() == true){
			System.out.println("Puzzle is solved!");
		}
		else if(puzzle.game[btGridX][btGridY].gridArray[x][y].getValue() != 0){
			next(x,y);
		}
		else{
			for(int testVal=1, testVal<10; testVall++){
				if (checkRow(testVal,x,btGridX) && checkCol(testVal,y,btGridY) && checkGrid(testVal,puzzle.getSmallGrid(btGridX,btGridY))){
					puzzle.game[btGridX][btGridY].gridArray[x][y].setValue(testVal);
					lastUsedGrid(x,y);
					next(x,y);
				}else{
					backtrack(x,y);
				}
			}	
		}	
	}
	//backtracking
	public void btValueTest(int row, int col, int val){
		
		int badValue = val;
		//
		for(int testVal=1 ; testVal<10 ; testVal++){
			//if i is available and not bad num, place it down, call next, else, recursive backtracking.
			if (testVal != badValue && checkRow(testVal,x,btGridX) && checkCol(testVal,y,btGridY) && checkGrid(testVal,puzzle.getSmallGrid(btGridX,btGridY))){
			puzzle.game[btGridX][btGridY].gridArray[x][y].setValue(testVal);
			next(x,y);
			} else{
			backtrack(x,y);
			} 
		}
	}
	
	public void next( int row, int col){
		int x = row;
		int y = col;
		if(y<2){
			solve(x,y+1);
		} else if(y=2 && btGridY < 2){
			y=0;
			btGridY=btGridY+1;
			solve(x,y);
		}else if(y=2 && btGridY=2 && x<2){
			y=0;
			btGridY=0;
			solve(x+1,y);
		}else if(y = 2 && btGridY=2 && x=2){
			btGridX= btGridX+1;
			btGridY=0;
			y=0;
			x=0;
			solve(x,y);
		}
	}
	public void backtrack( int row, int col){//insert more bad values
		int x = row;
		int y = col;
		int badValue;
		if(y>0){
			badValue = puzzle.game[btGridX][btGridY].gridArray[x][y-1].getValue();
			btValueTest(x,y-1,badValue);
		} else if(y=0 && btGridY>0){
			y=2;
			btGridY=btGridY-1;
			badValue = puzzle.game[btGridX][btGridY].gridArray[x][y].getValue();
			btValueTest(x,y,badValue);
		}else if(y=0 && btGridY=0 && x>0){
			y=2;
			btGridY=2;
			badValue = puzzle.game[btGridX][btGridY].gridArray[x-1][y].getValue();
			btValueTest(x-1,y,badValue);
		}else if(y = 0 && btGridY=0 && x=0 && btGridX!=0){
			btGridX= btGridX-1;
			btGridY=2;
			y=2;
			x=2;
			badValue = puzzle.game[btGridX][btGridY].gridArray[x][y].getValue();
			btValueTest(x,y,badValue);
		}else{}
	}
	
	public boolean puzzleSolved(){
		boolean solved = true;
		for(int i =0; i<3; i++){
			for(int j=0; j<3; j++){
				if(!puzzle.getSmallGrid(i, j).valid()){
					solved=false;
				}
			}
		}
		return solved;
	}
	
	public void printPuzzle(){
		System.out.println("----The current puzzle ----");
		for(int i=0; i<9; i++){
			String line= "";
			for(int j=0; j<9; j++){
				line= line + puzzle.getSmallGrid((int)Math.floor(i/3), (int)Math.floor(j/3)).getValue(i%3,j%3)+ " ";
			}
			System.out.println(line);
		}
	}
	

	/**
	 * Puzzle Begins Here
	 * @param args
	 */
	public static void main(String[] args) {
		SudokuSolver solve = new SudokuSolver();
		File f = new File("TheFile");
		try {
			solve.setPuzzle(f);
		} catch (IOException e) {
			System.out.println("Invalid file");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		solve.printPuzzle();
		//TODO

	}
}

//TODO
/* Time, nodes, 
command line 
mrv,forward - jackie
debug


