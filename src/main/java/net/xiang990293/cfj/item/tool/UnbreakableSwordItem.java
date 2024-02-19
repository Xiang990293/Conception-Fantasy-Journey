package net.xiang990293.cfj.item.tool;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.xiang990293.cfj.ConceptFantasyJourney;

public class UnbreakableSwordItem extends SwordItem {
    public UnbreakableSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }
}
