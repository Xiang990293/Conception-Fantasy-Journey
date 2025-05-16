package net.xiang990293.cfj.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class CfjFoodComponents {
    private static final int SEC_TO_TICK = 20;

    public static final FoodComponent Valentine_Chocolate = new FoodComponent.Builder().hunger(2).saturationModifier(0.25f)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 2), 1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 100, 5), 1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 60, 2), 1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 60, 2), 1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.WITHER, 60, 0), 1f).build();

    public static final FoodComponent PI = new FoodComponent.Builder().hunger(2).saturationModifier(3.14f)
            .alwaysEdible()
            .snack()
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 314*SEC_TO_TICK, 2), 1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 3*SEC_TO_TICK, 2), 0.314f)
            .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 31*SEC_TO_TICK, 3), 1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 314*SEC_TO_TICK, 14), 1f).build();

    public static final FoodComponent Water_Bucket_Cake = new FoodComponent.Builder().hunger(0).saturationModifier(0)
            .alwaysEdible()
            .snack()
            .statusEffect(new StatusEffectInstance(StatusEffects.UNLUCK, 20, 255), 1).build();
}
