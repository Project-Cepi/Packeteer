package world.cepi.packeteer.command

import net.kyori.adventure.text.Component
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.coordinate.Point
import net.minestom.server.coordinate.Vec
import net.minestom.server.entity.Player
import net.minestom.server.network.packet.server.ServerPacket
import net.minestom.server.utils.PacketUtils
import net.minestom.server.utils.location.RelativeVec
import org.slf4j.LoggerFactory
import world.cepi.kstom.command.arguments.literal
import world.cepi.kstom.command.kommand.Kommand
import world.cepi.packeteer.command.subcommands.ListenSubcommand
import world.cepi.packeteer.command.subcommands.SendSubcommand
import world.cepi.packeteer.generator.ServerPackets
import java.lang.reflect.Constructor
import java.util.*

object PacketeerCommand : Kommand(k@ {

    addSubcommands(SendSubcommand, ListenSubcommand)

}, "packeteer")