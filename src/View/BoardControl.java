package View;

import java.beans.EventHandler;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;

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
import javafx.scene.Scene;
import javafx.scene.control.skin.TextInputControlSkin.Direction;
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
    
    private Location PacmanLocation = new Location(300, 570);// the location on the matrix is [10][9]
    private Location BlueGhostLocation = new Location(270, 240); // the location on the matrix is [9][8]
    private Location RedGhostLocation = new Location(300, 240); // the location on the matrix is [10][8]
    private Location PinkGhostLocation = new Location(330, 240); // the location on the matrix is [11][8]
    
    private Direction red_movingAt, pink_movingAt, orange_movingAt, cyan_movingAt ;
    
    int[][] matrix1=Board.getInstance().matrixBoard_level1;
	//get board from board model( a matrix that describes the board- see Model\Board)
	int[][] matrix = Board.getInstance().putRandomQuestion(matrix1);
	
	//object size on the board is set to 30 
    int ObjectSize=30;
    
	private Timeline timeline;
	private Timeline timeline_redGhost;

    private GameState gameState;
    
    private Scene scene ;
	private Direction newDir;

	KeyFrame pacman_keyFrame;
	KeyFrame blueGhost_keyFrame;
	KeyFrame redGhost_keyFrame;
	KeyFrame pinkGhost_keyFrame;

	public int score;
	
	public Level level;

	
	private boolean down, up, left, right, keyActive, pause, resume, start;
	
	private int index_row_packMan, index_column_packMan;
	private int index_row_ghostRed, index_column_ghostRed;
	private int index_row_ghostBlue, index_column_ghostBlue;
	private int index_row_ghostPink, index_column_ghostPink;

	/**
	 * Variable to control PackMan speed
	 */
	private int Speed;

	private AnimationTimer time;

	
	


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		pane.setStyle("-fx-background-color : black") ;//set background to black
		fillBoard();
		up = down = right = left = pause = resume = start = false;
		keyActive = true;
		Speed = 300;
		gameState = GameState.Paused;
		ghosts();
		pressedKeys(pane);
		
	

	}

	


	private void movement() {
		if(isWall(newDir) == false) {
			if(newDir == Direction.RIGHT)
			{
				if(isWall(newDir) == false) {
					movePackman(newDir,PacmanLocation.getRow(), PacmanLocation.getColumn(), PacmanLocation.getRow()+30, PacmanLocation.getColumn());
					PacmanLocation.setRow((PacmanLocation.getRow())+30);
					}
			
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
		pane.setOnMouseClicked(new javafx.event.EventHandler<Event>() {		
			
			@Override public void handle(Event arg0) {
				
				setScene(pane.getScene()); //get scene 
				gameState= GameState.Started;
				scene.setOnKeyPressed(new javafx.event.EventHandler<KeyEvent>() {	
					
					private boolean keepGoing= false;

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
				 movement();
				 
				});
				timeline = new Timeline(pacman_keyFrame) ;
				timeline.setCycleCount(Timeline.INDEFINITE) ;
				timeline.play() ;

	}
		});
	}

	/** method to check for an incoming wall specific to the direction
	 * 
	 * @param theDir
	 * @param x_coord
	 * @param y_coord
	 * @return
	 */
	private Boolean checkForWalls(Direction theDir, double x_coord, double y_coord)
	{
		x_coord = x_coord - Speed ;
		y_coord = y_coord - Speed ;
		double checkX=0, checkY =0;
		for(int i = 0 ; i < 21 ; i++)
		{
			for(int j=0; j<21; j++)
			{
				if(matrix[i][j]==1)
				{ 
					checkX = i ;
				    checkY = j ;
				}
			}
			

			if(theDir == Direction.UP && checkY < y_coord)
			{
				if((x_coord == checkX || (x_coord < checkX+ObjectSize && x_coord > checkX) || x_coord+Speed == checkX) && y_coord-ObjectSize <= checkY)
					return true ;
			}
			else if(theDir == Direction.DOWN && checkY > y_coord)
			{
				if((x_coord == checkX || (x_coord < checkX+ObjectSize && x_coord > checkX) || x_coord+Speed == checkX) && y_coord+ObjectSize >= checkY)
					return true ;
			}
			else if(theDir == Direction.LEFT && checkX < x_coord)
			{
				if(x_coord-ObjectSize <= checkX && (y_coord == checkY || (y_coord < checkY+ObjectSize && y_coord > checkY) || y_coord+Speed == checkY))
					return true ;
			}
			else if(theDir == Direction.RIGHT && checkX > x_coord)
			{
				if(x_coord+ObjectSize >= checkX && (y_coord == checkY || (y_coord < checkY+ObjectSize && y_coord > checkY) || y_coord+Speed == checkY))
					return true ;
			}
		}

		return false ;
	}

	/**
	 * 
	 * @param Dir
	 * @return
	 */
	private boolean isWall(Direction Dir) {
		int nextRowIndex;
		int nextColumnIndex;
		if(Dir == Direction.RIGHT)
		{
			nextRowIndex= ((PacmanLocation.getColumn())/30);
			nextColumnIndex= ((PacmanLocation.getRow())/30) + 1;
			if(matrix[nextRowIndex][nextColumnIndex] == 1)
				return true;
		}
		if(Dir == Direction.LEFT)
		{
			nextRowIndex= ((PacmanLocation.getColumn())/30);
			nextColumnIndex= ((PacmanLocation.getRow())/30) - 1;
			if(matrix[nextRowIndex][nextColumnIndex] == 1)
				return true;
			
			else {
				return false;
			}
		}if(Dir == Direction.UP)
		{
			nextRowIndex= ((PacmanLocation.getColumn())/30) - 1;
			nextColumnIndex= ((PacmanLocation.getRow())/30);
			if(matrix[nextRowIndex][nextColumnIndex] == 1)
				return true;
			
			else {
				return false;
			}
		}if(Dir == Direction.DOWN)
		{
			nextRowIndex= ((PacmanLocation.getColumn())/30) + 1;
			nextColumnIndex= ((PacmanLocation.getRow())/30) ;
			if(matrix[nextRowIndex][nextColumnIndex] == 1)
				return true;
			
			else {
				return false;
			}
		}
		return false;
		
	}

	
	public void movePackman(Direction dir,int fromX, int fromY, int toX, int toY)
	{
		
		Rectangle wall = new Rectangle(fromX, fromY, ObjectSize, ObjectSize) ; 		// pass in x, y, width and height
		wall.setFill(Color.BLACK) ;
		pane.getChildren().add(wall) ;
		ImageView imageView= new ImageView("Photos/packMan.png");
		if(dir==Direction.LEFT)
			imageView= new ImageView("Photos/packManLeft.png");
		else if(dir==Direction.RIGHT)
			imageView= new ImageView("Photos/packManRight.png");
		imageView.setFitHeight(30);
		imageView.setFitWidth(30);
		imageView.setX(toX);
		imageView.setY(toY);
		pane.getChildren().add(imageView) ;
	}
	
	

	/** Fill board according to the matrix. 
	 *  Every object on the board has it's defining number(see on Model\Board).
	 *  For example - 0: wall
	*/
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
					index_row_packMan= i;
					index_column_packMan=j;

				}
				// update the ghost son the board 
				if(matrix[i][j] == 5)
				{
					index_row_ghostBlue=i;
					index_column_ghostBlue=j;
					ImageView imageView = new ImageView("Photos/ghost_blue.png");
					imageView.setFitHeight(30);
					imageView.setFitWidth(30);
					imageView.setX(thisRow);
					imageView.setY(thisColoum);
					pane.getChildren().add(imageView) ;

				}
				if(matrix[i][j] == 6)
				{
					index_row_ghostRed=i;
					index_column_ghostRed=j;
					ImageView imageView = new ImageView("Photos/ghost_red.png");
					imageView.setFitHeight(30);
					imageView.setFitWidth(30);
					imageView.setX(thisRow);
					imageView.setY(thisColoum);
					pane.getChildren().add(imageView) ;

				}
				if(matrix[i][j] == 7)
				{
					index_row_ghostPink=i;
					index_column_ghostPink=j;
					ImageView imageView = new ImageView("Photos/ghost_pink.png");
					imageView.setFitHeight(30);
					imageView.setFitWidth(30);
					imageView.setX(thisRow);
					imageView.setY(thisColoum);
					pane.getChildren().add(imageView) ;

				}
				if(matrix[i][j] == 8)
				{
					ImageView imageView = new ImageView("Photos/ghost_orange.png");
					imageView.setFitHeight(30);
					imageView.setFitWidth(30);
					imageView.setX(thisRow);
					imageView.setY(thisColoum);
					pane.getChildren().add(imageView) ;

				}
				
				thisRow+=30;
			}
			thisColoum+=30;
			thisRow=0;
		}
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
		
		
		/**method that'll return the direction that points towards pacman from a ghost's current position
		 * 
		 * @param ghost
		 * @return
		 */
//		private Direction pacmanAt(double xx, double yy)
//		{
//			double x = xx ;
//			double y = yy ;
//
//			if(y == pacmanY && (pacmanX-x) > 0 && (pacmanX-x) < 100 && checkForWallsBetween(x, y, pacmanX, pacmanY, Dir.RIGHT) == false)
//				return Dir.RIGHT ;
//			else if(y == pacmanY && (x-pacmanX) > 0 && (x-pacmanX) < 100 && checkForWallsBetween(x, y, pacmanX, pacmanY, Dir.LEFT) == false)
//				return Dir.LEFT ;
//			else if(x == pacmanX && (pacmanY-y) > 0 && (pacmanY-y) < 100 && checkForWallsBetween(x, y, pacmanX, pacmanY, Dir.DOWN) == false)
//				return Dir.DOWN ;
//			else if(x == pacmanX && (y-pacmanY) > 0 && (y-pacmanY) < 100 && checkForWallsBetween(x, y, pacmanX, pacmanY, Dir.UP) == false)
//				return Dir.UP ;
//
//			return null ;
//		}
		
		private void ghosts()
		{
			redGhost_keyFrame = new KeyFrame(Duration.millis(Speed), e->
			{
				Direction dontGo = null ;
				//dontGo = pacmanAt(RedGhostLocation.getRow(),RedGhostLocation.getColumn()) ;
				// move in a random direction
				Direction direction = getRandomDir() ;
				if(checkForWalls(direction, RedGhostLocation.getRow() , RedGhostLocation.getColumn()) == false)
				{
					if(dontGo != null && direction != dontGo)
					{
						red_movingAt = direction ;
						//break ;
					}
					else if(direction != oppositeDir(red_movingAt))
					{
						red_movingAt = direction ;
						//break ;
					}
				}
				
			 
			});

			timeline_redGhost = new Timeline(redGhost_keyFrame) ;
			timeline_redGhost.setCycleCount(Timeline.INDEFINITE) ;
			timeline_redGhost.play() ;
		}


	public Pane getPane() {
		return pane;
	}


	public void setScene(Scene scene) {
		this.scene = scene;
	}
	

}
