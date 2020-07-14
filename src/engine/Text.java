package engine;


public class Text extends QuadGrid{
	
	private String mString=null;
	private final float offset = 1.0f/16.0f;
	
//	########## Public functions ##########
	public Text(String pString,float x,float y, float width,float height) {
		mString = pString;
		mStartX = x;
		mStartY=y;
		scaleWidth=width;
		scaleHeight=height;
		mWidth = mString.length();
		mHeight = 1;
		computeDimensions();
		createGrid();
		createText();
		push();
	}
	
	public void setBgColour(float r,float g, float b,float a) {
		for (int i = 0; i < mQuadGrid.length; i++) {
			mQuadGrid[i].setBgColour(r, g, b, a);
		}
	}
	
	public void setText(String pString) {
		mString=pString;
		createText();
	}
//	########## Private functions ##########
	private void createText() {
		int temp = 0;
		int row,col;
		for(int i=0;i<mString.length();i++){
			temp = (int)mString.charAt(i);
			row = (int)temp%16;
			col = (int)temp/16;
			mQuadGrid[i].setCoordinates(row*offset, col*offset, offset,offset);
		}
	}
	private void push() {
//		System.out.println("Pushed to txtvec");
		push(Game.mTextVector);
	}
}
