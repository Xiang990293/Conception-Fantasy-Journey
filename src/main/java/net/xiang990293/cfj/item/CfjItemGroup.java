package net.xiang990293.cfj.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.xiang990293.cfj.ConceptFantasyJourney;
import net.xiang990293.cfj.block.CfjBlocks;

public class CfjItemGroup {
    public static final ItemGroup cfj_food_and_drinks_group = Registry.register(Registries.ITEM_GROUP,
            new Identifier(ConceptFantasyJourney.MOD_ID, "food_and_drinks"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.food_and_drinks.title"))
                    .icon(() -> new ItemStack(CfjItems.ValentineChocolate))
                    .entries((displayContext, entries) -> {
                        entries.add(CfjItems.ValentineChocolate);
                    })
                    .build());
    public static final ItemGroup cfj_blocks_and_tools_group = Registry.register(Registries.ITEM_GROUP,
            new Identifier(ConceptFantasyJourney.MOD_ID, "blocks_and_tools"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.blocks_and_tools.title"))
                    .icon(() -> new ItemStack(CfjBlocks.ConceptSimulatorBlock))
                    .entries((displayContext, entries) -> {
                        entries.add(CfjBlocks.ConceptSimulatorBlock);
                        entries.add(CfjBlocks.ImaginationLogBlock);
                        entries.add(CfjItems.UnbreakableSword);
                        entries.add(CfjItems.UnbreakableShovel);
                        entries.add(CfjItems.UnbreakableHoe);
                        entries.add(CfjItems.UnbreakablePickaxe);
                        entries.add(CfjItems.UnbreakableAxe);
                        entries.add(CfjItems.PureLoveSword);
                        entries.add(CfjItems.WINGS);

                        entries.add(CfjItems.UnbreakableHelmet);
//                        entries.add(CfjItems.);
                    })
                    .build());
    public static void registerItemGroups() {
        ConceptFantasyJourney.LOGGER.info("Registering Item Group for " + ConceptFantasyJourney.MOD_ID);
    }

}
