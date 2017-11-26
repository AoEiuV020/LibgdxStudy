package cc.aoeiuv020.example.libgdx

import com.badlogic.gdx.Game

class MyGdxGame : Game() {

    override fun create() {
        setScreen(MyScreen())
    }

}
