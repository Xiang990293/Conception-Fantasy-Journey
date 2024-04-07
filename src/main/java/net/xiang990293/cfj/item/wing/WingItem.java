package net.xiang990293.cfj.item.wing;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.xiang990293.cfj.ConceptFantasyJourney;

public class WingItem extends Item implements Equipment {
    public WingItem(Item.Settings settings) {
        super(settings);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
    }

    public static boolean isUsable(ItemStack stack) {
        return true;
    }


    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ConceptFantasyJourney.LOGGER.info(""+user.getHandItems());
        return this.equipAndSwap(this, world, user, hand);
//        WingItem test = new WingItem(new FabricItemSettings());
    }

    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA;
    }

    public EquipmentSlot getSlotType() {
        return EquipmentSlot.CHEST;
    }
}
