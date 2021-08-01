package io.github.heavyrain266.helloScala

import org.lwjgl.glfw._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl._
import org.lwjgl.opengl.GL11._
import org.lwjgl.system.MemoryUtil._

class HelloScala {
  private object Win {
    var instance: Long = 0
    val title: String  = "Hello, Scala!"
    val width: Int     = 800
    val height: Int    = 600
  }

  def start(): Unit = {
    try {
      init()
      loop()

      glfwDestroyWindow(Win.instance)
    } finally {
      glfwTerminate()
    }
  }

  private def init(): Unit = {
    if (!glfwInit()) {
      throw new IllegalThreadStateException(
        "Failed to initialize GLFW!"
      )
    }

    glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err))
    glfwSetKeyCallback(Win.instance, new KeyboardHandler())

    glfwDefaultWindowHints()
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

    Win.instance =
      glfwCreateWindow(Win.width, Win.height, Win.title, NULL, NULL)
    if (Win.instance == NULL) {
      throw new RuntimeException(
        "Failed to create new GLFW window instance!"
      )
    }

    val videoMode: GLFWVidMode = glfwGetVideoMode(glfwGetPrimaryMonitor())

    if (videoMode != null) {
      glfwSetWindowPos(
        Win.instance,
        (videoMode.width() - Win.width) / 2,
        (videoMode.height() - Win.height) / 2
      )
    }
    glfwMakeContextCurrent(Win.instance)
    glfwSwapInterval(1)
    glfwShowWindow(Win.instance)
  }

  private def loop(): Unit = {
    GL.createCapabilities()

    glClearColor(1.0f, 0.0f, 0.0f, 0.0f)

    while (!glfwWindowShouldClose(Win.instance)) {
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
      glfwSwapBuffers(Win.instance)
      glfwPollEvents()
    }
  }
}
