package cc.aoeiuv020.example.libgdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.StretchViewport

/**
 *
 * Created by AoEiuV020 on 2017.11.26-20:36:39.
 */
class MyScreen : ScreenAdapter() {
    val texture: Texture
    val actor: Image
    val stage: Stage

    init {
        // touch事件的xy范围也是限制这么大了，
        val viewPort = StretchViewport(1024f, 512f)

        // 256 * 256
        texture = Texture("badlogic.jpg")

        actor = Image(texture)
        actor.apply {
            setPosition(100f, 200f)
            // 两边压缩，中点还是中点，
            setScale(0.2f, 1f)
            setOrigin(width / 2, height / 2)
        }

        stage = Stage(viewPort)
        Gdx.input.inputProcessor = stage
        stage.apply {
            addActor(actor)
            addListener(object : ClickListener() {

                init {
                }

                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    Gdx.app.log("Click", "<$x, $y>")
                    // 每次都要new一个，复用无效，
                    val action = SequenceAction()
                    action.addAction(Actions.moveBy(100f, 0f, 0.5f))
                    action.addAction(Actions.moveBy(0f, 100f, 0.5f))
                    action.addAction(Actions.moveBy(-100f, -100f, 0.5f))
                    actor.addAction(action)
                }
            })
        }

    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(delta)

        actor.rotation += 360 * delta

        stage.draw()

    }

    override fun dispose() {
        stage.dispose()
        texture.dispose()
    }
}