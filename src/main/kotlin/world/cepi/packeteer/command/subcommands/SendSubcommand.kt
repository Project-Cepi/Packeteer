package world.cepi.packeteer.command.subcommands

import net.kyori.adventure.text.Component
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.coordinate.Point
import net.minestom.server.coordinate.Pos
import net.minestom.server.coordinate.Vec
import net.minestom.server.entity.Player
import net.minestom.server.network.packet.server.ServerPacket
import net.minestom.server.utils.PacketUtils
import net.minestom.server.utils.location.RelativeVec
import org.slf4j.LoggerFactory
import world.cepi.kstom.command.arguments.MiniMessageArgument
import world.cepi.kstom.command.arguments.generation.GeneratedArguments.Companion.createSyntaxesFrom
import world.cepi.kstom.command.arguments.generation.generateSyntaxes
import world.cepi.kstom.command.arguments.literal
import world.cepi.kstom.command.kommand.Kommand
import world.cepi.packeteer.command.PacketeerCommand
import world.cepi.packeteer.generator.ServerPackets
import java.lang.reflect.Constructor
import java.util.*
import kotlin.reflect.KClass

object SendSubcommand : Kommand(k@ {
    val logger = LoggerFactory.getLogger(PacketeerCommand::class.java)

    val players = ArgumentType.Entity("entities").onlyPlayers(true)

    val failedPackets = ServerPackets.serverPackets.filter { clazz ->
        val packetName = clazz.simpleName.replace("Packet", "")

        try {
            createSyntaxesFrom(clazz.kotlin, players, packetName.literal()) { instance ->
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