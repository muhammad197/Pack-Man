package View;

import java.awt.event.ActionEvent;
import java.beans.EventHandler;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import Controller.ShapeFactory;
import Controller.Sound;
import Controller.SysData;

import Model.Board;
import Model.BombPoints;
import Model.Game;
import Model.Ghost;
import Model.Location;
import Model.PackMan;
import Model.PeckPoints;
import Model.Player;
import Model.Question;
import Utils.GameState;
import Utils.GameStateWomen;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.skin.TextInputControlSkin.Direction;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Board_2playersControl implements Initializable {

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label namelab;
    
    @FXML
    private Label namelab2;
    

    @FXML
    private ImageView life3;

    @FXML
    private ImageView life2;
 
    @FXML
    private ImageView life1;
    
    @FXML
    private ImageView life31;

    @FXML
    private ImageView life21;
 
    @FXML
    private ImageView life11;

    @FXML
    private Label scorelab;
    
    @FXML
    private Label scorelab2;

    @FXML
    private ImageView stopIcon;
    
   

    @FXML
    private Pane pane;

    private PackMan pacman;
    private PackMan pacwomen;
    
    private Direction red_movingAt= Direction.UP, blue_movingAt=Direction.UP, pink_movingAt=Direction.LEFT ;

    //get board from board model( a matrix that describes the board- see Model\Board)
	int[][] matrix = Board.matrixBoard_2players_level1;
	//int[][] matrix = Board.putRandomQuestion(matrix1);
		
	//object size on the board is set to 30 
	int ObjectSize=30;
	
	private Timeline pacman_timeline,pecPoints_timeline,ghosts_timeline,bombPoints_timeline;
	private Timeline pacwomen_timeline;
	
	private Scene scene ;
	
	boolean QuestionMode=false;
	// save previous and new directions of the pacman
	private Direction newDir=Direction.RIGHT;
	private Direction prevDir=Direction.RIGHT;
	
	// save previous and new directions of the pacwomen
	private Direction newDir_women=Direction.RIGHT;
	private Direction prevDir_women=Direction.RIGHT;
	
	KeyFrame pacman_keyFrame,showCorrectAns;
	KeyFrame pacwomen_keyFrame,showCorrectAns_women;
	
	public boolean levelUp=false;
	public boolean levelDown=false;
	
	public boolean levelUp_women=false;
	public boolean levelDown_women=false;
	
	public boolean bonusEaten=false;
	public boolean bonusEaten_women=false;
	
	private Game game;
	private Game game_women;
	
	private ArrayList<Circle> peckpointlist = new ArrayList<Circle>() ;
	private ArrayList<Rectangle> wallList = new ArrayList<Rectangle>() ;
	private ArrayList<ImageView> bonusList = new ArrayList<ImageView>() ;
	private ArrayList<ImageView> questionsPoints = new ArrayList<ImageView>() ;	
	
	private Ghost redGhost;
	private Ghost blueGhost;
	private Ghost pinkGhost;
	
	boolean firstRedGhostMove=true;
	
	//fields for question
	CheckBox ans1= new CheckBox();
	CheckBox ans2 = new CheckBox();
	CheckBox ans3 = new CheckBox();
	CheckBox ans4 = new CheckBox();
	Label label_ans1 = new Label();
	Label label_ans2 = new Label();
	Label label_ans3 = new Label();
	Label label_ans4 = new Label();
	
	
	
	/**
	 * Variable to control PackMan speed
	 */
			
	private KeyFrame retrunPeckPoints,ghosts_keyFrame,retrunBombPoints;
	
		
	protected boolean isbonusUsed=true;
	protected boolean isbonusUsed_women=true;
	



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		pacman= new PackMan(1,300, new ImageView(),new Location(300, 570),Utils.Color.yellow);
		pacwomen= new PackMan(1,300, new ImageView(),new Location(300, 330),Utils.Color.pink);
		
		redGhost= new Ghost(2, 280, new ImageView(), new Location(300, 240), Utils.Color.red);
		blueGhost= new Ghost(1, 280, new ImageView(), new Location(270,240), Utils.Color.blue);
		pinkGhost= new Ghost(3, 280, new ImageView(), new Location(330, 240), Utils.Color.pink);
		
		game=new Game(0, 3, GameState.Started, 0, Level.easy, SysData.CurrentPlayer); 
		game_women=new Game(1, 3, GameStateWomen.Started, 0, Level.easy, SysData.CurrentPlayer2); 
		
		pane.setStyle("-fx-background-color : black") ;//set background to black
		fillBoard();
		

		namelab.setText(game.getPlayerName());
		namelab2.setText(game_women.getPlayerName());
		
		scorelab.setText(String.valueOf(game.getScore()));
		scorelab2.setText(String.valueOf(game_women.getScore()));
		pressedKeys(pane);
		
	
	}
	
	

	
	/*
	 * this function returns if there is a wall in location x, y on board according to the direction that the object is turning to
	 */
	private boolean isWall(Direction theDir, double x_coord, double y_coord)
	{
		int wallSize= ObjectSize;
	
		for(int counter = 0 ; counter < wallList.size() ; counter++)
		{
			double checkX = wallList.get(counter).getX() ;
			double checkY = wallList.get(counter).getY() ;
	
			if(theDir == Direction.UP && checkY < y_coord)
			{
				if((x_coord == checkX || (x_coord < checkX+wallSize && x_coord > checkX) || x_coord== checkX) && y_coord-wallSize <= checkY)
					return true ;
			}
			else if(theDir == Direction.DOWN && checkY > y_coord)
			{
				if((x_coord == checkX || (x_coord < checkX+wallSize && x_coord > checkX) || x_coord== checkX) && y_coord+wallSize >= checkY)
					return true ;
			}
			else if(theDir == Direction.LEFT && checkX < x_coord)
			{
				if(x_coord-wallSize <= checkX && (y_coord == checkY || (y_coord < checkY+wallSize && y_coord > checkY) || y_coord== checkY))
					return true ;
			}
			else if(theDir == Direction.RIGHT && checkX > x_coord)
			{
				if(x_coord+wallSize >= checkX && (y_coord == checkY || (y_coord < checkY+wallSize && y_coord > checkY) || y_coord== checkY))
					return true ;
			}
		}
	
		return false ;
		
		
		
	}

	 /**                         --------- man *** women ----------                * */
	
	
	/*
	 * this function calls the function movePacman which moves the pacman only if the movement direction does not contain a wall
	 */
	private void movement(Direction newDir) {
		
		if(isWall(newDir, pacman.getCurrentLocation().getRow(), pacman.getCurrentLocation().getColumn()) == false) {
			prevDir= newDir;
		}
		else
			newDir= prevDir;
		if(isWall(newDir, pacman.getCurrentLocation().getRow(), pacman.getCurrentLocation().getColumn()) == false) {
	
			if(newDir == Direction.RIGHT)
			{
				if(pacman.getCurrentLocation().getRow()== 600 && pacman.getCurrentLocation().getColumn()==300) {
					movePackman(newDir,pacman.getCurrentLocation().getRow(), pacman.getCurrentLocation().getColumn(), 0, pacman.getCurrentLocation().getColumn());
					pacman.getCurrentLocation().setRow(0);
				}
	
				else {
					movePackman(newDir,pacman.getCurrentLocation().getRow(), pacman.getCurrentLocation().getColumn(), pacman.getCurrentLocation().getRow()+30, pacman.getCurrentLocation().getColumn());
					pacman.getCurrentLocation().setRow((pacman.getCurrentLocation().getRow())+30);
				}
			}
			if(newDir == Direction.LEFT)
			{
				if(pacman.getCurrentLocation().getRow()== 0 && pacman.getCurrentLocation().getColumn()==300) {
					movePackman(newDir,pacman.getCurrentLocation().getRow(), pacman.getCurrentLocation().getColumn(), 600, pacman.getCurrentLocation().getColumn());
					pacman.getCurrentLocation().setRow(600);
				}
				else {
				movePackman(newDir,pacman.getCurrentLocation().getRow(), pacman.getCurrentLocation().getColumn(), pacman.getCurrentLocation().getRow()-30, pacman.getCurrentLocation().getColumn());
				pacman.getCurrentLocation().setRow((pacman.getCurrentLocation().getRow())-30);
				}
			}
				
			
			if(newDir == Direction.UP)
			{
				if(pacman.getCurrentLocation().getRow()== 90 && pacman.getCurrentLocation().getColumn()==0) {
					movePackman(newDir,pacman.getCurrentLocation().getRow(), pacman.getCurrentLocation().getColumn(), pacman.getCurrentLocation().getRow(), 600);
					pacman.getCurrentLocation().setColumn(600);
				}
				else {
				movePackman(newDir,pacman.getCurrentLocation().getRow(), pacman.getCurrentLocation().getColumn(), pacman.getCurrentLocation().getRow(), pacman.getCurrentLocation().getColumn()-30);
				pacman.getCurrentLocation().setColumn(pacman.getCurrentLocation().getColumn()-30);
				}
			}
			if(newDir == Direction.DOWN)
			{
				if(pacman.getCurrentLocation().getRow()== 90 && pacman.getCurrentLocation().getColumn()==600) {
					movePackman(newDir,pacman.getCurrentLocation().getRow(), pacman.getCurrentLocation().getColumn(), pacman.getCurrentLocation().getRow(), 0);
					pacman.getCurrentLocation().setColumn(0);
				}
				else {
				movePackman(newDir,pacman.getCurrentLocation().getRow(), pacman.getCurrentLocation().getColumn(), pacman.getCurrentLocation().getRow(), pacman.getCurrentLocation().getColumn()+30);
				pacman.getCurrentLocation().setColumn(pacman.getCurrentLocation().getColumn()+30);
				}
			}
		
		}
	
		
	}
	
	
	
	/*
	 * this function calls the function movePacwomen which moves the pacman only if the movement direction does not contain a wall
	 */
	private void movement_women(Direction newDir) {
		
		if(isWall(newDir, pacwomen.getCurrentLocation().getRow(), pacwomen.getCurrentLocation().getColumn()) == false) {
			prevDir_women= newDir;
		}
		else
			newDir= prevDir_women;
		if(isWall(newDir, pacwomen.getCurrentLocation().getRow(), pacwomen.getCurrentLocation().getColumn()) == false) {
	
			if(newDir == Direction.RIGHT)
			{
				if(pacwomen.getCurrentLocation().getRow()== 600 && pacwomen.getCurrentLocation().getColumn()==300) {
					movePackwomen(newDir,pacwomen.getCurrentLocation().getRow(), pacwomen.getCurrentLocation().getColumn(), 0, pacwomen.getCurrentLocation().getColumn());
					pacwomen.getCurrentLocation().setRow(0);
				}
	
				else {
					movePackwomen(newDir,pacwomen.getCurrentLocation().getRow(), pacwomen.getCurrentLocation().getColumn(), pacwomen.getCurrentLocation().getRow()+30, pacwomen.getCurrentLocation().getColumn());
					pacwomen.getCurrentLocation().setRow((pacwomen.getCurrentLocation().getRow())+30);
				}
			}
			if(newDir == Direction.LEFT)
			{
				if(pacwomen.getCurrentLocation().getRow()== 0 && pacwomen.getCurrentLocation().getColumn()==300) {
					movePackwomen(newDir,pacwomen.getCurrentLocation().getRow(), pacwomen.getCurrentLocation().getColumn(), 600, pacwomen.getCurrentLocation().getColumn());
					pacwomen.getCurrentLocation().setRow(600);
				}
				else {
					movePackwomen(newDir,pacwomen.getCurrentLocation().getRow(), pacwomen.getCurrentLocation().getColumn(), pacwomen.getCurrentLocation().getRow()-30, pacwomen.getCurrentLocation().getColumn());
				pacwomen.getCurrentLocation().setRow((pacwomen.getCurrentLocation().getRow())-30);
				}
			}
				
			
			if(newDir == Direction.UP)
			{
				if(pacwomen.getCurrentLocation().getRow()== 90 && pacwomen.getCurrentLocation().getColumn()==0) {
					movePackwomen(newDir,pacwomen.getCurrentLocation().getRow(), pacwomen.getCurrentLocation().getColumn(), pacwomen.getCurrentLocation().getRow(), 600);
					pacwomen.getCurrentLocation().setColumn(600);
				}
				else {
					movePackwomen(newDir,pacwomen.getCurrentLocation().getRow(), pacwomen.getCurrentLocation().getColumn(), pacwomen.getCurrentLocation().getRow(), pacwomen.getCurrentLocation().getColumn()-30);
				pacwomen.getCurrentLocation().setColumn(pacwomen.getCurrentLocation().getColumn()-30);
				}
			}
			if(newDir == Direction.DOWN)
			{
				if(pacwomen.getCurrentLocation().getRow()== 90 && pacwomen.getCurrentLocation().getColumn()==600) {
					movePackwomen(newDir,pacwomen.getCurrentLocation().getRow(), pacwomen.getCurrentLocation().getColumn(), pacwomen.getCurrentLocation().getRow(), 0);
					pacwomen.getCurrentLocation().setColumn(0);
				}
				else {
					movePackwomen(newDir,pacwomen.getCurrentLocation().getRow(), pacwomen.getCurrentLocation().getColumn(), pacwomen.getCurrentLocation().getRow(), pacwomen.getCurrentLocation().getColumn()+30);
				pacwomen.getCurrentLocation().setColumn(pacwomen.getCurrentLocation().getColumn()+30);
				}
			}
		
		}
	
		
	}
	
	
	
	
	
	
	/*
	 * this function handles the user key pressing(left, right, up, down)
	 */
	private void pressedKeys(Pane pane2) {
		
		pane.setOnMouseMoved(new javafx.event.EventHandler<Event>() {		//get scene 
	
	@Override
	public void handle(Event arg0) {
		if(game.gameState==GameState.Started && game_women.gameStatewomen==GameStateWomen.Started ) {
			{
				game.setGameState(GameState.Running);
				game_women.setGameStateWomen(GameStateWomen.Running);
			}
		setScene(pane.getScene());
		//if user press on the stop game icon and there is a question is shown on board, he can't stop the game
		stopIcon.setOnMouseClicked(new javafx.event.EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				if(QuestionMode==false) {
					pauseOrUnPauseGame();			          
					Alert alert = new Alert(AlertType.NONE);
					alert.setTitle("Take a break");
					alert.setHeaderText("");
					alert.setContentText("Do you want to quit the game?");
					ButtonType buttonYes = new ButtonType("Yes", ButtonData.YES);
					ButtonType buttonNo = new ButtonType("No", ButtonData.NO);
					DialogPane dialogPane = alert.getDialogPane();
					
				    dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
					alert.getButtonTypes().setAll(buttonYes, buttonNo);
					Optional<ButtonType> answer = alert.showAndWait();
					if (answer.get().getButtonData() == ButtonData.NO) {
						try {
							pauseOrUnPauseGame();
						} catch (Exception e) {
						}
					}
					else {
						//save game history and go to leaderboard window
						Date localdate = (Date) Calendar.getInstance().getTime();
			        	SysData.getInstance().addGameHistory(new Player(SysData.CurrentPlayer,game.getScore(),localdate));
			        	SysData.getInstance().addGameHistory(new Player(SysData.CurrentPlayer2,game_women.getScore(),localdate));
			        	((Stage) pane.getScene().getWindow()).close();
		        	    ViewLogic.leaderBoardWindow();
		        	    }
				}
			}
		});
		// on key listener, move the pacman
		scene.setOnKeyPressed(new javafx.event.EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				
				if(game.gameState==GameState.Running) {

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
				}
				
				if(game_women.gameStatewomen==GameStateWomen.Running) {
					switch(event.getCode()) 
					{
						case W : newDir_women = Direction.UP ;
								  break ;
			
						case S : newDir_women = Direction.DOWN ;
									break ;
			
						case A : newDir_women = Direction.LEFT ;
									break ;
			
						case D : newDir_women = Direction.RIGHT ;
								 break ;
					}
				}
			}
			});
			
			 
			if(game.gameState==GameState.Running && game_women.gameStatewomen==GameStateWomen.Running ) {
	 						movePackmanAtSpeed();// move pacman at pacman speed
	 						movePacwomenAtSpeed();
							moveGhostAtSpeed();//move all ghosts at ghosts speed 77777777777777777777777777777777777777777777777777777777777777
						}
			
					}
						
						
					}
				
				});}	
			
	/*
	 * all ghosts move at the same speed, so here we called the move ghost at speed of the red one 
	 * the speed is presented as: how many cells can a go move in a millisecond
	 */
	
	public void moveGhostAtSpeed() {
		ghosts_keyFrame = new KeyFrame(Duration.millis(redGhost.getSpeed()), e->
		{
			if(game.gameState==GameState.Running || game_women.gameStatewomen==GameStateWomen.Running) {

		 movePink();
		 moveRed();
		 moveBlue();
			}
		});
		ghosts_timeline = new Timeline(ghosts_keyFrame) ;
		ghosts_timeline.setCycleCount(Timeline.INDEFINITE) ;
		ghosts_timeline.play() ;
		
		
	}
	
	/*
	 * move specific ghost from the current location by specifying the direction
	 */
	public void moveGhost(Direction newDir, double d, double e, Ghost ghost)
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
			if(ghost.getId()==2)
				imageView = new ImageView("Photos/ghost_red.png");
			if(ghost.getId()==3)
				imageView = new ImageView("Photos/ghost_pink.png");
			if(ghost.getId()==1)
				imageView = new ImageView("Photos/ghost_blue.png");
			imageView.setFitHeight(30);
			imageView.setFitWidth(30);
			imageView.setX(toX);
			imageView.setY(toY);
			pane.getChildren().remove(ghost.getImage()) ;	
			pane.getChildren().add(imageView) ;
			if(ghost.getId()==2) {
				redGhost.getCurrentLocation().setRow((int) toX);
				redGhost.getCurrentLocation().setColumn((int) toY);
				redGhost.setImage(imageView);
			}
			if(ghost.getId()==3) {
				pinkGhost.getCurrentLocation().setRow((int) toX);
				pinkGhost.getCurrentLocation().setColumn((int) toY);
				pinkGhost.setImage(imageView);
			}
			if(ghost.getId()==1) {
				blueGhost.getCurrentLocation().setRow((int) toX);
				blueGhost.getCurrentLocation().setColumn((int) toY);
				blueGhost.setImage(imageView);
			}
		}

	 /**                         --------- man *** women ----------                * */
	
	
	 /**                         --------- man *** women ----------                * */
	/*
	 * when moving the pacman, check if a question is eaten/ pecpoint is eaten/ bomb points is eaten
	 * after checking move pacman from  location x, y to location x2,y2
	 */
	public void movePackman(Direction dir,int fromX, int fromY, int toX, int toY)
	{
		if(game.gameState==GameState.Running) {
		boolean isQuestion=false;
		isQuestion= checkQuestionEaten(toX, toY, isQuestion);
		checkPecPointEaten(toX, toY);
		checkBonusPointEaten(toX, toY);
		returnBombPoint(toX, toY);
		updatePacmanMove(dir,fromX, fromY, toX, toY, isQuestion);
		
		}
	}
	
	
	
	public void movePackwomen(Direction dir,int fromX, int fromY, int toX, int toY)
	{
		if(game_women.gameStatewomen==GameStateWomen.Running) {
		boolean isQuestion=false;
		isQuestion= checkQuestionEaten(toX, toY, isQuestion);
		checkPecPointEatenwomen(toX, toY);
		checkBonusPointEatenwomen(toX, toY); 
		//returnBombPointwomen(toX, toY);
		updatePacwomenMove(dir,fromX, fromY, toX, toY, isQuestion);
		
		}
	}
	
	 /**                         --------- man *** women ----------                * */
	
	
	 /**                         --------- man *** women ----------                * */
	/*
	 * update pacman moving from for moving to location 
	 */
	private void updatePacmanMove(Direction dir,int fromX, int fromY, int toX, int toY, boolean isQuestion) 
		{
			pane.getChildren().remove(pacman.getImage()) ;
			pacman.setImage(new ImageView());
		
			ImageView imageView =new ImageView();
			
			//if bomb point was eaten change pacman color
			if(pacman.getColor()==Utils.Color.yellow) {
				imageView = new ImageView("/Photos/Pac-Man__PMVS_-removebg-preview.png");
				if(dir==Direction.LEFT)
					imageView= new ImageView("/Photos/Pac-Man__PMVS_-removebg-preview.png");
				else if(dir==Direction.RIGHT)
					imageView= new ImageView("/Photos/Pac-Man__PMVS_-removebg-preview.png");
			}
	
		 	else {
				imageView = new ImageView("/Photos/Pac-Man__PMVS_-removebg-preview.png");
				if(dir==Direction.LEFT)
					imageView= new ImageView("/Photos/Pac-Man__PMVS_-removebg-preview.png");
				else if(dir==Direction.RIGHT)
					imageView= new ImageView("/Photos/Pac-Man__PMVS_-removebg-preview.png");
				}
				
			bonusEaten=false;
			if(isQuestion==false) {
			imageView.setFitHeight(30);
			imageView.setFitWidth(30);
			imageView.setX(toX);
			imageView.setY(toY);
			pane.getChildren().add(imageView) ;
			pacman.setImage(imageView);
			returnPeckPoints(fromX,fromY);
				}			
		}
	
	
	
	private void updatePacwomenMove(Direction dir,int fromX, int fromY, int toX, int toY, boolean isQuestion) 
		{
			pane.getChildren().remove(pacwomen.getImage()) ;
			pacwomen.setImage(new ImageView());
		
			ImageView imageView =new ImageView();
			
			//if bomb point was eaten change pacman color
			if(pacwomen.getColor()==Utils.Color.yellow) {
				imageView = new ImageView("/Photos/Photo87-removebg-preview.png");
				if(dir==Direction.LEFT)
					imageView= new ImageView("/Photos/Photo87-removebg-preview.png");
				else if(dir==Direction.RIGHT)
					imageView= new ImageView("/Photos/Photo87-removebg-preview.png");
			}
	
		 	else {
				imageView = new ImageView("/Photos/Photo87-removebg-preview.png");
				if(dir==Direction.LEFT)
					imageView= new ImageView("/Photos/Photo87-removebg-preview.png");
				else if(dir==Direction.RIGHT)
					imageView= new ImageView("/Photos/Photo87-removebg-preview.png");
				}
				
			bonusEaten=false;
			if(isQuestion==false) {
			imageView.setFitHeight(30);
			imageView.setFitWidth(30);
			imageView.setX(toX);
			imageView.setY(toY);
			pane.getChildren().add(imageView) ;
			pacwomen.setImage(imageView);
			returnPeckPointswomen(fromX,fromY);
				}			
		}
	
	 
	
	
	/**                         --------- man *** women ----------                * */	
	
	
	 /**                         --------- man *** women ----------                * */
	/*
	 * return bombPoints 30 seconds after eating them
	 */
	private void returnBombPoint(int toX, int toY) {
		if(bonusEaten==true) {
			
			retrunBombPoints = new KeyFrame(Duration.millis(30000), e->
			{
				if(game.gameState==GameState.Running) {

				
						BombPoints bomb=new BombPoints("");
						ImageView imageView2 = new ImageView("Photos/dynamite.png");
						imageView2.setFitHeight(30);
						imageView2.setFitWidth(30);
						imageView2.setX(toX);
						imageView2.setY(toY);
						
		
						boolean toadd= true;
						for(int n = 0 ; n < bonusList.size() ; n++)
						{
							if((bonusList.get(n).getX())== toX && (bonusList.get(n).getY())== toY)
									toadd=false;
						}
						
						if(toadd == true) {
						pane.getChildren().add(imageView2) ;
						bonusList.add(imageView2) ;
						}
						
				}
						
					});
					bombPoints_timeline = new Timeline(retrunBombPoints) ;
					bombPoints_timeline.setCycleCount(Timeline.INDEFINITE) ;
					bombPoints_timeline.play() ;
					
			
			pauseGhost();
		
			if(pacman.getColor() == Utils.Color.yellow) {
					pacman.setColor(Utils.Color.green);
					
			}
			else if(pacman.getColor()== Utils.Color.green)
			{
				pacman.setColor(Utils.Color.yellow);
		
			}
		
		}			
	}
	
	
	private void returnBombPointwomen(int toX, int toY) {
		if(bonusEaten_women==true) {
			
			retrunBombPoints = new KeyFrame(Duration.millis(30000), e->
			{
				if(game_women.gameStatewomen==GameStateWomen.Running) {

				
						BombPoints bomb=new BombPoints("");
						ImageView imageView2 = new ImageView("Photos/dynamite.png");
						imageView2.setFitHeight(30);
						imageView2.setFitWidth(30);
						imageView2.setX(toX);
						imageView2.setY(toY);
						
		
						boolean toadd= true;
						for(int n = 0 ; n < bonusList.size() ; n++)
						{
							if((bonusList.get(n).getX())== toX && (bonusList.get(n).getY())== toY)
									toadd=false;
						}
						
						if(toadd == true) {
						pane.getChildren().add(imageView2) ;
						bonusList.add(imageView2) ;
						}
						
				}
						
					});
					bombPoints_timeline = new Timeline(retrunBombPoints) ;
					bombPoints_timeline.setCycleCount(Timeline.INDEFINITE) ;
					bombPoints_timeline.play() ;
					
			
			pauseGhostwomen();
		
			if(pacwomen.getColor() == Utils.Color.pink) {
				pacwomen.setColor(Utils.Color.green);
					
			}
			else if(pacwomen.getColor()== Utils.Color.green)
			{
				pacwomen.setColor(Utils.Color.pink);
		
			}
		
		}			
	}
	
	
	/**                         --------- man *** women ----------                * */	
	
	 /**                         --------- man *** women ----------                * */
	/*
	 * check if a bomb point is eaten
	 */
	private void checkBonusPointEaten(int toX, int toY) {

		for(int b = 0 ; b < bonusList.size() ; b++)
		{
			if((bonusList.get(b).getX()== toX) && (bonusList.get(b).getY() == toY))
			{
				pane.getChildren().remove(bonusList.get(b)) ;
				bonusList.remove(b) ;
				game.setScore(game.getScore()+1);
				scorelab.setText(String.valueOf(game.getScore()));
				levelUp();
				bonusEaten=true;
				isbonusUsed=false;
				
			}
			
	
		}			
	}

	
	private void checkBonusPointEatenwomen(int toX, int toY) {

		for(int b = 0 ; b < bonusList.size() ; b++)
		{
			if((bonusList.get(b).getX()== toX) && (bonusList.get(b).getY() == toY))
			{
				pane.getChildren().remove(bonusList.get(b)) ;
				bonusList.remove(b) ;
				game_women.setScore(game_women.getScore()+1);
				scorelab2.setText(String.valueOf(game_women.getScore()));
				levelUpwomen();
				bonusEaten_women=true;
				isbonusUsed_women=false;
				
			}
			
	
		}			
	}

	
	/**                         --------- man *** women ----------                * */	
	
	 /**                         --------- man *** women ----------                * */
	/*
	 * check if pec point was eaten
	 */
	private void checkPecPointEaten(int toX, int toY) {
		for(int n = 0 ; n < peckpointlist.size() ; n++)
		{
			if((peckpointlist.get(n).getCenterX()-15)== toX && (peckpointlist.get(n).getCenterY()-15)== toY)
			{
//				Sound.playSound(Sound.class.getResource("../resources/eat.mp3"), 80);
				pane.getChildren().remove(peckpointlist.get(n)) ;
				peckpointlist.remove(n) ;
				game.setScore(game.getScore()+1);
				scorelab.setText(String.valueOf(game.getScore()));
				levelUp();
	
			}
			
		}
					
	}
	
	
	
	private void checkPecPointEatenwomen(int toX, int toY) {
		for(int n = 0 ; n < peckpointlist.size() ; n++)
		{
			if((peckpointlist.get(n).getCenterX()-15)== toX && (peckpointlist.get(n).getCenterY()-15)== toY)
			{
//				Sound.playSound(Sound.class.getResource("../resources/eat.mp3"), 80);
				pane.getChildren().remove(peckpointlist.get(n)) ;
				peckpointlist.remove(n) ;
				game_women.setScore(game_women.getScore()+1);
				scorelab2.setText(String.valueOf(game_women.getScore()));
				levelUpwomen();
	
			}
			
		}
					
	}
	
	
	
	/*
	 * check if a question is eaten
	 */
	private boolean checkQuestionEaten(int toX, int toY, boolean isQuestion) {

	
		for(int m = 0 ; m < questionsPoints.size() ; m++)
		{
			if((questionsPoints.get(m).getX()== toX) && (questionsPoints.get(m).getY() == toY))
			{
				QuestionMode=true;
				Level QuestionLevel= Level.valueOf(questionsPoints.get(m).getId());
				isQuestion=true;
				pane.getChildren().remove(questionsPoints.get(m)) ;
				questionsPoints.remove(m);
				Question q= SysData.getInstance().randomQuestion(QuestionLevel);
				pauseOrUnPauseGame();
				AnchorPane anchorpane= new AnchorPane();
				anchorpane.setLayoutX(95);
				anchorpane.setLayoutY(125);
				anchorpane.setPrefWidth(400);
				anchorpane.setPrefHeight(334);
				
				
				ImageView imageView = new ImageView("Photos/pacman-ghost.png");
				imageView.setLayoutX(434);
				imageView.setLayoutY(250);
				imageView.setFitWidth(100);
				imageView.setFitHeight(75);
				
				Label question = new Label(q.getQuestion());
				question.setLayoutX(23);
				question.setLayoutY(30);
				question.setFont(new Font("Rockwell", 14));
				Label level = new Label("Level: "+q.getLevel().toString());
				level.setLayoutX(23);
				level.setLayoutY(55);
				level.setFont(new Font("Rockwell", 14));
				

				
				// -----------------------------------answer 1			
					if(q.getAnswers().get(0).getContent().length() > 81)
				{
						int len= q.getAnswers().get(0).getContent().length();
					int c=81; // counter
					if(q.getAnswers().get(0).getContent().charAt(c)!=' ')
					{	while(q.getAnswers().get(0).getContent().charAt(c)!=' ')
							c= c-1;
						
						ans1= new CheckBox(q.getAnswers().get(0).getContent().substring(0, c));
						ans1.setLayoutX(29);
						ans1.setLayoutY(100);
						label_ans1= new Label(q.getAnswers().get(0).getContent().substring(c, len));
						label_ans1.setLayoutX(45);
						label_ans1.setLayoutY(118);
						if(q.level==Level.hard)
							label_ans1.setTextFill(Color.WHITE);
						anchorpane.getChildren().add(label_ans1);
					}
					else
					{
						
						ans1= new CheckBox(q.getAnswers().get(0).getContent().substring(0, c));
						ans1.setLayoutX(29);
						ans1.setLayoutY(100);
						label_ans1= new Label(q.getAnswers().get(0).getContent().substring(c, len));
						label_ans1.setLayoutX(45);
						label_ans1.setLayoutY(118);
						if(q.level==Level.hard)
							label_ans1.setTextFill(Color.WHITE);
						anchorpane.getChildren().add(label_ans1);
					}
					
				}
				else
				{
					ans1= new CheckBox(q.getAnswers().get(0).getContent());
					ans1.setLayoutX(29);
					ans1.setLayoutY(100);
				}
					// -----------------------------------answer 2 
				if(q.getAnswers().get(1).getContent().length() > 81)
				{
					int len= q.getAnswers().get(1).getContent().length();
					int c=81; // counter
					if(q.getAnswers().get(1).getContent().charAt(c)!=' ')
					{	while(q.getAnswers().get(1).getContent().charAt(c)!=' ')
							c= c-1;
						
						ans2= new CheckBox(q.getAnswers().get(1).getContent().substring(0, c));
						ans2.setLayoutX(29);
						ans2.setLayoutY(150);
						label_ans2= new Label(q.getAnswers().get(1).getContent().substring(c, len));
						label_ans2.setLayoutX(45);
						label_ans2.setLayoutY(168);
						if(q.level==Level.hard)
							label_ans2.setTextFill(Color.WHITE);
						anchorpane.getChildren().add(label_ans2);
					}
					else
					{
						
						ans2= new CheckBox(q.getAnswers().get(1).getContent().substring(0, c));
						ans2.setLayoutX(29);
						ans2.setLayoutY(150);
						label_ans2= new Label(q.getAnswers().get(1).getContent().substring(c, len));
						label_ans2.setLayoutX(45);
						label_ans2.setLayoutY(168);
						if(q.level==Level.hard)
							label_ans2.setTextFill(Color.WHITE);
						anchorpane.getChildren().add(label_ans2);
					}
				}
				else
				{
				 ans2= new CheckBox(q.getAnswers().get(1).getContent());
				 ans2.setLayoutX(29);
				 ans2.setLayoutY(150);
				}
				// -----------------------------------answer 3 
				if(q.getAnswers().get(2).getContent().length() > 81)
				{
					int len= q.getAnswers().get(2).getContent().length();
					int c=81; // counter
					if(q.getAnswers().get(2).getContent().charAt(c)!=' ')
					{	while(q.getAnswers().get(2).getContent().charAt(c)!=' ')
							c= c-1;
						
						ans3= new CheckBox(q.getAnswers().get(2).getContent().substring(0, c));
						ans3.setLayoutX(29);
						ans3.setLayoutY(200);
						label_ans3= new Label(q.getAnswers().get(2).getContent().substring(c, len));
						label_ans3.setLayoutX(45);
						label_ans3.setLayoutY(218);
						if(q.level==Level.hard)
							label_ans3.setTextFill(Color.WHITE);
						anchorpane.getChildren().add(label_ans3);
					}
					else
					{
						
						ans3= new CheckBox(q.getAnswers().get(2).getContent().substring(0, c));
						ans3.setLayoutX(29);
						ans3.setLayoutY(200);
						label_ans3= new Label(q.getAnswers().get(2).getContent().substring(c, len));
						label_ans3.setLayoutX(45);
						label_ans3.setLayoutY(218);
						if(q.level==Level.hard)
							label_ans3.setTextFill(Color.WHITE);
						anchorpane.getChildren().add(label_ans3);
					}
				}
				else
				{
					ans3= new CheckBox(q.getAnswers().get(2).getContent());
					ans3.setLayoutX(29);
					ans3.setLayoutY(200);
				}
				// -----------------------------------answer 4 					
				if(q.getAnswers().get(3).getContent().length() > 81)
				{
					int len= q.getAnswers().get(3).getContent().length();
					int c=81; // counter
					if(q.getAnswers().get(3).getContent().charAt(c)!=' ')
					{	while(q.getAnswers().get(3).getContent().charAt(c)!=' ')
							c= c-1;
						
						ans4= new CheckBox(q.getAnswers().get(3).getContent().substring(0, c));
						ans4.setLayoutX(29);
						ans4.setLayoutY(250);
						label_ans4= new Label(q.getAnswers().get(3).getContent().substring(c, len));
						label_ans4.setLayoutX(45);
						label_ans4.setLayoutY(268);
						if(q.level==Level.hard)
							label_ans4.setTextFill(Color.WHITE);
						anchorpane.getChildren().add(label_ans4);
					}
					else
					{
						
						ans4= new CheckBox(q.getAnswers().get(3).getContent().substring(0, c));
						ans4.setLayoutX(29);
						ans4.setLayoutY(250);
						label_ans4= new Label(q.getAnswers().get(3).getContent().substring(c, len));
						label_ans4.setLayoutX(45);
						label_ans4.setLayoutY(268);
						if(q.level==Level.hard)
							label_ans4.setTextFill(Color.WHITE);
						anchorpane.getChildren().add(label_ans4);
					}
				}
				else
				{
					ans4= new CheckBox(q.getAnswers().get(3).getContent());
					ans4.setLayoutX(29);
					ans4.setLayoutY(250);
				}
					
				// ------------------- submit 
				Button submit= new Button("Submit Answer");
				submit.setLayoutX(200);
				submit.setLayoutY(290);
				submit.setStyle(" -fx-background-color: \r\n"
						+ "        #090a0c,\r\n"
						+ "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\r\n"
						+ "        linear-gradient(#20262b, #191d22),\r\n"
						+ "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\r\n"
						+ "    -fx-background-radius: 5,4,3,5;\r\n"
						+ "    -fx-background-insets: 0,1,2,0;\r\n"
						+ "    -fx-text-fill: white;\r\n"
						+ "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\r\n"
						+ "    -fx-font-family: \"Arial\";\r\n"
						+ "    -fx-text-fill: linear-gradient(white, #d0d0d0);\r\n"
						+ "    -fx-font-size: 12px;\r\n"
						+ "    -fx-padding: 10 20 10 20;");
				Label label= new Label();
				label.setLayoutX(70);
				label.setLayoutY(280);
				label.setVisible(false);
				
				if(q.level==Level.easy)
					anchorpane.setStyle("-fx-background-color: #ffffff");
				if(q.level==Level.medium)
					anchorpane.setStyle("-fx-background-color: #DAA520");
				if(q.level==Level.hard)
				{
					anchorpane.setStyle("-fx-background-color: #B22222");
					question.setTextFill(Color.WHITE);
					level.setTextFill(Color.WHITE);
					ans1.setTextFill(Color.WHITE);
					ans2.setTextFill(Color.WHITE);
					ans3.setTextFill(Color.WHITE);
					ans4.setTextFill(Color.WHITE);
					label.setTextFill(Color.WHITE);
				}

				anchorpane.getChildren().add(question);
				anchorpane.getChildren().add(level);
				anchorpane.getChildren().add(imageView);
				anchorpane.getChildren().add(ans1);
				anchorpane.getChildren().add(ans2);
				anchorpane.getChildren().add(ans3);
				anchorpane.getChildren().add(ans4);
				anchorpane.getChildren().add(submit);
				anchorpane.getChildren().add(label);

				pane.getChildren().add(anchorpane);
				
				
				submit.setOnMouseClicked(new javafx.event.EventHandler<Event>() {
					private Timeline timeline5;

					@Override
					public void handle(Event arg0) {
						int numofsel=0;
							if(ans1.isSelected()) numofsel++;
							if(ans2.isSelected()) numofsel++;
							if(ans3.isSelected()) numofsel++;
							if(ans4.isSelected()) numofsel++;
							if(numofsel==0)
							{
								label.setText("You must select an answer");
								label.setVisible(true);

							}
							if(numofsel>1)
							{
								label.setText("You must select only one answer");
								label.setVisible(true);

							}
							if(numofsel==1)
							{
								int rightAns=1;
								if(ans1.isSelected()) rightAns=1;
								if(ans2.isSelected()) rightAns=2;
								if(ans3.isSelected()) rightAns=3;
								if(ans4.isSelected()) rightAns=4;
								if(q.getTrueAnswer()==rightAns) {
									Sound.playSound(Sound.class.getResource("../resources/correct.mp3"), 80);
									if(q.getLevel()==Level.easy)
										game.setScore(game.getScore()+1);
									if(q.getLevel()==Level.medium)
										game.setScore(game.getScore()+2);
									if(q.getLevel()==Level.hard)
										game.setScore(game.getScore()+3);
									scorelab.setText(String.valueOf(game.getScore()));
									levelUp();
									//show that answer is right
									if(q.getTrueAnswer()==1)
									{
										ans1.setStyle("-fx-background-color: #7CFC00");
										label_ans1.setStyle("-fx-background-color: #7CFC00");
									}
									if(q.getTrueAnswer()==2)
									{
										ans2.setStyle("-fx-background-color: #7CFC00");
										label_ans2.setStyle("-fx-background-color: #7CFC00");
									}
									if(q.getTrueAnswer()==3)
									{
										ans3.setStyle("-fx-background-color: #7CFC00");
										label_ans3.setStyle("-fx-background-color: #7CFC00");
									}
									if(q.getTrueAnswer()==4)
									{
										ans4.setStyle("-fx-background-color: #7CFC00");
										label_ans4.setStyle("-fx-background-color: #7CFC00");
									}


									showCorrectAns = new KeyFrame(Duration.millis(1000), e->
									{
										pane.getChildren().remove(anchorpane);
										replaceQuestionOnBoard(q.level);
										pauseOrUnPauseGame();
										QuestionMode=false;

											});
											timeline5 = new Timeline(showCorrectAns) ;
											timeline5.play() ;
										
								
								}
								else
								{
									Sound.playSound(Sound.class.getResource("../resources/wrong.mp3"), 80);
									if(q.getLevel()==Level.easy)
										game.setScore(game.getScore()-10);
									if(q.getLevel()==Level.medium)
										game.setScore(game.getScore()-20);
									if(q.getLevel()==Level.hard)
										game.setScore(game.getScore()-30);
									if(game.getScore()<0)
										game.setScore(0);
									scorelab.setText(String.valueOf(game.getScore()));
									leveldown();
									Timer timer = new Timer();
									//show selected answer in red and right answer in green
									if(ans1.isSelected()) ans1.setStyle("-fx-background-color: #fe0400");
									if(ans2.isSelected()) ans2.setStyle("-fx-background-color: #fe0400");
									if(ans3.isSelected()) ans3.setStyle("-fx-background-color: #fe0400");
									if(ans4.isSelected()) ans4.setStyle("-fx-background-color: #fe0400");
									if(q.getTrueAnswer()==1)
										ans1.setStyle("-fx-background-color: #7CFC00");
									if(q.getTrueAnswer()==2)
										ans2.setStyle("-fx-background-color: #7CFC00");
									if(q.getTrueAnswer()==3)
										ans3.setStyle("-fx-background-color: #7CFC00");
									if(q.getTrueAnswer()==4)
										ans4.setStyle("-fx-background-color: #7CFC00");
									
									showCorrectAns = new KeyFrame(Duration.millis(1000), e->
									{
										pane.getChildren().remove(anchorpane);
										replaceQuestionOnBoard(q.level);
										pauseOrUnPauseGame();
										QuestionMode=false;


											});
											timeline5 = new Timeline(showCorrectAns) ;
											timeline5.play() ;
											
								}
						
							}
						 
							 
					}
				});


			}
		
		}
		return isQuestion;
	}
	
	
	
	/**                         --------- man *** women ----------                * */		
	
	 /**                         --------- man *** women ----------                * */
	/*
	 * return peckPoints 30 seconds after eating them
	 */
	private void returnPeckPoints(int fromX, int fromY) {
		
		retrunPeckPoints = new KeyFrame(Duration.millis(30000), e->
		{
			if(game.gameState==GameState.Running) {
			
		
			for(int i=0; i<bonusList.size();i++)
			{
				if(bonusList.get(i).getX()!=fromX && bonusList.get(i).getY()!=fromY) {
					
					
					//on level 2- pacman can move from walls(transitions), there could be a chance that a pec point comes on a wall after it closes
						
					if( (fromX!=90 && fromY!=0) || (fromX!=90 && fromY!=600) || (fromX!=0 && fromY!=300) || (fromX!=600 && fromY!=300) ) {
						Circle peckPoint = (Circle) ShapeFactory.getShapeObject("Circle" , fromX, fromY, ObjectSize);
						
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
							
							if(pacman.getCurrentLocation().getRow()== fromX && pacman.getCurrentLocation().getColumn() == fromY)
							{
								toadd=false;
							}
						}
						
						if(toadd == true) {
						pane.getChildren().add(peckPoint) ;
						peckpointlist.add(peckPoint) ;
						}
					}
				}
							}
							}
							
					});
						pecPoints_timeline = new Timeline(retrunPeckPoints) ;
						pecPoints_timeline.setCycleCount(Timeline.INDEFINITE) ;
						pecPoints_timeline.play() ;
							
}
	
	
	
	private void returnPeckPointswomen(int fromX, int fromY) {
		
		retrunPeckPoints = new KeyFrame(Duration.millis(30000), e->
		{
			if(game_women.gameStatewomen==GameStateWomen.Running) {
			
		
			for(int i=0; i<bonusList.size();i++)
			{
				if(bonusList.get(i).getX()!=fromX && bonusList.get(i).getY()!=fromY) {
					
					
					//on level 2- pacman can move from walls(transitions), there could be a chance that a pec point comes on a wall after it closes
						
					if( (fromX!=90 && fromY!=0) || (fromX!=90 && fromY!=600) || (fromX!=0 && fromY!=300) || (fromX!=600 && fromY!=300) ) {
						Circle peckPoint = (Circle) ShapeFactory.getShapeObject("Circle" , fromX, fromY, ObjectSize);
						
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
							
							if(pacwomen.getCurrentLocation().getRow()== fromX && pacwomen.getCurrentLocation().getColumn() == fromY)
							{
								toadd=false;
							}
						}
						
						if(toadd == true) {
						pane.getChildren().add(peckPoint) ;
						peckpointlist.add(peckPoint) ;
						}
					}
				}
							}
							}
							
					});
						pecPoints_timeline = new Timeline(retrunPeckPoints) ;
						pecPoints_timeline.setCycleCount(Timeline.INDEFINITE) ;
						pecPoints_timeline.play() ;
							
}
	
	
	/**                         --------- man *** women ----------                * */		
	
	
	 /**                         --------- man *** women ----------                * */
	/*
	 * when the ghost eat a bomb point he has the right to bomb it by clicking the mouse
	 * if a ghost is away 3 cells from pacman and the pacman clicked on the pane, the ghost stops moving and disappears for 5 second
	 */
	private void pauseGhost() {
		pane.setOnMouseClicked(new javafx.event.EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				if(isbonusUsed==false) {
		
				if(isbonusUsed==false && checkGhostInRadar(redGhost)==true) {
					if(pacman.getColor() == Utils.Color.yellow) {
						pacman.setColor( Utils.Color.green);	
						
				}
				else if(pacman.getColor()== Utils.Color.green)
				{
					pacman.setColor( Utils.Color.yellow);	

				}
					ghosts_timeline.stop();
					ghosts_timeline.getKeyFrames().clear();

					ghosts_keyFrame = new KeyFrame(Duration.millis(redGhost.getSpeed()), e->
					{
						if(game.gameState==GameState.Running) {
						pane.getChildren().remove(redGhost.getImage());
					 movePink();
					 moveBlue();
						}
					 
					});
					ghosts_timeline = new Timeline(ghosts_keyFrame) ;
					ghosts_timeline.setCycleCount(Timeline.INDEFINITE) ;
					ghosts_timeline.play() ;
					
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						
						@Override
						public void run() {
							ghosts_timeline.stop();
							ghosts_timeline.getKeyFrames().clear();
							moveGhostAtSpeed();
							}
					}, 5000);

					isbonusUsed=true;
				}
				
				if(isbonusUsed==false && checkGhostInRadar(blueGhost)==true) {
					if(pacman.getColor() == Utils.Color.yellow) {
						pacman.setColor(Utils.Color.green);;	
						
				}
				else if(pacman.getColor()== Utils.Color.green)
				{
					pacman.setColor(Utils.Color.yellow);

				}
					ghosts_timeline.stop();
					ghosts_timeline.getKeyFrames().clear();

					ghosts_keyFrame = new KeyFrame(Duration.millis(redGhost.getSpeed()), e->
					{
						if(game.gameState==GameState.Running) {
							pane.getChildren().remove(blueGhost.getImage());

					 movePink();
					 moveRed();
						}
					 
					});
					ghosts_timeline = new Timeline(ghosts_keyFrame) ;
					ghosts_timeline.setCycleCount(Timeline.INDEFINITE) ;
					ghosts_timeline.play() ;
					
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						
						@Override
						public void run() {
							ghosts_timeline.stop();
							ghosts_timeline.getKeyFrames().clear();
							moveGhostAtSpeed();
						}
					}, 5000);
					isbonusUsed=true;
							}
				
				if(isbonusUsed==false && checkGhostInRadar(pinkGhost)==true) {
					if(pacman.getColor() == Utils.Color.yellow) {
						pacman.setColor(Utils.Color.green);	
						
				}
				else if(pacman.getColor()== Utils.Color.green)
				{
					pacman.setColor(Utils.Color.yellow);	

				}
					ghosts_timeline.stop();
					ghosts_timeline.getKeyFrames().clear();
					ghosts_keyFrame = new KeyFrame(Duration.millis(redGhost.getSpeed()), e->
					{
						if(game.gameState==GameState.Running) {
							pane.getChildren().remove(pinkGhost.getImage());

					 moveBlue();
					 moveRed();
						}
					 
					});
					ghosts_timeline = new Timeline(ghosts_keyFrame) ;
					ghosts_timeline.setCycleCount(Timeline.INDEFINITE) ;
					ghosts_timeline.play() ;
					
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						
						@Override
						public void run() {
							ghosts_timeline.stop();
							ghosts_timeline.getKeyFrames().clear();
							moveGhostAtSpeed();
							}
					}, 5000);
					isbonusUsed=true;
					}

				}

			}
			
		});
}
	
	
	
	
	private void pauseGhostwomen() {
		pane.setOnMouseClicked(new javafx.event.EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				if(isbonusUsed_women==false) {
		
				if(isbonusUsed_women==false && checkGhostInRadarwomen(redGhost)==true) {
					if(pacwomen.getColor() == Utils.Color.pink) {
						pacwomen.setColor( Utils.Color.green);	
						
				}
				else if(pacwomen.getColor()== Utils.Color.green)
				{
					pacwomen.setColor( Utils.Color.pink);	

				}
					ghosts_timeline.stop();
					ghosts_timeline.getKeyFrames().clear();

					ghosts_keyFrame = new KeyFrame(Duration.millis(redGhost.getSpeed()), e->
					{
						if(game_women.gameState==GameState.Running) {
						pane.getChildren().remove(redGhost.getImage());
					 movePink();
					 moveBlue();
						}
					 
					});
					ghosts_timeline = new Timeline(ghosts_keyFrame) ;
					ghosts_timeline.setCycleCount(Timeline.INDEFINITE) ;
					ghosts_timeline.play() ;
					
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						
						@Override
						public void run() {
							ghosts_timeline.stop();
							ghosts_timeline.getKeyFrames().clear();
							moveGhostAtSpeed();
							}
					}, 5000);

					isbonusUsed_women=true;
				}
				
				if(isbonusUsed_women==false && checkGhostInRadarwomen(blueGhost)==true) {
					if(pacwomen.getColor() == Utils.Color.pink) {
						pacwomen.setColor(Utils.Color.green);;	
						
				}
				else if(pacwomen.getColor()== Utils.Color.green)
				{
					pacwomen.setColor(Utils.Color.pink);

				}
					ghosts_timeline.stop();
					ghosts_timeline.getKeyFrames().clear();

					ghosts_keyFrame = new KeyFrame(Duration.millis(redGhost.getSpeed()), e->
					{
						if(game_women.gameState==GameState.Running) {
							pane.getChildren().remove(blueGhost.getImage());

					 movePink();
					 moveRed();
						}
					 
					});
					ghosts_timeline = new Timeline(ghosts_keyFrame) ;
					ghosts_timeline.setCycleCount(Timeline.INDEFINITE) ;
					ghosts_timeline.play() ;
					
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						
						@Override
						public void run() {
							ghosts_timeline.stop();
							ghosts_timeline.getKeyFrames().clear();
							moveGhostAtSpeed();
						}
					}, 5000);
					isbonusUsed_women=true;
							}
				
				if(isbonusUsed_women==false && checkGhostInRadarwomen(pinkGhost)==true) {
					if(pacwomen.getColor() == Utils.Color.pink) {
						pacwomen.setColor(Utils.Color.green);	
						
				}
				else if(pacwomen.getColor()== Utils.Color.green)
				{
					pacwomen.setColor(Utils.Color.pink);	

				}
					ghosts_timeline.stop();
					ghosts_timeline.getKeyFrames().clear();
					ghosts_keyFrame = new KeyFrame(Duration.millis(redGhost.getSpeed()), e->
					{
						if(game.gameState==GameState.Running) {
							pane.getChildren().remove(pinkGhost.getImage());

					 moveBlue();
					 moveRed();
						}
					 
					});
					ghosts_timeline = new Timeline(ghosts_keyFrame) ;
					ghosts_timeline.setCycleCount(Timeline.INDEFINITE) ;
					ghosts_timeline.play() ;
					
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						
						@Override
						public void run() {
							ghosts_timeline.stop();
							ghosts_timeline.getKeyFrames().clear();
							moveGhostAtSpeed();
							}
					}, 5000);
					isbonusUsed_women=true;
					}

				}

			}
			
		});


		

}
	
	
	/**                         --------- man *** women ----------                * */			
	
	 /**                         --------- man *** women ----------                * */
	/*  
	 * check if ghost is in radar
	 */
	private boolean checkGhostInRadar(Ghost ghost) {
		boolean toReturn=false;
		int pacmanX= pacman.getCurrentLocation().getRow();
		int pacmanY= pacman.getCurrentLocation().getColumn();
		int ghostX= (int) ghost.getCurrentLocation().getRow();
		int ghostY= (int) ghost.getCurrentLocation().getColumn();
		if((pacmanY-ghostY <= 90 && pacmanX==ghostX)
				|| (pacmanX-ghostX <= 90 && pacmanY==ghostY)|| (ghostY-pacmanY <= 90 && ghostX==pacmanX)|| (ghostX-pacmanX <= 90 && ghostY==pacmanY))
			toReturn=true;
		return toReturn;
		
	}
	
	
	private boolean checkGhostInRadarwomen(Ghost ghost) {
		boolean toReturn=false;
		int pacmanX= pacwomen.getCurrentLocation().getRow();
		int pacmanY= pacwomen.getCurrentLocation().getColumn();
		int ghostX= (int) ghost.getCurrentLocation().getRow();
		int ghostY= (int) ghost.getCurrentLocation().getColumn();
		if((pacmanY-ghostY <= 90 && pacmanX==ghostX)
				|| (pacmanX-ghostX <= 90 && pacmanY==ghostY)|| (ghostY-pacmanY <= 90 && ghostX==pacmanX)|| (ghostX-pacmanX <= 90 && ghostY==pacmanY))
			toReturn=true;
		return toReturn;
		
	}
	
	/**                         --------- man *** women ----------                * */	
	
	 /**                         --------- man *** women ----------                * */
	/*
	 * when a user answers a wrong answer, he might go down with the level, then some features might not exist/might exist
	 */
	private void leveldown() {
		/*
		 * level1: pacman-speed=300, ghost-speed=280
		 * level2: pacman-speed=290, ghost-speed=270
		 * level3: pacman-speed=270, ghost-speed=270
		 * level4: pacman-speed=260, ghost-speed=250
		 */
		if(game.score>=0 && game.score<=50) {
			
			game.setLevel(Level.easy);
			pacman.setSpeed(300);
			redGhost.setSpeed(280);
			pinkGhost.setSpeed(280);
			blueGhost.setSpeed(280);
			levelDown=true;
		 
		}
			if(game.score>50 && game.score<=100) {
				
				game.setLevel(Level.medium);
				pacman.setSpeed(290);
				redGhost.setSpeed(270);
				pinkGhost.setSpeed(270);
				blueGhost.setSpeed(270);

				levelDown=true;

			  
			}
			if(game.score>100 && game.score<=150) {
				
				game.setLevel(Level.hard);
				pacman.setSpeed(270);
				redGhost.setSpeed(270);
				pinkGhost.setSpeed(270);
				blueGhost.setSpeed(270);
				
				levelDown=true;
	
			}
			
			if(game.getLevel() == Level.easy && levelDown==true)	{
				if(QuestionMode==false) {
					boolean toaddwall=false;
					Rectangle wall1 =(Rectangle) ShapeFactory.getShapeObject("Rectangle" , 90, 0, ObjectSize); // pass in x, y, width and height
					Rectangle wall2 =(Rectangle) ShapeFactory.getShapeObject("Rectangle" , 90, 600, ObjectSize); // pass in x, y, width and height
					Rectangle wall3 =(Rectangle) ShapeFactory.getShapeObject("Rectangle" , 0, 300, ObjectSize); // pass in x, y, width and height
					Rectangle wall4 =(Rectangle) ShapeFactory.getShapeObject("Rectangle" , 600, 300, ObjectSize); // pass in x, y, width and height
					for(int i=0;i<wallList.size();i++)
					{
						if(wallList.get(i).getX()==wall1.getX() && wallList.get(i).getY()==wall1.getY() )
							toaddwall=true;
					}
					if(toaddwall==true) {
						pane.getChildren().add(wall1) ;
						pane.getChildren().add(wall2) ;
						pane.getChildren().add(wall3) ;
						pane.getChildren().add(wall4) ;
						
						wallList.add(wall1);
						wallList.add(wall2);
						wallList.add(wall3);
						wallList.add(wall4);
					}
					 levelDown=false;
					 
				}
							
			}
		
			if(game.getLevel() == Level.medium && levelDown==true)	{
					pacman_timeline.stop();  
					pacman_timeline.getKeyFrames().clear();
					movePackmanAtSpeed();
				 for(int c = 0 ; c < wallList.size() ; c++)
					{	
						if((wallList.get(c).getX())== 90 && (wallList.get(c).getY())== 0)
						{
							pane.getChildren().remove(wallList.get(c)) ;
							wallList.remove(c);
						}
						if((wallList.get(c).getX())== 90 && (wallList.get(c).getY())== 600)
						{
							pane.getChildren().remove(wallList.get(c)) ;
							wallList.remove(c);
	
						}
						
						if((wallList.get(c).getX())== 0 && (wallList.get(c).getY())== 300)
						{
							pane.getChildren().remove(wallList.get(c)) ;
							wallList.remove(c);
	
						}
						if((wallList.get(c).getX())== 600 && (wallList.get(c).getY())== 300)
						{
							pane.getChildren().remove(wallList.get(c)) ;
							wallList.remove(c);
	
						}
					}
				 levelDown=false;
	
			}
			
			if(game.getLevel()== Level.hard && levelDown==true)
			{
				pacman_timeline.stop();  
				pacman_timeline.getKeyFrames().clear();
				ghosts_timeline.stop();  
				ghosts_timeline.getKeyFrames().clear();
				levelDown=false;	
				movePackmanAtSpeed();
				moveGhostAtSpeed();
			}
			
			
	}

	
	private void leveldownwomen() {
		if(game_women.score>=0 && game_women.score<=50) {
			
			game_women.setLevel(Level.easy);
			pacwomen.setSpeed(300);
			redGhost.setSpeed(280);
			pinkGhost.setSpeed(280);
			blueGhost.setSpeed(280);
			levelDown_women=true;
		 
		}
			if(game_women.score>50 && game_women.score<=100) {
				
				game_women.setLevel(Level.medium);
				pacwomen.setSpeed(290);
				redGhost.setSpeed(270);
				pinkGhost.setSpeed(270);
				blueGhost.setSpeed(270);

				levelDown_women=true;

			  
			}
			if(game_women.score>100 && game_women.score<=150) {
				
				game_women.setLevel(Level.hard);
				pacwomen.setSpeed(270);
				redGhost.setSpeed(270);
				pinkGhost.setSpeed(270);
				blueGhost.setSpeed(270);
				
				levelDown_women=true;
	
			}
			
			if(game_women.getLevel() == Level.easy && levelDown_women==true)	{
				if(QuestionMode==false) {
					boolean toaddwall=false;
					Rectangle wall1 =(Rectangle) ShapeFactory.getShapeObject("Rectangle" , 90, 0, ObjectSize); // pass in x, y, width and height
					Rectangle wall2 =(Rectangle) ShapeFactory.getShapeObject("Rectangle" , 90, 600, ObjectSize); // pass in x, y, width and height
					Rectangle wall3 =(Rectangle) ShapeFactory.getShapeObject("Rectangle" , 0, 300, ObjectSize); // pass in x, y, width and height
					Rectangle wall4 =(Rectangle) ShapeFactory.getShapeObject("Rectangle" , 600, 300, ObjectSize); // pass in x, y, width and height
					for(int i=0;i<wallList.size();i++)
					{
						if(wallList.get(i).getX()==wall1.getX() && wallList.get(i).getY()==wall1.getY() )
							toaddwall=true;
					}
					if(toaddwall==true) {
						pane.getChildren().add(wall1) ;
						pane.getChildren().add(wall2) ;
						pane.getChildren().add(wall3) ;
						pane.getChildren().add(wall4) ;
						
						wallList.add(wall1);
						wallList.add(wall2);
						wallList.add(wall3);
						wallList.add(wall4);
					}
					 levelDown_women=false;
					 
				}
							
			}
		
			if(game_women.getLevel() == Level.medium && levelDown_women==true)	{
					pacwomen_timeline.stop();  
					pacwomen_timeline.getKeyFrames().clear();
					movePacwomenAtSpeed();
				 for(int c = 0 ; c < wallList.size() ; c++)
					{	
						if((wallList.get(c).getX())== 90 && (wallList.get(c).getY())== 0)
						{
							pane.getChildren().remove(wallList.get(c)) ;
							wallList.remove(c);
						}
						if((wallList.get(c).getX())== 90 && (wallList.get(c).getY())== 600)
						{
							pane.getChildren().remove(wallList.get(c)) ;
							wallList.remove(c);
	
						}
						
						if((wallList.get(c).getX())== 0 && (wallList.get(c).getY())== 300)
						{
							pane.getChildren().remove(wallList.get(c)) ;
							wallList.remove(c);
	
						}
						if((wallList.get(c).getX())== 600 && (wallList.get(c).getY())== 300)
						{
							pane.getChildren().remove(wallList.get(c)) ;
							wallList.remove(c);
	
						}
					}
				 levelDown_women=false;
	
			}
			
			if(game_women.getLevel()== Level.hard && levelDown_women==true)
			{
				pacwomen_timeline.stop();  
				pacwomen_timeline.getKeyFrames().clear();
				ghosts_timeline.stop();  
				ghosts_timeline.getKeyFrames().clear();
				levelDown_women=false;	
				movePacwomenAtSpeed();
				moveGhostAtSpeed();  
			}
			
			
	}

	/**                         --------- man *** women ----------                * */		
	
	 /**                         --------- man *** women ----------                * */
	/*
	 * update level according to player score
	 * add features on every level that the player reaches to
	 */
	private void levelUp() {
			/*
			 * level1: pacman-speed=300, ghost-speed=280
			 * level2: pacman-speed=290, ghost-speed=270
			 * level3: pacman-speed=270, ghost-speed=270
			 * level4: pacman-speed=260, ghost-speed=250
			 */
		
		
			if(game.score>50 && game.score<=100) {
				
				
				game.setLevel(Level.medium);
				pacman.setSpeed(290);
				redGhost.setSpeed(270);
				pinkGhost.setSpeed(270);
				blueGhost.setSpeed(270);
				levelUp= true;
			}
			if(game.score>100 && game.score<=150) {
				
				game.setLevel(Level.hard);
				pacman.setSpeed(270);
				redGhost.setSpeed(270);
				pinkGhost.setSpeed(270);
				blueGhost.setSpeed(270);
				
				levelUp= true;
	
			}
			if(game.score>150 && game.score<200) {
				
				game.setLevel(Level.super_hard);
				pacman.setSpeed(260);
				redGhost.setSpeed(250);
				pinkGhost.setSpeed(250);
				blueGhost.setSpeed(250);

				levelUp= true;
	
			}
			if(game.score>=200) {
				WinGame();
			}
			
			if(game.getLevel() == Level.medium && levelUp==true)	{
				
				 for(int c = 0 ; c < wallList.size() ; c++)
					{
						if((wallList.get(c).getX())== 90 && (wallList.get(c).getY())== 0)
						{
							pane.getChildren().remove(wallList.get(c)) ;
							wallList.remove(c);
						}
						if((wallList.get(c).getX())== 90 && (wallList.get(c).getY())== 600)
						{
							pane.getChildren().remove(wallList.get(c)) ;
							wallList.remove(c);
	
						}
						
						if((wallList.get(c).getX())== 0 && (wallList.get(c).getY())== 300)
						{
							pane.getChildren().remove(wallList.get(c)) ;
							wallList.remove(c);
	
						}
						if((wallList.get(c).getX())== 600 && (wallList.get(c).getY())== 300)
						{
							pane.getChildren().remove(wallList.get(c)) ;
							wallList.remove(c);
	
						}
					}
				 levelUp=false;
	
			}
			
			
			if(game.getLevel() == Level.hard && levelUp==true)	{
				
				Rectangle wall1 =(Rectangle) ShapeFactory.getShapeObject("Rectangle" , 90, 0, ObjectSize); // pass in x, y, width and height
				Rectangle wall2 =(Rectangle) ShapeFactory.getShapeObject("Rectangle" , 90, 600, ObjectSize); // pass in x, y, width and height
				Rectangle wall3 =(Rectangle) ShapeFactory.getShapeObject("Rectangle" , 0, 300, ObjectSize); // pass in x, y, width and height
				Rectangle wall4 =(Rectangle) ShapeFactory.getShapeObject("Rectangle" , 600, 300, ObjectSize); // pass in x, y, width and height
	
	
				pane.getChildren().add(wall1) ;
				pane.getChildren().add(wall2) ;
				pane.getChildren().add(wall3) ;
				pane.getChildren().add(wall4) ;
	
				wallList.add(wall1);
				wallList.add(wall2);
				wallList.add(wall3);
				wallList.add(wall4);
	
				pacman_timeline.stop();  
				pacman_timeline.getKeyFrames().clear();
				levelUp=false;	
				movePackmanAtSpeed();
				
			}			
			
			if(game.getLevel() == Level.super_hard && levelUp==true)	{
				
				pacman_timeline.stop();  
				pacman_timeline.getKeyFrames().clear();
				ghosts_timeline.stop();  
				ghosts_timeline.getKeyFrames().clear();
				levelUp=false;	
				movePackmanAtSpeed();
				moveGhostAtSpeed();
				
			}			
	}
	
	
	
	private void levelUpwomen() {
		/*
		 * level1: pacman-speed=300, ghost-speed=280
		 * level2: pacman-speed=290, ghost-speed=270
		 * level3: pacman-speed=270, ghost-speed=270
		 * level4: pacman-speed=260, ghost-speed=250
		 */
	
	
		if(game_women.score>50 && game_women.score<=100) {
			
			
			game_women.setLevel(Level.medium);
			pacwomen.setSpeed(290);
			redGhost.setSpeed(270);
			pinkGhost.setSpeed(270);
			blueGhost.setSpeed(270);
			levelUp_women= true;
		}
		if(game_women.score>100 && game_women.score<=150) {
			
			game_women.setLevel(Level.hard);
			pacwomen.setSpeed(270);
			redGhost.setSpeed(270);
			pinkGhost.setSpeed(270);
			blueGhost.setSpeed(270);
			
			levelUp_women= true;

		}
		if(game_women.score>150 && game_women.score<200) {
			
			game_women.setLevel(Level.super_hard);
			pacwomen.setSpeed(260);
			redGhost.setSpeed(250);
			pinkGhost.setSpeed(250);
			blueGhost.setSpeed(250);

			levelUp_women= true;

		}
		if(game_women.score>=200) {
			WinGamewomen();
		}
		
		if(game_women.getLevel() == Level.medium && levelUp_women==true)	{
			
			 for(int c = 0 ; c < wallList.size() ; c++)
				{
					if((wallList.get(c).getX())== 90 && (wallList.get(c).getY())== 0)
					{
						pane.getChildren().remove(wallList.get(c)) ;
						wallList.remove(c);
					}
					// 888888888888888888888888888888888888888888888888888888888888888
					if((wallList.get(c).getX())== 90 && (wallList.get(c).getY())== 600)
					{
						pane.getChildren().remove(wallList.get(c)) ;
						wallList.remove(c);

					}
					
					if((wallList.get(c).getX())== 0 && (wallList.get(c).getY())== 300)
					{
						pane.getChildren().remove(wallList.get(c)) ;
						wallList.remove(c);

					}
					if((wallList.get(c).getX())== 600 && (wallList.get(c).getY())== 300)
					{
						pane.getChildren().remove(wallList.get(c)) ;
						wallList.remove(c);

					}
				}
			 levelUp_women=false;

		}
		
		
		if(game_women.getLevel() == Level.hard && levelUp_women==true)	{
			
			Rectangle wall1 =(Rectangle) ShapeFactory.getShapeObject("Rectangle" , 90, 0, ObjectSize); // pass in x, y, width and height
			Rectangle wall2 =(Rectangle) ShapeFactory.getShapeObject("Rectangle" , 90, 600, ObjectSize); // pass in x, y, width and height
			Rectangle wall3 =(Rectangle) ShapeFactory.getShapeObject("Rectangle" , 0, 300, ObjectSize); // pass in x, y, width and height
			Rectangle wall4 =(Rectangle) ShapeFactory.getShapeObject("Rectangle" , 600, 300, ObjectSize); // pass in x, y, width and height


			pane.getChildren().add(wall1) ;
			pane.getChildren().add(wall2) ;
			pane.getChildren().add(wall3) ;
			pane.getChildren().add(wall4) ;

			wallList.add(wall1);
			wallList.add(wall2);
			wallList.add(wall3);
			wallList.add(wall4);

			pacwomen_timeline.stop();  
			pacwomen_timeline.getKeyFrames().clear();
			levelUp_women=false;	
			movePacwomenAtSpeed();
			
		}			
		
		if(game_women.getLevel() == Level.super_hard && levelUp_women==true)	{
			
			pacwomen_timeline.stop();  
			pacwomen_timeline.getKeyFrames().clear();
			ghosts_timeline.stop();  
			ghosts_timeline.getKeyFrames().clear();
			levelUp_women=false;	
			movePacwomenAtSpeed();
			moveGhostAtSpeed();
			
		}			
}
	
	/**                         --------- man *** women ----------                * */	
	
	
	 /**                         --------- man *** women ----------                * */
	/*
	 * this function tells the user that he won, then the game board changes to the leader board game
	 */
		private void WinGame() {
			pauseOrUnPauseGame();
			Sound.playSound(Sound.class.getResource("../resources/levelup.mp3"), 80);
			ImageView imageView = new ImageView("Photos/player1-removebg-preview.png");
			imageView.setLayoutX(140);
			imageView.setLayoutY(190);
			imageView.setFitWidth(350);
			imageView.setFitHeight(200);
			
			pane.getChildren().add(imageView);
            SysData.getInstance().addGameHistory(new Player(namelab.getText(), game.score,Calendar.getInstance().getTime()));;

			Timer timer = new Timer();

			TimerTask task = new TimerTask()
			{
			        public void run()
			        { 
			            Platform.runLater(() -> {
						((Stage) pane.getScene().getWindow()).close();
						ViewLogic.leaderBoardWindow();
			            });
			        
			        }

			};
			timer.schedule(task,1000);
			
	}
	
		private void WinGamewomen() {
			pauseOrUnPauseGame();
			Sound.playSound(Sound.class.getResource("../resources/levelup.mp3"), 80);
			ImageView imageView = new ImageView("Photos/player2-removebg-preview.png");
			imageView.setLayoutX(140);
			imageView.setLayoutY(190);
			imageView.setFitWidth(350);
			imageView.setFitHeight(200);
			
			pane.getChildren().add(imageView);
            SysData.getInstance().addGameHistory(new Player(namelab2.getText(), game_women.score,Calendar.getInstance().getTime()));;

			Timer timer = new Timer();

			TimerTask task = new TimerTask()
			{
			        public void run()
			        { 
			            Platform.runLater(() -> {
						((Stage) pane.getScene().getWindow()).close();
						ViewLogic.leaderBoardWindow();
			            });
			        
			        }

			};
			timer.schedule(task,1000);
			
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

	

	
	/**                         --------- man *** women ----------                * */	
	
	 /**                         --------- man *** women ----------                * */
	/*
	 * move pacman at a speed 
	 * speed is defined as: number of cells a pacman can move through within a specific time(in millisec)
	 * at every level we update the speed, so the pacman always moves according to the level's speed
	 */
	private void movePackmanAtSpeed() {
		pacman_keyFrame = new KeyFrame(Duration.millis(pacman.getSpeed()), e->
		{		
			if(game.gameState==GameState.Running) {
		

			if(caughtPacman((int) redGhost.getCurrentLocation().getRow(),(int) redGhost.getCurrentLocation().getColumn(),pacman.getCurrentLocation().getRow(),pacman.getCurrentLocation().getColumn())==true)
			{
				System.out.println("Reeeed BYEEEEE");
				game.setLive(game.getLive()-1);
			
			if(game.getLive()==0)
			{
				game.setGameState(GameState.Finished);
				
				//pauseOrUnPauseGame();
				GameOver();				
					
			}
			else 
			{
				
				if(game.getLive()<=2)
				{
					if(game.getLive()==2)
					{
						life3.setVisible(false);
					}
					if(game.getLive()==1)
					{		
						life3.setVisible(false);
		
						life2.setVisible(false);
					}
					pacman.getCurrentLocation().setRow(300);
					pacman.getCurrentLocation().setColumn(570);
					pane.getChildren().removeAll();
					redGhost.getCurrentLocation().setRow(300);
					redGhost.getImage().setX(300);
					redGhost.getCurrentLocation().setColumn(240);
					redGhost.getImage().setY(240);
					blueGhost.getCurrentLocation().setRow(270);
					blueGhost.getImage().setX(270);
					blueGhost.getCurrentLocation().setColumn(240);
					blueGhost.getImage().setY(240);
					pinkGhost.getCurrentLocation().setColumn(240);
					pinkGhost.getImage().setY(240);
					pinkGhost.getCurrentLocation().setRow(330);
					pinkGhost.getImage().setX(330);
		
					resume();
				}
				
				
			}
			
		}
		if(caughtPacman((int) blueGhost.getCurrentLocation().getRow(),(int)blueGhost.getCurrentLocation().getColumn(),pacman.getCurrentLocation().getRow(),pacman.getCurrentLocation().getColumn())==true)
		{
			System.out.println("Bluee BYEEEEE");
			game.setLive(game.getLive()-1);
			if(game.getLive()==0)
			{
				game.setGameState(GameState.Finished);
				//pauseOrUnPauseGame();
				GameOver();
				}
			else
			{
				
				if(game.getLive()<=2)
				{
					if(game.getLive()==2)
					{
						life3.setVisible(false);
					}
					if(game.getLive()==1)
					{		
						life3.setVisible(false);
		
						life2.setVisible(false);
					}
					pane.getChildren().removeAll();
					pacman.getCurrentLocation().setRow(300);
					pacman.getCurrentLocation().setColumn(570);
					redGhost.getCurrentLocation().setRow(300);
					redGhost.getImage().setX(300);
					redGhost.getCurrentLocation().setColumn(240);
					redGhost.getImage().setY(240);
					blueGhost.getCurrentLocation().setRow(270);
					blueGhost.getImage().setX(270);
					blueGhost.getCurrentLocation().setColumn(240);
					blueGhost.getImage().setY(240);
					pinkGhost.getCurrentLocation().setColumn(240);
					pinkGhost.getImage().setY(240);
					pinkGhost.getCurrentLocation().setRow(330);
					pinkGhost.getImage().setX(330);
					resume();
				}
				
			}
			
		}
		if(caughtPacman((int) pinkGhost.getCurrentLocation().getRow(),(int)pinkGhost.getCurrentLocation().getColumn(),pacman.getCurrentLocation().getRow(),pacman.getCurrentLocation().getColumn())==true)
		{
			System.out.println("pinkkk BYEEEEE");
					game.setLive(game.getLive()-1);
					if(game.getLive()==0)
					{
						game.setGameState(GameState.Finished);
						//pauseOrUnPauseGame();
						GameOver();
					
						}
					else 
					{
						if(game.getLive()<=2)
						{
							if(game.getLive()==2)
							{
								life3.setVisible(false);
							}
							if(game.getLive()==1)
							{		
								life3.setVisible(false);
		
								life2.setVisible(false);
							}
							pane.getChildren().removeAll();
							pacman.getCurrentLocation().setRow(300);
							
							pacman.getCurrentLocation().setColumn(570);
							redGhost.getCurrentLocation().setRow(300);
							redGhost.getImage().setX(300);
							redGhost.getCurrentLocation().setColumn(240);
							redGhost.getImage().setY(240);
							blueGhost.getCurrentLocation().setRow(270);
							blueGhost.getImage().setX(270);
							blueGhost.getCurrentLocation().setColumn(240);
							blueGhost.getImage().setY(240);
							pinkGhost.getCurrentLocation().setColumn(240);
							pinkGhost.getImage().setY(240);
							pinkGhost.getCurrentLocation().setRow(330);
							pinkGhost.getImage().setX(330);
							resume();
						}
					}
					
				}
			 movement(newDir);
				}
			});
			pacman_timeline = new Timeline(pacman_keyFrame) ;
			pacman_timeline.setCycleCount(Timeline.INDEFINITE) ;
			pacman_timeline.play() ;		
	}
	
	
	
	
	private void movePacwomenAtSpeed() {
		pacwomen_keyFrame = new KeyFrame(Duration.millis(pacwomen.getSpeed()), e->
		{		
			if(game_women.gameStatewomen==GameStateWomen.Running) {
		

			if(caughtPacman((int) redGhost.getCurrentLocation().getRow(),(int) redGhost.getCurrentLocation().getColumn(),pacwomen.getCurrentLocation().getRow(),pacwomen.getCurrentLocation().getColumn())==true)
			{
				System.out.println("Reeeed BYEEEEE");
				game_women.setLive(game_women.getLive()-1);
			
			if(game_women.getLive()==0)
			{
				game_women.setGameStateWomen(GameStateWomen.Finished);
				pauseOrUnPauseGame();  /////// 777777777777777777777777777777777777777777777777777
				GameOverwomen();			//// 7777777777777777777777777777777777777777777777777777777	
					
				}
			else 
			{
				
				if(game_women.getLive()<=2)
				{
					if(game_women.getLive()==2)
					{
						life31.setVisible(false);
					}
					if(game_women.getLive()==1)
					{		
						life31.setVisible(false);
		
						life21.setVisible(false);
					}
					pacwomen.getCurrentLocation().setRow(300);
					pacwomen.getCurrentLocation().setColumn(330);
					
					pane.getChildren().removeAll();
					redGhost.getCurrentLocation().setRow(300);
					redGhost.getImage().setX(300);
					redGhost.getCurrentLocation().setColumn(240);
					redGhost.getImage().setY(240);
					blueGhost.getCurrentLocation().setRow(270);
					blueGhost.getImage().setX(270);
					blueGhost.getCurrentLocation().setColumn(240);
					blueGhost.getImage().setY(240);
					pinkGhost.getCurrentLocation().setColumn(240);
					pinkGhost.getImage().setY(240);
					pinkGhost.getCurrentLocation().setRow(330);
					pinkGhost.getImage().setX(330);
		
					resume();
				}
				
				
			}
			
		}
		if(caughtPacman((int) blueGhost.getCurrentLocation().getRow(),(int)blueGhost.getCurrentLocation().getColumn(),pacwomen.getCurrentLocation().getRow(),pacwomen.getCurrentLocation().getColumn())==true)
		{
			System.out.println("Bluee BYEEEEE");
			game_women.setLive(game_women.getLive()-1);
			if(game_women.getLive()==0)
			{
				game_women.setGameStateWomen(GameStateWomen.Finished);
				pauseOrUnPauseGame();
				GameOverwomen();
				}
			else
			{
				
				if(game_women.getLive()<=2)
				{
					if(game_women.getLive()==2)
					{
						life31.setVisible(false);
					}
					if(game_women.getLive()==1)
					{		
						life31.setVisible(false);
		
						life21.setVisible(false);
					}
					pane.getChildren().removeAll();
					pacwomen.getCurrentLocation().setRow(300);
					pacwomen.getCurrentLocation().setColumn(330);
					
					redGhost.getCurrentLocation().setRow(300);
					redGhost.getImage().setX(300);
					redGhost.getCurrentLocation().setColumn(240);
					redGhost.getImage().setY(240);
					blueGhost.getCurrentLocation().setRow(270);
					blueGhost.getImage().setX(270);
					blueGhost.getCurrentLocation().setColumn(240);
					blueGhost.getImage().setY(240);
					pinkGhost.getCurrentLocation().setColumn(240);
					pinkGhost.getImage().setY(240);
					pinkGhost.getCurrentLocation().setRow(330);
					pinkGhost.getImage().setX(330);
					resume();
				}
				
			}
			
		}
		if(caughtPacman((int) pinkGhost.getCurrentLocation().getRow(),(int)pinkGhost.getCurrentLocation().getColumn(),pacwomen.getCurrentLocation().getRow(),pacwomen.getCurrentLocation().getColumn())==true)
		{
			System.out.println("pinkkk BYEEEEE");
			game_women.setLive(game_women.getLive()-1);
					if(game_women.getLive()==0)
					{
						game_women.setGameStateWomen(GameStateWomen.Finished);
						pauseOrUnPauseGame();
						GameOverwomen();
					
						}
					else 
					{
						if(game_women.getLive()<=2)
						{
							if(game_women.getLive()==2)
							{
								life31.setVisible(false);
							}
							if(game_women.getLive()==1)
							{		
								life31.setVisible(false);
		
								life21.setVisible(false);
							}
							pane.getChildren().removeAll();
							
							pacwomen.getCurrentLocation().setRow(300);
							pacwomen.getCurrentLocation().setColumn(330);
							
							
							redGhost.getCurrentLocation().setRow(300);
							redGhost.getImage().setX(300);
							redGhost.getCurrentLocation().setColumn(240);
							redGhost.getImage().setY(240);
							blueGhost.getCurrentLocation().setRow(270);
							blueGhost.getImage().setX(270);
							blueGhost.getCurrentLocation().setColumn(240);
							blueGhost.getImage().setY(240);
							pinkGhost.getCurrentLocation().setColumn(240);
							pinkGhost.getImage().setY(240);
							pinkGhost.getCurrentLocation().setRow(330);
							pinkGhost.getImage().setX(330);
							resume();
						}
					}
					
				}
			 movement_women(newDir_women);
				}
			});
			pacwomen_timeline = new Timeline(pacwomen_keyFrame) ;
			pacwomen_timeline.setCycleCount(Timeline.INDEFINITE) ;
			pacwomen_timeline.play() ;		
	}
	
		
	
	 /**                         --------- man *** women ----------                * */
	/*
	 * on game over, finish the game and add history to json
	 */
	private void GameOver() {
		Sound.playSound(Sound.class.getResource("../resources/Death.mp3"), 80);
		ImageView imageView = new ImageView("Photos/over_1.jpeg");
		imageView.setLayoutX(140);
		imageView.setLayoutY(190);
		imageView.setFitWidth(350);
		imageView.setFitHeight(200);
		pane.getChildren().add(imageView);
        SysData.getInstance().addGameHistory(new Player(namelab.getText(), game.score,Calendar.getInstance().getTime()));;
        
        for(int i=0; i<21; i++)
    	   for(int j=0; j<21; j++)
    		   if(matrix[i][j]==4)
    			   matrix[i][j] = 0;
        
        pacman_timeline.getKeyFrames().clear();
        //pacman= new PackMan(1,0, new ImageView(),null,Utils.Color.yellow);
        
		Timer timer = new Timer();
		TimerTask task = new TimerTask()
		{
			
		        public void run()
		        { Platform.runLater(() -> {
		        	if(game_women.getLive()==0 && game.getLive()==0 )
		        	{	
		        		((Stage) pane.getScene().getWindow()).close();
						ViewLogic.leaderBoardWindow();
		        	}
		        	else if(game_women.getLive()!=0 && game.getLive()==0 ) {
		        		pane.getChildren().remove(imageView);
		        	}
					
		            });
		        }
			

		};
		timer.schedule(task,2000);			
		pauseOrUnPauseGame();
		long count=0;
		while(count!=300000000)
			count++;
		count=0;
		while(count!=300000000)
			count++;
		count=0;
		while(count!=300000000)
			count++;
		pane.getChildren().remove(imageView);
	}

	
	private void GameOverwomen() {
		Sound.playSound(Sound.class.getResource("../resources/Death.mp3"), 80);
		ImageView imageView = new ImageView("Photos/over_2.jpeg");
		imageView.setLayoutX(140);
		imageView.setLayoutY(190);
		imageView.setFitWidth(350);
		imageView.setFitHeight(200);
		pane.getChildren().add(imageView);
        SysData.getInstance().addGameHistory(new Player(namelab.getText(), game.score,Calendar.getInstance().getTime()));;
        game_women.setGameStateWomen(GameStateWomen.Finished);
        pacwomen.setImage(new ImageView());
        
        pacwomen_timeline.getKeyFrames().clear();
        //pacwomen= new PackMan(1,0, new ImageView(),null,Utils.Color.pink);
        for(int i=0; i<21; i++)
     	   for(int j=0; j<21; j++)
     		   if(matrix[i][j]==10)
     			   matrix[i][j] = 0;
        
		Timer timer = new Timer();
		TimerTask task = new TimerTask()
		{
		        public void run()
		        { Platform.runLater(() -> {

		        	if(game_women.getLive()==0 && game.getLive()==0)
		        	{	((Stage) pane.getScene().getWindow()).close();
						ViewLogic.leaderBoardWindow();
		        	}
		            });
		        }

		};
		timer.schedule(task,2000);	
		pauseOrUnPauseGame();
		long count=0;
		while(count!=300000000)
			count++;
		pane.getChildren().remove(imageView);
	}
	/*
	 * replace question with the same level in a random place
	 */
	private void replaceQuestionOnBoard(Level level)
	{
			boolean placeFound=false;
			int index = 0, index2=0;
			while (placeFound==false)
			{	
				index = (int)(Math.random()*21);
				index2= (int)(Math.random()*21);
				if(matrix[index][index2]==0)
				{
					placeFound=true;
					matrix[index][index2]=3; 
				}
				
			}
			
			Question questin=new Question();
			int index3 = (int)(Math.random() * questin.getPointsQuestions().size());
			ImageView imageView = new ImageView(questin.getPointsQuestions().get(index3).getImage());
			imageView.setId(level.toString());
			imageView.setFitHeight(30);
			imageView.setFitWidth(30);
			imageView.setX(index2*30);
			imageView.setY(index*30);
			pane.getChildren().add(imageView) ;
			questionsPoints.add(imageView);
	
	}
	
	/*
	 * fill board acccording to the matrix. every object on the board has it's defining number(see on Model\Board) for example - 0:wall
	 */
	private void fillBoard() {
		int thisRow=0;
		int thisColoum=0;
		int thisRow1=0;
		int thisColoum1=0;

		int questioncounter=0;
		Level question1= Level.easy;
		Level question2=Level.medium;
		Level question3= Level.hard;
		for(int i=0; i<21; i++)
		{
			for(int j=0;j<21;j++) {
				
				if(matrix[i][j]==3)
					matrix[i][j]=0;
				
				thisRow1+=30;
			}
			thisColoum1+=30;
			thisRow1=0;
		}
		
	    //matrix = Board.getInstance().putRandomQuestion(matrix1);

		for(int i=0; i<21; i++)
		{
			for(int j=0;j<21;j++) {
				
				// update the walls on the board
		if(matrix[i][j]==1)
		{
			Rectangle wall =(Rectangle) ShapeFactory.getShapeObject("Rectangle" , thisRow, thisColoum, ObjectSize); // pass in x, y, width and height
			pane.getChildren().add(wall) ;
			wallList.add(wall);
		
		}
		
		// update the points on the board 
		if(matrix[i][j] == 0)
		{
			
			Circle peckPoint = (Circle) ShapeFactory.getShapeObject("Circle" , thisRow, thisColoum, ObjectSize);// pass in x, y, width and height
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
			ImageView imageView = new ImageView("Photos/dynamite.png");
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
			if(questioncounter==0)
				imageView.setId(question1.toString());
			else if(questioncounter==1)
				imageView.setId(question2.toString());
			else if(questioncounter==2)
				imageView.setId(question3.toString());
			questioncounter++;
			imageView.setFitHeight(30);
			imageView.setFitWidth(30);
			imageView.setX(thisRow);
			imageView.setY(thisColoum);
			pane.getChildren().add(imageView) ;
			questionsPoints.add(imageView);
		}
		
		// update the  Pack-Man on the board
		if(matrix[i][j] == 4)
		{
			ImageView imageView = new ImageView("Photos/Pac-Man__PMVS_-removebg-preview.png");
			imageView.setFitHeight(40);
			imageView.setFitWidth(40);
			imageView.setX(thisRow);
			imageView.setY(thisColoum);
			pane.getChildren().add(imageView) ;
			pacman.setImage( imageView);
		
		}
		if(matrix[i][j] == 10)
		{
			ImageView imageView = new ImageView("Photos/Photo87-removebg-preview.png");
			//ImageView imageView = new ImageView("Photos/ms_pac_man-removebg-preview-2.png");
			imageView.setFitHeight(40);
			imageView.setFitWidth(40);
			imageView.setX(thisRow);
			imageView.setY(thisColoum);
			pane.getChildren().add(imageView) ;
			pacwomen.setImage(imageView);
		
		}
		// update the ghost son the board 
		if(matrix[i][j] == 5)
		{
			ImageView imageView = new ImageView("Photos/ghost_blue.png");
			imageView.setFitHeight(30);
			imageView.setFitWidth(30);
			imageView.setX(thisRow);
			imageView.setY(thisColoum);
			pane.getChildren().add(imageView) ;
			blueGhost.setId(1);
			blueGhost.setSpeed(280);
			blueGhost.setImage(imageView);
			blueGhost.setCurrentLocation(new Location(thisRow, thisColoum));
		
		
		}
		if(matrix[i][j] == 6)
		{
			ImageView imageView = new ImageView("Photos/ghost_red.png");
			imageView.setFitHeight(30);
			imageView.setFitWidth(30);
			imageView.setX(thisRow);
			imageView.setY(thisColoum);
		
			pane.getChildren().add(imageView) ;
			redGhost.setId(2);
			redGhost.setSpeed(280);
			redGhost.setImage(imageView);
			redGhost.setCurrentLocation(new Location(thisRow, thisColoum));
			}
		
		if(matrix[i][j] == 7)
		{
			ImageView imageView = new ImageView("Photos/ghost_pink.png");
						imageView.setFitHeight(30);
						imageView.setFitWidth(30);
						imageView.setX(thisRow);
						imageView.setY(thisColoum);
		
						pane.getChildren().add(imageView) ;
						pinkGhost.setId(3);
						pinkGhost.setSpeed(280);
						pinkGhost.setImage(imageView);
						pinkGhost.setCurrentLocation(new Location(thisRow, thisColoum));
		
					}
					
					
					thisRow+=30;
				}
				thisColoum+=30;
				thisRow=0;
			}
	}
	
	
	/*
	 * resume game after pacman loses a live
	 */
	private void resume()
	{
		int thisRow=0;
	    int thisColoum=0;
	    int thisRow1=0;
	    int thisColoum1=0;
	    int questioncounter=0;
		Level question1= Level.easy;
		Level question2=Level.medium;
		Level question3= Level.hard;

	    pane.getChildren().removeAll(bonusList);
	    pane.getChildren().remove(pacman.getImage());
	    pane.getChildren().remove(pacwomen.getImage());
	    pane.getChildren().removeAll(peckpointlist);
	    pane.getChildren().removeAll(wallList);
	    pane.getChildren().removeAll(questionsPoints);
	    
		peckpointlist = new ArrayList<Circle>() ;
		wallList = new ArrayList<Rectangle>() ;
		bonusList = new ArrayList<ImageView>() ;
		pacman.setImage(new ImageView()) ;
		pacwomen.setImage(new ImageView());
		
		questionsPoints= new ArrayList<ImageView>() ;
		boolean addWall=false;
		
		for(int i=0; i<21; i++)
		{
			for(int j=0;j<21;j++) {
				
				if(matrix[i][j]==3)
					matrix[i][j]=0;
				
				thisRow1+=30;
			}
			thisColoum1+=30;
			thisRow1=0;
		}
		
	    //matrix = Board.getInstance().putRandomQuestion(matrix1);
		 
		for(int i=0; i<21; i++)
		{
			for(int j=0;j<21;j++) {
				
				// update the walls on the board
				if(matrix[i][j]==1)
				{
						if(game.getLevel()==Level.medium || game_women.getLevel()==Level.medium) {
							if(((thisRow!=90 && thisColoum==0) || (thisRow!=90 && thisColoum!=600) ||
									(thisRow!=0 && thisColoum!=300) || (thisRow!=600 && thisColoum!=300) ))
								addWall=true;
					}	
				else
					addWall=true;
						
				if(addWall==true) {
					Rectangle wall =(Rectangle) ShapeFactory.getShapeObject("Rectangle" , thisRow, thisColoum, ObjectSize); // pass in x, y, width and height

					pane.getChildren().add(wall) ;
					wallList.add(wall);
					addWall=false;
					}

				}
				

// update the points on the board 
if(matrix[i][j] == 0)
{
	
	Circle peckPoint =(Circle) ShapeFactory.getShapeObject("Circle" , thisRow, thisColoum, ObjectSize); // pass in x, y, width and height
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
	ImageView imageView = new ImageView("Photos/dynamite.png");
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
				if(questioncounter==0)
					imageView.setId(question1.toString());
				else if(questioncounter==1)
					imageView.setId(question2.toString());
				else if(questioncounter==2)
					imageView.setId(question3.toString());
				questioncounter++;
				imageView.setFitHeight(30);
				imageView.setFitWidth(30);
				imageView.setX(thisRow);
				imageView.setY(thisColoum);
				pane.getChildren().add(imageView) ;
				questionsPoints.add(imageView);


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
	
	/*
	 * method to move the red ghost
	 */
	private void moveRed()
	{
		Direction dontGo = null ;
	
		if(isWall(red_movingAt, redGhost.getCurrentLocation().getRow(),  redGhost.getCurrentLocation().getColumn()) == true && numOfTurns(red_movingAt, redGhost.getCurrentLocation().getRow(),  redGhost.getCurrentLocation().getColumn()) == 1)
		{							// if the ghost runs to a dead end it goes in the direction opposite to its current direction
		red_movingAt = oppositeDir(red_movingAt) ;
	}
	else
	{
		for(;;)
		{
			
				if(tailPacman(redGhost, 10) != null)
				{
					red_movingAt = tailPacman(redGhost, 10) ;	// check if pacman is in red's radar. If pacman is 7 walls away from red, red will follow him
					break ;
				}
				if(tailPacwomen(redGhost, 10) != null)
				{
					red_movingAt = tailPacwomen(redGhost, 10) ;	// check if pacman is in red's radar. If pacman is 7 walls away from red, red will follow him
					break ;
				}
	
	// move in a random direction
				Direction direction = getRandomDir() ;
				if(isWall(direction, redGhost.getCurrentLocation().getRow(),  redGhost.getCurrentLocation().getColumn()) == false)
				{
					if(dontGo != null && direction != dontGo)
					{
						red_movingAt = direction ;
						break ;
					}
					else if(direction != oppositeDir(red_movingAt))
					{
						red_movingAt = direction ;
						break ;
					}
				}
			}
		}
		if(redGhost.getCurrentLocation().getRow()==90 &&  redGhost.getCurrentLocation().getColumn()==0) {
			red_movingAt=Direction.DOWN;
		}
		if(redGhost.getCurrentLocation().getRow()==90 &&  redGhost.getCurrentLocation().getColumn()==600) {
			red_movingAt=Direction.UP;
		}if(redGhost.getCurrentLocation().getRow()==0 &&  redGhost.getCurrentLocation().getColumn()==300) {
			red_movingAt=Direction.RIGHT;
		}if(redGhost.getCurrentLocation().getRow()==600 &&  redGhost.getCurrentLocation().getColumn()==300) {
			red_movingAt=Direction.LEFT;
		}
	
		moveGhost(red_movingAt, redGhost.getCurrentLocation().getRow(), redGhost.getCurrentLocation().getColumn(), redGhost);
	
	}
	
	/*
	 * method to move the blue ghost
	 */
	private void moveBlue()
	{
		Direction dontGo = null ;

		if(isWall(blue_movingAt, blueGhost.getCurrentLocation().getRow(), blueGhost.getCurrentLocation().getColumn()) == true && numOfTurns(blue_movingAt, blueGhost.getCurrentLocation().getRow(), blueGhost.getCurrentLocation().getColumn()) == 1)
		{							// if the ghost runs to a dead end it goes in the direction opposite to its current direction
			blue_movingAt = oppositeDir(blue_movingAt) ;
		}
		else
		{
			for(;;)
			{
				
					if(tailPacman(blueGhost, 6) != null)
					{
						blue_movingAt = tailPacman(blueGhost, 6) ;	// check if pacman is in red's radar. If pacman is 7 walls away from red, red will follow him
						break ;
					}
					if(tailPacwomen(blueGhost, 6) != null)
					{
						blue_movingAt = tailPacwomen(blueGhost, 6) ;	// check if pacman is in red's radar. If pacman is 7 walls away from red, red will follow him
						break ;
					}
		
		// move in a random direction
					Direction direction = getRandomDir() ;
					if(isWall(direction, blueGhost.getCurrentLocation().getRow(), blueGhost.getCurrentLocation().getColumn()) == false)
					{
						if(dontGo != null && direction != dontGo)
						{
							blue_movingAt = direction ;
							break ;
						}
						else if(direction != oppositeDir(blue_movingAt))
						{
							blue_movingAt = direction ;
							break ;
						}
					}
				}
			}
			if(blueGhost.getCurrentLocation().getRow()==90 && blueGhost.getCurrentLocation().getColumn()==0) {
				blue_movingAt=Direction.DOWN;
			}if(blueGhost.getCurrentLocation().getRow()==90 && blueGhost.getCurrentLocation().getColumn()==600) {
				blue_movingAt=Direction.UP;
			}if(blueGhost.getCurrentLocation().getRow()==0 && blueGhost.getCurrentLocation().getColumn()==300) {
				blue_movingAt=Direction.RIGHT;
			}if(blueGhost.getCurrentLocation().getRow()==600 && blueGhost.getCurrentLocation().getColumn()==300) {
				blue_movingAt=Direction.LEFT;
			}
		
			moveGhost(blue_movingAt, blueGhost.getCurrentLocation().getRow(), blueGhost.getCurrentLocation().getColumn(), blueGhost);

	}
	
	/*
	 * method to move the pink ghost
	 */
	private void movePink()
		{
			Direction dontGo = null ;
			
			if(isWall(pink_movingAt, pinkGhost.getCurrentLocation().getRow(), pinkGhost.getCurrentLocation().getColumn()) == true && numOfTurns(pink_movingAt, pinkGhost.getCurrentLocation().getRow(), pinkGhost.getCurrentLocation().getColumn()) == 1)
			{							// if the ghost runs to a dead end it goes in the direction opposite to its current direction
				pink_movingAt = oppositeDir(pink_movingAt) ;
			}
			else
			{
				for(;;)
				{
						if(tailPacman(pinkGhost, 4) != null)
						{
							pink_movingAt = tailPacman(pinkGhost, 4) ;	// check if pacman is in pink's radar. If pacman is 4 walls away from pink, pink will follow him
							break ;
						}
						if(tailPacwomen(pinkGhost, 4) != null)
						{
							pink_movingAt = tailPacwomen(pinkGhost, 4) ;	// check if pacman is in pink's radar. If pacman is 4 walls away from pink, pink will follow him
							break ;
						}
			
			// move in a random direction
					Direction direction = getRandomDir() ;
					if(isWall(direction, pinkGhost.getCurrentLocation().getRow(), pinkGhost.getCurrentLocation().getColumn()) == false)
					{
						if(dontGo != null && direction != dontGo)
						{
							pink_movingAt = direction ;
							break ;
						}
						else if(direction != oppositeDir(pink_movingAt))
						{
							pink_movingAt = direction ;
							break ;
						}
					}
				}
			}
			if(pinkGhost.getCurrentLocation().getRow()==90 && pinkGhost.getCurrentLocation().getColumn()==0) {
				pink_movingAt=Direction.DOWN;
			}
			if(pinkGhost.getCurrentLocation().getRow()==90 && pinkGhost.getCurrentLocation().getColumn()==600) {
				pink_movingAt=Direction.UP;
			}if(pinkGhost.getCurrentLocation().getRow()==0 && pinkGhost.getCurrentLocation().getColumn()==300) {
				pink_movingAt=Direction.RIGHT;
			}if(pinkGhost.getCurrentLocation().getRow()==600 && pinkGhost.getCurrentLocation().getColumn()==300) {
				pink_movingAt=Direction.LEFT;
			}
			
			moveGhost(pink_movingAt, pinkGhost.getCurrentLocation().getRow(), pinkGhost.getCurrentLocation().getColumn(), pinkGhost);

		}
	
	/**                         --------- man *** women ----------                * */	
	
	 /**                         --------- man *** women ----------                * */
	/*
	 * checks the pacman location according to ghost location
	 * this method was used in order that the ghost can chase the pacman	
	 */
	private Direction pacmanAt(Ghost ghost)
		{
			double x = ghost.getCurrentLocation().getRow();
			double y = ghost.getCurrentLocation().getColumn();
			double pacmanX= pacman.getCurrentLocation().getRow();
			double pacmanY= pacman.getCurrentLocation().getColumn();
	
			if(y == pacmanY && (pacmanX-x) > 0 && (pacmanX-x) < 100 && checkForWallsBetween(x, y, pacmanX, pacmanY, Direction.RIGHT) == false)
				return Direction.RIGHT ;
			else if(y == pacmanY && (x-pacmanX) > 0 && (x-pacmanX) < 100 && checkForWallsBetween(x, y, pacmanX, pacmanY, Direction.LEFT) == false)
				return Direction.LEFT ;
			else if(x == pacmanX && (pacmanY-y) > 0 && (pacmanY-y) < 100 && checkForWallsBetween(x, y, pacmanX, pacmanY, Direction.DOWN) == false)
				return Direction.DOWN ;
			else if(x == pacmanX && (y-pacmanY) > 0 && (y-pacmanY) < 100 && checkForWallsBetween(x, y, pacmanX, pacmanY, Direction.UP) == false)
				return Direction.UP ;
	
			return null ;
		}	
		
	
	
	private Direction pacwomenAt(Ghost ghost)
	{
		double x = ghost.getCurrentLocation().getRow();
		double y = ghost.getCurrentLocation().getColumn();
		double pacmanX= pacwomen.getCurrentLocation().getRow();
		double pacmanY= pacwomen.getCurrentLocation().getColumn();

		if(y == pacmanY && (pacmanX-x) > 0 && (pacmanX-x) < 100 && checkForWallsBetween(x, y, pacmanX, pacmanY, Direction.RIGHT) == false)
			return Direction.RIGHT ;
		else if(y == pacmanY && (x-pacmanX) > 0 && (x-pacmanX) < 100 && checkForWallsBetween(x, y, pacmanX, pacmanY, Direction.LEFT) == false)
			return Direction.LEFT ;
		else if(x == pacmanX && (pacmanY-y) > 0 && (pacmanY-y) < 100 && checkForWallsBetween(x, y, pacmanX, pacmanY, Direction.DOWN) == false)
			return Direction.DOWN ;
		else if(x == pacmanX && (y-pacmanY) > 0 && (y-pacmanY) < 100 && checkForWallsBetween(x, y, pacmanX, pacmanY, Direction.UP) == false)
			return Direction.UP ;

		return null ;
	}	
	
	
	/*
	 * method that'll check for walls between 2 positions in a specific direction
	 */
	private Boolean checkForWallsBetween(double from_x, double from_y, double to_x, double to_y, Direction direction)
		{
			
			boolean wall_present = false ;
	
			if(direction == Direction.UP)
			{
				while(from_y > to_y && wall_present == false)
				{
					wall_present = isWall(direction, from_x, from_y) ;
					from_y-=ObjectSize ;
				}
			}
			else if(direction == Direction.DOWN)
			{
				while(from_y < to_y && wall_present == false)
				{
					wall_present = isWall(direction, from_x, from_y) ;
					from_y+=ObjectSize ;
				}
			}
			else if(direction == Direction.LEFT)
			{
				while(from_x > to_x && wall_present == false)
				{
					wall_present = isWall(direction, from_x, from_y) ;
					from_x-=ObjectSize ;
				}
			}
			else if(direction == Direction.RIGHT)
			{
				while(from_x < to_x && wall_present == false)
				{
					wall_present = isWall(direction, from_x, from_y) ;
					from_x+=ObjectSize ;
				}
			}
	
			return wall_present ;
		}
	
	
	/**                         --------- man *** women ----------                * */		
	
	 /**                         --------- man *** women ----------                * */
	/*
	 * this function returns the direction that the ghost has to move to in order to chase tha pacman
	 */
	private Direction tailPacman(Ghost ghost, int wallCount) {
		Direction direction = null ;
		double ghostX = ghost.getCurrentLocation().getRow();
		double ghostY = ghost.getCurrentLocation().getColumn();	
		double pacmanX = pacman.getCurrentLocation().getRow() ;	
		double pacmanY = pacman.getCurrentLocation().getColumn() ;	
	
		// from the ghost's current position find out in which direction pacman is
		
			Direction pacmanDir = pacmanAt(ghost) ;	
			int wallSize= ObjectSize;
	
			if(pacmanDir == Direction.DOWN && pacmanY-ghostY <= (wallSize*wallCount))
			{
				if(checkForWallsBetween(ghostX, ghostY, pacmanX, pacmanY, Direction.DOWN) == false)	direction = Direction.DOWN ;				
			}
			else if(pacmanDir == Direction.UP && ghostY-pacmanY <= (wallSize*wallCount))
			{
				if(checkForWallsBetween(ghostX, ghostY, pacmanX, pacmanY, Direction.UP) == false)	direction = Direction.UP ;				
			}
			else if(pacmanDir == Direction.LEFT && ghostX-pacmanX <= (wallSize*wallCount))
			{
				if(checkForWallsBetween(ghostX, ghostY, pacmanX, pacmanY, Direction.LEFT) == false)	direction = Direction.LEFT ;				
			}
			else if(pacmanDir == Direction.RIGHT && pacmanX-ghostX <= (wallSize*wallCount))
			{
				if(checkForWallsBetween(ghostX, ghostY, pacmanX, pacmanY, Direction.RIGHT) == false)	direction = Direction.RIGHT ;				
			}
	
			return direction ;
			}

	
	private Direction tailPacwomen(Ghost ghost, int wallCount) {
		Direction direction = null ;
		double ghostX = ghost.getCurrentLocation().getRow();
		double ghostY = ghost.getCurrentLocation().getColumn();	
		double pacmanX = pacwomen.getCurrentLocation().getRow() ;	
		double pacmanY = pacwomen.getCurrentLocation().getColumn() ;	
	
		// from the ghost's current position find out in which direction pacman is
		
			Direction pacmanDir = pacwomenAt(ghost) ;	
			int wallSize= ObjectSize;
	
			if(pacmanDir == Direction.DOWN && pacmanY-ghostY <= (wallSize*wallCount))
			{
				if(checkForWallsBetween(ghostX, ghostY, pacmanX, pacmanY, Direction.DOWN) == false)	direction = Direction.DOWN ;				
			}
			else if(pacmanDir == Direction.UP && ghostY-pacmanY <= (wallSize*wallCount))
			{
				if(checkForWallsBetween(ghostX, ghostY, pacmanX, pacmanY, Direction.UP) == false)	direction = Direction.UP ;				
			}
			else if(pacmanDir == Direction.LEFT && ghostX-pacmanX <= (wallSize*wallCount))
			{
				if(checkForWallsBetween(ghostX, ghostY, pacmanX, pacmanY, Direction.LEFT) == false)	direction = Direction.LEFT ;				
			}
			else if(pacmanDir == Direction.RIGHT && pacmanX-ghostX <= (wallSize*wallCount))
			{
				if(checkForWallsBetween(ghostX, ghostY, pacmanX, pacmanY, Direction.RIGHT) == false)	direction = Direction.RIGHT ;				
			}
	
			return direction ;
			}
	/*
	 * this method returns num of turns that were made by the ghost 
	 * used for the ghost algorithm for chasing pacman
	 */
	private int numOfTurns(Direction currentDir, double x, double y) {

		int numOfTurns = 0 ;

		if(currentDir != Direction.UP && isWall(Direction.UP, x, y) == false)	numOfTurns++ ;

		if(currentDir != Direction.DOWN && isWall(Direction.DOWN, x, y) == false)	numOfTurns++ ;

		if(currentDir != Direction.LEFT && isWall(Direction.LEFT, x, y) == false)	numOfTurns++ ;

		if(currentDir != Direction.RIGHT && isWall(Direction.RIGHT, x, y) == false)	numOfTurns++ ;

		return numOfTurns ;
		}

	public Pane getPane() {
		return pane;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	/**                         --------- man *** women ----------                * */	
	public void pauseOrUnPauseGame() {
			
		if(game.gameState==GameState.Finished && game_women.gameStatewomen==GameStateWomen.Running)  {
			game_women.gameStatewomen=GameStateWomen.Running;
			movePacwomenAtSpeed();
			moveGhostAtSpeed();
		}
		if(game.gameState==GameState.Running && game_women.gameStatewomen==GameStateWomen.Finished)  {
			game.gameStatewomen=GameStateWomen.Running;
			movePackmanAtSpeed();
			moveGhostAtSpeed();
		}
			if(game.gameState==GameState.Paused )  {
				game.gameState=GameState.Running;
				game_women.gameStatewomen=GameStateWomen.Running;
				movePackmanAtSpeed();
				movePacwomenAtSpeed();
				moveGhostAtSpeed();
			}
			else if(game.gameState==GameState.Running) {
				game.gameState=GameState.Paused;
				game_women.gameStatewomen=GameStateWomen.Paused;
				if(pacman_timeline!=null) {
					pacman_timeline.stop();
					pacman_timeline.getKeyFrames().clear();
				}
				if(pacwomen_timeline!=null) {
					pacwomen_timeline.stop();
					pacwomen_timeline.getKeyFrames().clear();
				}
				if(pecPoints_timeline!=null) {
		
					pecPoints_timeline.stop();
					pecPoints_timeline.getKeyFrames().clear();
				}
				if(ghosts_timeline!=null) {
					ghosts_timeline.stop();
					ghosts_timeline.getKeyFrames().clear();
				}
				if(bombPoints_timeline!=null) {
					bombPoints_timeline.stop();
					bombPoints_timeline.getKeyFrames().clear();
				}
		}
	
		
	
			
			}		
	
	
	
	
	
}
