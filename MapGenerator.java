
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
	public int map[][];//creates bricks
	public int brickWidth;
	public int brickHeight;
	
	public MapGenerator(int row,int col) {
		map=new int[row][col];
		for(int i=0;i<map.length;i++)			//map.length → number of rows   map[0].length → number of columns
		{
			for(int j=0;j<map[0].length;j++) {
				// iterate through the columns
				map[i][j]=1;		// add 1 in each ele of 2d array, 1 determines that the brick is not interact with ball 
				//this brick will be determined on the screen; if its not there on the screen then it will be 0
			}
		}
		brickWidth=540/col;
		brickHeight=150/row;
	}
		
		public void draw(Graphics2D g)
		{
			for(int i=0;i<map.length;i++)			//map.length → number of rows   map[0].length → number of columns
			{
				for(int j=0;j<map[0].length;j++) {
					if(map[i][j]>0)		// if it is true , then draw a brick in that position 
					{
						g.setColor(Color.red);
						g.fillRect(j*brickWidth+80,i*brickHeight+50, brickWidth, brickHeight);		//draws brick
						// x=col*width --moves it 80 pixels to right
						//y=row*height--move it 50 pixcels down
						
						// the above statements just create a big red block, to add borders, do the following:
						
						g.setStroke(new BasicStroke(3));
						g.setColor(Color.black);
						g.drawRect(j*brickWidth+80,i*brickHeight+50, brickWidth, brickHeight);
						
						//This sets the thickness of the lines you are going to draw. 3 means the border will be 3 pixels thick.

						//This draws only the outline of a rectangle at the same position where the brick was filled.

						//So:

						//fillRect (red) → draws the inside of the brick

						//drawRect (black) → draws the border of the brick

						//This makes each brick look clean and separate from others.
					}
				}
		}
	}
		public void setBrickValue(int value,int row,int col)	//checks the intersedction of bricks and ball; put it in the same func
		//containing the intersection of paddle and ball i.e in the gameplay class
		{
			map[row][col]=value;
			
		}
}