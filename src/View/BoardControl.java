package View;

import java.beans.EventHandler;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import Model.Board;
import Model.BombPoints;
import Model.Ghost;
import Model.Location;
import Model.PeckPoints;
import Model.Question;
import Utils.GameState;
import Utils.Level;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.skin.TextInputControlSkin.Direction;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BoardControl implements Initializable {
	

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane pane;
    
    private Location PacmanLocation = new Location(300, 570);
    private Location BlueGhostLocation = new Location(270, 240); // the location on the matrix is [9][8]
    private Location PinkGhostLocation = new Location(330, 240); // the location on the matrix is [11][8]

    private Direction red_movingAt= Direction.UP, blue_movingAt=Direction.UP, pink_movingAt=Direction.LEFT ;

	//get board from board model( a matrix that describes the board- see Model\Board)
	int[][] matrix1 = Board.getInstance().matrixBoard_level1;
	int[][] matrix = Board.getInstance().putRandomQuestion(matrix1);

	//object size on the board is set to 30 
    int ObjectSize=30;
	private Timeline timeline;
	private Timeline timeline_redGhost;

    private GameState gameState;
    
    private Scene scene ;
	private Direction newDir;

	KeyFrame pacman_keyFrame;

	public int score;
	
	public Level level;

	private ArrayList<Circle> peckpointlist = new ArrayList<Circle>() ;
	private ArrayList<Rectangle> wallList = new ArrayList<Rectangle>() ;
	private ArrayList<ImageView> bonusList = new ArrayList<ImageView>() ;
	private ArrayList<ImageView> packmanMoves = new ArrayList<ImageView>() ;
	
	private ImageView redGhost= new ImageView();
	private ImageView blueGhost= new ImageView();
	private ImageView pinkGhost= new ImageView();



	/**
	 * Variable to control PackMan speed
	 */
	private int Speed;
	private AnimationTimer time;

	private KeyFrame retrunPeckPoints;

	private Timeline timeline2;

	protected KeyFrame ghosts_keyFrame;

	protected Timeline timeline3;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		pane.setStyle("-fx-background-color : black") ;//set background to black
		fillBoard();
		Speed = 300;
		gameState = GameState.Paused;
		resume();
		pressedKeys(pane);

	}

	private boolean isWall(Direction newDir, double x, double y)
	{
		if(newDir == Direction.RIGHT)
			x=x+30;
		if(newDir == Direction.LEFT)
			x=x-30;
		if(newDir == Direction.UP)
			y= y-30;
		if(newDir == Direction.DOWN)
			y=y+30;
		for(int n=0; n<wallList.size(); n++)
		{
			if(wallList.get(n).getX()== x && wallList.get(n).getY()==y)
				return true;
		}
		return false;
	}


	private void movement() {
		if(isWall(newDir, PacmanLocation.getRow(), PacmanLocation.getColumn()) == false) {
			if(newDir == Direction.RIGHT)
			{
				movePackman(newDir,PacmanLocation.getRow(), PacmanLocation.getColumn(), PacmanLocation.getRow()+30, PacmanLocation.getColumn());
				PacmanLocation.setRow((PacmanLocation.getRow())+30);
			
			}
			if(newDir == Direction.LEFT)
			{
				movePackman(newDir,PacmanLocation.getRow(), PacmanLocation.getColumn(), PacmanLocation.getRow()-30, PacmanLocation.getColumn());
				PacmanLocation.setRow((PacmanLocation.getRow())-30);
			}
			
			if(newDir == Direction.UP)
			{
				movePackman(newDir,PacmanLocation.getRow(), PacmanLocation.getColumn(), PacmanLocation.getRow(), PacmanLocation.getColumn()-30);
				PacmanLocation.setColumn(PacmanLocation.getColumn()-30);
			}
			if(newDir == Direction.DOWN)
			{
				movePackman(newDir,PacmanLocation.getRow(), PacmanLocation.getColumn(), PacmanLocation.getRow(), PacmanLocation.getColumn()+30);
				PacmanLocation.setColumn(PacmanLocation.getColumn()+30);
			}
		}
	}
	
	private void pressedKeys(Pane pane2) {
		pane.setOnMouseClicked(new javafx.event.EventHandler<Event>() {		//get scene 

		
			@Override
			public void handle(Event arg0) {
				setScene(pane.getScene());
				gameState= GameState.Started;
				scene.setOnKeyPressed(new javafx.event.EventHandler<KeyEvent>() {	
					@Override
					public void handle(KeyEvent event) {
						switch(event.getCode()) 
						{
							case UP : newDir = Direction.UP ;
									  break ;

							case DOWN : newDir = Direction.DOWN ;
										break ;

							case LEFT : newDir = Direction.LEFT ;
										break ;

							case RIGHT : newDir = Direction.RIGHT ;
										 break ;
						}
						
				System.out.println(scene);			
				System.out.println(newDir);
				
			}
					
			});		
				
				

				
				if(level == level.easy)	
					Speed= 300;
				if(level == level.medium)	
					Speed= 200;
				if(level == level.hard)	
					Speed= 100;
	
				pacman_keyFrame = new KeyFrame(Duration.millis(Speed), e->
				{
					
					if(caughtPacman((int) redGhost.getX(),(int)redGhost.getY(),PacmanLocation.getRow(),PacmanLocation.getColumn())==true)
					{
						System.out.println("Reeeed BYEEEEE");
						pane.isDisable();
					}
					if(caughtPacman((int) blueGhost.getX(),(int)blueGhost.getY(),PacmanLocation.getRow(),PacmanLocation.getColumn())==true)
					{
						System.out.println("Bluee BYEEEEE");
						pane.isDisable();
					}
					if(caughtPacman((int) pinkGhost.getX(),(int)pinkGhost.getY(),PacmanLocation.getRow(),PacmanLocation.getColumn())==true)
					{
						System.out.println("pinkkk BYEEEEE");
						pane.isDisable();
					}
					movement();
				});
				timeline = new Timeline(pacman_keyFrame) ;
				timeline.setCycleCount(Timeline.INDEFINITE) ;
				timeline.play() ;
				
				
				
				ghosts_keyFrame = new KeyFrame(Duration.millis(Speed+10), e->
				{
				 moveGhost1();
				 moveGhost2();
				 moveGhost3();
				 
				});
				timeline3 = new Timeline(ghosts_keyFrame) ;
				timeline3.setCycleCount(Timeline.INDEFINITE) ;
				timeline3.play() ;
			}
		
				});
		
		
				}	
	/**
	 * will check if the ghost has caught pacman 
	 * @param x_ghost
	 * @param y_ghost
	 * @param x_pacMan
	 * @param y_pacMan
	 * @return
	 */
		public Boolean caughtPacman(int x_ghost, int y_ghost , int x_pacMan,int y_pacMan)		
		{		
			if((x_ghost- ObjectSize == x_pacMan && y_ghost == y_pacMan) || (x_ghost+ ObjectSize == x_pacMan && y_ghost == y_pacMan))
				return true ;
			if((x_ghost == x_pacMan && y_ghost-ObjectSize == y_pacMan) || (x_ghost == x_pacMan && y_ghost+ObjectSize == y_pacMan))
				return true ;
			if(x_ghost == x_pacMan && y_ghost == y_pacMan)
				return true ;

			return false ;
	}
			
		public void moveGhost(Direction newDir, double d, double e, ImageView ghost)
		{
			
			double toX=d, toY=e;
			if(newDir == Direction.RIGHT)
				toX+=30;
			else if(newDir == Direction.LEFT)
				toX-=30;
			else if(newDir == Direction.UP)
				toY-=30;
			else if(newDir == Direction.DOWN)
				toY+=30;
			ImageView imageView= new ImageView();
			if(ghost.getId()=="Red")
				imageView = new ImageView("Photos/ghost_red.png");
			if(ghost.getId()=="Pink")
				imageView = new ImageView("Photos/ghost_pink.png");
			if(ghost.getId()=="Blue")
				imageView = new ImageView("Photos/ghost_blue.png");

			imageView.setFitHeight(30);
			imageView.setFitWidth(30);
			imageView.setX(toX);
			imageView.setY(toY);
			imageView.setId(ghost.getId());
			pane.getChildren().remove(ghost) ;	
			pane.getChildren().add(imageView) ;
			System.out.println(toX+","+ toY +","+ d +"," +e);
			if(imageView.getId()=="Red")
				redGhost= imageView;
			if(imageView.getId()=="Pink")
				pinkGhost= imageView;
			if(imageView.getId()=="Blue")
				blueGhost= imageView;

		}

		
		private void moveGhost1() {
	        //check if ghost is in PacMan's column and move towards him
			if (PacmanLocation.getColumn() == redGhost.getY()) {
                if (redGhost.getX() > PacmanLocation.getRow()) 
                    red_movingAt = Direction.LEFT;
                else 
                	red_movingAt= Direction.RIGHT;
                while(isWall(red_movingAt,redGhost.getX(), redGhost.getY()) == true) 
                	red_movingAt= getRandomDir();
			}
			
		    //check if ghost is in PacMan's row and move towards him
		        else if (redGhost.getX() == PacmanLocation.getRow()) {
		            if (redGhost.getY()> PacmanLocation.getColumn()) 
		                red_movingAt = Direction.UP;
		             else 
		                red_movingAt= Direction.DOWN;
		           
		            while(isWall(red_movingAt,redGhost.getX(), redGhost.getY()) == true) 
		                	red_movingAt= getRandomDir();
		 
		        }
		        //move in a consistent random direction until it hits a wall, then choose a new random direction
		    else{
		    	if(redGhost.getX()>PacmanLocation.getRow())
		    	{
		    		if(redGhost.getY()>PacmanLocation.getColumn())
		    		{
		    			
		    			red_movingAt= Direction.LEFT;
		    			if(isWall(red_movingAt,redGhost.getX(), redGhost.getY()) == true) 
		    				red_movingAt=Direction.UP;
			        }
			    	
		    	}
		    			
    			if(redGhost.getX()<PacmanLocation.getRow())
		    	{
		    		if(redGhost.getY()>PacmanLocation.getColumn())
		    		{
		    			red_movingAt= Direction.RIGHT;
		    			if(isWall(red_movingAt,redGhost.getX(), redGhost.getY()) == true) 
		    				red_movingAt=Direction.UP;
			        
		    		} 	
		    		if(redGhost.getY()<PacmanLocation.getColumn())
		    		{
		    			red_movingAt= Direction.DOWN;
		    			if(isWall(red_movingAt,redGhost.getX(), redGhost.getY()) == true) 
		    				red_movingAt=Direction.RIGHT;
			        
		    		} 	
		    	}
    			
    			while(isWall(red_movingAt,redGhost.getX(), redGhost.getY()) == true) 
    				red_movingAt= getRandomDir();	

		    }        	
			moveGhost(red_movingAt, redGhost.getX(), redGhost.getY(),redGhost);
				
        }	
	

		
		private void moveGhost2() {
	        //check if ghost is in PacMan's column and move towards him
			if (PacmanLocation.getColumn() == blueGhost.getY()) {
                if (blueGhost.getX() > PacmanLocation.getRow()) 
                    blue_movingAt = Direction.LEFT;
                else 
                	blue_movingAt= Direction.RIGHT;
                while(isWall(blue_movingAt,blueGhost.getX(), blueGhost.getY()) == true) 
                	blue_movingAt= getRandomDir();
			}
			
		    //check if ghost is in PacMan's row and move towards him
		        else if (blueGhost.getX() == PacmanLocation.getRow()) {
		            if (blueGhost.getY()> PacmanLocation.getColumn()) 
		            	blue_movingAt = Direction.UP;
		             else 
		            	 blue_movingAt= Direction.DOWN;
		           
		            while(isWall(blue_movingAt,blueGhost.getX(), blueGhost.getY()) == true) 
		            	blue_movingAt= getRandomDir();
		 
		        }
		        //move in a consistent random direction until it hits a wall, then choose a new random direction
		        else{
			    	if(blueGhost.getX()>PacmanLocation.getRow())
			    	{
			    		if(blueGhost.getY()>PacmanLocation.getColumn())
			    		{
			    			
			    			blue_movingAt= Direction.UP;
			    			if(isWall(blue_movingAt,blueGhost.getX(), blueGhost.getY()) == true) 
			    				blue_movingAt=Direction.LEFT;
				        }
				    	
			    		
				    	
			    	}
			    			
	    			if(blueGhost.getX()<PacmanLocation.getRow())
			    	{
			    		if(blueGhost.getY()>PacmanLocation.getColumn())
			    		{
			    			blue_movingAt= Direction.UP;
			    			if(isWall(blue_movingAt,blueGhost.getX(), blueGhost.getY()) == true) 
			    				blue_movingAt=Direction.RIGHT;
				        
			    		} 
			    		if(blueGhost.getY()<PacmanLocation.getColumn())
			    		{
			    			blue_movingAt= Direction.RIGHT;
			    			if(isWall(blue_movingAt,blueGhost.getX(), blueGhost.getY()) == true) 
			    				blue_movingAt=Direction.DOWN;
				        
			    		} 
			    	}
	    			while(isWall(blue_movingAt,blueGhost.getX(), blueGhost.getY()) == true) 
	    				blue_movingAt= getRandomDir();	

			    }        	
				moveGhost(blue_movingAt, blueGhost.getX(), blueGhost.getY(),blueGhost);				
        }	
	

	
	
		
		private void moveGhost3() {
	        //check if ghost is in PacMan's column and move towards him
			if (PacmanLocation.getColumn() == pinkGhost.getY()) {
                if (pinkGhost.getX() > PacmanLocation.getRow()) 
                    pink_movingAt = Direction.LEFT;
                else 
                	pink_movingAt= Direction.RIGHT;
                while(isWall(pink_movingAt,pinkGhost.getX(), pinkGhost.getY()) == true) 
                	pink_movingAt= getRandomDir();
			}
			
		    //check if ghost is in PacMan's row and move towards him
		        else if (pinkGhost.getX() == PacmanLocation.getRow()) {
		            if (pinkGhost.getY()> PacmanLocation.getColumn()) 
		            	pink_movingAt = Direction.UP;
		             else 
		            	 pink_movingAt= Direction.DOWN;
		           
		            while(isWall(pink_movingAt,pinkGhost.getX(), pinkGhost.getY()) == true) 
		            	pink_movingAt= getRandomDir();
		 
		        }
		        //move in a consistent random direction until it hits a wall, then choose a new random direction
		        //move in a consistent random direction until it hits a wall, then choose a new random direction
		        else{
	    			while(isWall(pink_movingAt,pinkGhost.getX(), pinkGhost.getY()) == true) 
	    				pink_movingAt= getRandomDir();	

			    }        	
				moveGhost(pink_movingAt, pinkGhost.getX(), pinkGhost.getY(),pinkGhost);
				
        }	
	

	
	public void movePackman(Direction dir,int fromX, int fromY, int toX, int toY)
	{
		for(int n = 0 ; n < peckpointlist.size() ; n++)
		{
			if((peckpointlist.get(n).getCenterX()-15)== toX && (peckpointlist.get(n).getCenterY()-15)== toY)
			{
				pane.getChildren().remove(peckpointlist.get(n)) ;
				peckpointlist.remove(n) ;
				score+=1 ;
				System.out.println(score);// increment the player's score by 1
			}
		}
		for(int n = 0 ; n < packmanMoves.size() ; n++)
		{
			if((packmanMoves.get(n).getX())== fromX && (packmanMoves.get(n).getY())== fromY)
			{
				pane.getChildren().remove(packmanMoves.get(n)) ;
				packmanMoves.remove(n) ;
			}
		}
		ImageView imageView = new ImageView("Photos/packMan.png");
		if(dir==Direction.LEFT)
			imageView= new ImageView("Photos/packManLeft.png");
		else if(dir==Direction.RIGHT)
			imageView= new ImageView("Photos/packManRight.png");
		imageView.setFitHeight(30);
		imageView.setFitWidth(30);
		imageView.setX(toX);
		imageView.setY(toY);
		pane.getChildren().add(imageView) ;
		packmanMoves.add(imageView) ;
		//return peckPoints 30 seconds after eating them
		retrunPeckPoints = new KeyFrame(Duration.millis(30000), e->
		{
			Circle peckPoint = new Circle() ; // pass in x, y, width and height
			peckPoint.setCenterX(fromX+15);  
			peckPoint.setCenterY(fromY+15);  
			peckPoint.setRadius(4); 
			peckPoint.setFill(Color.web("#E4CB18"));
			
			boolean toadd= true;
			for(int n = 0 ; n < peckpointlist.size() ; n++)
			{
				if((peckpointlist.get(n).getCenterX()-15)== fromX && (peckpointlist.get(n).getCenterY()-15)== fromY)
				{
					toadd=false;
				}
				if(n< bonusList.size())
					if((bonusList.get(n).getX())== fromX && (bonusList.get(n).getY())== fromY)
						toadd=false;
				
				if(PacmanLocation.getRow()== fromX && PacmanLocation.getColumn() == fromY)
				{
					toadd=false;
				}
			}
			
			if(toadd == true) {
			pane.getChildren().add(peckPoint) ;
			peckpointlist.add(peckPoint) ;
			System.out.println(fromX +","+ fromY +","+
					PacmanLocation.getRow() +"," + PacmanLocation.getColumn());
			}
			

			
		});
		timeline2 = new Timeline(retrunPeckPoints) ;
		timeline2.setCycleCount(Timeline.INDEFINITE) ;
		timeline2.play() ;

	}
	
	//fill board acccording to the matrix. every object on the board has it's defining number(see on Model\Board) for example - 0:wall
		private void fillBoard() {
	    int thisRow=0;
	    int thisColoum=0;
	    
		for(int i=0; i<21; i++)
		{
			for(int j=0;j<21;j++) {
				
				// update the walls on the board
				if(matrix[i][j]==1)
				{
					Rectangle wall = new Rectangle(thisRow, thisColoum, ObjectSize, ObjectSize) ; 		// pass in x, y, width and height
					wall.setFill(Color.web("#191970")) ;
					wall.setStroke(Color.CORNFLOWERBLUE) ;
					wall.setStrokeWidth(2.0) ;
					pane.getChildren().add(wall) ;
					wallList.add(wall);

				}
				
				// update the points on the board 
				if(matrix[i][j] == 0)
				{
					Circle peckPoint = new Circle() ; // pass in x, y, width and height
					peckPoint.setCenterX(thisRow+15);  
					peckPoint.setCenterY(thisColoum+15);  
					peckPoint.setRadius(4); 
					peckPoint.setFill(Color.web("#E4CB18"));
					pane.getChildren().add(peckPoint) ;
					peckpointlist.add(peckPoint) ;


				}
				
				// update the bomb points on the board 
				if(matrix[i][j] == 2)
				{
					/**
					 * Getting random photo 
					 */
					BombPoints bomb=new BombPoints("");
					int index = (int)(Math.random() * bomb.getBombPoints().size());
					ImageView imageView = new ImageView(bomb.getBombPoints().get(index).getImage());
					imageView.setFitHeight(30);
					imageView.setFitWidth(30);
					imageView.setX(thisRow);
					imageView.setY(thisColoum);
					pane.getChildren().add(imageView) ;
					bonusList.add(imageView);

				}
				
				// update the questions on the board
				if(matrix[i][j] == 3)
				{
					/**
					 * Getting random photo 
					 */
					Question questin=new Question();
					int index = (int)(Math.random() * questin.getPointsQuestions().size());
					ImageView imageView = new ImageView(questin.getPointsQuestions().get(index).getImage());
					imageView.setFitHeight(30);
					imageView.setFitWidth(30);
					imageView.setX(thisRow);
					imageView.setY(thisColoum);
					pane.getChildren().add(imageView) ;

				}
				
				// update the  Pack-Man on the board
				if(matrix[i][j] == 4)
				{
					ImageView imageView = new ImageView("Photos/packMan.png");
					imageView.setFitHeight(30);
					imageView.setFitWidth(30);
					imageView.setX(thisRow);
					imageView.setY(thisColoum);
					pane.getChildren().add(imageView) ;
					packmanMoves.add(imageView);

				}
				// update the ghost son the board 
				if(matrix[i][j] == 5)
				{
					ImageView imageView = new ImageView("Photos/ghost_blue.png");
					imageView.setFitHeight(30);
					imageView.setFitWidth(30);
					imageView.setX(thisRow);
					imageView.setY(thisColoum);
					imageView.setId("Blue");
					pane.getChildren().add(imageView) ;
					blueGhost= imageView;

					

				}
				if(matrix[i][j] == 6)
				{
					ImageView imageView = new ImageView("Photos/ghost_red.png");
					imageView.setFitHeight(30);
					imageView.setFitWidth(30);
					imageView.setX(thisRow);
					imageView.setY(thisColoum);
					imageView.setId("Red");

					pane.getChildren().add(imageView) ;
					redGhost= imageView;
				}
				if(matrix[i][j] == 7)
				{
					ImageView imageView = new ImageView("Photos/ghost_pink.png");
					imageView.setFitHeight(30);
					imageView.setFitWidth(30);
					imageView.setX(thisRow);
					imageView.setY(thisColoum);
					imageView.setId("Pink");

					pane.getChildren().add(imageView) ;
					pinkGhost= imageView;


				}
				
				
				thisRow+=30;
			}
			thisColoum+=30;
			thisRow=0;
		}
	}
		
		private void resume()
		{
			time= new AnimationTimer() {

				@Override
				public void handle(long arg0) {
					// TODO Auto-generated method stub



				}



			};
		}

		
		/** method that'll return a random direction 
		 * 
		 * @return dirc
		 */
		private Direction getRandomDir()
		{
			Direction dirc = null ;		
			int num = (int)(Math.random() * 4) ;

			switch(num) 
			{
				case 0 : dirc = Direction.UP ;
						 break ;
				case 1 : dirc = Direction.DOWN ;
						 break ;
				case 2 : dirc = Direction.LEFT ;
						 break ;
				case 3 : dirc = Direction.RIGHT ;
						 break ;
			}		

			return dirc ;
		}

		/** returns the opposite direction of the specified dirrection
		 * 
		 * @param d
		 * @return
		 */
		private Direction oppositeDir(Direction d) 
		{
			Direction dir = null ;

			if(d == Direction.UP) 
				dir = Direction.DOWN ;
			else if(d == Direction.DOWN) 
				dir = Direction.UP ;
			else if(d == Direction.LEFT)
				dir = Direction.RIGHT ;
			else if(d == Direction.RIGHT)
				dir = Direction.LEFT ;

			return dir ;
		}
		
	
		
		
		
		
	public Pane getPane() {
		return pane;
	}


	public void setScene(Scene scene) {
		this.scene = scene;
	}
	

}
