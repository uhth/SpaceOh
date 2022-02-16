package application.view.sprites;

import application.view.animations.Animation;
import javafx.scene.image.Image;

public class PlayerSprite extends Sprite
{
			
	private static final String PLAYER_IMG_PATH = "/application/images/player/playerSpriteSheetV4.png";
		
	private static PlayerSprite playerSprite;
	
	public static PlayerSprite getInstance() {
		if( playerSprite == null )
			playerSprite = new PlayerSprite();
		return playerSprite;
	}
	
	private PlayerSprite() {		
		init();
		initAnimations();
	}
	

	private void initAnimations() {
		animations = new Animation[ 5 ];
		animations[ 0 ] = Animation.createAnimation( 3, getSpriteArray(), 20, 8 );
		animations[ 1 ] = Animation.createAnimation( 10, getSpriteArray(), 0, 5 );
		animations[ 2 ] = Animation.createAnimation( 10, getSpriteArray(), 10, 5 );
		animations[ 3 ] = Animation.createAnimation( 3, getSpriteArray(), 6, 5 );
		animations[ 4 ] = Animation.createAnimation( 3, getSpriteArray(), 16, 5 );
	}

	private void init() {
		Image playerSpriteSheet = new Image( getClass().getResourceAsStream( PLAYER_IMG_PATH ) );
		loadSprites( playerSpriteSheet );
	}
		
	@Override
	public float getScaledX() { return 3; }
	
	@Override
	public float getScaledY() { return 3; }

}
