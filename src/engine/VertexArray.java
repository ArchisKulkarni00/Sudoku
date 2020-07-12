package engine;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Vector;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;

public class VertexArray {
	private int vertexArrayID, vertexBufferID,indexBufferID,numQuads;
	
	public int getVertexArrayID() {
		return vertexArrayID;
	}

	public void setVertexArrayID(int vertexArrayID) {
		this.vertexArrayID = vertexArrayID;
	}
	
	public void init(Vector<Quad> quads) {
		
		//get the vertex data inside a float buffer
		numQuads = quads.size();
		int offset=0;
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(80*numQuads);
		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(6*numQuads);
		for (int i = 0; i < numQuads; i++) {
			verticesBuffer.put(quads.get(i).getVertices());
			
			indicesBuffer.put(0+offset);
			indicesBuffer.put(1+offset);
			indicesBuffer.put(2+offset);
			
			indicesBuffer.put(1+offset);
			indicesBuffer.put(2+offset);
			indicesBuffer.put(3+offset);
			offset+=4;
		}
		verticesBuffer.flip();
		indicesBuffer.flip();
		
		
		
		//create vertex array
		vertexArrayID = GL33.glGenVertexArrays();
		GL33.glBindVertexArray(vertexArrayID);
		
		//create vertex buffer
		vertexBufferID = GL33.glGenBuffers();
		GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, vertexBufferID);
		GL33.glBufferData(GL33.GL_ARRAY_BUFFER, verticesBuffer, GL33.GL_STATIC_DRAW);
		GL33.glEnableVertexAttribArray(0);
		GL33.glVertexAttribPointer(0, 2, GL33.GL_FLOAT, false, 4*9, 0);
		
		GL33.glEnableVertexAttribArray(1);
		GL33.glVertexAttribPointer(1, 2, GL33.GL_FLOAT, false, 4*9, 8);
		
		GL33.glEnableVertexAttribArray(2);
		GL33.glVertexAttribPointer(2, 1, GL33.GL_FLOAT, false, 4*9, 16);
		
		GL33.glEnableVertexAttribArray(3);
		GL33.glVertexAttribPointer(3, 4, GL33.GL_FLOAT, false, 4*9, 20);
		
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
	}

	public void setActive() {
		GL33.glBindVertexArray(vertexArrayID);
		
	}

	public int getVertexCount() {
//		int numVerts=numQuads*6;
		return numQuads*6;
	}
}
