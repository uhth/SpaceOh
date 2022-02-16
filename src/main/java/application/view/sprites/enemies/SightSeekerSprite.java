package application.view.sprites.enemies;

import application.view.animations.Animation;
import application.view.sprites.Sprite;
import javafx.scene.image.Image;

public class SightSeekerSprite extends Sprite {
	
	private static final String SIGHT_SEEKER_IMG_PATH = "/application/images/enemies/sightSeekerSpriteSheetV1.png";
	
	private static SightSeekerSprite sightSeekerSprite;
	
	public static SightSeekerSprite getInstance() {
		if( sightSeekerSprite == null )
			sightSeekerSprite = new SightSeekerSprite();
		return sightSeekerSprite;
	}
	
	private SightSeekerSprite() {
		init();
		initAnimations();
	}
	
	private void init() {
		Image sightSeekerSpriteSheet = new Image( getClass().getResourceAsStream( SIGHT_SEEKER_IMG_PATH ) );
		loadSprites( sightSeekerSpriteSheet );
	}
	
	private void initAnimations() {
		animations = new Animation[ 1 ];
		animations[ 0 ] = Animation.createAnimation( 2, getSpriteArray(), 0, 20 );
	//	animations[ 1 ] = Animation.createAnimation( 2, getSpriteArray(), 8, 7 );
	//	animations[ 2 ] = Animation.createAnimation( 7, getSpriteArray(), 10, 7 );
	} 
	
	@Override
	protected float getScaledX() { return 3; }
	
	@Override
	protected float getScaledY() { return 3; }
	
}
