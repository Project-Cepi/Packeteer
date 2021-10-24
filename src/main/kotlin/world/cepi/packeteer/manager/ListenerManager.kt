package world.cepi.packeteer.manager

import net.kyori.adventure.text.Component
import net.minestom.server.entity.Player
import net.minestom.server.event.player.PlayerPacketEvent
import net.minestom.server.message.Messenger.sendMessage
import net.minestom.server.network.packet.client.ClientPacket
import net.minestom.server.network.packet.server.ServerPacket
import world.cepi.kstom.Manager
import world.cepi.kstom.event.listenOnly

object ListenerManager {

    val clientboundMap = mutableMapOf<Player, Set<Class<out ServerPacket>>>()
    val serverboundMap = mutableMapOf<Player, Set<Class<out ClientPacket>>>()

    fun listen() {
        Manager.connection.onPacketSend { players, listener, packet ->
            players.filter { clientboundMap.contains(it) }.forEach {
                it.sendMessage(Component.text(
                    packet::class.java.simpleName
                ))
            }
        }

        Manager.connection.onPacketReceive { player, packetController, packet ->
             if (serverboundMap.contains(player)) {
                player.sendMessage(Component.text(
                    packet::class.java.simpleName
                ))
            }
        }
    }
}