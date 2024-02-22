package net.xiang990293.cfj.screen;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.xiang990293.cfj.block.entity.ConceptSimulatorBlockEntity;

public class ConceptSimulatorScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final ConceptSimulatorBlockEntity blockEntity;

    public ConceptSimulatorScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()), new ArrayPropertyDelegate(3));
    }
    public ConceptSimulatorScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate) {
        super(CfjScreenHandlers.CONCEPT_SIMULATOR_SCREEN_HANDLER, syncId);
        checkSize(((Inventory)blockEntity), 2);
        this.inventory = ((Inventory) blockEntity);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = ((ConceptSimulatorBlockEntity) blockEntity);

        this.addSlot(new Slot(inventory,0,88,11));
        this.addSlot(new Slot(inventory,1,88,59));
        this.addSlot(new Slot(inventory,2,150,59));

        addPlayerInventtory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(arrayPropertyDelegate);
    }


    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    public boolean isCalculating(){
        return propertyDelegate.get(6) > 0;

    }

    public int getScaledProgress() {
        int progress = this.propertyDelegate.get(6);
        int maxProgress = this.propertyDelegate.get(7);
        int progressBarSize = 162; // Progress Bar width in pixels.

        return maxProgress != 0 && progress != 0 ? progress * progressBarSize / maxProgress : 0;
    }
    private void addPlayerHotbar(PlayerInventory playerInventory) {
    }

    private void addPlayerInventtory(PlayerInventory playerInventory) {
    }
}
