

import java.awt.Color;
import java.util.Random;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;//JPanel is like a canvas that contains the ball, the sliders etc
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//port java.util.Timer;
import javax.swing.Timer;//if we have a timer class with parameters, then we import this package else we import the util paclage of the timer class

import javax.swing.JPanel;

public class GamePlay  extends JPanel implements KeyListener,ActionListener{
	private boolean play=false;//the game shdnt start or play by itself
	//private int score=0;
	private int tempScore = 0; // score accumulated in current level only

	private int levelBaseScore = 0;

	private int level = 1;
	private int ballSpeed = 2;
	private Random rand = new Random();

	private float ballXdir;
	private float ballYdir;

	
	private int totalbricks=21;
	
	private Timer timer;//this is the timer class ie till when can it run
	private int delay=8;//speed given to timer is int
	
//properties for x and y axis of slider and ball
	private int playerX=310;//starting posistion of slider
	
	private int ballposX=120;
	private int ballposY=350;
	


	
	// we need to create a object for mapgenerator class
	
	private MapGenerator map;
	
	public GamePlay() {
		map=new MapGenerator(3,7);
		
		
		addKeyListener(this);//to work with keylisternes we need this : listends to the key inputs
		setFocusable(true);//allows the key events to happen, when we press lef arraow key the paddle shd move to the left
		setFocusTraversalKeysEnabled(false);//prevents arrow from changing its focus; does not allow it to perfrom other tasks
		timer =new Timer(delay,this);//creates the game
		timer.start();//strats the game loop-------------we are getting error before therfore comment it 
		setRandomBallDirection();

		// if we put the timer in comments in the run console screen : even if we press the arrow keys it will not move
		//but if we do import javax.swing.timer--no error so code will work , paddle shd move;   timer moves the paddle
		
	}
	private void setRandomBallDirection() {
	    int xDir = rand.nextBoolean() ? 1 : -1;

	    float speed = level+1;   // level 1 → 2, level 2 → 3

	    ballXdir = xDir * speed;
	    ballYdir = -speed;
	}


	private void startLevel(boolean resetScore) {
	    play = false;                 // wait for arrow key

	    ballposX = 120;
	    ballposY = 350;
	    playerX = 310;

	    setRandomBallDirection();     // fully random direction

	    

	    // bricks depend on level
	    int rows = 3 + level;
	    int cols = 7;

	    map = new MapGenerator(rows, cols);
	    totalbricks = rows * cols;

	    if (resetScore) 
	       // score = 0;
	        levelBaseScore = 0;
	    
	    tempScore=0;

	    requestFocusInWindow();
	    repaint();
	}



	
	public void paint(Graphics g){//we can draw differnt shaper:slider,ball,tiles
		//background
//g.setColor(Color.black);//this will set the entire screen black; here we r taking 1 1 since if we take 0 it wld overlap with the borders
//g.fillRect(1, 1,680,592);//position- 1,1 : size=692 : height=592
		
		//borders
//g.setColor(Color.yellow);
//g.fillRect(0, 0, 3,592);//left border
//g.fillRect(0, 0, 692,3);//top border
//g.fillRect(691,0,3,592);///not add  border at the bottom, why?cause we want our ball to come down;right border
		
		// Background
		g.setColor(Color.black);
		g.fillRect(3, 3, 680, 586);
		
		//drawing map
		map.draw((Graphics2D)g);

		// Borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);     // left
		g.fillRect(0, 0, 692, 4);    // top
		g.fillRect(683, 0, 3, 592);   // right

		
		//to print score
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD,25));		//25 is size
		g.drawString("SCORE: "+(levelBaseScore+tempScore),530,30);
		
		//paddle
		g.setColor(Color.green);
		g.fillRect(playerX,550,100,8);// horizontal pos,vertical pos(fixed near bottom), width,height
		
		//ball
		g.setColor(Color.yellow);//x coordiante, y coordiante, 20*20 is size of the ball
		g.fillOval(ballposX,ballposY,20,20); //x-coordinate,y-coordinate,20*20 ball size; also we put oval cause the shape we want is oval
		
		//to display level
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("LEVEL: " + level, 30, 30);

		
		//if totalbricks are 0
		if(totalbricks<=0)
		{
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.green);
			g.setFont(new Font("serif",Font.BOLD,30));		
			g.drawString("YOU WON: "+(levelBaseScore+tempScore),190,300);
			
			g.setFont(new Font("serif",Font.BOLD,20));		
			g.drawString("Press Enter to Restart ",230,350);// 
		}
		
		
		// if the ball goes below panel
		if(ballposY>570)
		{
			play=false;
			//tempScore=0; //rollback temp score
			 
			
			g.setColor(Color.green);
			g.setFont(new Font("serif",Font.BOLD,30));		
			g.drawString("GAME OVER, SCORES: "+(tempScore+levelBaseScore),190,300);
			
			g.setFont(new Font("serif",Font.BOLD,20));		
			g.drawString("Press Enter to Restart ",230,350);// but we can only see this output , for computer to restart after pressing enter fo to keypressed
			
			//tempScore=0;
		}
		
		g.dispose();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {//automatically called when timer is called
		//have a delay of 8ms i.e the speed of ball is 8
		//we have a ball, to move the ball we need to add a code
		// all these methods in interfaces needs to be implemented in the gameplay classs
		timer.start();
		
		//to move the ball
		if(play)//ball moves only when game is active
		{	
			//when the paddle and the ball intersect it shd bounce offf: the code will be
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8)))// creates a rectangle that is both a ball and a paddle
			{
				ballYdir=-ballYdir;//onyl reversing y direction is sufficient, why? the ball will keep moving in the x direc but its y direc changes
			}
			
			//to check if brick and ball have intersected
			//first go thrugh all the bricks
		A:	for(int i=0;i<map.map.length;i++) //first map is the object; snd map is the variable name in the mapgenerator class
			{
					for(int j=0;j<map.map[0].length;j++)
					{
						if(map.map[i][j]>0)		// if map of a particular pos>0 then detect the intersection
						{
							int brickX=j*map.brickWidth+80;
							int brickY=i*map.brickHeight+50;
							int brickWidth=map.brickWidth;
							int brickHeight=map.brickHeight;
							
							//create a rect ard that brick
							
							Rectangle rect=new Rectangle(brickX,brickY,brickWidth,brickHeight);		//brickarea
							Rectangle ballRect=new Rectangle(ballposX,ballposY,20,20);		//ballarea
							Rectangle brickRect=rect;
							
							if(ballRect.intersects(brickRect))		//if areas intersect
							{
								map.setBrickValue(0, i, j);		//delete brick
								totalbricks--;
								tempScore+=5;//score of this level
								//score=levelBaseScore+tempScore;//show total score on screen
								
								// creates a problem for left and right intersection
								if(ballposX+19<= brickRect.x || ballposX+1 >= brickRect.x+brickRect.width)
								{
									ballXdir=-ballXdir;
								}
								else
									ballYdir=-ballYdir;
								
								break A; // after removing the brick the compiler has to come out of this loop
							}
						}
					}
			
			}
		if (totalbricks == 0) { // level cleared
			levelBaseScore+=tempScore;//add tempscore permenentaly to levelbasescore
			tempScore=0;//reset tempscore for next level
			//score=levelBaseScore;//display only base sore now

		    level++;            // go to next level
		    startLevel(false);  // start next level
		}



			ballposX+=ballXdir;// increase ball position . if dir is pos it adds else subtracts
			ballposY+=ballYdir;// increases ball y posistion ; positive-upwards negative-downwards
			if(ballposX<0)//left border
			{
				ballXdir=-ballXdir;// move is -x direc
			}
			if(ballposY<0)//top border
			{
				ballYdir=-ballYdir;// move in -y direc
			}
			if(ballposX>662)//right border
			{
				ballXdir=-ballXdir;
			}
		}
		repaint();//calls the paint method again; why? when we moveright or left through arrow keys we need to draw the canvas again
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e)
	{
	    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
	        if (playerX < 600) {
	            moveRight();   // THIS starts the game
	        }
	    }

	    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
	        if (playerX > 10) {
	            moveLeft();    // THIS starts the game
	        }
	    }

	    if (e.getKeyCode() == KeyEvent.VK_ENTER) {//restart
	        if (!play) {
	            // Prepare SAME level, DO NOT start
	        	tempScore=0;				//reset current level score
	            //score = levelBaseScore;   // show total score from completed
	            startLevel(false);        // reset bricks & ball
	            play = false;             
	            repaint();
	        }
	    }
	}

	public void moveRight() {
	    if (!play) {
	        play = true;   // game starts ONLY here
	    }
	    playerX += 20;
	}

	public void moveLeft() {
	    if (!play) {
	        play = true;   //game starts ONLY here
	    }
	    playerX -= 20;
	}
	@Override
	public void keyReleased(KeyEvent e) {}
}