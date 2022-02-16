package application.view;

import java.util.ArrayList;
import java.util.List;
import application.controller.gamestate.GameStateController;
import application.entities.Entity;
import application.entities.PlayerEntity;
import application.view.sprites.BackgroundSprite;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class SpaceOhGraphics extends StackPane
{
	
	private static SpaceOhGraphics spaceOhGraphics;
	Canvas canvas;
	GameStateController controller;
		
	List< Entity > onScreenEntities;
	
	private SpaceOhGraphics() {
	
		onScreenEntities = new ArrayList< Entity >();		
		canvas = new Canvas();
		getChildren().add( canvas );
		canvas.widthProperty().bind( this.widthProperty() );
		canvas.heightProperty().bind( this.heightProperty() );
		canvas.getGraphicsContext2D().setFill( Color.WHITE );
		this.setBackground( new Background( new BackgroundFill( Color.BLACK, null, null ) ) );
		
	}
	
	public static SpaceOhGraphics getInstance() {
		if( spaceOhGraphics == null )
			spaceOhGraphics = new SpaceOhGraphics();
		return spaceOhGraphics;
	}
	
	public void setController( GameStateController controller )
	{
		this.controller = controller;
		canvas.setFocusTraversable( true );
		canvas.setOnKeyPressed( controller.keysPressedEH );
		canvas.setOnKeyReleased( controller.keysRelasedEH );
	}
	
	public void clear() {
		onScreenEntities.clear();
		canvas.getGraphicsContext2D().clearRect( 0, 0, canvas.getWidth(),  canvas.getHeight() );
	}
	
	
	//DRAW PROJECTILES
	public void drawProjectiles( List< Entity > projectiles ) 
	{
		if( projectiles.isEmpty() ) return;
		for( Entity projectileEntity : projectiles )  {
			
			canvas.getGraphicsContext2D().fillRect( projectileEntity.getHitbox().getMinX(), projectileEntity.getHitbox().getMinY(), projectileEntity.getHitbox().getWidth(), projectileEntity.getHitbox().getHeight() );

			canvas.getGraphicsContext2D().drawImage( projectileEntity.getFrame(), projectileEntity.getDrawCoords().getX(), projectileEntity.getDrawCoords().getY() );
						
			//hitbox stuff
			onScreenEntities.add( projectileEntity );
		}

	}
	
	//DRAW BACKGROUND
	public void drawBackground() 
	{
		canvas.getGraphicsContext2D().drawImage( BackgroundSprite.getInstance().getSpriteAt( 0 ) , 0, 0 );
	}
	
	//DRAW ENEMIES
	public void drawEnemies( List< Entity > enemies ) 
	{
		if( enemies.isEmpty() ) return;
		for( Entity enemyEntity : enemies )
		{
			//draws actual enemy
			canvas.getGraphicsContext2D().drawImage( enemyEntity.getFrame(), enemyEntity.getDrawCoords().getX(), enemyEntity.getDrawCoords().getY() );
		//	hit box testing stuff
		//	canvas.getGraphicsContext2D().setFill( Color.WHITE );
		//	canvas.getGraphicsContext2D().setGlobalBlendMode( BlendMode.EXCLUSION );
			canvas.getGraphicsContext2D().fillRect( enemyEntity.getHitbox().getMinX(), enemyEntity.getHitbox().getMinY(), enemyEntity.getHitbox().getWidth(), enemyEntity.getHitbox().getHeight() );
			//draws healthbar
			if( enemyEntity.drawHealthBar() )
				canvas.getGraphicsContext2D().drawImage( enemyEntity.getHealthBar().getFrame(), enemyEntity.getHealthBar().getDrawCoords().getX(), enemyEntity.getHealthBar().getDrawCoords().getY() );
			//hitbox stuff
			onScreenEntities.add( enemyEntity );
		}
	}
	
	//DRAW PLAYER
	public void drawPlayer( PlayerEntity playerEntity, Image frame ) 
	{
		canvas.getGraphicsContext2D().fillRect( playerEntity.getHitbox().getMinX(), playerEntity.getHitbox().getMinY(), playerEntity.getHitbox().getWidth(), playerEntity.getHitbox().getHeight() );
		canvas.getGraphicsContext2D().drawImage( frame, playerEntity.getDrawCoords().getX(), playerEntity.getDrawCoords().getY() );
		//lines below are used to see player's emitter dots and hitbox
	//	canvas.getGraphicsContext2D().fillRect( playerEntity.getEmitter().getPoint().getX(), playerEntity.getEmitter().getPoint().getY(), 2, 2 );
	//	canvas.getGraphicsContext2D().fillRect( playerEntity.getEmitter().getPivot().getX(), playerEntity.getEmitter().getPivot().getY(), 2, 2 );
		
		
		//hud hearts
		for( Entity e : playerEntity.getHearts() ) 
			canvas.getGraphicsContext2D().drawImage( e.getFrame(), e.getDrawCoords().getX(), e.getDrawCoords().getY() );
		
		//hitbox stuff
		onScreenEntities.add( playerEntity );
	}
	
	public List< Entity > getOnScreenEntities() {
		return onScreenEntities;
	}
	
}
