package com.thecherno.flappy;




import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;



public class Main implements Runnable {
	
	private int width = 1280;
	private int height = 720;
	//private String title = "Flappy";
	
	private long window;
			
	private boolean running = false;
	private Thread thread;
	
	public void start()
	{
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}
	
	private void init()
	{
		if(glfwInit() != true)
		{
			
		}
		
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		window = glfwCreateWindow(width, height, "flappy", 0, 0);
		
		if(window == 0)
		{
			//todo
		}
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		
		glfwSetWindowPos(window,(vidmode.width() - width)/2,(vidmode.height() - height)/2);
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
//		
	
	}
	
	public void run()
	{
		init();
		while(running)
		{
			update();
			render();
			
			if(glfwWindowShouldClose(window) != false)
			{
				running = false;
			}
		}
	}
	
	private void update()
	{
		glfwPollEvents();
	}
	
	private void render()
	{
		glfwSwapBuffers(window);
				
	}
	
	public static void main(String[] args)
	{
		new Main().start();
	}

}
