package net.xiang990293.cfj.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.xiang990293.cfj.ConceptFantasyJourney;

import java.util.function.Supplier;

public enum CfjArmorMaterials {
    UNBREAKABLE("unbreakable", 0, new int[] {2147483647,2147483647,2147483647,2147483647}, -1, SoundEvents.ITEM_AXE_SCRAPE, 2f,10, null)
    ;
    private final String name;
    private final int DurabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = {11,16,15,13};

    CfjArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound,
                      float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.DurabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

//    @Override
    public int getDurability(ArmorItem.Type type) {
        return BASE_DURABILITY[type.ordinal()] * this.DurabilityMultiplier;
    }

//    @Override
    public int getProtection(ArmorItem.Type type) {
        return protectionAmounts[type.ordinal()];
    }

//    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

//    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

//    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

//    @Override
    public String getName() {
        return ConceptFantasyJourney.MOD_ID + ":" + this.name;
    }

//    @Override
    public float getToughness() {
        return this.toughness;
    }

//    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
