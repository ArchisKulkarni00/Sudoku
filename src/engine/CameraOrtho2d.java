package engine;

import org.joml.Matrix4f;

public class CameraOrtho2d {
	
	private Matrix4f projection;
	float scale = 1.3f;
	private boolean isEnabled;
	int width,height;
	
	public CameraOrtho2d(int sWidth, int sHeight) {
		setProjection(sWidth, sHeight);
		isEnabled = true;
	}
	
	public void setProjection(int width,int height) {
		this.width = width;
		this.height = height;
		updateProjection();
	}
	
	private void updateProjection() {
//		projection = new Matrix4f().ortho2D(-width/2, width/2, -height/2, height/2).scale(scale);
		System.out.println((float)width/height);
		projection = new Matrix4f().ortho2D(-(float)width/height,(float)width/height,
				-(float)height/height,(float)height/height).scale(scale);
		
//		projection = new Matrix4f().ortho2D(-1.777f,1.777f,-1.0f,1.0f);
	}
	
	public float getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = Math.abs(this.scale+scale);
		updateProjection();
	}

	public Matrix4f getProjection() {
		return projection;
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}


}
