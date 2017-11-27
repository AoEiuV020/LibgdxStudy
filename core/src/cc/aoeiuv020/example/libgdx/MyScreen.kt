package cc.aoeiuv020.example.libgdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.viewport.StretchViewport

/**
 *
 * Created by AoEiuV020 on 2017.11.26-20:36:39.
 */
class MyScreen : ScreenAdapter() {
    val texture: Texture
    val actor: Actor
    val stage: Stage
    private val defaultFont = BitmapFont()
    private val atlas: TextureAtlas

    init {
        // touch事件的xy范围也是限制这么大了，
        val viewPort = StretchViewport(1024f, 512f)

        val p = Pixmap(256, 256, Pixmap.Format.RGB888).apply {
            setColor(Color.GREEN)
            drawCircle(width / 2, height / 2, minOf(width, height) / 2)
        }
        texture = Texture(p)
        p.dispose()

        atlas = TextureAtlas("checkbox/checkbox.atlas")

        defaultFont.data.scale(2f)

        actor = Image(texture).apply {
            setPosition(300f, 200f)
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
        defaultFont.dispose()
    }
}