package net.xiang990293.cfj.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.xiang990293.cfj.ConceptFantasyJourney;
import net.xiang990293.cfj.item.CfjItems;
import net.xiang990293.cfj.network.CfjNetworkingContants;
import net.xiang990293.util.Lambda;

import java.util.Arrays;

public class CheckHandsEvents {
    public static void registerEvents(){
        ServerTickEvents.END_SERVER_TICK.register((server) -> {
            server.getPlayerManager().getPlayerList().forEach((player) -> {
                if (player == null) return;
                player.getHandItems().forEach((itemStack) -> {
                    if (itemStack.getItem().equals(CfjItems.TotemOfDying)) {
                        player.kill();
                    }
                });
            });
        });
    }
}
