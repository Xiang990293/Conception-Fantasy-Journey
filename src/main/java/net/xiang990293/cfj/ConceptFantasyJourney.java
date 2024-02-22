package net.xiang990293.cfj;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.xiang990293.cfj.block.CfjBlocks;
import net.xiang990293.cfj.block.entity.CfjBlockEntities;
import net.xiang990293.cfj.item.CfjItemGroup;
import net.xiang990293.cfj.item.CfjItems;
import net.xiang990293.cfj.screen.CfjScreenHandlers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConceptFantasyJourney implements ModInitializer {
	public static final String MOD_ID = "concept_fantasy_journey";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final ItemGroup ConceptFantasyJourneyItems = FabricItemGroup.builder()
			.icon(() -> new ItemStack(CfjItems.ValentineChocolate))
			.displayName(Text.translatable("itemGroup.tutorial.test_group"))
			.entries((context, entries) -> {
				entries.add(CfjItems.ValentineChocolate);
			})
			.build();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		CfjItemGroup.registerItemGroups();
		CfjItems.registerCfjItems();
		CfjBlocks.registerCfjBlocks();
		CfjBlockEntities.registerCfjBlockEntities();
		CfjScreenHandlers.registerScreenHandlers();
	}
}