package com.skillw.particlelib.utils

import taboolib.common.platform.function.onlinePlayers
import taboolib.common.util.Location
import taboolib.common.util.Vector
import taboolib.library.xseries.ProxyParticle

/**
 * 将粒子发送到指定坐标
 */
fun ProxyParticle.sendTo(
    location: Location,
    range: Double = 128.0,
    offset: Vector = Vector(0, 0, 0),
    count: Int = 1,
    speed: Double = 0.0,
    data: Any? = null
) {
    onlinePlayers().filter { it.world == location.world && it.location.distance(location) <= range }.forEach { player ->
        player.sendParticle(this.name, location, offset, count, speed, data)
    }
}