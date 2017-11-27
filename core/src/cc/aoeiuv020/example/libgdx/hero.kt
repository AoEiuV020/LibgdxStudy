package cc.aoeiuv020.example.libgdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.utils.Disposable

/**
 *
 * Created by AoEiuV020 on 2017.11.27-15:52:16.
 */
class Hero : Image(SpriteDrawable(Sprite(texture))), Disposable {
    companion object : Disposable {

        private val texture = Texture("badlogic.jpg")

        override fun dispose() {
            texture.dispose()
        }
    }

    enum class Direction {
        LEFT, RIGHT
    }

    private val bulletPool = BulletPool()
    private var direction = Direction.RIGHT
    private val pa: Music = Gdx.audio.newMusic(Gdx.files.internal("pa.wav"))

    init {
        setSize(100f, 100f)
    }

    fun left() {
        direction = Direction.LEFT
        (drawable as SpriteDrawable).sprite.flip(false, false)
    }

    fun right() {
        direction = Direction.RIGHT
        (drawable as SpriteDrawable).sprite.flip(true, false)
    }

    fun fire() {
        bulletPool.fire()?.also {
            if (pa.isPlaying) {
                pa.stop()
            }
            pa.play()
            when (direction) {
                Direction.LEFT -> it.fireLeft(x + width / 2, y + height / 2)
                Direction.RIGHT -> it.fireRight(x + width / 2, y + height / 2)
            }
            stage.addActor(it)
        }
    }

    fun move(x: Float, y: Float) {
        setPosition(x - width / 2, y - height / 2)
    }

    override fun dispose() {
        bulletPool.dispose()
        pa.dispose()
    }
}