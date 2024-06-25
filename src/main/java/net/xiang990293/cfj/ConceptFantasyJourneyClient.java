package net.xiang990293.cfj;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.xiang990293.cfj.event.keybind.KeyInputHandler;
import net.xiang990293.cfj.screen.CfjScreenHandlers;
import net.xiang990293.cfj.screen.ConceptSimulatorScreen;

public class ConceptFantasyJourneyClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		HandledScreens.register(CfjScreenHandlers.CONCEPT_SIMULATOR_SCREEN_HANDLER, ConceptSimulatorScreen::new);

		KeyInputHandler.register();
	}
}