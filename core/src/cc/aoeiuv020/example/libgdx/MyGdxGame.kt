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
        sprite.apply {
            setBounds(100f, 200f, 200f, 100f)
            // 设置旋转中心，
            setOriginCenter()
        }
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()

        sprite.rotation += 360 * Gdx.graphics.deltaTime

        sprite.draw(batch)

        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        img.dispose()
    }
}
