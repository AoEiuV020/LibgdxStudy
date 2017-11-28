package cc.aoeiuv020.example.libgdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.physics.box2d.joints.MouseJoint
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef
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
        renderer = Box2DDebugRenderer()

        Gdx.input.inputProcessor = stage

        stage.apply {
            addListener(object : InputListener() {
                private lateinit var mouseJoint: MouseJoint
                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    //我们来定义一个鼠标关节的声明
                    val mouseJointDef = MouseJointDef()
                    mouseJointDef.bodyA = ground//设为物理世界的边界
                    mouseJointDef.bodyB = body//我们想要拖动的物体
                    //是否检测2个物体间的碰撞，不检测会怎么样？砖块会飞离屏幕！
                    mouseJointDef.collideConnected = true
                    //最大力设为砖块质量的1000倍
                    //设小点会怎么样？砖块响应你的速度会很慢很慢
                    mouseJointDef.maxForce = 100.0f * body.mass
                    mouseJointDef.target.x = body.position.x
                    mouseJointDef.target.y = body.position.y
                    //好了，让世界生成它吧
                    mouseJoint = world.createJoint(mouseJointDef) as MouseJoint
                    //传入目的地坐标
                    mouseJoint.target = Vector2(x, y)

                    return true
                }

                override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                    mouseJoint.target = Vector2(x, y)

                }

                override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                    world.destroyJoint(mouseJoint)
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
                    restitution = 1f
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
    }

    override fun dispose() {
        stage.dispose()
        world.dispose()
        Assets.dispose()
    }
}