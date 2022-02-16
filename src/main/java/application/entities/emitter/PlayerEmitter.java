package application.entities.emitter;

import application.entities.PlayerEntity;
import application.utils.Vector2D;

public class PlayerEmitter extends Emitter
{

	PlayerEntity playerEntity;	
	
	public PlayerEmitter(Vector2D pivot, PlayerEntity playerEntity ) {
		super( pivot );
		this.playerEntity = playerEntity;
	}
	
	@Override
	public void fire() {
		super.fireProjectile( playerEntity.getBulletSupplier() );
	}
	
}