package net.xiang990293.cfj.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.particle.TotemParticle;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.xiang990293.cfj.ConceptFantasyJourney;
import net.xiang990293.cfj.event.UnbreakableSwordAttackEntityItemDrop;
import net.xiang990293.cfj.event.WingItemGeneralEvents;
import net.xiang990293.cfj.item.tool.PureLoveTools;
import net.xiang990293.cfj.item.tool.UnbreakableTools;
import net.xiang990293.cfj.item.wing.WingItem;

public class CfjItems {
    //public static final Item ValentineChocolate = registerItem("valentine_chocolate", new Item(new FabricItemSettings()));

    public static final Item ValentineChocolate = registerItem("valentine_chocolate", new Item(new FabricItemSettings().food(CfjFoodComponents.Valentine_Chocolate)));
    public static final Item PI = registerItem("pi", new Item(new FabricItemSettings().food(CfjFoodComponents.PI)));

    public static final Item UnbreakableSword = registerItem("unbreakable_sword", UnbreakableTools.UnbreakableSword);
    public static final Item UnbreakableShovel = registerItem("unbreakable_shovel", UnbreakableTools.UnbreakableShovel);
    public static final Item UnbreakableAxe = registerItem("unbreakable_axe", UnbreakableTools.UnbreakableAxe);
    public static final Item UnbreakablePickaxe = registerItem("unbreakable_pickaxe", UnbreakableTools.UnbreakablePickaxe);
    public static final Item UnbreakableHoe = registerItem("unbreakable_hoe", UnbreakableTools.UnbreakableHoe);

    public static final Item PureLoveSword = registerItem("pure_love_sword", PureLoveTools.PureLoveSword);

    public static final Item WINGS = registerItem("wings", new WingItem(new FabricItemSettings()
            .maxDamage(-1)
            .rarity(Rarity.RARE)
    ));

    public static final Item UnbreakableHelmet = registerItem("unbreakable_helmet", new ArmorItem(CfjArmorMaterials.UNBREAKABLE, ArmorItem.Type.HELMET, new FabricItemSettings()));

    public static final Item TotemOfDying = registerItem("totem_of_dying", new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON).maxCount(1)));
    public static final Item WaterBucketCake = registerItem("water_bucket_cake", new Item(new FabricItemSettings().food(CfjFoodComponents.Water_Bucket_Cake).maxCount(1)));



    private static void addItemsToIngredientTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(ValentineChocolate);
        entries.add(PI);
    }

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ConceptFantasyJourney.MOD_ID, name), item);
    }
    public static void registerCfjItems() {
        ConceptFantasyJourney.LOGGER.info("Registering Mod Items for " + ConceptFantasyJourney.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(CfjItems::addItemsToIngredientTabItemGroup);

        UnbreakableSwordAttackEntityItemDrop.registerEvents();
        WingItemGeneralEvents.registerEvents();
    }
}
