package cc.aoeiuv020.example.libgdx

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.DragListener
import com.badlogic.gdx.utils.viewport.StretchViewport

class MyGdxGame : ApplicationAdapter() {
    lateinit var img: Texture
    lateinit var actor: Actor
    lateinit var stage: Stage

    override fun create() {
        // touch事件的xy范围也是限制这么大了，
        val viewPort = StretchViewport(1024f, 512f)

        // 256 * 256
        img = Texture("badlogic.jpg")

        actor = MyActor(img)
        actor.apply {
        }

        stage = Stage(viewPort)
        Gdx.input.inputProcessor = stage
        stage.apply {
            addActor(actor)
            addListener(object : DragListener() {
                override fun drag(event: InputEvent, x: Float, y: Float, pointer: Int) {
                    Gdx.app.log("Drag ", "${event.type} <$x, $y>")
                }
            })
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
