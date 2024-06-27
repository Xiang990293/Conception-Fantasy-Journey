package net.xiang990293.cfj.item.tool;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.xiang990293.cfj.item.CfjItems;

import java.util.Set;
import java.util.UUID;

public class UnbreakableAxeItem extends AxeItem {
    public UnbreakableAxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
            super(material, settings);
        }

    @Override
    public void onItemEntityDestroyed(ItemEntity entity) {
//        World world = entity.getWorld();
//        NbtCompound copiedNBT = entity.writeNbt(new NbtCompound());
//        java.util.Collection<ServerPlayerEntity> list = PlayerLookup.tracking((ServerWorld) world, entity.getBlockPos());
//        Entity player = (entity.getOwner() != null)? entity.getOwner() : (Entity)list.toArray()[0];
//        if (player != null) {
//            ItemEntity itemEntity = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), new ItemStack(CfjItems.UnbreakableAxe));
//            UUID uuid = UUID.randomUUID();
//            copiedNBT.putIntArray("UUID", new int[]{0, 0, 0, 0});
//            itemEntity.readNbt(copiedNBT);
//            itemEntity.teleportTo(new TeleportTarget((ServerWorld) world, player, (TeleportTarget.PostDimensionTransition) entity1 -> {
//
//            }));
//            itemEntity.setUuid(uuid);
//            ItemEntity entityToSpawn = itemEntity;
//            entityToSpawn.setPickupDelay(1);
//            world.spawnEntity(entityToSpawn);
//        }
    }
}
