package net.xiang990293.cfj.item.tool;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class CfjPureLoveToolMaterial implements ToolMaterial {
    public static final CfjPureLoveToolMaterial INSTANCE = new CfjPureLoveToolMaterial();

    @Override
    public int getDurability(){
        return -1;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 0.00f;
    }

    @Override
    public float getAttackDamage() {
        return 0.0f;
    }

    @Override
    public int getMiningLevel() {
        return 0;
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
//        return Ingredient.ofItems(Items.POTATO);
    }
}
