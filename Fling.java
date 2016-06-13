/* package whatever; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

/* Link class stores the valid moves solution */
class Link {
    public int dir;
    public int[][] balls; // = new int[][] balls;
    public int row;
    public int col;
    public Link nextLink;

    public static int[][] deepCopyIntMatrix(int[][] input) {
    if (input == null)
        return null;
    int[][] result = new int[input.length][];
    for (int r = 0; r < input.length; r++) {
        result[r] = input[r].clone();
    }
    return result;
}

    //Print Link data
    public void printLink() {
	    //System.out.print("{" + dir + "," + row + ","+ col+"}\n");
	    if(dir == 0) System.out.println("Fling! " + Fling.map[row][col]  +" UP");
		else if(dir == 1) System.out.println("Fling! " + Fling.map[row][col]+ " DOWN");
		else if(dir == 2) System.out.println("Fling! " + Fling.map[row][col] + " LEFT");
		else if(dir == 3) System.out.println("Fling! " + Fling.map[row][col] + " RIGHT");
	    /*for(int i=0;i<8;i++){
	    		for(int j=0;j<7;j++){
	    			System.out.print(" "+balls[i][j]+" ");
	    		}	
	    		System.out.println();
	    }*/		
    }
    
}

class LinkList {
    private Link first;

    //LinkList constructor
    public LinkList() {
	    first = null;
    }

    //Returns true if list is empty
    public boolean isEmpty() {
	    return first == null;
    }
    
    public Link newnode(){
    	 Link link = new Link();
    	 link.nextLink = first;
	     first = link;
    	 return link;
    }
    //Inserts a new Link at the first of the list
    public Link insert_dir(Link l,int dir) {
	    l.dir = dir;
	    return l;
    }
    //Insert co-ordinates
    public Link insert_cord(Link l, int r, int c){
    	l.row=r;
    	l.col=c;
    	return l;
    }
    
    //Insert array - to trace the movements of balls
    public Link insert_array(Link l,int[][]balls_c){
    	l.balls=l.deepCopyIntMatrix(balls_c);
    	return l;
    }
    
    //Prints balls traced movemnets and postion on applymove
    public void printList() {
	    Link currentLink = first;
	    //System.out.print("List: ");
	    while(currentLink != null) {
		    currentLink.printLink();
		    currentLink = currentLink.nextLink;
	    }
	    System.out.println("");
    }
} 

/* Name of the class has to be "Main" only if the class is public. */
class Fling
{
	int count = 0;
	public static final int ROWS = 8;
	public static final int COLS = 7;
	public static final int UP  = 0;
	public static final int DOWN  = 1;
	public static final int LEFT  = 2;
	public static final int RIGHT  = 3;
	public static final int ALLDIR  = 4;
	public static final int curStep  = 0;
	int[] solution;
	LinkList solutionSteps = new LinkList();
	static int[][]  balls = new int[ROWS][COLS];
	static char[][] map = new char[ROWS][COLS];
	/* int[][] balls = {
					{0,0,0,1,0,0,0}, 
					{0,0,0,0,0,0,0}, 
					{0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0},
					{0,0,0,0,0,1,0},
					{0,0,1,0,1,0,0}}; */
	public void solve(){
	/* for(int i=0; i<ROWS; i++) {
      		for(int j=0; j<COLS; j++)
        		System.out.print(balls[i][j] + " ");
      	   System.out.println();
    } */
    /*check if sum of ball zero */
    if(sumBalls(balls) == 0) System.out.println("No balls placed" + sumBalls(balls));
    else if(MoveRecursive(balls) == false){
        System.out.println("no solution found");
    }
        else {
            //System.out.println("soultion found");
            /* if solution found print linked list which has trace of valid movements dir, row, cols */
            solutionSteps.printList();
        }
    
	}
	/*method: to count number of balls in ball[][]
	@param:  balls[][]  array of balls
	@return: 	    count number*/
	public int sumBalls(int balls[][]){
	    int add=0;
	    for(int i=0;i<ROWS;i++)
		for(int j=0;j<COLS;j++)
			if(balls[i][j]!=0)
				add++;
	return add;
	}
	
	/*method: recursive method to find every possible move recusively in array/ board balls[][]
	@param:  balls[][]  array of balls
	@return: 	    true: if balls count in array = 1 
				    else perform recursive solution and finally if moves not possible return false */
	boolean MoveRecursive(int balls[][]){
	    if(sumBalls(balls) == 1) return true;
	    for(int i = 0;i<ROWS;i++){
			for(int j = 0;j<COLS;j++){
				if(balls[i][j] == 1){
					for(int dir=UP;dir<ALLDIR;dir++){
						/* test if there a ball to move at position (r,c) ? for all possible 4 direction */
						if(getEntry(balls,i,j,dir)){
						   	/*can this ball move in direction dir ? and returns the new instance of balls of array*/
							int[][] move = canMove(balls,i,j,dir);
							int temp_dir = dir;
							//int temp_array;
							if(move != null){ // Imp: to check move case: returned null when balls fopund in adjacent in appymove()
								if(MoveRecursive(move)){ 
									/* save the trace in linked list */
									Link l = solutionSteps.newnode();
									l=solutionSteps.insert_dir(l,temp_dir);
									//l=solutionSteps.insert_array(l,move);
									l=solutionSteps.insert_cord(l, i , j);
									dir = l.dir;									
								return true;
								}
								else{dir = temp_dir;} 
							}
							
						}
						
					}
					
				}
				
			}
	}
	return false;
	}
	/*method: is there a ball to move at possible position ?
	@param:  balls[][]  array of balls
	@param:  i 	rows index
	@param:  j 	column index
	@param:  dir  	direction code
	@return: 	 true if balls has ball in their corresponding  rows or columns else entry false*/
	public boolean getEntry(int[][] balls,int i,int j, int dir){
		// checks at the four direction and returns if it can move.
	if(dir == ALLDIR){
		for(int k = 0;k<ROWS;k++)
			if(balls[k][j] == 1 && k != i)
				return true;
		for(int k = 0;k<COLS;k++)
			if(balls[i][k] ==1 && k != j)
				return true;
		return false;
	}
	else if(dir == UP){
		if(i == 0) return false;
		for(int k=i-1;k > -1;k--)
			if(balls[k][j] == 1)
				return true;
		return false;
	}
	else if(dir == DOWN){
		if(i == ROWS - 1) return false;
		for(int k = i+1;k<ROWS;k++)
			if(balls[k][j] ==1)
				return true;
		return false;
	}
	else if(dir == RIGHT){
		if(j == COLS - 1) return false;
		for(int k=j+1;k<COLS;k++)
			if(balls[i][k] == 1)
				return true;
		return false;
	}
	else if(dir == LEFT){
		if(j == 0) return false;
		for(int k=j-1;k>-1;k--)
			if(balls[i][k] ==1)
				return true;
		return false;
	}
	return false;
	}
	
	public int[][] canMove(int[][] balls, int i , int j, int dir){
		int direction = dir;
		int[][] clonedballs = deepCopyIntMatrix(balls);
		return applyNextMove(clonedballs, i, j, direction, true);
	}
	
	public static int[][] deepCopyIntMatrix(int[][] input) {
	    if (input == null)
	        return null;
	    int[][] result = new int[input.length][];
	    for (int r = 0; r < input.length; r++) {
	        result[r] = input[r].clone();
	    }
	    return result;
	}
	
	/*method: make ball move to next possible position. Also record this move in history stored in newB
	@param:  balls[][]  array of balls
	@param:  i 	rows index
	@param:  j 	column index
	@param:  dir  	direction code
	@param   firstmove we pass true for first call after that false
	@return: 	    array of new balls after the moves*/
	public int[][] applyNextMove(int[][] balls,int i, int j, int dir, boolean firstMove){
		if(dir == UP){
		if(firstMove){
			if(balls[i-1][j] ==1) return null;
			else{
				int nextBall = -1;
				for(int k = i-1;k> -1;k--){
					if(balls[k][j] ==1){
						nextBall = k;
						break;
					}
				}
				if(nextBall != -1){
					balls[i][j] = 0;
					balls[nextBall+1][j] = 1;
					return applyNextMove(balls,nextBall,j,dir, false);
				}
				else return null;
			}
		}
		if(!firstMove){
			if(i == 0){
				balls[i][j] = 0;
				return balls;
			}
			else if(balls[i-1][j] == 1){
				return applyNextMove(balls,i-1,j,dir, false);
			}
			else{
				if(!getEntry(balls, i, j, dir)){
					balls[i][j] = 0;
					return balls;
				}
				else{
					return applyNextMove(balls,i,j,dir, true);
				}
			}
		}
	}
	else if(dir == DOWN){
		if(firstMove){
			if(balls[i+1][j] == 1) return null;
			else{
				int nextBall = -1;
				for(int k = i+1;k< ROWS;k++){
					if(balls[k][j] == 1){
						nextBall = k;
						break;
					}
				}
				if(nextBall != -1){
					balls[i][j] = 0;
					balls[nextBall-1][j] = 1;
					return applyNextMove(balls,nextBall,j,dir, false);
				}
				else return null;
			}
		}
		if(!firstMove){
			if(i == ROWS-1){
				balls[i][j] = 0;
				return balls;
			}
			else if(balls[i+1][j] == 1){
				return applyNextMove(balls,i+1,j,dir, false);
			}
			else{
				if(!getEntry(balls, i, j, dir)){
					balls[i][j] = 0;
					return balls;
				}
				else{
					return applyNextMove(balls,i,j,dir, true);
				}
			}
		}
	}
	else if(dir == RIGHT){
		if(firstMove){
			if(balls[i][j+1] ==1) return null;
			else{
				int nextBall = -1;
				for(int k = j+1;k< COLS;k++){
					if(balls[i][k] == 1){
						nextBall = k;
						break;
					}
				}
				if(nextBall != -1){
					balls[i][j] = 0;
					balls[i][nextBall-1] = 1;
					return applyNextMove(balls,i,nextBall,dir, false);
				}
				else return null;
			}
		}
		if(!firstMove){
			if(j == COLS-1){
				balls[i][j] = 0;
				return balls;
			}
			else if(balls[i][j+1] == 1){
				return applyNextMove(balls,i,j+1,dir, false);
			}
			else{
				if(!getEntry(balls, i, j, dir)){
					balls[i][j] = 1;
					return balls;
				}
				else{
					return applyNextMove(balls,i,j,dir, true);
				}
			}
		}
	}
	else if(dir == LEFT){
		if(firstMove){
			if(balls[i][j-1] == 1) return null;
			else{
				int nextBall = -1;
				for(int k = j-1;k> -1;k--){
					if(balls[i][k] == 1){
						nextBall = k;
						break;
					}
				}
				if(nextBall != -1){
					balls[i][j] = 0;
					balls[i][nextBall+1] = 1;
					return applyNextMove(balls,i,nextBall,dir, false);
				}
				else return null;
			}
		}
		if(!firstMove){
			if(j == 0){
				balls[i][j] = 0;
				return balls;
			}
			else if(balls[i][j-1] == 1){
				return applyNextMove(balls,i,j-1,dir, false);
			}
			else{
				if(!getEntry(balls, i, j, dir)){
					balls[i][j] = 0;
					return balls;
				}
				else{
					return applyNextMove(balls,i,j,dir, true);
				}
			}
		}
	}
	return balls;
	}
	
	
	public static void main (String[] args) throws java.lang.Exception
	{
		// your code goes here
		
		Scanner sc = new Scanner(System.in);
		String data = "";
		int count = 0;
		for (int i = 0; i < ROWS; i++) {
		    if (sc.hasNext()) {
		        data = sc.next();
		        count = 0;
		    } else {
		        break;
		    }
		    for (int j = 0; j < COLS; j++){
		    map[i][j] = data.charAt(count++);
		    if(map[i][j] == '.')  balls[i][j] = 0;
                else{
                	balls[i][j] = 1;	
                } 
		    }
		}
		Fling fling = new Fling();
		fling.solve();
	}
}
