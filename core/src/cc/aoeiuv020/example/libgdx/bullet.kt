package cc.aoeiuv020.example.libgdx

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
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
        private val texture: Texture

        init {
            val p = Pixmap(20, 20, Pixmap.Format.RGBA8888).apply {
                setColor(Color.WHITE)
                fillCircle(width / 2, height / 2, minOf(width, height).let { it / 2 - it / 10 })
            }
            texture = Texture(p)
            p.dispose()

        }

        override fun dispose() {
            texture.dispose()
        }
    }

    private var alive = false

    init {
        val action = RepeatAction().apply {
            count = RepeatAction.FOREVER
            action = Actions.moveBy(1000f, 0f, 1f)
        }
        addAction(action)
    }

    override fun reset() {
        alive = true
    }

    override fun act(delta: Float) {
        super.act(delta)

        if (x > stage.width) {
            stage.root.removeActor(this)
            pool.free(this)
        }
    }
}

class BulletPool : Pool<Bullet>(), Disposable {
    private val bulletList = mutableListOf<Bullet>()
    override fun newObject(): Bullet {
        return Bullet(this).also { bulletList.add(it) }
    }

    override fun dispose() {
        val list = ArrayList(bulletList)
        bulletList.clear()
        list.forEach {
            it.stage.root.removeActor(it)
            free(it)
        }
    }
}