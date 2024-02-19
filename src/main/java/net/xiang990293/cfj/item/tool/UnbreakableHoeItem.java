package net.xiang990293.cfj.item.tool;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;

public class UnbreakableHoeItem extends ToolItem {
    public UnbreakableHoeItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }


    @Override
    public void onItemEntityDestroyed(ItemEntity entity) {
        NbtCompound nbt = new NbtCompound();
        NbtCompound copiedNBT = entity.writeNbt(nbt);
        MinecraftServer server = entity.getServer();
        CommandManager commandManager = server.getCommandManager();
        commandManager.executeWithPrefix(server.getCommandSource(), "/execute at @p run summon item ~ ~ ~ " + copiedNBT.toString());
    }
}
