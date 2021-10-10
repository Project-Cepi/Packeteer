package world.cepi.packeteer.command

import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import net.minestom.server.network.packet.server.ServerPacket
import net.minestom.server.utils.PacketUtils
import org.slf4j.LoggerFactory
import world.cepi.kstom.command.arguments.literal
import world.cepi.kstom.command.kommand.Kommand
import world.cepi.packeteer.generator.ServerPackets
import java.lang.reflect.Constructor
import java.util.*

object PacketeerCommand : Kommand(k@ {
    val logger = LoggerFactory.getLogger(PacketeerCommand::class.java)

    val send by literal
    val players = ArgumentType.Entity("entities").onlyPlayers(true)

    val failedPackets = ServerPackets.serverPackets.filter { clazz ->

        val packetName = clazz.simpleName.replace("Packet", "")

        val emptyConstructor = clazz.constructors.firstOrNull { it.parameterCount == 0 } as? Constructor<out ServerPacket> ?: return@filter true

        val arguments = clazz.declaredFields.associateBy { field ->
            return@associateBy when (field.type) {
                Int::class.java -> ArgumentType.Integer(field.name)
                Float::class.java -> ArgumentType.Float(field.name)
                Long::class.java -> ArgumentType.Long(field.name)
                Double::class.java -> ArgumentType.Double(field.name)
                UUID::class.java -> ArgumentType.UUID(field.name)
                else -> {
                    return@filter true // This failed, send it as a failed packet
                }
            }
        }

        syntax(send, players, packetName.literal(), *arguments.keys.toTypedArray()) {
            val instance = emptyConstructor.newInstance()

            arguments.forEach {

                when(val value = !it.key) {
                    is Int -> it.value.setInt(instance, value)
                    is Float -> it.value.setFloat(instance, value)
                    is Long -> it.value.setLong(instance, value)
                    is Double -> it.value.setDouble(instance, value)
                    is UUID -> it.value.set(instance, value)
                }
            }

            PacketUtils.sendGroupedPacket((!players).find(sender) as List<Player>, instance)
        }

        return@filter false // This is successful, dont add to failed packets
    }

    if (failedPackets.isEmpty())
        return@k

    logger.warn("Could not generate ${failedPackets.size}/${ServerPackets.serverPackets.size} packets: ${
        failedPackets.joinToString(",") { it.simpleName.replace("Packet", "") }
    }")

}, "packeteer")