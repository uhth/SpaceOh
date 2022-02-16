package application.entities;

import application.utils.Vector2D;
import application.view.sprites.HealthBarSprite;
import javafx.scene.image.Image;

public class HealthBarEntity extends Entity {
	
	Entity entity;
	
	public HealthBarEntity( Entity entity, HealthBarSprite sprite ) {
		super( entity, sprite );
		this.entity = entity;
		
	}
	
	@Override
	public Image getFrame() {
		if( entity.hp >= entity.maxHp * 0.75f )
			return sprite.getSpriteAt( 0 );
		else if( entity.hp >= entity.maxHp * 0.45f )
			return sprite.getSpriteAt( 1 );
		else if( entity.hp > entity.maxHp * 0.01f )
			return sprite.getSpriteAt( 2 );
		else
			return sprite.getSpriteAt( 3 );
	}	
	
	
	@Override
	public Vector2D getDrawCoords() {
		Vector2D temp = super.getDrawCoords();
		temp.add( 0, entity.getOffY() + 5 );
		return temp;
	}
	

}