package me.outspending.tabapi.textures

import com.mojang.authlib.properties.Property
import com.mojang.authlib.properties.PropertyMap

open class Texture(value: String) {
    private val textureProperty: Property = Property("textures", value)

    fun addTexture(properties: PropertyMap) {
        properties.put("textures", textureProperty)
    }
}
