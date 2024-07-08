package net.xiang990293.cfj.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.xiang990293.cfj.ConceptFantasyJourney;
import net.xiang990293.cfj.event.PureLoveSwordAttackEntityItemDrop;
import net.xiang990293.cfj.event.WingItemGeneralEvents;
import net.xiang990293.cfj.item.armor.UnbreakableArmorMaterial;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CfjArmorMaterials {

    public static final RegistryEntry<ArmorMaterial> UNBREAKABLE = registerArmorMaterial("unbreakable",(Map<ArmorItem.Type,Integer>) Util.make(new EnumMap(ArmorItem.Type.class), (map)-> {
        map.put(ArmorItem.Type.HELMET,2147483647);
        map.put(ArmorItem.Type.CHESTPLATE,2147483647);
        map.put(ArmorItem.Type.LEGGINGS,2147483647);
        map.put(ArmorItem.Type.BOOTS,2147483647);
    }), -1, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, ()-> Ingredient.ofItems(CfjItems.UnbreakableHelmet), 2f,10);

    public static void registerCfjArmorMaterials() {
        ConceptFantasyJourney.LOGGER.info("Registering Mod ArmorMaterials for " + ConceptFantasyJourney.MOD_ID);
    }

    public static RegistryEntry<ArmorMaterial> registerArmorMaterial(String name, Map<ArmorItem.Type, Integer> map, int enchantability, RegistryEntry<SoundEvent> equipSound, Supplier<Ingredient> repairIngredient, List<ArmorMaterial.Layer> layers, float toughness, float knockbackResistance) {
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(name),new ArmorMaterial(map, enchantability, equipSound, repairIngredient, layers, toughness,knockbackResistance));
    }

    public static RegistryEntry<ArmorMaterial> registerArmorMaterial(String name, Map<ArmorItem.Type, Integer> map, int enchantability, RegistryEntry<SoundEvent> equipSound, Supplier<Ingredient> repairIngredient, float toughness, float knockbackResistance) {
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(Identifier.of(name)));
        return registerArmorMaterial(name, map, enchantability, equipSound, repairIngredient, layers, toughness,knockbackResistance);
    }
}
