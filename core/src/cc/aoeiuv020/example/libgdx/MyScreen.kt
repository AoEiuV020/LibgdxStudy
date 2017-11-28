package cc.aoeiuv020.example.libgdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport

/**
 *
 * Created by AoEiuV020 on 2017.11.26-20:36:39.
 */
class MyScreen : ScreenAdapter() {
    private val stage: Stage
    private val viewPort: Viewport
    private val world: World
    private val renderer: Box2DDebugRenderer

    init {
        Assets.init()

        viewPort = FitViewport(108f, 72f)
        stage = Stage(viewPort)

        world = World(Vector2(0f, -10f), true)
        renderer = Box2DDebugRenderer()

        Gdx.input.inputProcessor = stage

        stage.apply {
        }

        world.apply {
            createBody(BodyDef().apply {
                type = BodyDef.BodyType.DynamicBody
                position.set(stage.width / 2, stage.height)
            }).apply {
                val circleShape = CircleShape().apply {
                    radius = 10f
                }
                createFixture(FixtureDef().apply {
                    shape = circleShape
                })
                circleShape.dispose()
            }
            createBody(BodyDef().apply {
                type = BodyDef.BodyType.StaticBody
                position.set(stage.width / 2 - 10f, 2f)
            }).apply {
                val circleShape = CircleShape().apply {
                    radius = 10f
                }
                createFixture(FixtureDef().apply {
                    shape = circleShape
                })
                circleShape.dispose()
            }
        }
    }

    override fun resize(width: Int, height: Int) {
        viewPort.update(width, height)
    }


    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        world.step(delta, 3, 3)
        renderer.render(world, stage.camera.combined)
    }

    override fun dispose() {
        stage.dispose()
        world.dispose()
        Assets.dispose()
    }
}