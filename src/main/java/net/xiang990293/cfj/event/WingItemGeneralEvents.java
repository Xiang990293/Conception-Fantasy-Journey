package net.xiang990293.cfj.event;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.xiang990293.cfj.item.wing.WingItem;
import net.xiang990293.cfj.network.payload.WingFlySyncC2SPayload;
import net.xiang990293.cfj.network.payload.WingFlySyncS2CPayload;
import net.xiang990293.cfj.util.FlyingData;

public class WingItemGeneralEvents {
    public static void playerFlyStateChange(PlayerEntity player, NbtCompound nbt, boolean flying, boolean flyable, boolean elytraFly, boolean CreativeFlyed){
        // this function use to **let player set to creative flying mode or may flying**, and **store data in NBT: CreativeFlyed, elytrafly**.
        // separate the server player and client player entity and let them do they need in each
        if (player instanceof ClientPlayerEntity) { // what client player do when function called
            NbtCompound abilities = new NbtCompound();
            abilities.putBoolean("flying", flying);
            abilities.putBoolean("mayfly", flyable);
            nbt.put("abilities", abilities);
            player.readNbt(nbt);

            ClientPlayNetworking.send(new WingFlySyncC2SPayload(flying, flyable, elytraFly, CreativeFlyed));
        }
        else{ // what server player do when function called
            NbtCompound abilities = new NbtCompound();
            abilities.putBoolean("flying", flying);
            abilities.putBoolean("mayfly", flyable);
            nbt.put("abilities", abilities);
            NbtCompound flyingTags = nbt.getCompound("flyingTags");
            flyingTags.putBoolean("elytraFly",elytraFly);
            flyingTags.putBoolean("creativeFlyed",CreativeFlyed);
            nbt.put("flyingTags",flyingTags);
            player.readNbt(nbt);

            FlyingData.syncData(elytraFly,CreativeFlyed, (ServerPlayerEntity) player);
            ServerPlayNetworking.send((ServerPlayerEntity) player, new WingFlySyncS2CPayload(flying, flyable));
        }
    }

    public static void registerEvents(){
        /*
        * 鞘翅開始飛行事件 當玩家嘗試展開鞘翅時呼叫/當在空中一下空白鍵時呼叫
        * 作用：
        * 1. 切換玩家的飛行狀態 (正常->鞘翅飛行)
        * 2. 限制鞘翅飛行
        * 限制：僅限客戶端
        * */
        EntityElytraEvents.CUSTOM.register((entity, tickElytra) -> {
            PlayerEntity player = (PlayerEntity) entity;

            if (player.isCreative() || player.isSpectator()) { return false;}

            NbtCompound nbt = new NbtCompound();
            player.writeNbt(nbt);
            boolean hasWingsOn = player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof WingItem;
            boolean CanCreativeFlying = nbt.getCompound("abilities").getBoolean("mayfly");
            boolean IsCreativeFlying = nbt.getCompound("abilities").getBoolean("flying");
            boolean IsElytraFlying = nbt.getCompound("flyingTags").getBoolean("elytraFly");
            boolean CreativeFlyed = nbt.getCompound("flyingTags").getBoolean("creativeFlyed");

            // 不要創造模式 不要觀察者 不要沒穿
            if (player.isSpectator() || !hasWingsOn) {
                playerFlyStateChange(player, nbt, false, false, false, false);
                return false;
            }

            if (player.isOnGround()) {
                playerFlyStateChange(player, nbt, false, true, false, false);
                return false;
            }

            if (player.getPitch() <= 80.0f && !IsElytraFlying){
                playerFlyStateChange(player, nbt, false, true, true, false);
                return true;
            }

             return true;
        });

        /*
        * 作用：
        * 1. 檢查改寫玩家狀態
        * */
        ServerTickEvents.END_SERVER_TICK.register((server) -> {
            for (ServerPlayerEntity player : PlayerLookup.all(server)) {
                if (player.isCreative() || player.isSpectator()) {
                    return;
                }

                NbtCompound nbt = new NbtCompound();
                player.writeNbt(nbt);
                boolean hasWingsOn = player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof WingItem;
                boolean CanCreativeFlying = nbt.getCompound("abilities").getBoolean("mayfly");
                boolean IsCreativeFlying = nbt.getCompound("abilities").getBoolean("flying");
                boolean IsElytraFlying = nbt.getCompound("flyingTags").getBoolean("elytraFly");
                boolean CreativeFlyed = nbt.getCompound("flyingTags").getBoolean("creativeFlyed");

                if (!hasWingsOn) { // 取消飛行
                    playerFlyStateChange(player, nbt, false, false, false, false);
                    return;
                }

                if (player.isOnGround()) {
                    playerFlyStateChange(player, nbt, false, true, false, false);
                    return;
                }

                if (IsCreativeFlying && !CreativeFlyed) {
                    playerFlyStateChange(player, nbt, true, true, false, true);
                    return;
                }

                if (!CanCreativeFlying){ // 可飛行狀態
                    playerFlyStateChange(player, nbt, false, true, IsElytraFlying, CreativeFlyed);
                    return;
                }
                return;
            }
        });
    }
}