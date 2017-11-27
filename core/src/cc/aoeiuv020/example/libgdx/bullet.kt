package cc.aoeiuv020.example.libgdx

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.Pool

/**
 *
 * Created by AoEiuV020 on 2017.11.27-12:34:06.
 */
class Bullet(private val pool: BulletPool) : Image(texture), Pool.Poolable {
    companion object : Disposable {
        private val texture = Texture("badlogic.jpg")

        override fun dispose() {
            texture.dispose()
        }
    }

    init {
        setSize(20f, 20f)
        val action = Actions.repeat(RepeatAction.FOREVER, Actions.moveBy(1000f, 0f, 1f))
        addAction(action)
    }

    override fun reset() {
    }

    override fun act(delta: Float) {
        super.act(delta)

        if (x > stage.width) {
            stage.root.removeActor(this)
            pool.remove(this)
        }
    }
}

class BulletPool : Pool<Bullet>(), Disposable {
    private val bulletList = mutableListOf<Bullet>()
    private val bulletCount = 3
    override fun newObject(): Bullet {
        return Bullet(this)
    }

    override fun dispose() {
        val list = ArrayList(bulletList)
        bulletList.clear()
        list.forEach {
            it.stage.root.removeActor(it)
            free(it)
        }
    }

    fun fire(): Bullet? = if (bulletList.size < bulletCount) {
        obtain().also { bulletList.add(it) }
    } else {
        null
    }

    fun remove(bullet: Bullet) {
        bulletList.remove(bullet)
        free(bullet)
    }
}