package net.xiang990293.cfj.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.xiang990293.cfj.network.CfjNetworkingContants;
import net.xiang990293.cfj.network.payload.CustomWingFlySyncPayload;

public class FlyingData {
    public static void changeData(IEntityDataSaver player, boolean elytraFly, boolean creativeFlyed){
        NbtCompound nbt = player.getPersistentData();
        nbt.putBoolean("elytraFly", elytraFly);
        nbt.putBoolean("creativeFlyed", creativeFlyed);
        syncData(elytraFly, creativeFlyed, (ServerPlayerEntity) player);
        return;
    }

    public static void syncData(boolean elytrafly, boolean creativeFlyed, ServerPlayerEntity player) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeBoolean(elytrafly);
        buffer.writeBoolean(creativeFlyed);
        ServerPlayNetworking.send(player, new CustomWingFlySyncPayload(elytrafly, creativeFlyed));
    }
}
