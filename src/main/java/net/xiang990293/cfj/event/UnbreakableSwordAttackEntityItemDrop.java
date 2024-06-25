package net.xiang990293.cfj.event;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.xiang990293.cfj.damageType.CfjDamageTypes;
import net.xiang990293.cfj.item.CfjItems;

public class UnbreakableSwordAttackEntityItemDrop {
    public static void registerEvents(){
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!entity.isInvulnerable() && !player.isSpectator() &&
                    player.getMainHandStack().isOf(CfjItems.PureLoveSword)) {
                entity.damage(CfjDamageTypes.of(world, CfjDamageTypes.EMOTIONAL_DAMAGE), 1.0F);

//                ItemEntity entityToSpawn = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(CfjItems.ValentineChocolate));
//                entityToSpawn.setPickupDelay(10);

//                world.spawnEntity(entityToSpawn);
            }

            return ActionResult.PASS;
        });
    }
}
