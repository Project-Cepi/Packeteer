package world.cepi.packeteer

import net.minestom.server.extensions.Extension;
import world.cepi.kstom.util.log
import world.cepi.packeteer.command.PacketeerCommand
import world.cepi.packeteer.manager.ListenerManager

class Packeteer : Extension() {

    override fun initialize(): LoadStatus {
        PacketeerCommand.register()
        ListenerManager.listen()

        log.info("[Packeteer] has been enabled!")

        return LoadStatus.SUCCESS
    }

    override fun terminate() {
        PacketeerCommand.unregister()

        log.info("[Packeteer] has been disabled!")
    }

}