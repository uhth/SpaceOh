package application.view.sprites;

import application.view.animations.Animation;

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
		super( loadSprite( PLAYER_IMG_PATH ), 32, 32, 3.5, 3.5 );		
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
		

}
