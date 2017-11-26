package cc.aoeiuv020.example.libgdx

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage

class MyGdxGame : ApplicationAdapter() {
    lateinit var img: Texture
    lateinit var actor: Actor
    lateinit var stage: Stage

    override fun create() {
        img = Texture("badlogic.jpg")

        actor = MyActor(img)

        stage = MyStage()
        stage.apply {
            addActor(actor)
        }
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act()
        stage.draw()
    }

    override fun dispose() {
        stage.dispose()
        img.dispose()
    }
}
