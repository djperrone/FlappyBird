package com.thecherno.flappy;




import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import com.thecherno.flappy.graphics.Shader;
import com.thecherno.flappy.input.Input;
import com.thecherno.flappy.level.Level;
import com.thecherno.flappy.math.Matrix4f;




public class Main implements Runnable {
	
	private int width = 1280;
	private int height = 720;
	//private String title = "Flappy";
	
	private long window;
	private Level level;
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
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		window = glfwCreateWindow(width, height, "flappy", 0, 0);
		
		if(window == 0)
		{
			//todo
		}
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		
		glfwSetWindowPos(window,(vidmode.width() - width)/2,(vidmode.height() - height)/2);
		
		glfwSetKeyCallback(window, new Input());
		
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		//glfwSwapInterval(1);
		
		GL.createCapabilities();
		
		//glClearColor(1.0f,1.0f,1.0f,1.0f);
		glEnable(GL_DEPTH_TEST);
		glActiveTexture(GL_TEXTURE1);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		System.out.println("OpenGL " + glGetString(GL_VERSION));
		
		Shader.loadAll();
		
	
		Matrix4f pr_matrix = Matrix4f.orthographic(-10.0f, 10.0f, -10.0f * 9.0f/16.0f, 10.0f * 9.0f/16.0f, -1.0f,1.0f);
		Shader.BG.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BG.setUniform1i("tex",1);
		
		Shader.BIRD.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BIRD.setUniform1i("tex",1);
		
		Shader.PIPE.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.PIPE.setUniform1i("tex",1);
		
		
		
		
		level = new Level();
		
	}
	
	public void run()
	{
		init();
		
		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1000000000.0/60;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while(running)
		{
			long now = System.nanoTime();
			delta += (now-lastTime)/ns;
			lastTime = System.nanoTime();
			
			if(delta >= 1)
			{
				update();
				updates++;
				delta--;
			}			
			
			render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				System.out.println(updates + " ups, " + frames + "fps");
				frames = 0;
				updates = 0;
			}
			
			if(glfwWindowShouldClose(window) != false)
			{
				running = false;
			}
		}
		
		glfwDestroyWindow(window);
		glfwTerminate();
	}
	
	private void update()
	{
		glfwPollEvents();
		level.update();
		
		if(level.isGameOver())
		{
			level = new Level();
		}
	}
	
	private void render()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		level.render();
		int i = glGetError();
		if(i != GL_NO_ERROR)
		{
			System.out.println(i);
		}
		
		glfwSwapBuffers(window);
				
	}
	
	public static void main(String[] args)
	{
		new Main().start();
	}

}
