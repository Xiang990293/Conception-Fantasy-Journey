package net.xiang990293.cfj.block;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.xiang990293.cfj.ConceptFantasyJourney;

public class CfjBlocks extends Blocks{
    public static final Block ConceptSimulatorBlock = registerBlock("concept_simulator", new ConceptSimulatorBlock(FabricBlockSettings
            .copyOf(Blocks.IRON_BLOCK)
            .instrument(Instrument.IRON_XYLOPHONE)
            .requiresTool()
            .strength(3.5f)
//            .luminance(Blocks.createLightLevelFromLitBlockState(13))
    ));

    public static final Block ImaginationLogBlock = registerBlock("imagination_log", createLogBlock(
            MapColor.OAK_TAN, MapColor.SPRUCE_BROWN
    ));


    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(ConceptFantasyJourney.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(ConceptFantasyJourney.MOD_ID, name), block);
    }

    private static void addBlocksToIngredientTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(ConceptSimulatorBlock);
        entries.add(ImaginationLogBlock);
    }
    public static void registerCfjBlocks() {
        ConceptFantasyJourney.LOGGER.info("Registering Mod Blocks for " + ConceptFantasyJourney.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(CfjBlocks::addBlocksToIngredientTabItemGroup);
    }

//    PlayerBlockBreakEvents.After;
}
