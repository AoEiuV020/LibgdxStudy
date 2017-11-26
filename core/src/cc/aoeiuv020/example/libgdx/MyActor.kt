package cc.aoeiuv020.example.libgdx

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Actor

/**
 *
 * Created by AoEiuV020 on 2017.11.26-18:02:48.
 */
class MyActor(texture: Texture) : Actor() {
    private val sprite = Sprite(texture)
    val font: BitmapFont

    init {
        sprite.apply {
            setBounds(100f, 200f, 200f, 100f)
            // 设置旋转中心，
            setOriginCenter()
        }
        setBounds(sprite.x, sprite.y, sprite.width, sprite.height)
        font = BitmapFont()
    }

    override fun positionChanged() {
        sprite.setPosition(x, y)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        sprite.draw(batch)
        // 默认字体图片不支持中文，支持换行，
        font.draw(stage.batch, "Hel夹中文lo\nWorld<>?\n试试中文", 30f, 60f)
    }
}