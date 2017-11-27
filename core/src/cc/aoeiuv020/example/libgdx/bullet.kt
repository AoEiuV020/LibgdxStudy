package cc.aoeiuv020.example.libgdx

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.Pool

/**
 *
 * Created by AoEiuV020 on 2017.11.27-12:34:06.
 */
class Bullet : Image(texture.findRegion("right")), Pool.Poolable {
    private var pool: BulletPool? = null
    private lateinit var action: Action

    companion object : Disposable {

        private val texture = TextureAtlas("bullet.atlas")

        override fun dispose() {
            texture.dispose()
        }
    }

    init {
        setSize(40f, 20f)
        setOrigin(width / 2, height / 2)
    }

    override fun reset() {
    }

    fun fireLeft(x: Float, y: Float) {
        setPosition(x - width / 2, y - height / 2)
        setLeft()
    }

    fun fireRight(x: Float, y: Float) {
        setPosition(x - width / 2, y - height / 2)
        setRight()
    }

    private fun setLeft() {
        rotation = 180f
        action = Actions.repeat(RepeatAction.FOREVER, Actions.moveBy(-1000f, 0f, 3f))
        addAction(action)
    }

    private fun setRight() {
        rotation = 0f
        action = Actions.repeat(RepeatAction.FOREVER, Actions.moveBy(1000f, 0f, 3f))
        addAction(action)
    }

    override fun act(delta: Float) {
        super.act(delta)

        if (x > stage.width || x < 0f) {
            removeAction(action)
            stage.root.removeActor(this)
            pool?.remove(this)
        }
    }

    fun setPool(bulletPool: BulletPool?) {
        this.pool = bulletPool
    }
}

class BulletPool : Disposable {
    // 多个BulletPool共用Pool<Bullet>,
    companion object : Pool<Bullet>() {
        override fun newObject(): Bullet {
            return Bullet()
        }
    }

    private val bulletList = mutableListOf<Bullet>()
    private val bulletCount = 3

    override fun dispose() {
        val list = ArrayList(bulletList)
        bulletList.clear()
        list.forEach {
            it.stage.root.removeActor(it)
            free(it)
        }
    }

    fun fire(): Bullet? = if (bulletList.size < bulletCount) {
        obtain().also {
            it.setPool(this)
            bulletList.add(it)
        }
    } else {
        null
    }

    fun remove(bullet: Bullet) {
        bullet.setPool(null)
        bulletList.remove(bullet)
        free(bullet)
    }
}