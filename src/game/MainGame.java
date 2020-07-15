package game;

import java.io.IOException;
import java.util.Random;

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
		@SuppressWarnings("unused")
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
		public void setTextBgColour(float r,float g, float b,float a) {
			number.setBgColour(r, g, b, a);
		}
		
	}
	
	private static int[] sudoku = null;
	private static int[] arrayOfBlanks = null;
	private static cell[] cellGrid = null;
	private static int currIndex=0;
	
	public static void main(String[] args) throws IOException {
		
//		main data stores required
//		1. base sudoku (can be used as solution)
//		2. array of blank spaces which would punch holes through the sudoku
//		3. punched sudoku which will be filled by the user
//		---------------------------------------------------------------------------
		
		Game game = new Game(720,720,"Sudoku");
		
		boolean isGame = game.init();
		game.initShader("Shaders/vs001", "Shaders/fs002");
		
		
		
		Quad bgQuad = new Quad(-0.5f,0.5f,1.0f,1.0f);
		bgQuad.setBgColour(0.5f, 0.5f, 0.0f, 1.0f);
		bgQuad.setTexture(1);
		bgQuad.setCoordinates(0, 0, 0.25f, 0.25f);
		Game.mVQuadVector.add(bgQuad);
		
//		1.base sudoku
//		-----------------
		SudokuGridGenerator generator = new SudokuGridGenerator();
		sudoku = generator.generateGrid();
		
//		2. array of blank members
//		---------------------------
		arrayOfBlanks = createBlanks(10);
		
//		3.punched array
//		---------------
		punchArray();
		
//		once all data is generated we can create grid and assign the sudoku
		cellGrid = createGrid();
//		changeStyleOfActive(23);
		
		
		Texture TextTexture = new Texture("images/SegoeUISemibold.png",0);
		Game.mTextureVector.add(TextTexture);
		Texture cellTexture = new Texture("images/Theme.png",1);
		Game.mTextureVector.add(cellTexture);
		if (isGame) {
			Game.initVertexArray();
			game.runloop();
		}
		
	}
	
//	creates the drawable grid layout and adds the sudoku numbers to their position
//	------------------------------------------------------------------------------
	private static cell[] createGrid() {
		cellGrid = new cell[81];
		
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
			numString = sudoku[i]==999 ? " ":Integer.toString(sudoku[i]);
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
	
//	randomly generates blanks in the sudoku
//	----------------------------------------
	private static int[] createBlanks(int numberOfBlanks) {
		
		int blankIndices[] = new int[numberOfBlanks];
		Random random = new Random();
		
		for(int i=0;i<numberOfBlanks;i++) {
			blankIndices[i] = Math.abs(random.nextInt()%81);
		}
		
		return blankIndices;
		
	}
	
//	punch holes in the sudoku
//	-------------------------
	private static void punchArray() {
		
		for (int i = 0; i < arrayOfBlanks.length; i++) {
			sudoku[arrayOfBlanks[i]] = 999;
		}
	}
	
//	change style of the active cell
//	-------------------------------
	private static void changeStyleOfActive(int newIndex) {
		setDefaultColour();
		cellGrid[newIndex].setBgColour(1.0f, 0.5f, 0.0f, 1.0f);
		cellGrid[newIndex].setTextBgColour(0.0f, 0.0f, 1.0f, 0.0f);
		currIndex = newIndex;
		Game.initVertexArray();
	}

//	called by Input class when left click is pressed
//	------------------------------------------------
	static void selectActiveCell(float xpos,float ypos) {
//		System.out.println("LMB at X:"+xpos+"  Y:"+ypos);
		
//		!!! this cursor system works on window coordinates rather than opengl coordinates !!!
		
		if (xpos>0.275f && xpos<0.725f && ypos>0.275f && ypos<0.725f) {
			int row = (int) ((ypos-0.275f)/0.05f)+1;
			int column = (int) ((xpos-0.275f)/0.05f)+1;
			int cellNumber = (row-1)*9 + column-1;
			changeStyleOfActive(cellNumber);
//			System.out.println("LMB in grid row="+row+" column="+column+" cell number="+sudoku[cellNumber]);
		}
	}
	
	private static void setDefaultColour() {
		int row = (int)(currIndex/9);
		int column = (int)(currIndex%9);
		
//		we check if row and column are even or odd.
//		then we assign colours by XORing those results.
//		--------------------------------------------------
		if ((row/3)%2==0 ^ (column/3)%2==0) {
			cellGrid[currIndex].setBgColour(0.0f, 0.3f, 0.0f, 1.0f);
		}
		else {
			cellGrid[currIndex].setBgColour(0.0f, 0.0f, 0.3f, 1.0f);
		}
		cellGrid[currIndex].setTextBgColour(1.0f, 1.0f, 1.0f, 0.0f);
	}
}


