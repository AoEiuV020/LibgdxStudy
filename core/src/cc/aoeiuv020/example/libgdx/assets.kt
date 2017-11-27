package cc.aoeiuv020.example.libgdx

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Disposable

/**
 *
 * Created by AoEiuV020 on 2017.11.27-17:13:04.
 */
object Assets : Disposable {
    private lateinit var manager: AssetManager

    lateinit var bulletRightTexture: TextureRegion
    lateinit var badlogicTexture: Texture
    lateinit var circleTexture: Texture
    lateinit var paSound: Sound

    fun init() {
        manager = AssetManager()

        manager.load("bullet.atlas", TextureAtlas::class.java)
        manager.load("badlogic.jpg", Texture::class.java)
        manager.load("pa.wav", Sound::class.java)

        manager.finishLoading()

        val bulletAtlas: TextureAtlas = manager.get("bullet.atlas")
        bulletRightTexture = bulletAtlas.findRegion("right")
        badlogicTexture = manager.get("badlogic.jpg")
        paSound = manager.get("pa.wav")

        val p = Pixmap(111, 111, Pixmap.Format.RGBA8888).apply {
            setColor(Color.WHITE)
            fillCircle(width / 2, height / 2, minOf(width, height) / 2)
        }
        circleTexture = Texture(p)
        p.dispose()
    }

    override fun dispose() {
        circleTexture.dispose()
        manager.dispose()
    }
}