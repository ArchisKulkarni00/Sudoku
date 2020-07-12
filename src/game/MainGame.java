package game;

import java.io.IOException;

import backend.SudokuGridGenerator;
import engine.Game;
import engine.Quad;
import engine.Text;
import engine.Texture;

public class MainGame {
	
//	encapsulation class for each cell
//	-----------------------------------
	private static class cell{
		Quad mQuad = null;
		Text number = null;
		
		public cell(float x,float y,float width,float height) {
			mQuad = new Quad(x, y, width, height);
			mQuad.setTexture(1);
			mQuad.setCoordinates(0, 0, 0.25f, 0.25f);
			Game.mVQuadVector.add(mQuad);
		}
		
		public cell(float x,float y,float width,float height,String pString) {
			this(x, y, width, height);
			number = new Text(pString, x+(0.35f*width), y-(0.2f*height), width*0.6f, height*0.6f);
		}
		
		public void setBgColour(float r,float g, float b,float a) {
			mQuad.setBgColour(r, g, b, a);
		}
	}
	
	public static void main(String[] args) throws IOException {
		Game game = new Game(1920,1080,"Sudoku");
		
		boolean isGame = game.init();
		game.initShader("Shaders/vs001", "Shaders/fs002");
		game.enableCamera();
		
		Quad bgQuad = new Quad(-0.5f,0.5f,1.0f,1.0f);
		bgQuad.setBgColour(0.5f, 0.5f, 0.0f, 1.0f);
		bgQuad.setTexture(1);
		bgQuad.setCoordinates(0, 0, 0.25f, 0.25f);
		Game.mVQuadVector.add(bgQuad);
		
		SudokuGridGenerator generator = new SudokuGridGenerator();
		int[] sudokuSolution = generator.generateGrid();
		cell[] cellGrid = createGrid(sudokuSolution);
		
		Texture TextTexture = new Texture("images/SegoeUISemibold.png",0);
		Game.mTextureVector.add(TextTexture);
		Texture cellTexture = new Texture("images/Theme.png",1);
		Game.mTextureVector.add(cellTexture);
		if (isGame) {
			game.initVertexArray();
			game.runloop();
		}
		
	}
	
	private static cell[] createGrid(int[] sudoku) {
		cell[] cellGrid = new cell[81];
		
//		bg starts at -0.5,0.5
//		so we have a margin of 0.025
//		so total length available for drawing is 1-0.05=0.95
//		-----------------------------------------------------
		
		float startX = -0.475f;
		float startY = 0.475f;
		float sideDim = 0.95f/9.0f;
		int row = 0;
		int column= 0 ;
		String numString = ""; 
		
		for(int i=0;i<81;i++) {
			row = (int)(i/9);
			column = (int)(i%9);
			numString = Integer.toString(sudoku[i]);
			cellGrid[i] = new cell(startX+(column*sideDim),startY-(row*sideDim),sideDim,sideDim,numString);
			
//			we check if row and column are even or odd.
//			then we assign colours by XORing those results.
//			--------------------------------------------------
			if ((row/3)%2==0 ^ (column/3)%2==0) {
				cellGrid[i].setBgColour(0.0f, 0.3f, 0.0f, 1.0f);
			}
			else {
				cellGrid[i].setBgColour(0.0f, 0.0f, 0.3f, 1.0f);
			}
		}
		
		return cellGrid;
	}

}
