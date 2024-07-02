package net.xiang990293.cfj.item.tool;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.command.ControlFlowAware;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.xiang990293.cfj.ConceptFantasyJourney;
import net.xiang990293.cfj.item.CfjItems;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public class UnbreakableSwordItem extends SwordItem {
    public UnbreakableSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public void onItemEntityDestroyed(ItemEntity entity) {
        World world = entity.getWorld();
        NbtCompound copiedNBT = entity.writeNbt(new NbtCompound());
        Collection<ServerPlayerEntity> list = PlayerLookup.tracking((ServerWorld) world, entity.getBlockPos());
        Entity player = (entity.getOwner() != null)? entity.getOwner() : (Entity)list.toArray()[0];
        if (player != null) {
            ItemEntity itemEntity = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), new ItemStack(CfjItems.UnbreakableSword));
            UUID uuid = UUID.randomUUID();
            itemEntity.readNbt(copiedNBT);
            itemEntity.setUuid(uuid);
            itemEntity.setPickupDelay(1);
            itemEntity.teleportTo(new TeleportTarget((ServerWorld) player.getWorld(), player, entity1->{
                entity1.updatePosition(player.getX(), player.getY(), player.getZ());
                world.spawnEntity(entity1);
            }));
//            itemEntity.setPos(player.getX(), player.getY(), player.getZ());
//            world.spawnEntity(teleported);
            ConceptFantasyJourney.LOGGER.info("{} {} {} {}",world ,itemEntity.getPos(),player.getPos(),itemEntity);
        }
    }
}
