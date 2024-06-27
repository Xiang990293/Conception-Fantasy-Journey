package net.xiang990293.cfj.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.xiang990293.cfj.ConceptFantasyJourney;
import net.xiang990293.cfj.block.entity.ConceptSimulatorBlockEntity;

public class CfjScreenHandlers {
    public static final ExtendedScreenHandlerType<ConceptSimulatorScreenHandler, BlockPos> CONCEPT_SIMULATOR_SCREEN_HANDLER;

    static {
        CONCEPT_SIMULATOR_SCREEN_HANDLER = Registry.register(
                Registries.SCREEN_HANDLER,
                Identifier.of(ConceptFantasyJourney.MOD_ID, "concept_simulator"),
                new ExtendedScreenHandlerType<>(
                        ConceptSimulatorScreenHandler::new,
                        BlockPos.PACKET_CODEC
                )
        );
    }

    public static void registerScreenHandlers() {
        ConceptFantasyJourney.LOGGER.info("Registering Mod Screen Handlers for " + ConceptFantasyJourney.MOD_ID);
    }
}
