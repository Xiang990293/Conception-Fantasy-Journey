package net.xiang990293.cfj.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class CfjFoodComponents {
    public static final FoodComponent Valentine_Chocolate =  new FoodComponent.Builder().hunger(2).saturationModifier(0.25f)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 2), 1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 100, 5), 1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 60, 2), 1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 60, 2), 1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.WITHER, 60, 0), 1f).build();
}
