package cc.aoeiuv020.example.libgdx.desktop

import cc.aoeiuv020.example.libgdx.MyGdxGame
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.apply {
            width = 1024
            height = 512
            foregroundFPS = 111
        }
        LwjglApplication(MyGdxGame(), config)
    }
}
