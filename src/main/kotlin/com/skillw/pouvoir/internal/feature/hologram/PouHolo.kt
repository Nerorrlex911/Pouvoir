package com.skillw.pouvoir.internal.feature.hologram

import com.skillw.pouvoir.internal.feature.hologram.impl.PouAdyHologramsLine
import com.skillw.pouvoir.internal.feature.hologram.impl.PouDecentHologramsLine
import com.skillw.pouvoir.internal.feature.hologram.impl.PouHologramLine
import org.bukkit.Location
import org.bukkit.entity.Player
import taboolib.module.chat.colored

/**
 * @className PouHolo
 *
 * @author Glom
 * @date 2022/7/31 18:59 Copyright 2022 user. All rights reserved.
 */
interface PouHolo {
    val isDeleted: Boolean
    fun destroy()
    fun respawn()
    fun spawn(location: Location)
    fun teleport(location: Location)
    fun delete()
    fun visible(viewer: Player, visible: Boolean)
    fun update(line: String)

    companion object {
        @JvmStatic
        fun create(location: Location, line: String, vararg viewers: Player): PouHolo {
            return if (PouDecentHologramsLine.enable) {
                PouDecentHologramsLine(location, line, *viewers)
            } else if (PouAdyHologramsLine.enable) {
                PouAdyHologramsLine(location, line, *viewers)
            } else PouHologramLine(location) {
                it.setSmall(true)
                it.setMarker(true)
                it.setBasePlate(false)
                it.setInvisible(true)
                it.setCustomName(line.colored())
                it.setCustomNameVisible(line.isNotEmpty())
                viewers.forEach { player -> it.visible(player, true) }

            }
        }
    }
}