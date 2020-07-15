package game;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import engine.Game;

public class Input {
	
//	cursor movement callback
//	--------------------------
	public static GLFWCursorPosCallback cursorPosCallback=new GLFWCursorPosCallback() {
		
		public void invoke(long window, double xpos, double ypos) {
			currentCursorPosX = (float)(xpos/(float)mWidth);
			currentCursorPosY = (float)(ypos/(float)mHeight);
		}
	};
	
//	mouse button press callback
//	----------------------------
	public static GLFWMouseButtonCallback mouseButtonCallback = new GLFWMouseButtonCallback() {
		
		@Override
		public void invoke(long window, int button, int action, int mods) {
			
			if (button==GLFW_MOUSE_BUTTON_LEFT && action==GLFW_PRESS) {
				mouseState[0] = true;
			}
			if (button==GLFW_MOUSE_BUTTON_LEFT && action==GLFW_RELEASE) {
				mouseState[0] = false;
			}
			
			if (button==GLFW_MOUSE_BUTTON_RIGHT && action==GLFW_PRESS) {
				mouseState[2] = true;
			}
			if (button==GLFW_MOUSE_BUTTON_RIGHT && action==GLFW_RELEASE) {
				mouseState[2] = false;
			}
		}
	};
	
//		window size change callback
//		---------------------------	
	public static GLFWWindowSizeCallback windowSizeCallback=new GLFWWindowSizeCallback() {
		
		@Override
		public void invoke(long window, int width, int height) {
			mWidth=width;
			mHeight=height;
			glViewport(0, 0, width, height);
			Game.setDimensions();
			
		}
	};
	
	public static float currentCursorPosX=0.0f;
	public static float currentCursorPosY=0.0f;
	public static boolean[] mouseState = new boolean[3];
	
	public static int mWidth=0;
	public static int mHeight=0;

	
//	the bridge function that is called in Game class 
//	and executes updates based on MainGame class
//	-------------------------------------------------
	public static void ProcessUpdate() {
		
		if (mouseState[0]) {
			MainGame.selectActiveCell(currentCursorPosX,currentCursorPosY);
		}
		else if (mouseState[2]) {
			System.out.println("Hello there");
		}
	}
	
}
