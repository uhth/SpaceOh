package application.view.sprites.hud;

import application.view.animations.Animation;
import application.view.sprites.Sprite;
import javafx.scene.image.Image;

public class HeartSprite extends Sprite
{
	private static final String HEART_IMG_PATH = "/application/images/ui/heartSpriteSheetV1.png";
	
	private static HeartSprite healthBarSprite;
	
	public static HeartSprite getInstance() {
		if( healthBarSprite == null )
			healthBarSprite = new HeartSprite();
		return healthBarSprite;
	}
	
	private HeartSprite() {		
		init();
		initAnimations();
	}
	

	private void initAnimations() {
		animations = new Animation[ 3 ];
		animations[ 0 ] = Animation.createAnimation(1, getSpriteArray(), 0, 1 );
		animations[ 1 ] = Animation.createAnimation( 4, getSpriteArray(), 1, 7 );
		animations[ 2 ] = Animation.createAnimation( 4, getSpriteArray(), 1, 7 );
	}

	private void init() {
		Image heartSpriteSheet = new Image( getClass().getResourceAsStream( HEART_IMG_PATH ) );
		loadSprites( heartSpriteSheet );
	}
	
	
}
