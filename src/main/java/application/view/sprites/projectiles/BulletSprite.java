package application.view.sprites.projectiles;

import application.view.animations.Animation;
import application.view.sprites.Sprite;

public class BulletSprite extends Sprite
{
	private static final String BULLET_IMAGE_PATH = "/application/images/projectiles/bulletSpriteSheetV1.png";
	
	private static BulletSprite bulletSprite;
		
	public static BulletSprite getInstance() {
		if( bulletSprite == null )
			bulletSprite = new BulletSprite();
		return bulletSprite;
	}
	
	private BulletSprite() {
		super( loadSprite( BULLET_IMAGE_PATH ), 32, 32, 2.0, 2.0 );
		initAnimations();
	}
	
	private void initAnimations() {
		animations = new Animation[ 2 ];
		animations[ 0 ] = Animation.createAnimation( 6, getSpriteArray(), 0, 8 );
		animations[ 1 ] = Animation.createAnimation( 4, getSpriteArray(), 6, 8 );
	}
	
	
	

}
