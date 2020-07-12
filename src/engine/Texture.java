package engine;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL33;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

public class Texture {
	private int mTexID;
	int mWidth;
	int mHeight;
	int mNchannels;
	int mSlot;
	private String fileName;
	
	
	public Texture(String fileName,int slot) {
		this.fileName = fileName;
		this.mSlot = slot;
		loadTexture();
	}
	
	public void loadTexture() {
		int format=GL33.GL_RGBA;
		ByteBuffer image = null;
		
		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer xBuffer = stack.mallocInt(1);
			IntBuffer yBuffer = stack.mallocInt(1);
			IntBuffer nchannelsBuffer = stack.mallocInt(1);
			
//			STBImage.stbi_set_flip_vertically_on_load(true);
			image = STBImage.stbi_load(fileName,xBuffer,yBuffer,nchannelsBuffer,0);
			mWidth = xBuffer.get(0);
			mHeight = yBuffer.get(0);
			mNchannels = nchannelsBuffer.get(0);
		} catch (Exception e) {
			throw new IllegalStateException("Image loading failed"+STBImage.stbi_failure_reason());
		}
		if (mNchannels==3) {
			format=GL33.GL_RGB;
		}
		
		mTexID = GL33.glGenTextures();
		GL33.glBindTexture(GL33.GL_TEXTURE_2D, mTexID);
		GL33.glTexParameteri(GL33.GL_TEXTURE_2D, GL33.GL_TEXTURE_MIN_FILTER, GL33.GL_LINEAR);
		GL33.glTexParameteri(GL33.GL_TEXTURE_2D, GL33.GL_TEXTURE_MAG_FILTER, GL33.GL_LINEAR);
		GL33.glTexImage2D(GL33.GL_TEXTURE_2D, 0, format, mWidth, mHeight, 0, format, GL33.GL_UNSIGNED_BYTE, image);
		STBImage.stbi_image_free(image);
	}
	
	public void setActive() {
		GL33.glActiveTexture(GL33.GL_TEXTURE0+mSlot);
		GL33.glBindTexture(GL33.GL_TEXTURE_2D, mTexID);
	}
	
	public void delete() {
		GL33.glDeleteTextures(mTexID);
	}
	
	
}
