package net.xiang990293.cfj;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.xiang990293.cfj.block.CfjBlocks;
import net.xiang990293.cfj.event.keybind.KeyInputHandler;
import net.xiang990293.cfj.screen.CfjScreenHandlers;
import net.xiang990293.cfj.screen.ConceptSimulatorScreen;

public class ConceptFantasyJourneyClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		HandledScreens.register(CfjScreenHandlers.CONCEPT_SIMULATOR_SCREEN_HANDLER, ConceptSimulatorScreen::new);

		KeyInputHandler.register();

		BlockRenderLayerMap.INSTANCE.putBlock(CfjBlocks.IMAGINATION_LOG, RenderLayer.getCutout());
	}
}