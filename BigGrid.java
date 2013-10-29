
public class BigGrid {

	public SmallGrid[][] game;
	public BigGrid(){
		//Initialize the puzzle
		for(int i =0; i<3; i++){
			for(int j=0; j<3; j++){
				game[i][j]= new SmallGrid(i,j);
			}
		}
	}
	
	public SmallGrid getSmallGrid(int x, int y){
		return game[x][y];
	}
}
