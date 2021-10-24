package world.cepi.packeteer.command.subcommands

import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.network.PacketProcessor
import net.minestom.server.network.packet.client.handler.ClientPacketsHandler
import net.minestom.server.network.packet.client.handler.ClientPlayPacketsHandler
import net.minestom.server.network.packet.client.play.ClientCloseWindowPacket
import world.cepi.kstom.Manager
import world.cepi.kstom.command.arguments.literal
import world.cepi.kstom.command.kommand.Kommand
import world.cepi.packeteer.generator.ServerPackets
import world.cepi.packeteer.manager.ListenerManager

object ListenSubcommand : Kommand({

    val serverbound by literal
    val clientbound by literal

    // Clientbound
    run {

        val clientboundPacketName = ArgumentType.Word("serverPackets").from(
            *ServerPackets.serverPackets.map { it.simpleName.replace("Packet", "") }.toTypedArray()
        )

        fun nameToPacket(name: String) =
            ServerPackets.serverPackets.first { it.simpleName.replace("Packet", "") == name }

        syntax(clientbound, clientboundPacketName) {
            val packet = nameToPacket(!clientboundPacketName)

            ListenerManager.clientboundMap[player] = setOf(packet)
        }
    }

    // Serverbound
    run {

        val serverboundPackets = (0x00..0x2F).mapNotNull {
            try {
                MinecraftServer.getPacketProcessor().playPacketsHandler.getPacketInstance(it).javaClass
            } catch (e: java.lang.IllegalStateException) {
                null
            }
        }

        val serverboundPacketName = ArgumentType.Word("clientPackets").from(
            *serverboundPackets.map {
                it.simpleName
                    .replace("Packet", "")
                    .replace("Client", "")
            }.toTypedArray()
        )

        fun nameToPacket(name: String) =
            serverboundPackets.first { it.simpleName
                .replace("Packet", "")
                .replace("Client", "") == name
            }

        syntax(serverbound, serverboundPacketName) {
            val packet = nameToPacket(!serverboundPacketName)

            ListenerManager.serverboundMap[player] = setOf(packet)
        }
    }
}, "listen")