package com.skillw.pouvoir.api.handle

import com.skillw.pouvoir.api.annotation.Defaultable
import com.skillw.pouvoir.api.manager.ConfigManager
import com.skillw.pouvoir.api.manager.TotalManager
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.Plugin

object DefaultableHandle {

    fun isDefaultableClass(clazz: Class<*>): Boolean =
        clazz.isAnnotationPresent(Defaultable::class.java) && ConfigurationSerializable::class.java.isAssignableFrom(
            clazz
        )

    fun handle(clazz: Class<*>, plugin: Plugin) {
        val subPouvoir = TotalManager.pluginData[plugin] ?: return
        if (!isDefaultableClass(clazz)) return
        val defaultable = clazz.getAnnotation(Defaultable::class.java) ?: return
        val key = defaultable.config
        val path = defaultable.path
        val manager = (subPouvoir.managerData["ConfigManager"] as ConfigManager)
        if (!manager.defaults.contains(clazz)) {
            ConfigurationSerialization.registerClass(clazz.asSubclass(ConfigurationSerializable::class.java))
            manager.defaults.add(clazz)
        }
        manager[key].getConfigurationSection(path)!!["=="] = clazz.name
        clazz.getField("default").set(null, clazz.cast(manager[key][path]))
    }
}