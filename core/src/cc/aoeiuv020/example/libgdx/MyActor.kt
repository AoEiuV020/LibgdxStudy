package cc.aoeiuv020.example.libgdx

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Actor

/**
 *
 * Created by AoEiuV020 on 2017.11.26-18:02:48.
 */
class MyActor(texture: Texture) : Actor() {
    private val sprite = Sprite(texture)

    init {
        sprite.apply {
            setBounds(100f, 200f, 200f, 100f)
            // 设置旋转中心，
            setOriginCenter()
        }
        setBounds(sprite.x, sprite.y, sprite.width, sprite.height)
    }

    override fun positionChanged() {
        sprite.setPosition(x, y)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        sprite.draw(batch)
    }
}