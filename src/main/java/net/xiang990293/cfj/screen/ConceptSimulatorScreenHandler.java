package net.xiang990293.cfj.screen;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.xiang990293.cfj.ConceptFantasyJourney;
import net.xiang990293.cfj.block.CfjBlocks;
import net.xiang990293.cfj.block.entity.ConceptSimulatorBlockEntity;
import net.xiang990293.cfj.item.CfjItems;

public class ConceptSimulatorScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    public final PropertyDelegate propertyDelegate;
    public final ConceptSimulatorBlockEntity blockEntity;

    // server side
    // when server wants Client-side open "screenHandler", Client-side call this constructor
    // if inventory is empty, Client-side will call other constructor, "screenHandler" will automatically
    // sync empty inventory to inventory in Client-side.


    // server side
    public ConceptSimulatorScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate) {
        super(CfjScreenHandlers.CONCEPT_SIMULATOR_SCREEN_HANDLER, syncId);
        NbtCompound nbt = new NbtCompound();
        checkSize(((Inventory)blockEntity), 3);
        this.inventory = ((Inventory) blockEntity);
        // when player open, some inventory have its own logic
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = ((ConceptSimulatorBlockEntity) blockEntity);

        this.addSlot(new Slot(inventory,0,152,62));
        this.addSlot(new Slot(inventory,1,79,17));
        this.addSlot(new Slot(inventory,2,79,62));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(arrayPropertyDelegate);
    }

    //client side, call when Server-side blockEntity call writeScreenOpeningData()
    public ConceptSimulatorScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(pos), new ArrayPropertyDelegate(8));
    }

    //we provide this getter for the synced integer so the Screen can access this to show it on screen

    public int getProperties(int index) {
        return this.propertyDelegate.get(index);
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

    public int getScaledProgress() {
        int progress = this.propertyDelegate.get(6);
        int maxProgress = this.propertyDelegate.get(7);
//        boolean isCalculated = this.blockEntity.isCalculated;
//        ConceptFantasyJourney.LOGGER.info("world? "+this.blockEntity.getWorld());
        int progressBarSize = 51; // Progress Bar width in pixels.

//        return isCalculated? progressBarSize : maxProgress != 0 && progress != 0 ? progressBarSize - progress * progressBarSize / maxProgress : progressBarSize;
        return progressBarSize;
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
