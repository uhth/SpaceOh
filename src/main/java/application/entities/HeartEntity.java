package application.entities;

import application.view.animations.Animation;
import application.view.sprites.hud.HeartSprite;

public class HeartEntity extends Entity 
{
	public HeartEntity() {
		super( HeartSprite.getInstance() );
		playAnimationOnce( animations[ 1 ], 0 );
	}
		
	
	@Override
	public Animation getIdleAnimation() {
		return animations[ 0 ];
	}
	
	@Override
	public Animation getDeathAnimation() {
		return animations[ 2 ];
	}
	
	@Override
	public Animation getSpawnAnimation() {
		return animations[ 1 ];
	}
	
	@Override
	protected boolean hasAnimation() { return true;	}
	
}
