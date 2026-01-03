package com.skillw.pouvoir.internal.core.asahi.prefix.bukkit.particle

import com.skillw.asahi.api.annotation.AsahiPrefix
import com.skillw.asahi.api.member.parser.prefix.namespacing.PrefixParser
import com.skillw.asahi.api.member.quest.Quester
import com.skillw.asahi.api.prefixParser
import com.skillw.asahi.api.quest
import com.skillw.asahi.api.quester
import org.bukkit.Particle
import org.bukkit.block.data.BlockData
import taboolib.library.xseries.XMaterial
import java.awt.Color

/**
 * @className FunctionBlock
 *
 * @author Glom
 * @date 2022/8/9 16:26 Copyright 2022 user. 
 */

@AsahiPrefix(["particleData"])
fun particleData() = prefixParser {
    val token = next()
    expect("[", "{")
    when (token) {
        "dust" -> questDustData()
        "dustTransition" -> questDustTransitionData()
        "block" -> questBlockData()
        //"item" -> questItemData()
        //"vibration" -> questVibrationData()
        else -> error("Wrong Particle Data Type $token")
    }.also {
        expect("]", "}")
    } as Quester<Any?>
}

private fun PrefixParser<*>.questDustData(): Quester<Particle.DustOptions> {
    val color = quest<Color>()
    expect("in")
    val size = quest<Float>()
    return result { Particle.DustOptions(org.bukkit.Color.fromRGB(color.get().rgb), size.get()) }
}

private fun PrefixParser<*>.questDustTransitionData(): Quester<Particle.DustTransition> {
    val from = quest<Color>()
    expect("to")
    val to = quest<Color>()
    expect("in")
    val size = quest<Float>()
    return result {
        Particle.DustTransition(org.bukkit.Color.fromRGB(from.get().rgb), org.bukkit.Color.fromRGB(to.get().rgb), size.get())
    }
}

private fun PrefixParser<*>.questBlockData(): Quester<BlockData> {
    val material = quest<String>()
    val data = if (expect("with")) quest() else quester { 0 }
    return result { XMaterial.valueOf(material.get()).get()!!.createBlockData(data.get().toString())}
}

//private fun PrefixParser<*>.questItemData(): Quester<ProxyParticle.ItemData> {
//    val material = quest<String>()
//    val map = if (peek() == "[" || peek() == "{") questTypeMap() else quester { emptyMap<String, Any>() }
//    val data = map.quester { it.getOrDefault("data", 0).cint }
//    val name = map.quester { it.getOrDefault("name", material.get()).toString() }
//    val lore = map.quester { it.getOrDefault("lore", emptyList<String>()) as List<String> }
//    val customModelData = map.quester { it.getOrDefault("data", -1).cint }
//    return result {
//        ProxyParticle.ItemData(
//            material.get(),
//            data.get(),
//            name.get(),
//            lore.get(),
//            customModelData.get()
//        )
//    }
//}

//@AsahiPrefix
//fun destination() = prefixParser<ProxyParticle.VibrationData.Destination> {
//    when (val token = next()) {
//        "location" -> {
//            val loc = quest<org.bukkit.Location>()
//            result { ProxyParticle.VibrationData.LocationDestination(loc.get().toProxyLocation()) }
//        }
//
//        "entity" -> {
//            val entity = quest<Entity>()
//            result { ProxyParticle.VibrationData.EntityDestination(entity.get().uniqueId) }
//        }
//
//        else -> error("Wrong Destination Type $token")
//    }
//}
//
//private fun PrefixParser<*>.questVibrationData(): Quester<ProxyParticle.VibrationData> {
//    val from = quest<Location>()
//    expect("to")
//    val destination = quest<ProxyParticle.VibrationData.Destination>()
//    expect("in")
//    val time = quest<Int>()
//    return result {
//        ProxyParticle.VibrationData(from.get(), destination.get(), time.get())
//    }
//}