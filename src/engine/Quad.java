package engine;

public class Quad {
	
//	quad dimension variables
	protected float x,y,width,height;
//	stores vertex data
	protected float[] mVertices = new float[24];
	
//	########## Public functions ##########
	public Quad(float x,float y,float width,float height,float textureSlot) {
		this(x,y,width,height);
		setTexture(textureSlot);
	};
	
	public Quad(float x,float y,float width,float height) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		float[] tempVerts= {
			x,y, 0.0f,0.0f, 0.0f, 1.0f,1.0f,1.0f,1.0f,
			x+width,y, 1.0f,0.0f, 0.0f, 1.0f,1.0f,1.0f,1.0f,
			x,y-height, 0.0f,1.0f, 0.0f, 1.0f,1.0f,1.0f,1.0f,
			x+width,y-height, 1.0f,1.0f, 0.0f, 1.0f,1.0f,1.0f,1.0f
		};
		
	
		mVertices=tempVerts;
		
	}
	
	
	public float getXCord() {
		return x;
	}
	
	public float getYCord() {
		return y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	
	public float[] getVertices() {
		return mVertices;
	}
	
	
	public void setTexture(float textureSlot) {
		mVertices[4]=mVertices[4+9]=mVertices[4+18]=mVertices[4+27]=textureSlot;
	}
	
	public void setCoordinates(float x,float y,float width, float height) {
		mVertices[2]=x; mVertices[3]=y;
		mVertices[2+9]=x+width; mVertices[3+9]=y;
		mVertices[2+18]=x; mVertices[3+18]=y+height;
		mVertices[2+27]=x+width; mVertices[3+27]=y+height;
	}
	
	public void setBgColour(float r,float g, float b,float a) {
		for(int i=5;i<=32;i+=9) {
			mVertices[i]=r;
			mVertices[i+1]=g;
			mVertices[i+2]=b;
			mVertices[i+3]=a;
		}
	}

}
