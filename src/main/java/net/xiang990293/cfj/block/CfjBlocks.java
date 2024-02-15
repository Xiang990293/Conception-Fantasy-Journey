package net.xiang990293.cfj.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.xiang990293.cfj.ConceptFantasyJourney;
import net.xiang990293.cfj.item.CfjFoodComponents;

import static net.xiang990293.cfj.item.CfjItems.registerItem;

public class CfjBlocks {
    public static final Item ValentineChocolate = registerItem("valentine_chocolate", new Item(new FabricItemSettings().food(CfjFoodComponents.Valentine_Chocolate)));

    public static final Block ConceptSimulatorBlock = registerBlock("concept_simulator", new Block(AbstractBlock.Settings.create()));
    public static final Item ConceptSimulatorItem = registerItem("concept_simulator", new Item(new FabricItemSettings()));

    private static void addBlocksToIngredientTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(ConceptSimulatorItem);
    }

    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(ConceptFantasyJourney.MOD_ID, name), block);
    }
    public static void registerCfjBlocks() {
        ConceptFantasyJourney.LOGGER.info("Registering Mod Items for" + ConceptFantasyJourney.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(CfjBlocks::addBlocksToIngredientTabItemGroup);
    }
}
