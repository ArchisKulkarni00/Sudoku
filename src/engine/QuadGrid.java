package engine;

import java.util.Vector;

public class QuadGrid {
	protected Quad[] mQuadGrid = null;
	protected int mHeight,mWidth;
	protected float mStartX,mStartY;
	protected float mQuadHeight,mQuadWidth;
	protected float scaleWidth = 0.3f;
	protected float scaleHeight = 0.3f;
	
	
//	public QuadGrid(int gridWidth,int gridHeight,float x,float y,float width, float height) {
//		mHeight = gridHeight;
//		mWidth = gridWidth;
//		mStartX = x;
//		mStartY=y;
//		scaleWidth=width;
//		scaleHeight=height;
//		computeDimensions();
//	}

	public int getmHeight() {
		return mHeight;
	}

	public int getmWidth() {
		return mWidth;
	}

	public float getmQuadHeight() {
		return mQuadHeight;
	}

	public float getmQuadWidth() {
		return mQuadWidth;
	}
	
	
	protected void computeDimensions() {
		mQuadWidth = (float)scaleWidth/mWidth;
		mQuadHeight = (float)scaleHeight/mHeight;
//		System.out.println(mQuadWidth+"   "+mQuadHeight);
	}
	
	public void createGrid() {
		mQuadGrid = new Quad[mWidth*mHeight];
		for(int i=0;i<mHeight;i++) {
			for(int j=0;j<mWidth;j++) {
				mQuadGrid[i*mWidth+j] = new Quad(mStartX+(j*mQuadWidth), mStartY-(i*mQuadHeight), mQuadWidth, mQuadHeight);
			}
		}
	}
	
	public void updateTextureTemp(int tex[]) {
		for(int i=0;i<mWidth*mHeight;i++) {
			mQuadGrid[i].setTexture(tex[i]);
		}
	}
	
	public void push(Vector<Quad>mQuadVector) {
		for(int i=0;i<mWidth*mHeight;i++) {
			mQuadVector.add(mQuadGrid[i]);
		}
	}

}
