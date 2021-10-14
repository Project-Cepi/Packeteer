package world.cepi.packeteer.manager

import net.kyori.adventure.text.Component
import net.minestom.server.entity.Player
import net.minestom.server.event.player.PlayerPacketEvent
import net.minestom.server.network.packet.server.ServerPacket
import world.cepi.kstom.Manager
import world.cepi.kstom.event.listenOnly

object ListenerManager {

    val map = mutableMapOf<Player, List<Class<ServerPacket>>>()

    fun listen() {
        Manager.connection.onPacketSend { players, listener, packet ->
            players.filter { map.contains(it) }.forEach {
                it.sendMessage(Component.text(
                    packet::class.java.simpleName
                ))
            }
        }
    }
}