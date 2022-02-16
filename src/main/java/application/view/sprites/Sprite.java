package application.view.sprites;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import application.view.animations.Animation;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;


public abstract class Sprite extends ImageView
{
	
	/*
	 * Most of cutting and resizing operations executed on sprites
	 * are very slow, but they usually only need to be done once. 
	 */
	
	//sprite properties
	private SnapshotParameters parameters;
	private Rectangle2D[] viewportArray;
	private Image[] spriteArray;
	private int numCells;
	private int numHCells;
	private int numVCells;
	private float sWidth; //sprite width
	private float sHeight; //sprite height
	
	//animations
	protected Animation[] animations;
		
	public void loadSprites( Image spriteSheet ) {
			
	    setImage( spriteSheet );
		
		this.sWidth = getSWidth();
		this.sHeight = getSHeight();
		this.setSmooth( false );
					
		numHCells = (int) ( spriteSheet.getWidth() / sWidth );
		numVCells = (int) ( spriteSheet.getHeight() / sHeight );
		
		numHCells += numHCells > 0 ? 0 : 1; 
		numVCells += numVCells > 0 ? 0 : 1; 
		
		numCells = numHCells * numVCells;
						
		viewportArray = new Rectangle2D[ numCells ];
		spriteArray = new Image[ numCells ];
		
		int counter = 0;
		
		parameters = new SnapshotParameters();
	    parameters.setFill( Color.TRANSPARENT );
	    

		for( int y = 0; y < numVCells; y++ )
			for( int x = 0; x < numHCells ; x++ ) {
				viewportArray[ counter++ ] = new Rectangle2D( x * sWidth, y * sHeight, sWidth, sHeight );
			}
		
		loadImages();
	}
	
	//nightmare func
	private void loadImages() 
	{
		for( int i = 0; i < numCells; i++ ) 
		{
			parameters.setViewport( viewportArray[ i ] );
			
			float widthS = sWidth * getScaledX();
			float heightS = sHeight * getScaledY();
			
			//SPRITE RESIZING
			BufferedImage bufferedImage = SwingFXUtils.fromFXImage( snapshot(parameters, null ), null );
			ByteArrayOutputStream bArrayOS = new ByteArrayOutputStream();
			try { ImageIO.write( bufferedImage, "png", bArrayOS ); } 
			catch ( IOException e ) { System.out.println("Unable to write sprite"); }
			ByteArrayInputStream bArrayIS = new ByteArrayInputStream( bArrayOS.toByteArray() );
			try { bArrayOS.close();	}
			catch ( IOException e ) { System.out.println("Unable to close stream"); }
			Image img = new Image( bArrayIS, widthS, heightS, false, false );
			try { bArrayIS.close();	} 
			catch ( IOException e ) { System.out.println("Unable to close stream"); }
			
			spriteArray[ i ] = img;
		}
	}
	
	public Image getSpriteAt( int i ) {	return spriteArray[ i ]; }
	
	public Image[] getSpriteArray() { return spriteArray; }
	
	protected float getScaledX() { return 1.0f; }
	protected float getScaledY() { return 1.0f; }
	
	protected float getSWidth() {
		return 32.0f;
	}
	
	protected float getSHeight() {
		return 32.0f;
	}
	
	public Animation[] getAnimations() { return animations; }
	
}
