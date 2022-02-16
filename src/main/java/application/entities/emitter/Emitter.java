package application.entities.emitter;
import java.util.function.Supplier;

import application.entities.Entity;
import application.model.GameState;
import application.utils.Vector2D;

public class Emitter {
		
	protected Vector2D point;
	protected Vector2D pivot;
	protected Vector2D direction;
	
	public Emitter( Vector2D pivot ) {		
		this.pivot = new Vector2D( pivot );
		point = new Vector2D( pivot.getX(), pivot.getY() - getRadius() );
		updateDirection();
	}
	
	public void Rotate( double a ) {
	  double sin = Math.sin( a );
	  double cos = Math.cos( a );
	  point.subtract( pivot );
	  point = new Vector2D( ( point.getX() * cos ) - ( point.getY() * sin ), ( point.getX() * sin ) + ( point.getY() * cos ) ).round();
	  point.add( pivot );
	  updateDirection();
	}
	
	protected <T> void fireProjectile( Supplier<T> supplier ) {
		GameState.getInstance().addProjectile( (Entity) supplier.get() );
	}
	
	//override to create firing patterns etc
	public void fire() {}
	
	private void updateDirection() { direction = new Vector2D( point.getX() - pivot.getX(), point.getY() - pivot.getY() ).Normalize(); }
	public Vector2D getDirection() { return direction; }	
	public double getRadius() {	return 110.0; }
	public Vector2D getPoint() { return point; }
	public void setPoint( Vector2D point ) { this.point = point; }
	public Vector2D getPivot() { return pivot; }
	public void setPivot( Vector2D pivot ) { this.pivot = pivot; }

	
}
