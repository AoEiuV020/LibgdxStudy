package cc.aoeiuv020.example.libgdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.StretchViewport

/**
 *
 * Created by AoEiuV020 on 2017.11.26-20:36:39.
 */
class MyScreen : ScreenAdapter() {
    val texture: Texture
    val username: Actor
    val password: Actor
    val stage: Stage
    private val defaultFont = BitmapFont()
    private val atlas: TextureAtlas

    init {
        // touch事件的xy范围也是限制这么大了，
        val viewPort = StretchViewport(1024f, 512f)

        // 256 * 256
        texture = Texture("badlogic.jpg")

        atlas = TextureAtlas("checkbox/checkbox.atlas")

        defaultFont.data.scale(2f)

        username = TextField("username", TextField.TextFieldStyle().apply {
            font = defaultFont
            fontColor = Color.BLUE
            background = TextureRegionDrawable(TextureRegion(Texture(Pixmap(1, 1, Pixmap.Format.RGB888).apply {
                setColor(Color.BLACK)
                fill()
            })))
            selection = TextureRegionDrawable(TextureRegion(Texture(Pixmap(1, 1, Pixmap.Format.RGB888).apply {
                setColor(Color.GREEN)
                fill()
            })))
            cursor = TextureRegionDrawable(TextureRegion(Texture(Pixmap(1, 1, Pixmap.Format.RGB888).apply {
                setColor(Color.WHITE)
                fill()
            })))
        }).apply {
            setPosition(100f, 200f)
        }

        password = TextField("password", TextField.TextFieldStyle().apply {
            font = defaultFont
            fontColor = Color.BLUE
            background = TextureRegionDrawable(TextureRegion(Texture(Pixmap(1, 1, Pixmap.Format.RGB888).apply {
                setColor(Color.BLACK)
                fill()
            })))
            selection = TextureRegionDrawable(TextureRegion(Texture(Pixmap(1, 1, Pixmap.Format.RGB888).apply {
                setColor(Color.GREEN)
                fill()
            })))
            cursor = TextureRegionDrawable(TextureRegion(Texture(Pixmap(1, 1, Pixmap.Format.RGB888).apply {
                setColor(Color.WHITE)
                fill()
            })))
        }).apply {
            setPosition(300f, 200f)
            isPasswordMode = true
            setPasswordCharacter('*')
            setAlignment(Align.center)
        }

        stage = Stage(viewPort)
        Gdx.input.inputProcessor = stage
        stage.apply {
            addActor(username)
            addActor(password)
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