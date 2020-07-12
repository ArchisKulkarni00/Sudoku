package engine;

import org.joml.Matrix4f;

public class CameraOrtho2d {
	
	private Matrix4f projection;
	int scale = 1000;
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
		projection = new Matrix4f().ortho2D(-width/2, width/2, -height/2, height/2).scale(scale);
	}
	
	public int getScale() {
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
