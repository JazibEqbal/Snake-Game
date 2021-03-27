import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
public class GamePanel extends JPanel implements ActionListener{

	static final int Screen_width=600;
	static final int Screen_height=600;
	static final int unit_size=25;
	static final int Game_units=(Screen_width*Screen_height)/unit_size;
	final int x[]=new int[Game_units];
	final int y[]=new int[Game_units];
	Timer timer;
	Random random;
	char direction='R' ;
	int bodyparts=6;
	boolean running=false;
	int appleX;
	int appleY;
	static final int delay=85;
	int applesEaten;
	
	GamePanel(){
		random=new Random();
		this.setPreferredSize(new Dimension(Screen_width,Screen_height));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();	
	}
         public void startGame() {
	           newApple();
	           running=true;
	           timer=new Timer(delay, this);
	           timer.start();
	           
       }            
          public void newApple() {
        	 appleX=random.nextInt((int)(Screen_width/unit_size))*unit_size;    //apple position of x coordinate 
        	 appleY=random.nextInt((int)(Screen_height/unit_size))*unit_size;   // apple position of y coordinate 
          }
          
          public void paintComponent(Graphics g) {
        	  super.paintComponent(g);
        	  draw(g);
          }
           public void draw(Graphics g) {
        	   if(running) {
        		   g.setColor(Color.RED);
            	   g.fillOval(appleX, appleY,unit_size , unit_size);      //drawing apple randomly.
            	   
            	   for(int i=0;i<bodyparts;i++) {
            		   if(i==0) {
            			   g.setColor(Color.GREEN);
                		   //g.fillRect(x[i],y[i],unit_size,unit_size); 
            			   g.fillRoundRect(x[i],y[i],unit_size,unit_size,20,20);  //for head of snake
            		   }    		   
            		   else {
            			   //g.setColor(Color.GREEN);
            			   g.setColor(new Color(random.nextInt(255),random.nextInt(255), random.nextInt(255)));
            		      // g.fillRect(x[i],y[i],unit_size,unit_size);     //for body parts of snake
            			   g.fillRoundRect(x[i],y[i],unit_size,unit_size,20,20);
            		   }            			       
            	   }
            	   g.setColor(Color.GREEN);
            	   g.setFont(new Font("Ink Free",Font.PLAIN,50));
            	   FontMetrics metrics1 = getFontMetrics(g.getFont());    //displaying current score in center
           		   g.drawString("Score: "+applesEaten, (Screen_width - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        	   }
        	   else {
        		   gameOver(g);
        	   }
           }
           
           
       public void move() {
    	   
           for(int i=bodyparts;i>0;i--) {
                  x[i]=x[i-1];
                  y[i]=y[i-1];                   //for body parts and head
           }
                  switch(direction) {
                  case'R':
                	  x[0]=x[0]+unit_size;
                	  break;
                  case 'L':
                	x[0]=x[0]- unit_size;               //shifting snake a unit size
                	break;
                  case 'U':
                	  y[0]=y[0]-unit_size;
                	  break;
                  case 'D':
                	  y[0]=y[0]+unit_size;
                	  break;
                  }
            	 }
             public void checkCollisions() {
            	 for(int i=bodyparts;i>0;i--) {
            		if((x[0]== x[i]) && (y[0]==y[i])) {
            			running=false;                                 //for body collision
            		}
            	 }
            	 if(x[0]<0 ) {
            		 running=false;
            	 }
            	 if(x[0]>Screen_width) {
            		 running=false;
            	 }
            	 if(y[0]<0) {
            		 running=false;
            	 }
            	 if(y[0]>Screen_height) {
            		 running=false;
            	 }
            	 if(!running) {
            		 timer.stop();
            	 }
             }
             
  public void checkApple() {
	   if((x[0]==appleX) && (y[0] ==appleY)) {
		   bodyparts++;
		   applesEaten++;
		   newApple();
	   }
            	 
             }
         public void gameOver(Graphics g) {
        	 g.setColor(Color.RED);
        	 g.setFont(new Font("Tahoma",Font.BOLD,60));
        	 FontMetrics metrics2 = getFontMetrics(g.getFont());
     		g.drawString("Game Over", (Screen_width - metrics2.stringWidth("Game Over"))/2, Screen_height/2);    //display game over in the centre.
     		
     		
     		 g.setFont(new Font("Ink Free",Font.BOLD,50));
     		FontMetrics metrics1 = getFontMetrics(g.getFont());
    		g.drawString("Score: "+applesEaten, (Screen_width- metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());   //score update
        	 
         }
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(running) {
			move();
			checkApple();
			checkCollisions();			
		}
          repaint();
             
	}
	public class MyKeyAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction !='R') {
					direction='L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction !='L') {
					direction='R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction !='D') {
					direction='U';	
			}
				break;
			case KeyEvent.VK_DOWN:
				if(direction !='U') {
					direction='D';	
		}
				break;
	}
		}
	}
}
