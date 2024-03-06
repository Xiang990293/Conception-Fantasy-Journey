package net.xiang990293.cfj.item.tool;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class CfjUnbreakableToolMaterial implements ToolMaterial {
    public static final CfjUnbreakableToolMaterial INSTANCE = new CfjUnbreakableToolMaterial();

    @Override
    public int getDurability(){
        return -1;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 100.00f;
    }

    @Override
    public float getAttackDamage() {
        return 0.0f;
    }

    @Override
    public int getMiningLevel() {
        return 10;
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
