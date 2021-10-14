package world.cepi.packeteer.command.subcommands

import net.minestom.server.command.builder.arguments.ArgumentType
import world.cepi.kstom.command.kommand.Kommand
import world.cepi.packeteer.generator.ServerPackets

object ListenSubcommand : Kommand({
    val packets = ServerPackets.serverPackets.map { it.simpleName.replace("packet", "") }

    val packetName = ArgumentType.Word("packets").from(*packets.toTypedArray())

    fun nameToPacket(name: String) {
        ServerPackets.serverPackets.filter { it.simpleName.replace("packet", "") == name }
    }

    syntax(packetName) {
        val packet = nameToPacket(!packetName)
    }
}, "listen")