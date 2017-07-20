
package fr.triedge.sekai.client.controller;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWVidMode;

public class Controller {
	
	private long window;

	public void init(){
		if (!glfwInit()){
			throw new IllegalStateException("Failed to initialize GLFW!");
		}
		
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		setWindow(glfwCreateWindow(640, 480, "Sekai", 0, 0));
		if (getWindow() == 0){
			throw new IllegalStateException("Failed to create video");
		}
		
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(getWindow(), (videoMode.width() - 640)/2, (videoMode.height() - 480)/2);
		glfwShowWindow(getWindow());
	}
	
	public void startLoop(){
		while (!glfwWindowShouldClose(getWindow())) {
			glfwPollEvents();
		}
	}
	
	public void close(){
		glfwTerminate();
	}

	public long getWindow() {
		return window;
	}

	public void setWindow(long window) {
		this.window = window;
	}
}
