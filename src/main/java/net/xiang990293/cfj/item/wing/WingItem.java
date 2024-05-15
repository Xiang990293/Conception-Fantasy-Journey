package net.xiang990293.cfj.item.wing;

import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.xiang990293.cfj.ConceptFantasyJourney;

public class WingItem extends Item implements Equipment{
    public WingItem(Settings settings) {
        super(settings);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
    }

    public static boolean isUsable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ConceptFantasyJourney.LOGGER.info(""+user.getHandItems());
        return this.equipAndSwap(this, world, user, hand);
//        WingItem test = new WingItem(new FabricItemSettings());
    }

    @Override
    public RegistryEntry<SoundEvent> getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA;
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.CHEST;
    }
}
// ポットモターンスケ

//Q: What is the easiest way to render a new elytra?
//A:
//mixin to ElytraFeatureRenderer's render method
//ModifyReturnValue the stack.isOf invoke
//return originalResult || stack.isOf(yourItem)
//make sure this is a client mixin
//if you plan to add multiple, check if the stack isIn a tag and add your items to a tag
