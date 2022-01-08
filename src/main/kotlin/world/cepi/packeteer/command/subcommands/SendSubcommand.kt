package world.cepi.packeteer.command.subcommands

import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import net.minestom.server.utils.PacketUtils
import org.slf4j.LoggerFactory
import world.cepi.kstom.command.arguments.generation.ClassArgumentGenerator.Companion.syntaxesFrom
import world.cepi.kstom.command.arguments.literal
import world.cepi.kstom.command.kommand.Kommand
import world.cepi.packeteer.command.PacketeerCommand
import world.cepi.packeteer.generator.ServerPackets

object SendSubcommand : Kommand(k@ {
    val logger = LoggerFactory.getLogger(PacketeerCommand::class.java)

    val players = ArgumentType.Entity("entities").onlyPlayers(true)

    val failedPackets = ServerPackets.serverPackets.filter { clazz ->
        val packetName = clazz.simpleName.replace("Packet", "")

        try {
            syntaxesFrom(clazz.kotlin, players, packetName.literal()) { instance ->
                PacketUtils.sendGroupedPacket((!players).find(sender) as List<Player>, instance)
            }
        } catch (e: Exception) {
            return@filter true
        }

        return@filter false // This is successful, dont add to failed packets
    }

    if (failedPackets.isEmpty())
        return@k

    logger.warn("Could not generate ${failedPackets.size}/${ServerPackets.serverPackets.size} packets: ${
        failedPackets.joinToString(",") { it.simpleName.replace("Packet", "") }
    }")
}, "send")