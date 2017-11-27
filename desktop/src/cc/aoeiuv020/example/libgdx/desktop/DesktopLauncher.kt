package cc.aoeiuv020.example.libgdx.desktop

import cc.aoeiuv020.example.libgdx.MyGdxGame
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import java.io.File

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        println(File(".").absolutePath)
        val config = LwjglApplicationConfiguration()
        config.apply {
            width = 1024
            height = 512
            forceExit = false
        }
        LwjglApplication(MyGdxGame(), config)
    }
}
