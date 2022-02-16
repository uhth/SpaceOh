package application.controller.ai;

import application.entities.Entity;
import application.utils.Vector2D;


public abstract interface RattlerAI {

	public default void accelerateX( Entity entity, Vector2D playerPos ) {
		if( entity.getPos().getX() - playerPos.getX() > 50 ) entity.getVelocity().subtract( entity.getSpeed(), 0 );
		else if( entity.getPos().getX() - playerPos.getX() < -50 ) entity.getVelocity().add( entity.getSpeed(), 0 );
		
		//overshoot check
		if( entity.getVelocity().getX() > entity.getMaxSpeed() ) entity.setVelocity( entity.getMaxSpeed(), entity.getVelocity().getY() );
		else if( entity.getVelocity().getX() < -entity.getMaxSpeed() ) entity.setVelocity( -entity.getMaxSpeed(), entity.getVelocity().getY() );
	}
	
	public default void accelerateY( Entity entity, Vector2D playerPos ) {
		
//		if( ( playerPos.y - entity.getPosY() >= 500 ) && ( playerPos.y - entity.getPosY() <= 600 ) ) entity.getVelocity().y = 0;
//		else if( playerPos.y - entity.getPosY() < 500 ) entity.getVelocity().y += -entity.getSpeed();
/*		if( playerPos.getY() - entity.getPos().getY() > 20 ) entity.getVelocity().add( 0, entity.getSpeed() );
		
		//overshoot check
		if( entity.getVelocity().getY() > entity.getMaxSpeed() ) entity.setVelocity( entity.getVelocity().getX(), entity.getMaxSpeed() );
		else if( entity.getVelocity().getY() < -entity.getMaxSpeed() ) entity.setVelocity( entity.getVelocity().getX(), -entity.getMaxSpeed() );
*/	}
	
}
