package com.thecherno.flappy.graphics;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.thecherno.flappy.util.BufferUtils;

//import org.lwjgl.BufferUtils;

public class Texture {
	private int width, height;
	private int texture;
	
	public Texture(String path)	
	{
		texture = load(path);
	}
	
	private int load(String path)
	{
		int[] pixels = null;
		
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width*height];
			image.getRGB(0, 0,width,height,pixels,0,width);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int[] data = new int[width*height];
		for(int i =0; i < width * height; i++)
		{
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0x00ff0000) >> 16;
			int g = (pixels[i] & 0x0000ff00) >> 8;
			int b = (pixels[i] & 0x000000ff);
			
			data[i] = a << 24 | b << 16 | g <<8 | r;
		}
		
		int tex = glGenTextures();
		glBindTexture(GL_TEXTURE_2D,tex);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,GL_NEAREST);		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER,GL_NEAREST);		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA,width,height,0,GL_RGBA,GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
		glBindTexture(GL_TEXTURE_2D,0);
		return tex;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getID()
	{
		return texture;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
