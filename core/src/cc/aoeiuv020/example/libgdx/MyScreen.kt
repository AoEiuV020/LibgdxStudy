package cc.aoeiuv020.example.libgdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
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
    private lateinit var body: Body
    private lateinit var ground: Body

    init {
        Assets.init()

        viewPort = FitViewport(1f, 0.5f)
        stage = Stage(viewPort)

        world = World(Vector2(0f, 0f), true)
        World.setVelocityThreshold(0f)
        renderer = Box2DDebugRenderer()

        Gdx.input.inputProcessor = stage

        stage.apply {
            addListener(object : InputListener() {
                private val touchDown = Vector2()
                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    touchDown.set(x, y)

                    return true
                }

                override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                    body.setLinearVelocity(1000f * (x - touchDown.x), 1000f * (y - touchDown.y))
                    touchDown.set(x, y)
                }

                override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                }
            })
        }

        world.apply {
            body = createBody(BodyDef().apply {
                type = BodyDef.BodyType.DynamicBody
                position.set(stage.width / 2, stage.height / 2)
            }).apply {
                val circleShape = CircleShape().apply {
                    radius = 0.05f
                }
                createFixture(FixtureDef().apply {
                    shape = circleShape
                    density = 10f
                    friction = 0f
                    // 这个为1也会越弹越高，
                    restitution = 0f
                })
                circleShape.dispose()
            }
            ground = createBody(BodyDef().apply {
                position.set(0f, 0f)
            }).apply {
                val edge = EdgeShape()
                createFixture(FixtureDef().apply {
                    shape = edge.apply {
                        set(0f, 0f, stage.width, 0f)
                    }
                })
                createFixture(FixtureDef().apply {
                    shape = edge.apply {
                        set(0f, stage.height, stage.width, stage.height)
                    }
                })
                createFixture(FixtureDef().apply {
                    shape = edge.apply {
                        set(0f, 0f, 0f, stage.height)
                    }
                })
                createFixture(FixtureDef().apply {
                    shape = edge.apply {
                        set(stage.width, 0f, stage.width, stage.height)
                    }
                })
                edge.dispose()
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
        body.setLinearVelocity(0f, 0f)
    }

    override fun dispose() {
        stage.dispose()
        world.dispose()
        Assets.dispose()
    }
}