package application.controller.gamestate;

import java.util.concurrent.ConcurrentHashMap;
import application.controller.enemies.EnemiesController;
import application.controller.handlers.CollisionHandler;
import application.controller.player.PlayerController;
import application.controller.projectiles.BulletsController;
import application.entities.PlayerEntity;
import application.entities.enemies.RattlerEntity;
import application.entities.enemies.SightSeekerEntity;
import application.model.GameState;
import application.view.SpaceOhGraphics;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameStateController extends AnimationTimer
{
	private static GameStateController gameStateController;
	private GameState game;
	private SpaceOhGraphics g;
	private PlayerController playerController;
	private EnemiesController enemiesController;
	private BulletsController bulletsController;
	private CollisionHandler collisionHandler;
	public EventHandler< KeyEvent > keysPressedEH;
	public EventHandler< KeyEvent > keysRelasedEH;	
	private ConcurrentHashMap< KeyCode, Boolean > keysState;
	public static boolean running;
	
	private GameStateController() 
	{
		game = GameState.getInstance();
		g = SpaceOhGraphics.getInstance();
		running = true;

		this.keysState = new ConcurrentHashMap< KeyCode, Boolean >();
		game.setPlayer( new PlayerEntity() );
		playerController = new PlayerController( game, g );
		enemiesController = new EnemiesController( game, g );
		bulletsController = new BulletsController( game, g );
		collisionHandler = new CollisionHandler( g );
		
		keysPressedEH = new EventHandler< KeyEvent >() {
			@Override
			public void handle( KeyEvent event ) {
				if( checkForSpecialKeys( event ) ) return;
				keysState.replace( event.getCode(), true );
			}
		};
		
		keysRelasedEH = new EventHandler< KeyEvent >() {
			@Override
			public void handle( KeyEvent event ) {
				keysState.replace( event.getCode(), false );
			}
		};
				
		initInputMap();
		
		initStage();
		
		
		start();
	}
	
	public static GameStateController getInstance() {
		if( gameStateController == null )
			gameStateController = new GameStateController();
		return gameStateController;
	}

	public void initStage() {
		game.addEnemy( new RattlerEntity( 10, 50 ) {
			@Override
			protected float getBaseHp() {
				return 5.0f;
			}
			@Override
			protected float getBaseMaxSpeed() {
				return 3.0f;
			}
			@Override
			public float getBaseSpeed() {
				return 0.5f;
			}
		});
		
		game.addEnemy( new RattlerEntity( 80, 150 ) {
			@Override
			protected float getBaseHp() {
				return 5.0f;
			}
			@Override
			protected float getBaseMaxSpeed() {
				return 3.0f;
			}
			@Override
			public float getBaseSpeed() {
				return 0.5f;
			}
		});
		
		game.addEnemy( new RattlerEntity( 170, 250 ) {
			@Override
			protected float getBaseHp() {
				return 5.0f;
			}
			@Override
			protected float getBaseMaxSpeed() {
				return 3.0f;
			}
			@Override
			public float getBaseSpeed() {
				return 0.5f;
			}
		});
		
	/*	game.addEnemy( new SightSeekerEntity() {
			@Override
			protected float getBaseHp() {
				return 20.0f;
			}
		});
	*/}

	//special keys
	private boolean checkForSpecialKeys( KeyEvent event ) {
		switch( event.getCode() ) {
		case ENTER :
			running = running ? false : true;
			return true;
			
		case ESCAPE :
			System.exit( 0 );
			return true;
		
		default :
			return false;
		}
	}
	
	//keyState map init
	public void initInputMap() {
		for( KeyCode k : KeyCode.values() ) {
			keysState.put( k, false );
		}
	}
	
	private void filterInputs() {

		//check movements
		if( keysState.get( KeyCode.D ) || keysState.get( KeyCode.RIGHT ) ) playerController.accelerateRIGHT();
		if( keysState.get( KeyCode.A ) || keysState.get( KeyCode.LEFT ) ) playerController.accelerateLEFT();
		if( keysState.get( KeyCode.W ) || keysState.get( KeyCode.UP ) ) playerController.accelerateUP();
		if( keysState.get( KeyCode.S ) || keysState.get( KeyCode.DOWN ) ) playerController.accelerateDOWN();
		
		//check fire
		if( keysState.get( KeyCode.SPACE ) )  playerController.fire();
		
	}
	

	@Override
	public void handle( long now ) {
			
		filterInputs();

		//check if game's paused
		if( !running ) return;
		
		//game logic
		playerController.updatePlayer();
		bulletsController.updateBullets();
		enemiesController.update();
				
		//graphics
		g.clear();
		g.drawBackground();
		playerController.drawPlayer();
		bulletsController.drawBullets();
		enemiesController.drawEnemies();
		
		//collisions
		collisionHandler.checkCollisions();
		
		//last stuff ( game removes )
		bulletsController.killBullets();
		enemiesController.killEnemies();
		
		//test
	//	System.out.println( g.getOnScreenEntities().size() );
	}
	
	
}
