package application.controller.projectiles;

import java.util.ArrayList;
import java.util.List;

import application.entities.Entity;
import application.entities.projectiles.BulletEntity;
import application.model.GameState;
import application.settings.Settings;
import application.utils.Vector2D;
import application.view.SpaceOhGraphics;

public class BulletsController 
{
	
	GameState game;
	SpaceOhGraphics g;
	private List< BulletEntity > killBuffer;
	
	public BulletsController( GameState game, SpaceOhGraphics g ) {
		this.game = game;
		this.g = g;
		this.killBuffer = new ArrayList< BulletEntity >();
	}
	
	public static void hurtBullet( BulletEntity entity, float dmg ) {
		entity.takeDmg( dmg );
	}
	
	public void updateBullets() {
		List< Entity > bullets = game.getProjectiles();
				
		for( Entity entity : bullets ) 
		{
			if( !( entity instanceof BulletEntity ) ) continue;
			
			BulletEntity bulletEntity = ( BulletEntity ) entity;
			
			if( !bulletEntity.isAlive() ||bulletEntity.getPos().getX() < 0 || bulletEntity.getPos().getY() < 0 || bulletEntity.getPos().getX() > Settings.WINDOW_SIZE_W || bulletEntity.getPos().getY() > Settings.WINDOW_SIZE_H  ) 
			{
				killBuffer.add( bulletEntity );
				continue;
			}
			
			//Animation
			if( !bulletEntity.getCurrentAnimation().isPlaying() ) 
				bulletEntity.playAnimation( bulletEntity.getBulletFlyAnimation() );
			
			//movement
			if( bulletEntity.getCurrentAnimation() != bulletEntity.getBulletSpawnAnimation() ) {
				bulletEntity.getPos().add( bulletEntity.getVelocity() );
			}
			else
				bulletEntity.posDuringSpawn();
									
			bulletEntity.updateAnimation();
			
			//velocity
			bulletEntity.setVelocity( new Vector2D( bulletEntity.getDirection().productWithScalar( bulletEntity.getMaxSpeed() ) ) );
						
		} 
	}
	
	//make sure to call it before graphics
	public void killBullets() {
		for( BulletEntity bulletEntity : killBuffer )
			game.removeProjectile( bulletEntity );
		
		killBuffer.clear();
	}
	
	public void drawBullets() {	g.drawProjectiles( game.getProjectiles() );	}
		
	
}
