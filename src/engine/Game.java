package engine;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

import java.io.IOException;
import java.util.Vector;

//import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;

public class Game {
	
	//########## Internal Data members ##########
	private int mWidth,mHeight;
	private String mWindowTitle;
	private long mWindow;
	private Shader mShader=new Shader();
	private CameraOrtho2d mCamera = null;
	private GLFWWindowSizeCallback windowSizeCallback = null;
	
	private double frameCapacity = 1.0/30.0;
	private double initTime = 0.0;
	private double finalTime = 0.0;
	private double deltaTime = 0.0;
//	private double fpsChecker = 0.0;
//	private int frames = 0;
	
//	private GLFWScrollCallback scrollCallback = null;
	public static Vector<Texture> mTextureVector = new Vector<>();
	
//	text element holders
	public static Vector<Quad> mTextVector = new Vector<>();
	VertexArray mTextVertexArray = new VertexArray();
	
	
//	Viewport element holders
	public static Vector<Quad> mVQuadVector = new Vector<>();
	VertexArray mVVertexArray = new VertexArray();
	
	//########## External public functionality ##########
	
	public Game(int width,int height, String title) {
		mWidth = width;
		mHeight = height;
		mWindowTitle = title;
	}
	
	public  Game() {
		this(1280, 720, "GameWindow");
	}
	
	public boolean init() {
		if (!glfwInit()) {
			throw new IllegalStateException("cannot init glfw");
		}
		
//		glfwWindowHint(GLFW_RESIZABLE, 0);
		glfwWindowHint(GLFW_MAXIMIZED, 1);
		mWindow = glfwCreateWindow(mWidth,mHeight, mWindowTitle, 0, 0);
//		long mCursor = glfwCreateStandardCursor(GLFW_HRESIZE_CURSOR);
//		glfwSetCursor(mWindow, mCursor);
		
		if (mWindow==0) {
			throw new IllegalStateException("cannot start window");
		}
		
		//create opengl context
		glfwMakeContextCurrent(mWindow);
		GL.createCapabilities();
//		glfwSwapInterval(0);
		
		//set colour buffer data
		glClearColor(0.8f, 0.8f, 0.8f, 0.0f);
		setAllCallbacks();
		
		//make window visible
		glfwShowWindow(mWindow);
		return true;
	}
	
	public void runloop() {
		while (!glfwWindowShouldClose(mWindow)) {
//			we get initial time reading
			initTime = TimerControl.getTime();
			
			ProcessInput();
			ProcessOutput();
			
//			we get time after processing
			finalTime = TimerControl.getTime();
			
//			we get total time of executing the processes
			deltaTime = finalTime-initTime;
			
//			wait till time for 1 frame is completed if processing finishes before allotted time
			while (deltaTime<=frameCapacity) {
				deltaTime = TimerControl.getTime()-initTime;
			}
			
//			frame rate calculation code
			
//			frames++;
//			fpsChecker+=deltaTime;
//			if (fpsChecker>=1.0) {
//				fpsChecker=0;
//				System.out.println("FPS: "+frames);
//				frames=0;
//			}
			
		}
	}
	
	public void terminate() {
		mShader.delete();
		for(int i=0;i<mTextureVector.size();i++) {
			mTextureVector.get(i).delete();
		}
		mVVertexArray.delete();
		mTextVertexArray.delete();
//		plane.delete();
		glfwTerminate();
	}
	
	public void initShader(String vs,String fs) throws IOException {
		mShader.load(vs, fs);
	}
	
	public void initVertexArray() {
		mVVertexArray.init(mVQuadVector);
		mTextVertexArray.init(mTextVector);
	}
	
	public void enableCamera() {
		mCamera = new CameraOrtho2d(mWidth, mHeight);
		mShader.setCamera(mCamera.getProjection());
	}
	
	//########## Internal private functionality ##########
	
//	Drawing functions
	private void DrawViewPort() {
		for(int i=0;i<mTextureVector.size();i++) {
			mTextureVector.get(i).setActive();
		}
		mShader.setCamera(mCamera.getProjection());
		mShader.setActive();
		mVVertexArray.setActive();
		GL33.glDrawElements(GL33.GL_TRIANGLES, mVVertexArray.getVertexCount(), GL33.GL_UNSIGNED_INT, 0);
	}
	
	private void DrawText() {
		GL33.glEnable(GL33.GL_BLEND);
		GL33.glBlendFunc(GL33.GL_ONE, GL33.GL_ONE);
		for(int i=0;i<mTextureVector.size();i++) {
			mTextureVector.get(i).setActive();
		}
		mShader.setActive();
		mTextVertexArray.setActive();
		GL33.glDrawElements(GL33.GL_TRIANGLES, mTextVertexArray.getVertexCount(), GL33.GL_UNSIGNED_INT, 0);
		GL33.glDisable(GL33.GL_BLEND);
	}
	
	private void ProcessOutput() {
		
		glClear(GL_COLOR_BUFFER_BIT);
		
		DrawViewPort();
		DrawText();
		
		glfwSwapBuffers(mWindow);
		
	}
	
	private void ProcessInput() {
		glfwPollEvents();
		
		//add key maps here
		if (glfwGetKey(mWindow, GLFW_KEY_ESCAPE)==1) {
			glfwSetWindowShouldClose(mWindow,true);
		}
		
	}
	
//	find all the required callbacks here
	private void setAllCallbacks() {
		windowSizeCallback = new GLFWWindowSizeCallback() {
			
			@Override
			public void invoke(long window, int width, int height) {
				mWidth=width;
				mHeight=height;
				glViewport(0, 0, width, height);
				if (mCamera.isEnabled()) {
					mCamera.setProjection(width, height);
				}
//				System.out.println(mWidth+"  "+mHeight);
				
			}
		};
		
//		scrollCallback = new GLFWScrollCallback() {
//			
//			@Override
//			public void invoke(long window, double xoffset, double yoffset) {
//				mCamera.setScale((int)yoffset*50);
//			}
//		};
		
		glfwSetWindowSizeCallback(mWindow, windowSizeCallback);
//		glfwSetScrollCallback(mWindow, scrollCallback);
	}
	
	
	
}
