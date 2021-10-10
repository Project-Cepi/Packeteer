package world.cepi.packeteer

import net.minestom.server.extensions.Extension;
import world.cepi.packeteer.command.PacketeerCommand

class Packeteer : Extension() {

    override fun initialize() {
        PacketeerCommand.register()

        logger.info("[Packeteer] has been enabled!")
    }

    override fun terminate() {
        PacketeerCommand.unregister()

        logger.info("[Packeteer] has been disabled!")
    }

}