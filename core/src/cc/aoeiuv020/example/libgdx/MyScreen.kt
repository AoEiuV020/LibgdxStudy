package cc.aoeiuv020.example.libgdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.StretchViewport

/**
 *
 * Created by AoEiuV020 on 2017.11.26-20:36:39.
 */
class MyScreen : ScreenAdapter() {
    val stage: Stage
    private val pool: BulletPool

    init {
        // touch事件的xy范围也是限制这么大了，
        val viewPort = StretchViewport(1024f, 512f)


        pool = BulletPool()

        val hero1 = Hero().apply { left() }
        val hero2 = Hero().apply { right() }

        stage = Stage(viewPort)

        stage.addActor(hero1)
        stage.addActor(hero2)

        Gdx.input.inputProcessor = stage
        stage.apply {
            addListener(object : InputListener() {
                private val pointerMap = mutableMapOf<Int, Int>()
                private fun move(button: Int, x: Float, y: Float) {
                    when (button) {
                        Input.Buttons.LEFT -> hero1.move(x, y)
                        Input.Buttons.RIGHT -> hero2.move(x, y)
                    }
                }

                override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    pointerMap.put(pointer, button)
                    move(button, x, y)
                    return true
                }

                override fun touchDragged(event: InputEvent, x: Float, y: Float, pointer: Int) {
                    move(pointerMap[pointer]!!, x, y)
                }

                override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                    Gdx.app.log("Fire", "<$x, $y>")
                    when (event.button) {
                        Input.Buttons.LEFT -> hero1.fire()
                        Input.Buttons.RIGHT -> hero2.fire()
                    }
                }
            })
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
        pool.dispose()
        Bullet.dispose()
        Hero.dispose()
    }
}