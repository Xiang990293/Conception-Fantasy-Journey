package net.xiang990293.cfj.block;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.xiang990293.cfj.ConceptFantasyJourney;
import net.minecraft.block.enums.NoteBlockInstrument;

public class CfjBlocks extends Blocks{
    public static final Block CONCEPT_SIMULATOR = registerBlock("concept_simulator", new ConceptSimulatorBlock(AbstractBlock.Settings.create()
            .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
            .requiresTool()
            .strength(3.5f, 6.0f)
            .dropsLike(Blocks.OAK_LOG)
//            .luminance(Blocks.createLightLevelFromLitBlockState(13))
    ));

    public static final Block IMAGINATION_LOG = registerBlock("imagination_log", new TransparentPillarBlock(AbstractBlock.Settings.create()
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.0F).sounds(BlockSoundGroup.WOOD)
            .burnable()
            .nonOpaque()
            .blockVision(CfjBlocks::never)
            .suffocates(CfjBlocks::never)
    ));
//    public static final Block IMAGINATION_GLASS = registerBlock("imagination_glass", new StainedGlassBlock(DyeColor.BLACK,AbstractBlock.Settings.create()
//            .instrument(NoteBlockInstrument.BASS)
//            .strength(2.0F).sounds(BlockSoundGroup.WOOD)
//            .burnable()
//            .nonOpaque()
//            .blockVision(CfjBlocks::never)
//            .suffocates(CfjBlocks::never)
//    ));

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, Identifier.of(ConceptFantasyJourney.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(ConceptFantasyJourney.MOD_ID, name), block);
    }

    private static void addBlocksToIngredientTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(CONCEPT_SIMULATOR);
        entries.add(IMAGINATION_LOG);
    }
    public static void registerCfjBlocks() {
        ConceptFantasyJourney.LOGGER.info("Registering Mod Blocks for " + ConceptFantasyJourney.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(CfjBlocks::addBlocksToIngredientTabItemGroup);
    }

//    PlayerBlockBreakEvents.After;
}
