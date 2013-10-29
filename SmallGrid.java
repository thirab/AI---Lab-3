import java.util.ArrayList;
import java.util.List;


public class SmallGrid {

	public int[][] gridArray= {{0,0,0},{0,0,0},{0,0,0}};
	private int xCoor;
	private int yCoor;
	public SmallGrid(int x, int y){
	//list of available values in the cell	
	//x and y coordinate on the grid
	xCoor = x;
	yCoor = y;
	}
	
	public void setValue(int x, int y, int change){
		gridArray[x][y]=change;
		
	}
	public int getValue(int x, int y){
		return gridArray[x][y];
	}
	public boolean valid(){
		boolean valid = true;
		List values = new ArrayList(); 
		for(int i = 0; i<3;i++){
			for(int j = 0; j<3; j++){
				if(values.contains(gridArray[i][j]) || gridArray[i][j]==0){ 
					valid=false;
				}else{
					values.add(gridArray[i][j]);
				}
			}
		}
		
		return valid;
	}
	public List notPossibleNums(){
		List notPossible = new ArrayList();
		for(int i = 0; i<3;i++){
			for(int j = 0; j<3; j++){
				if(gridArray[i][j] != 0){
					notPossible.add(gridArray[i][j]);
				}
			}
		}
		return notPossible;
	}
		//get gridX, gridY
		public int getGridX (){return xCoor;}
		public int getGridY (){return yCoor;}
		
}
