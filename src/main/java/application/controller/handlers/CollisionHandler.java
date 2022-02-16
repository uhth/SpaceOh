package application.controller.handlers;

import java.util.List;

import application.controller.enemies.EnemiesController;
import application.controller.player.PlayerController;
import application.controller.projectiles.BulletsController;
import application.entities.Entity;
import application.entities.PlayerEntity;
import application.entities.projectiles.BulletEntity;
import application.view.SpaceOhGraphics;

public class CollisionHandler
{
	
	SpaceOhGraphics g;
	
	public CollisionHandler( SpaceOhGraphics g ) {
		this.g = g;
	}
	
	public void checkCollisions() {
		List< Entity > entities = g.getOnScreenEntities();
		for( int i = 0; i < entities.size(); i++ ) {
			if( !entities.get( i ).isAlive() ) continue;
			for( int j = i + 1; j < entities.size(); j++ ) {
				if( !entities.get( j ).isAlive() ) continue;
				entities.get( i ).createHitbox();
				entities.get( j ).createHitbox();
				if ( collides( entities.get( i ), entities.get( j ) ) )
					handleCollision( entities.get( i ), entities.get( j ) );
			}
		}
	}
	
	
	
	private boolean collides( Entity entity1, Entity entity2 ) {
		return entity1.getHitbox().intersects( entity2.getHitbox() );
	}
	
	private void handleCollision( Entity entity1, Entity entity2 ) {
		
		if( entity1.getEntityType().equals( "PLAYER" ) ) 
		{
			PlayerEntity playerEntity = ( PlayerEntity ) entity1;
			switch( entity2.getEntityType() ) 
			{
				case "BULLET" :
					BulletEntity bulletEntity = ( BulletEntity ) entity2;
					if( bulletEntity.getSource() instanceof PlayerEntity ) break;
					PlayerController.hurtPlayer( playerEntity, bulletEntity.getDmg() );
					BulletsController.hurtBullet( bulletEntity, 10 );
				break;
				case "ENEMY" :
					EnemiesController.hurtEnemy( entity2, playerEntity.getBodyDmg(), false );
					PlayerController.hurtPlayer( playerEntity, entity2.getBodyDmg() );
					break;
				default :
				break;
			}
		}
		
		else if( entity1.getEntityType().equals( "ENEMY" ) ) 
		{
			switch( entity2.getEntityType() ) 
			{							
				case "BULLET" :
					BulletEntity bulletEntity = ( BulletEntity ) entity2;
					if( bulletEntity.getSource().getEntityType().equals( "ENEMY" ) ) break;
					EnemiesController.hurtEnemy( entity1, bulletEntity.getDmg(), true );
					BulletsController.hurtBullet( bulletEntity, 10 );
				break;
				
				case "PLAYER" :
					PlayerEntity playerEntity = ( PlayerEntity ) entity2;
					EnemiesController.hurtEnemy( entity1, playerEntity.getBodyDmg(), false );
					PlayerController.hurtPlayer( playerEntity, entity1.getBodyDmg() );
					break;
				default :
				break;
			}
		}
		
		else if( entity1.getEntityType().equals( "BULLET" ) ) 
		{
			BulletEntity bulletEntity = ( BulletEntity ) entity1;
			switch( entity2.getEntityType() ) 
			{							
				
				case "ENEMY" :
					if( bulletEntity.getSource().getEntityType().equals( "ENEMY" ) ) break;
					EnemiesController.hurtEnemy( entity2, bulletEntity.getDmg(), true );
					BulletsController.hurtBullet( bulletEntity, bulletEntity.getHp() );
					break;
					
				case "PLAYER" :
					if( bulletEntity.getSource().getEntityType().equals( "PLAYER" ) ) break;
					PlayerEntity playerEntity = ( PlayerEntity ) entity2;
					PlayerController.hurtPlayer( playerEntity, bulletEntity.getDmg() );
					BulletsController.hurtBullet( bulletEntity, bulletEntity.getHp()  );
					break;
					
				default :
				break;
			}
		}
	}
		
	
}
