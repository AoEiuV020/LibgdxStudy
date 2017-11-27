package cc.aoeiuv020.example.libgdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.StretchViewport

/**
 *
 * Created by AoEiuV020 on 2017.11.26-20:36:39.
 */
class MyScreen : ScreenAdapter() {
    val texture: Texture
    val actor: Actor
    val stage: Stage
    private val atlas: TextureAtlas

    init {
        // touch事件的xy范围也是限制这么大了，
        val viewPort = StretchViewport(1024f, 512f)

        // 256 * 256
        texture = Texture("badlogic.jpg")

        atlas = TextureAtlas("button/button.atlas")

        actor = Button(Button.ButtonStyle().apply {
            up = TextureRegionDrawable(atlas.findRegion("button", 1))
            down = TextureRegionDrawable(atlas.findRegion("button", 2))
        }).apply {
            setPosition(100f, 200f)
            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    Gdx.app.log("Click", "<$x, $y>")
                }
            })
        }

        stage = Stage(viewPort)
        Gdx.input.inputProcessor = stage
        stage.apply {
            addActor(actor)
        }

    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(delta)

        stage.draw()

    }

    override fun dispose() {
        stage.dispose()
        texture.dispose()
        atlas.dispose()
    }
}