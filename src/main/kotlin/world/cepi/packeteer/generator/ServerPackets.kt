package world.cepi.packeteer.generator

import net.minestom.server.network.packet.server.login.*
import net.minestom.server.network.packet.server.play.*

object ServerPackets {

    val serverPackets = arrayOf(
        LoginDisconnectPacket::class,
        EncryptionRequestPacket::class,
        LoginSuccessPacket::class,
        SetCompressionPacket::class,
        LoginPluginRequestPacket::class,
        SpawnEntityPacket::class,
        SpawnExperienceOrbPacket::class,
        SpawnLivingEntityPacket::class,
        SpawnPaintingPacket::class,
        SpawnPlayerPacket::class,
        SculkVibrationSignal::class,
        EntityAnimationPacket::class,
        StatisticsPacket::class,
        AcknowledgePlayerDiggingPacket::class,
        BlockBreakAnimationPacket::class,
        BlockEntityDataPacket::class,
        BlockActionPacket::class,
        BlockChangePacket::class,
        BossBarPacket::class,
        ServerDifficultyPacket::class,
        ChatMessagePacket::class,
        ClearTitlesPacket::class,
        TabCompletePacket::class,
        DeclareCommandsPacket::class,
        CloseWindowPacket::class,
        WindowItemsPacket::class,
        WindowPropertyPacket::class,
        SetSlotPacket::class,
        SetCooldownPacket::class,
        PluginMessagePacket::class,
        NamedSoundEffectPacket::class,
        DisconnectPacket::class,
        EntityStatusPacket::class,
        ExplosionPacket::class,
        UnloadChunkPacket::class,
        ChangeGameStatePacket::class,
        OpenHorseWindowPacket::class,
        InitializeWorldBorderPacket::class,
        KeepAlivePacket::class,
        ChunkDataPacket::class,
        EffectPacket::class,
        ParticlePacket::class,
        UpdateLightPacket::class,
        JoinGamePacket::class,
        MapDataPacket::class,
        TradeListPacket::class,
        EntityPositionPacket::class,
        EntityPositionAndRotationPacket::class,
        EntityRotationPacket::class,
        VehicleMovePacket::class,
        OpenBookPacket::class,
        OpenWindowPacket::class,
        OpenSignEditorPacket::class,
        PingPacket::class,
        CraftRecipeResponse::class,
        PlayerAbilitiesPacket::class,
        EndCombatEventPacket::class,
        EnterCombatEventPacket::class,
        DeathCombatEventPacket::class,
        PlayerInfoPacket::class,
        FacePlayerPacket::class,
        PlayerPositionAndLookPacket::class,
        UnlockRecipesPacket::class,
        DestroyEntitiesPacket::class,
        RemoveEntityEffectPacket::class,
        ResourcePackSendPacket::class,
        RespawnPacket::class,
        EntityHeadLookPacket::class,
        MultiBlockChangePacket::class,
        SelectAdvancementTabPacket::class,
        ActionBarPacket::class,
        WorldBorderCenterPacket::class,
        WorldBorderLerpSizePacket::class,
        WorldBorderSizePacket::class,
        WorldBorderWarningDelayPacket::class,
        WorldBorderWarningReachPacket::class,
        CameraPacket::class,
        HeldItemChangePacket::class,
        UpdateViewPositionPacket::class,
        UpdateViewDistancePacket::class,
        SpawnPositionPacket::class,
        DisplayScoreboardPacket::class,
        EntityMetaDataPacket::class,
        AttachEntityPacket::class,
        EntityVelocityPacket::class,
        EntityEquipmentPacket::class,
        SetExperiencePacket::class,
        ScoreboardObjectivePacket::class,
        SetExperiencePacket::class,
        UpdateHealthPacket::class,
        ScoreboardObjectivePacket::class,
        SetPassengersPacket::class,
        TeamsPacket::class,
        UpdateScorePacket::class,
        SetTitleSubTitlePacket::class,
        TimeUpdatePacket::class,
        SetTitleTextPacket::class,
        SetTitleTimePacket::class,
        EntitySoundEffectPacket::class,
        SoundEffectPacket::class,
        SoundEffectPacket::class,
        StopSoundPacket::class,
        PlayerListHeaderAndFooterPacket::class,
        NbtQueryResponsePacket::class,
        CollectItemPacket::class,
        EntityTeleportPacket::class,
        AdvancementsPacket::class,
        EntityPropertiesPacket::class,
        EntityEffectPacket::class,
        DeclareRecipesPacket::class,
        TagsPacket::class
    ).distinct().map { it.java }

}