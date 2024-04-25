package net.xiang990293.cfj;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.xiang990293.cfj.event.keybind.KeyInputHandler;
import net.xiang990293.cfj.network.CfjNetworkingContants;
import net.xiang990293.cfj.screen.CfjScreenHandlers;
import net.xiang990293.cfj.screen.ConceptSimulatorScreen;
import org.lwjgl.glfw.GLFW;

public class ConceptFantasyJourneyClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		HandledScreens.register(CfjScreenHandlers.CONCEPT_SIMULATOR_SCREEN_HANDLER, ConceptSimulatorScreen::new);

		KeyInputHandler.register();
	}
}