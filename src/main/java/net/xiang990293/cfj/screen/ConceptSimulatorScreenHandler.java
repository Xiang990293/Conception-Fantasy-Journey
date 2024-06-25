package net.xiang990293.cfj.screen;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.xiang990293.cfj.ConceptFantasyJourney;
import net.xiang990293.cfj.block.entity.ConceptSimulatorBlockEntity;

public class ConceptSimulatorScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    public final PropertyDelegate propertyDelegate;
    public final ConceptSimulatorBlockEntity blockEntity;

    //server side
    public ConceptSimulatorScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate) {
        super(CfjScreenHandlers.CONCEPT_SIMULATOR_SCREEN_HANDLER, syncId);
        checkSize(((Inventory)blockEntity), 3);
        this.inventory = ((Inventory) blockEntity);
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

    //server side
    public ConceptSimulatorScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity) {
        this(syncId, playerInventory, blockEntity, new ArrayPropertyDelegate(8));
    }

    //client side, call when server side blockEntity call writeScreenOpeningData()
    public ConceptSimulatorScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        // Object pos is type of ConceptSimulatorBlockEntity, but I need to wrote as Object which I don't know.
        super(CfjScreenHandlers.CONCEPT_SIMULATOR_SCREEN_HANDLER, syncId);
        BlockPos pos = buf.readBlockPos();
        BlockEntity blockEntity = playerInventory.player.getWorld().getBlockEntity((BlockPos) pos);
        checkSize(((Inventory)blockEntity), 3);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = new ArrayPropertyDelegate(3);
        this.blockEntity = ((ConceptSimulatorBlockEntity) blockEntity);

        this.addSlot(new Slot(inventory,0,152,62));
        this.addSlot(new Slot(inventory,1,79,17));
        this.addSlot(new Slot(inventory,2,79,62));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

//        addProperties(arrayPropertyDelegate);
    }

//    public ConceptSimulatorScreenHandler(int syncId, PlayerInventory playerInventory, Object o) {
//        super(CfjScreenHandlers.CONCEPT_SIMULATOR_SCREEN_HANDLER, syncId);
//    }

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
        boolean isCalculated = this.blockEntity.isCalculated;
//        ConceptFantasyJourney.LOGGER.info("world? "+this.blockEntity.getWorld());
        int progressBarSize = 51; // Progress Bar width in pixels.

        return isCalculated? progressBarSize : maxProgress != 0 && progress != 0 ? progressBarSize - progress * progressBarSize / maxProgress : progressBarSize;
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
