
import javax.swing.JFrame;// this package helps us create a jframe object

public class Main {

	public static void main(String[] args) {
		JFrame obj=new JFrame();// this is used to make the outer window , minimize icon, close icon etc
		GamePlay gamePlay=new GamePlay();
		obj.setBounds(10,10,700,600);// the size
		obj.setTitle("Brick Breaker");
		obj.setResizable(false);// if it is resizeable
		obj.setVisible(true);//if it is visible
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//all these r the properties of jframe
		obj.add(gamePlay);
		gamePlay.requestFocus();
		

	}

}