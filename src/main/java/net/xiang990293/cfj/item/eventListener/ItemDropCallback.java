package net.xiang990293.cfj.item.eventListener;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface ItemDropCallback {
    /*
    * Call when player drop an Item,
    * - SUCCESS */
    Event<ItemDropCallback> EVENT = EventFactory.createArrayBacked(ItemDropCallback.class,
            (listeners) -> (player, item) -> {
                for (ItemDropCallback listener : listeners) {
                    ActionResult result = listener.drop(player, item);

                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

            ActionResult drop(PlayerEntity player, ItemEntity item);

//    private static void summonReplacementSword(ItemStack originalItemStack, MinecraftServer server) {
//        NbtCompound copiedNBT = entity.writeNbt(nbt);
//        MinecraftServer server = entity.getServer();
//        summonReplacementSword(ItemStack originalItemStack, server);
//        CommandManager commandManager = server.getCommandManager();
//        commandManager.executeWithPrefix(server.getCommandSource(), "/execute at @p run summon item ~ ~ ~ " + copiedNBT.toString());
//    }
}
