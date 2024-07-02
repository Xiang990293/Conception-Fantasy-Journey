package net.xiang990293.cfj.item.tool;

import net.minecraft.block.Block;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;

public class CfjUnbreakableToolMaterial implements ToolMaterial {
    public static final CfjUnbreakableToolMaterial INSTANCE = new CfjUnbreakableToolMaterial();

    @Override
    public int getDurability(){
        return 0;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 100.00f;
    }

    @Override
    public float getAttackDamage() {
        return 2147483647.0f;
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return null;
    }

//    @Override
//    public int getMiningLevel() {
//        return 10;
//    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
//        return Ingredient.ofItems(Items.POTATO);
    }

    @Override
    public ToolComponent createComponent(TagKey<Block> tag) {
        return ToolMaterial.super.createComponent(tag);
    }
}
