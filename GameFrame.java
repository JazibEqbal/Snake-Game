import javax.swing.*;
public class GameFrame extends JFrame{

	GameFrame(){
		this.add(new GamePanel());
		this.setVisible(true);
		this.setTitle("Snake Game");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);		
	}
}
