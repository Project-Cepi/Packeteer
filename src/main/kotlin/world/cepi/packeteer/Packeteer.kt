package world.cepi.packeteer

import net.minestom.server.extensions.Extension;
import world.cepi.packeteer.command.PacketeerCommand
import world.cepi.packeteer.manager.ListenerManager

class Packeteer : Extension() {

    override fun initialize() {
        PacketeerCommand.register()
        ListenerManager.listen()

        logger.info("[Packeteer] has been enabled!")
    }

    override fun terminate() {
        PacketeerCommand.unregister()

        logger.info("[Packeteer] has been disabled!")
    }

}