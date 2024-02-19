package net.xiang990293.cfj.item.tool;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.context.CommandContextBuilder;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.world.World;

public class UnbreakableItem extends ToolItem {
    public UnbreakableItem(ToolMaterial material, Settings settings) {
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
