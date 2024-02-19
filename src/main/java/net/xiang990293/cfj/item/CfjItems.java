package net.xiang990293.cfj.item;

import net.fabricmc.fabric.api.item.v1.CustomDamageHandler;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.xiang990293.cfj.ConceptFantasyJourney;
import net.xiang990293.cfj.item.tool.CfjUnbreakableToolMaterial;
import net.xiang990293.cfj.item.tool.UnbreakableTools;

import java.util.function.Consumer;

public class CfjItems {
    //public static final Item ValentineChocolate = registerItem("valentine_chocolate", new Item(new FabricItemSettings()));

    public static final Item ValentineChocolate = registerItem("valentine_chocolate", new Item(new FabricItemSettings().food(CfjFoodComponents.Valentine_Chocolate)));
    public static final Item UnbreakableSword = registerItem("unbreakable_sword", UnbreakableTools.UnbreakableSword);
    public static final Item UnbreakableShovel = registerItem("unbreakable_shovel", UnbreakableTools.UnbreakableShovel);
    public static final Item UnbreakableAxe = registerItem("unbreakable_axe", UnbreakableTools.UnbreakableAxe);
    public static final Item UnbreakablePickaxe = registerItem("unbreakable_pickaxe", UnbreakableTools.UnbreakablePickaxe);
    public static final Item UnbreakableHoe = registerItem("unbreakable_hoe", UnbreakableTools.UnbreakableHoe);



    private static void addItemsToIngredientTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(ValentineChocolate);
    }

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ConceptFantasyJourney.MOD_ID, name), item);
    }
    public static void registerCfjItems() {
        ConceptFantasyJourney.LOGGER.info("Registering Mod Items for " + ConceptFantasyJourney.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(CfjItems::addItemsToIngredientTabItemGroup);
    }
}
