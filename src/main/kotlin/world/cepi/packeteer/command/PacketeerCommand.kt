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
import world.cepi.packeteer.generator.ServerPackets
import java.lang.reflect.Constructor
import java.util.*

object PacketeerCommand : Kommand(k@ {
    val logger = LoggerFactory.getLogger(PacketeerCommand::class.java)

    val send by literal
    val players = ArgumentType.Entity("entities").onlyPlayers(true)

    val failedPackets = ServerPackets.serverPackets.filter { clazz ->
        val packetName = clazz.simpleName.replace("Packet", "")

        // Get the first empty constructor
        val emptyConstructor = clazz.constructors.firstOrNull { it.parameterCount == 0 } as? Constructor<out ServerPacket> ?: return@filter true

        val arguments = clazz.declaredFields.associateBy { field ->

            return@associateBy when {
                field.type == Int::class.java -> ArgumentType.Integer(field.name)
                field.type == Float::class.java -> ArgumentType.Float(field.name)
                field.type == Long::class.java -> ArgumentType.Long(field.name)
                field.type == Double::class.java -> ArgumentType.Double(field.name)
                field.type == UUID::class.java -> ArgumentType.UUID(field.name)
                field.type == Boolean::class.java -> ArgumentType.Boolean(field.name)
                field.type == Short::class.java -> ArgumentType.Integer(field.name)
                field.type == String::class.java -> ArgumentType.String(field.name)
                field.type == Component::class.java -> ArgumentType.Component(field.name)
                field.type == Point::class.java -> ArgumentType.RelativeVec3(field.name)
                field.type == Vec::class.java -> ArgumentType.RelativeVec3(field.name)
                field.type.enumConstants != null -> ArgumentType.Enum(field.name, field.type as Class<Enum<*>>)
                else -> return@filter true // This failed, send it as a failed packet

            }
        }

        syntax(send, players, packetName.literal(), *arguments.keys.toTypedArray()) {
            val instance = emptyConstructor.newInstance()

            arguments.forEach {

                val field = it.value
                val value = !it.key

                when(field.type) {
                    Int::class.java -> field.setInt(instance, value as Int)
                    Float::class.java -> field.setFloat(instance, value as Float)
                    Long::class.java -> field.setLong(instance, value as Long)
                    Double::class.java -> field.setDouble(instance, value as Double)
                    Boolean::class.java -> field.setBoolean(instance, value as Boolean)
                    Short::class.java -> field.setShort(instance, value as Short)
                    UUID::class.java -> field.set(instance, value)
                    String::class.java -> field.set(instance, value)
                    Component::class.java -> field.set(instance, value)
                    Point::class.java -> field.set(instance, (value as RelativeVec).fromSender(sender))
                    Vec::class.java -> field.set(instance, (value as RelativeVec).fromSender(sender))
                    Enum::class.java -> field.set(instance, value)
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