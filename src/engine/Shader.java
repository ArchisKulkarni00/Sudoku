package engine;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;

public class Shader {
	
	private int shaderProgram;
	private String vertexShader,fragmentShader;
	private Matrix4f identitiyMatrix4f = new Matrix4f(); 
	
	
	public int getShaderProgram() {
		return shaderProgram;
	}

	public void load(String vertex, String fragment) throws IOException {
		vertexShader = loadFiles(vertex);
		fragmentShader = loadFiles(fragment);
		this.createShader();
		this.setUniform1iv();
	}
	
	public void setActive() {
		GL33.glUseProgram(shaderProgram);
	}
	
	public void delete() {
		GL33.glDeleteProgram(shaderProgram);
	}
	
	public void setUniform1iv() {
		this.setActive();
		int tempLocation;
		for(int i=0;i<8;i++) {
			tempLocation = GL33.glGetUniformLocation(shaderProgram, "TextureArray["+i+"]");
			GL33.glUniform1i(tempLocation, i);
		}
	}
	
	public void setCamera(Matrix4f projection) {
		int location = GL33.glGetUniformLocation(shaderProgram, "projection");
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		projection.get(buffer);
		GL33.glUniformMatrix4fv(location, false, buffer);
	}
	
	public void disableCamera() {
		int location = GL33.glGetUniformLocation(shaderProgram, "projection");
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		identitiyMatrix4f.get(buffer);
		GL33.glUniformMatrix4fv(location, false, buffer);
	}
	
	
	private String loadFiles(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
	}
	
	private void createShader() {
		shaderProgram = GL33.glCreateProgram();
		int vs = compileShader(GL33.GL_VERTEX_SHADER,vertexShader);
		int fs = compileShader(GL33.GL_FRAGMENT_SHADER,fragmentShader);
		GL33.glAttachShader(shaderProgram,vs);
		GL33.glAttachShader(shaderProgram, fs);
		GL33.glLinkProgram(shaderProgram);
		GL33.glDeleteProgram(vs);
		GL33.glDeleteProgram(fs);
	}
	
	private int compileShader(int type,String source) {
		int shader = GL33.glCreateShader(type);
		GL33.glShaderSource(shader, source);
		return shader;
	}

}
