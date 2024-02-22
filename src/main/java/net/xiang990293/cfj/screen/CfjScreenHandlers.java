package net.xiang990293.cfj.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.xiang990293.cfj.ConceptFantasyJourney;

public class CfjScreenHandlers {
    public static final ScreenHandlerType<ConceptSimulatorScreenHandler> CONCEPT_SIMULATOR_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(ConceptFantasyJourney.MOD_ID, "concept_simulator"),
                    new ExtendedScreenHandlerType<>(ConceptSimulatorScreenHandler::new));

    public static void registerScreenHandlers() {
        ConceptFantasyJourney.LOGGER.info("Registering Mod Screen Handlers for " + ConceptFantasyJourney.MOD_ID);
    }
}
