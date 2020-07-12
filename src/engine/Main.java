package engine;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		Game game = new Game(1920,1080,"GameWindow");
		boolean isInitialized = game.init();
		
		game.initShader("Shaders/vs001", "Shaders/fs002");
		game.enableCamera();
	
		Quad mQuad = new Quad(0.0f,0.0f,0.5f,0.5f);
		mQuad.setTexture(1);
		mQuad.setCoordinates(0, 0, 0.25f, 0.25f);
		mQuad.setBgColour(1.0f, 0.0f, 0.0f, 1.0f);
		game.mVQuadVector.add(mQuad);
		
		
		
		Text mText = new Text("archis",-0.5f,0.5f,1.0f,1.0f);
		
		Texture mTexture1 = new Texture("images/theme.png",1);
		game.mTextureVector.add(mTexture1);
		Texture mTexture = new Texture("images/SegoeUISemibold.png",0);
		game.mTextureVector.add(mTexture);
		
		if (isInitialized) {
			
			game.initVertexArray();
			game.runloop();
		}
		game.terminate();
	}

}
