package cc.aoeiuv020.example.libgdx.desktop

import cc.aoeiuv020.example.libgdx.MyGdxGame
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.apply {
            width = 800
            height = 480
        }
        LwjglApplication(MyGdxGame(), config)
    }
}
