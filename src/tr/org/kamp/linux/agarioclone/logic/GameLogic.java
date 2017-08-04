package tr.org.kamp.linux.agarioclone.logic;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

import tr.org.kamp.linux.agarioclone.model.Chip;
import tr.org.kamp.linux.agarioclone.model.Difficulty;
import tr.org.kamp.linux.agarioclone.model.Enemy;
import tr.org.kamp.linux.agarioclone.model.GameObject;
import tr.org.kamp.linux.agarioclone.model.Mine;
import tr.org.kamp.linux.agarioclone.model.Player;
import tr.org.kamp.linux.agarioclone.view.GameFrame;
import tr.org.kamp.linux.agarioclone.view.GamePanel;

public class GameLogic {

	private Player player;
	
	//ArrayListsDeclarations:
	private ArrayList<GameObject> gameObjects;
	ArrayList<GameObject> chipsToRemove=new ArrayList<GameObject>();
	ArrayList<GameObject> minesToRemove=new ArrayList<GameObject>();
	ArrayList<GameObject> enemiesToRemove= new ArrayList<GameObject>();
				
	private GameFrame gameFrame;
	private GamePanel gamePanel;
	private Random random=new Random();
	private boolean isGameRunning;
	private int xTarget;
	private int yTarget;
	
	
	public GameLogic(String playerName, Color selectedColor,Difficulty difficulty) {
		player=new Player(10, 10,12, selectedColor, 7,playerName);
		
		/**
		 * ArrayLists:
		 */
		gameObjects= new ArrayList<GameObject>();
		chipsToRemove=new ArrayList<GameObject>();
		minesToRemove=new ArrayList<GameObject>();
		enemiesToRemove=new ArrayList<GameObject>();
		
		gameObjects.add(player);
	/**
	 * difficulty settings:	
	 */
		switch (difficulty) {
		case EASY:
			fillChips(20);
			fillMines(3);
			fillEnemies(3);
			break;

		case NORMAL:
			fillChips(20);
			fillMines(6);
			fillEnemies(5);
			break;

		case HARD:
			fillChips(30);
			fillMines(10);
			fillEnemies(10);
			
			break;

	
		default:
			break;
		}
		
		
		gameFrame=new GameFrame();
		gamePanel=new GamePanel(gameObjects);
		
		addMouseEvents();
		
	}
	
	/**
	 * starts game and sets thread period
	 */
	private void startGame() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(isGameRunning) {
					movePlayer();
					moveEnemies();
					addnewObjects();
					checkCollisions();
					gamePanel.repaint();
					try {
						Thread.sleep(50);
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			
			}
		}).start();
		
	}
	
	
	/**
	 * here checks collisions by using intersects method. 
	 */
	private void checkCollisions() {
	
	
		for (GameObject gameObject : gameObjects) {
			if(player.getRectangle().intersects(gameObject.getRectangle())) {
				if(gameObject instanceof Chip) {
					player.setRadius(player.getRadius()+ gameObject.getRadius());
					//gameObjects.remove(gameObject);
					chipsToRemove.add(gameObject);	
				}
				
				if(gameObject instanceof Mine) {
					player.setRadius((int)player.getRadius()/2 );
					minesToRemove.add(gameObject);
				}
				
				if(gameObject instanceof Enemy) {
					if(player.getRadius()> gameObject.getRadius()) {
						player.setRadius(player.getRadius()+gameObject.getRadius());
						enemiesToRemove.add(gameObject);
					}else if(player.getRadius()<gameObject.getRadius()) {
						gameObject.setRadius(gameObject.getRadius() + player.getRadius());
						isGameRunning=false;
					}
					
				}

			
			}
			/**
			 * here checks collisions for enemy.
			 */
			if(gameObject instanceof Enemy) {
				Enemy enemy=(Enemy) gameObject;
				for (GameObject gameObject2 : gameObjects) {
					if(enemy.getRectangle().intersects(gameObject2.getRectangle())) {
						if(gameObject2 instanceof Chip) {
							enemy.setRadius(enemy.getRadius()+ gameObject2.getRadius());
							//gameObjects.remove(gameObject);
							chipsToRemove.add(gameObject2);	
						}
						
						if(gameObject2 instanceof Mine) {
							enemy.setRadius((int)enemy.getRadius()/2 );
							minesToRemove.add(gameObject2);
						}
					}
				}
				
			
			}
			
			
			
	
		}
		/**
		 * arraylists for objects to remove
		 */
		gameObjects.removeAll(chipsToRemove);	
		gameObjects.removeAll(minesToRemove);	
		gameObjects.removeAll(enemiesToRemove);
	}
	
	
	/**
	 * here calls the methods which add new objects. the keyword synchronized is used.
	 */
	private synchronized void addnewObjects() {
		fillChips(chipsToRemove.size());
		chipsToRemove.clear();
		
		fillMines(minesToRemove.size());
		minesToRemove.clear();
		
		
	}
	
	
	/**
	 * 
	 * @param count is number of objects to add.
	 * these methods adds new objects.
	 */
	private void fillChips(int count) {
		for(int i=0;i<count;i++) {
			gameObjects.add(new Chip (random.nextInt(1800),random.nextInt(900),10,Color.BLUE));
		}
	}
	
	private void fillMines(int count) {
		for(int i=0;i<count;i++) {
			Mine mine= new Mine(random.nextInt(1800),random.nextInt(900) , 15, Color.ORANGE);
			while(player.getRectangle().intersects(mine.getRectangle())) {
				mine.setX(random.nextInt(1800));
				mine.setY(random.nextInt(900));
			}
		
			gameObjects.add(mine);
		}	
	}
	
	private void fillEnemies(int count) {
		for(int i=0;i<count;i++) {
			Enemy enemy=new Enemy(random.nextInt(1000), random.nextInt(1000), (random.nextInt(10)+10), Color.RED,5);
			while(player.getRectangle().intersects(enemy.getRectangle())) {
				enemy.setX(random.nextInt(1000));
				enemy.setY(random.nextInt(1000));
			}
			gameObjects.add(enemy);
		}
	}

	/**
	 * this method starts game.
	 */
	public void startApplication() {
		gameFrame.setContentPane(gamePanel);
		isGameRunning=true;
		gameFrame.setVisible(true);
		startGame();
		
	}
	
	/**
	 * sets player moves. decides new direction of player.
	 */
	private void movePlayer() {
		if(xTarget>player.getX()) {
			player.setX(player.getX() + player.getSpeed());
			
		}else if(xTarget<player.getX()) {
			player.setX(player.getX() - player.getSpeed());
		}else {
			
		}
		
		if(yTarget>player.getY()) {
			player.setY(player.getY() + player.getSpeed());
			
		}else if(yTarget<player.getY()) {
			player.setY(player.getY() - player.getSpeed());
		}		
	}
	

	/**
	 * sets enemy moves. decides new direction of enemy considering radius of both player and enemy. 
	 * if radius of enemy is bigger than players, enemy tries to catch.
	 * otherwise, it runs.
	 */
	private void moveEnemies() {
		for (GameObject gameObject : gameObjects) {
			if(gameObject instanceof Enemy) {
				Enemy enemy=(Enemy) gameObject;
				if(enemy.getRadius()<player.getRadius()) {
					//run
					int distance= (int) Point.distance(player.getX(), player.getY(), enemy.getX(), enemy.getY());
					
					int newX=enemy.getX()+enemy.getSpeed();
					int newY= enemy.getY()+ enemy.getSpeed();
					if(calculateNewDistanceToEnemy(enemy,distance,newX,newY)) {
						continue;
					}
					
					newX=enemy.getX()+enemy.getSpeed();
					newY= enemy.getY()-enemy.getSpeed();
					if(calculateNewDistanceToEnemy(enemy,distance,newX,newY)) {
						continue;
					}
					newX=enemy.getX()-enemy.getSpeed();
					newY= enemy.getY()+enemy.getSpeed();
					if(calculateNewDistanceToEnemy(enemy,distance,newX,newY)) {
						continue;
					}
					
					newX=enemy.getX()-enemy.getSpeed();
					newY= enemy.getY()-enemy.getSpeed();
					if(calculateNewDistanceToEnemy(enemy,distance,newX,newY)) {
						continue;
					}
					
					
				}else {
					//catch
					if(player.getX()>enemy.getX()) {
						enemy.setX(enemy.getX() + enemy.getSpeed());
						
					}else if(player.getX()<enemy.getX()) {
						enemy.setX(enemy.getX() - enemy.getSpeed());
					}else {
						
					}
					
					if(player.getY()>enemy.getY()) {
						enemy.setY(enemy.getY() + enemy.getSpeed());
						
					}else if(player.getY()<enemy.getY()) {
						enemy.setY(enemy.getY() - enemy.getSpeed());
					}
				}
				
			}
		}
		
	}
	
	
	private boolean calculateNewDistanceToEnemy(Enemy enemy,int distance,int x,int y) {
		int newDistance=(int) Point.distance(player.getX(), player.getY(), x, y);
		if(newDistance>distance) {
			
			enemy.setX(x);
			enemy.setY(y);
			return true;	
		}
		return false;
	}
	
	private void addMouseEvents() {
		gameFrame.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				xTarget=e.getX();
				yTarget=e.getY();
	
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	
			
			
		
	
	}
	
}
