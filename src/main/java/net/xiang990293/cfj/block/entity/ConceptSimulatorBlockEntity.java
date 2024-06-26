/*
 * Decompiled with CFR 0.2.1 (FabricMC 53fa44c9).
 */
package net.xiang990293.cfj.block.entity;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xiang990293.cfj.block.CfjBlocks;
import net.xiang990293.cfj.item.CfjItems;
import net.xiang990293.cfj.network.CfjNetworkingContants;
import net.xiang990293.cfj.screen.ConceptSimulatorScreenHandler;
import org.jetbrains.annotations.Nullable;

public class ConceptSimulatorBlockEntity
        extends BlockEntity
        implements ExtendedScreenHandlerFactory, ImplementedInventory {
    public ConceptSimulatorBlockEntity(BlockPos pos, BlockState state) {
        super(CfjBlockEntities.CONCEPT_SIMULATOR_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ConceptSimulatorBlockEntity.this.energy;
                    case 1 -> ConceptSimulatorBlockEntity.this.maxEnergy;
                    case 2 -> ConceptSimulatorBlockEntity.this.sleepEnergy;
                    case 3 -> ConceptSimulatorBlockEntity.this.maxSleepEnergy;
                    case 4 -> ConceptSimulatorBlockEntity.this.fictitiousMass;
                    case 5 -> ConceptSimulatorBlockEntity.this.maxFictitiousMass;
                    case 6 -> ConceptSimulatorBlockEntity.this.progress;
                    case 7 -> ConceptSimulatorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ConceptSimulatorBlockEntity.this.energy = value;
                    case 1 -> ConceptSimulatorBlockEntity.this.maxEnergy = value;
                    case 2 -> ConceptSimulatorBlockEntity.this.sleepEnergy = value;
                    case 3 -> ConceptSimulatorBlockEntity.this.maxSleepEnergy = value;
                    case 4 -> ConceptSimulatorBlockEntity.this.fictitiousMass = value;
                    case 5 -> ConceptSimulatorBlockEntity.this.maxFictitiousMass = value;
                    case 6 -> ConceptSimulatorBlockEntity.this.progress = value;
                    case 7 -> ConceptSimulatorBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return propertyCount;
            }
        };
    }

    public static void serverTick(World world, BlockPos pos, BlockState state) {
//        markDirty(world, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
    private static final int CHIPS_SLOT = 0;
    private static final int CRYSTAL_SLOT = 1;
    private static final int LIGHT_BULB_SLOT = 2;
    protected final PropertyDelegate propertyDelegate;
    private int energy = 0;
    private int maxEnergy = 2000;
    private int sleepEnergy = 0;
    private int maxSleepEnergy = 100;
    private int fictitiousMass = 0;
    private int maxFictitiousMass = 500;
    public int progress = 0;
    public int maxProgress = 2000;
    public boolean isCalculating;
    public boolean isSimulating;
    public boolean isCalculated;
    public boolean IsChipAvailable;
    public boolean IsCrystalAvailable;
    public boolean IsLightBulbAvailable;
    public final int propertyCount = 8;

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("blockentity.gui.concept_simulator");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        NbtCompound concept_simulator = new NbtCompound();
        concept_simulator.putInt("concept_simulator.energy", energy);
        concept_simulator.putInt("concept_simulator.sleep_energy", sleepEnergy);
        concept_simulator.putInt("concept_simulator.fictitious_mass", fictitiousMass);
        concept_simulator.putBoolean("concept_simulator.simulating", isSimulating);
        concept_simulator.putBoolean("concept_simulator.calculating", isCalculating);
        concept_simulator.putBoolean("concept_simulator.calculated", isCalculated);
        nbt.put("concept_simulator",concept_simulator);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        NbtCompound concept_simulator = nbt.getCompound("concept_simulator");
        energy = concept_simulator.getInt("concept_simulator.energy");
        sleepEnergy = concept_simulator.getInt("concept_simulator.sleep_energy");
        fictitiousMass = concept_simulator.getInt("concept_simulator.fictitious_mass");
        isSimulating = concept_simulator.getBoolean("concept_simulator.simulating");
        isCalculating = concept_simulator.getBoolean("concept_simulator.calculating");
        isCalculated = concept_simulator.getBoolean("concept_simulator.calculated");
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        IsChipAvailable = (getStack(CHIPS_SLOT).getItem() == CfjItems.UnbreakableSword);
        IsLightBulbAvailable = (getStack(LIGHT_BULB_SLOT).getItem() == CfjBlocks.ConceptSimulatorBlock.asItem());
        IsCrystalAvailable = (getStack(CRYSTAL_SLOT).getItem() == CfjItems.ValentineChocolate);
        markDirtyAndSync(world, pos, state);

//        logic executing every tick in game
        if(world.isClient()){
            return;
        }

        if(!isSimulating) {
            if(this.hasRecipe()) {
                if(isCalculating) {
                    progress += 1;
                    markDirtyAndSync(world, pos, state);
                }

                if(this.hasCalculationFinished()) {
                    this.resetProgress(state);
                    isCalculated = true;
                    isCalculating = false;

                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeBoolean(isCalculated);
                    buf.writeBlockPos(this.getPos());

                    for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, this.getPos())) {
                        ServerPlayNetworking.send((ServerPlayerEntity) player, CfjNetworkingContants.Concept_Simulator_Finish_Calculating_ID, buf);
                    }

                    markDirtyAndSync(world, pos, state);
                }
            } else {
                this.resetProgress(state);
                this.failedSimulationAndCalculation(state);
                markDirtyAndSync(world, pos, state);
            }
        } else if (isSimulating) {
            markDirtyAndSync(world, pos, state);
        }
    }

    protected void markDirtyAndSync(World world, BlockPos pos, BlockState state){
        if (world instanceof ServerWorld)
            ((ServerWorld)world).getChunkManager().markForUpdate(pos);
        markDirty(world, pos, state);
    }

    private void failedSimulationAndCalculation(BlockState state) {
        isCalculating = false;
        isSimulating = false;
        progress = 0;
        markDirty(world, pos, state);
    }

    public boolean hasRecipe() {
        boolean hasInput = IsChipAvailable && IsLightBulbAvailable && IsCrystalAvailable;

        return hasInput;
    }



    private void resetProgress(BlockState state) {
        progress = 0;
        markDirty(world, pos, state);
    }

    private boolean hasCalculationFinished(){
        return (progress >= maxProgress);
    }

    private boolean isCalculating(){
        return (progress != 0);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ConceptSimulatorScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }
}

/*
{FallFlying: 0b, abilities: {mayfly: 1b, flying: 0b}, Tags: ["inlist"]}
*/