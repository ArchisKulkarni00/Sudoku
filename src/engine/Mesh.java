package engine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;

public class Mesh {
	private int vertexArrayID, vertexBufferID,indexBufferID,vertexCount;
	private float[] vertices;
	private int[] indices;
	private Texture mTexture=null;


	public void setTexture(String textureName) {
//		mTexture=new Texture(textureName);
		mTexture.loadTexture();
	}

	public int getVertexArrayID() {
		return vertexArrayID;
	}

	public void setVertexArrayID(int vertexArrayID) {
		this.vertexArrayID = vertexArrayID;
	}
	
	public Mesh(float[] vertices,int[] indices) {
		this.vertices = vertices;
		this.indices = indices;
		this.vertexCount = indices.length;
	}
	
	public void initMesh() {
		
		//get the vertex data inside a float buffer
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
		verticesBuffer.put(vertices);
		verticesBuffer.flip();
		indicesBuffer.put(indices);
		indicesBuffer.flip();
		
		//create vertex array
		vertexArrayID = GL33.glGenVertexArrays();
		GL33.glBindVertexArray(vertexArrayID);
		
		//create vertex buffer
		vertexBufferID = GL33.glGenBuffers();
		GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, vertexBufferID);
		GL33.glBufferData(GL33.GL_ARRAY_BUFFER, verticesBuffer, GL33.GL_STATIC_DRAW);
		GL33.glEnableVertexAttribArray(0);
		GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 20, 0);
		GL33.glEnableVertexAttribArray(1);
		GL33.glVertexAttribPointer(1, 2, GL33.GL_FLOAT, false, 20, 12);
//		GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, 0);
		
		//create index buffer
		indexBufferID = GL33.glGenBuffers();
		GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, indexBufferID);
		GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL33.GL_STATIC_DRAW);
//		GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		
		GL33.glBindVertexArray(0);
		
	}
	
	public void delete() {
		GL33.glDeleteVertexArrays(vertexArrayID);
		GL33.glDeleteBuffers(vertexBufferID);
		GL33.glDeleteBuffers(indexBufferID);
		if (mTexture!=null) {
			mTexture.delete();
		}
	}
	
	public void render() {
		
		//checks if texture is activated for mesh and binds it
		if (mTexture!=null) {
			mTexture.setActive();
		}
		GL33.glBindVertexArray(vertexArrayID);
		GL33.glDrawElements(GL33.GL_TRIANGLES, vertexCount, GL33.GL_UNSIGNED_INT, 0);
//		GL33.glBindVertexArray(0);
	}
}
