package application.entities.emitter;

import application.entities.enemies.SightSeekerEntity;
import application.utils.Vector2D;

public class SightSeekerEmitter extends Emitter
{
	
	SightSeekerEntity sightSeekerEntity;
	private int fireTime;

	public SightSeekerEmitter( Vector2D pivot, SightSeekerEntity sightSeekerEntity ) {
		super(pivot);
		this.sightSeekerEntity = sightSeekerEntity;
		this.fireTime = 0;
	}
	
	@Override
	public void fire() {
		//cross projectiles pattern
		if( fireTime < 3 ) {
			int a = 4;
			int b = 0;
			while( b++ < a ) {
				fireProjectile( sightSeekerEntity.getBulletSupplier() );
				Rotate( 1.5708 ); //90 degrees
			}
		}
		//x projectiles pattern	
		else if( fireTime > 2 && fireTime < 6 ) {
			int a = 4;
			int b = 0;
			Rotate( 0.785398 ); //45 degrees
			while( b++ < a ) {
				fireProjectile( sightSeekerEntity.getBulletSupplier() );
				Rotate( 1.5708 ); //90 degrees
			}
			Rotate( 0.785398 ); //45 degrees
		}
		fireTime++;
		if( fireTime >= 10 ) fireTime = 0;
	}

}
