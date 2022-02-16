package application.view.sprites.enemies;

import application.view.animations.Animation;
import application.view.sprites.Sprite;

public class SightSeekerSprite extends Sprite {
	
	private static final String SIGHT_SEEKER_IMG_PATH = "/application/images/enemies/sightSeekerSpriteSheetV1.png";
	
	private static SightSeekerSprite sightSeekerSprite;
	
	public static SightSeekerSprite getInstance() {
		if( sightSeekerSprite == null )
			sightSeekerSprite = new SightSeekerSprite();
		return sightSeekerSprite;
	}
	
	private SightSeekerSprite() {
		super( loadSprite( SIGHT_SEEKER_IMG_PATH ), 32, 32, 2.0, 2.0 );
		animations = new Animation[ 1 ];
		initAnimations();
	}
	

	private void initAnimations() {
		animations[ 0 ] = Animation.createAnimation( 2, getSpriteArray(), 0, 20 );
	//	animations[ 1 ] = Animation.createAnimation( 2, getSpriteArray(), 8, 7 );
	//	animations[ 2 ] = Animation.createAnimation( 7, getSpriteArray(), 10, 7 );
	} 
	
	
}
