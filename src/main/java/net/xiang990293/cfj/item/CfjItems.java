package net.xiang990293.cfj.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.xiang990293.cfj.ConceptFantasyJourney;

public class CfjItems {
    //public static final Item ValentineChocolate = registerItem("valentine_chocolate", new Item(new FabricItemSettings()));

    public static final Item ValentineChocolate = registerItem("valentine_chocolate", new Item(new FabricItemSettings().food(CfjFoodComponents.Valentine_Chocolate)));

    private static void addItemsToIngredientTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(ValentineChocolate);
    }

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ConceptFantasyJourney.MOD_ID, name), item);
    }
    public static void registerCfjItems() {
        ConceptFantasyJourney.LOGGER.info("Registering Mod Items for" + ConceptFantasyJourney.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(CfjItems::addItemsToIngredientTabItemGroup);
    }
}
