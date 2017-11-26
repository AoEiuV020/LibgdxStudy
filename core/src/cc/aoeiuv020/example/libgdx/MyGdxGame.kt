package cc.aoeiuv020.example.libgdx

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class MyGdxGame : ApplicationAdapter() {
    lateinit var batch: SpriteBatch
    lateinit var img: Texture

    private lateinit var sprite: Sprite

    override fun create() {
        batch = SpriteBatch()
        img = Texture("badlogic.jpg")

        sprite = Sprite(img)
        sprite.setPosition(100f, 200f)
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()

        sprite.draw(batch)
        // 原地旋转，覆盖上面的，
        sprite.rotation = 90f
        sprite.draw(batch)
        // 中心旋转，得到八角形，
        sprite.rotation = 45f
        sprite.draw(batch)

        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        img.dispose()
    }
}
