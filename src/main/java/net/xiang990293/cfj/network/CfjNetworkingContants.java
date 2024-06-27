package net.xiang990293.cfj.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.xiang990293.cfj.ConceptFantasyJourney;
import net.xiang990293.cfj.block.entity.ConceptSimulatorBlockEntity;
import net.xiang990293.cfj.item.wing.WingItem;
import net.xiang990293.cfj.network.payload.*;
import net.xiang990293.cfj.util.FlyingData;
import net.xiang990293.cfj.util.IEntityDataSaver;

public class CfjNetworkingContants {
//    public static final Identifier Concept_Simulator_Sync_ID = Identifier.of(ConceptFantasyJourney.MOD_ID, "concept_simulator_sync");
//    public static final Identifier Concept_Simulator_Finish_Calculating_ID = Identifier.of(ConceptFantasyJourney.MOD_ID, "concept_simulator_finish_calculating");
//    public static final Identifier Wing_Fly_Sync_C2S_ID = Identifier.of(ConceptFantasyJourney.MOD_ID, "wing_fly_sync_c2s");
//    public static final Identifier Wing_Fly_Sync_S2C_ID = Identifier.of(ConceptFantasyJourney.MOD_ID, "wing_fly_sync_s2c");
//    public static final Identifier Custom_Wing_Fly_Sync_ID = Identifier.of(ConceptFantasyJourney.MOD_ID, "custom_wing_fly_sync");
//    public static final Identifier Wing_Fly_High_ID = Identifier.of(ConceptFantasyJourney.MOD_ID, "wing_fly_high");

    public static void registerC2SPackets() {
        PayloadTypeRegistry.playC2S().register(ConceptSimulatorSyncPayload.ID, ConceptSimulatorSyncPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ConceptSimulatorSyncPayload.ID, (payload, context) -> {
            World world = context.player().getEntityWorld();
            boolean isCalculating = payload.isCalculating();
            boolean isSimulating = payload.isSimulating();
            BlockEntity blockEntity = world.getBlockEntity(payload.blockPos());
            if (blockEntity instanceof ConceptSimulatorBlockEntity) {
                ((ConceptSimulatorBlockEntity) blockEntity).isCalculating = isCalculating;
                ((ConceptSimulatorBlockEntity) blockEntity).isSimulating = isSimulating;
            }
//            context.player().execute(() -> {
//            });
        });

        PayloadTypeRegistry.playC2S().register(WingFlyHighPayload.ID, WingFlyHighPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(WingFlyHighPayload.ID, (payload, context) -> {
            float flySpeed = payload.rate();
            ServerPlayerEntity player = context.player();
            player.addVelocity(new Vec3d(0.0f,flySpeed,0.0f));
            player.velocityModified = true;
        });

        // sync from client to server the NBT state
        PayloadTypeRegistry.playC2S().register(WingFlySyncC2SPayload.ID, WingFlySyncC2SPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(WingFlySyncC2SPayload.ID, (payload, context) -> {
            boolean flying = payload.flying();
            boolean mayfly = payload.mayfly();
            boolean elytraFly = payload.elytraFly();
            boolean creativeFlyed = payload.creativeFlyed();
            ServerPlayerEntity player = context.player();
            if (!(player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof WingItem)) {
                return;
            }
            NbtCompound nbt = new NbtCompound();
            NbtCompound abilities = new NbtCompound();
            player.writeNbt(nbt);
            abilities.putBoolean("flying", flying);
            abilities.putBoolean("mayfly", mayfly);
            nbt.put("abilities", abilities);
            player.readNbt(nbt);
            FlyingData.changeData((IEntityDataSaver) player, elytraFly, creativeFlyed);
        });
    }

    public static void registerS2CPackets() {
        // sync to client when calculating done
        PayloadTypeRegistry.playS2C().register(ConceptSimulatorFinishCalculatingPayload.ID, ConceptSimulatorFinishCalculatingPayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(ConceptSimulatorFinishCalculatingPayload.ID, (payload, context) -> {
            boolean isCalculated = payload.isCalculated();
            BlockPos pos = payload.blockPos();

            World world = context.client().world;
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ConceptSimulatorBlockEntity) {
                ((ConceptSimulatorBlockEntity) blockEntity).isCalculated = isCalculated;
            }
        });

        // sync from server to client the NBT state abd let player flying or mayfly
        PayloadTypeRegistry.playS2C().register(WingFlySyncS2CPayload.ID, WingFlySyncS2CPayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(WingFlySyncS2CPayload.ID, (payload, context) -> {
            boolean flying = payload.flying();
            boolean mayfly = payload.mayfly();
            PlayerEntity player = context.client().player;
            NbtCompound nbt = new NbtCompound();
            NbtCompound abilities = new NbtCompound();
            player.writeNbt(nbt);
            abilities.putBoolean("flying", flying);
            abilities.putBoolean("mayfly", mayfly);
            nbt.put("abilities", abilities);
            player.readNbt(nbt);
        });

        PayloadTypeRegistry.playS2C().register(CustomWingFlySyncPayload.ID, CustomWingFlySyncPayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(CustomWingFlySyncPayload.ID, (payload, context) -> {
            boolean elytraFly = payload.elytraFly();
            boolean creativeFlyed = payload.creativeFlyed();
            PlayerEntity player = context.client().player;
            ((IEntityDataSaver) player).getPersistentData().putBoolean("elytraFly", elytraFly);
            ((IEntityDataSaver) player).getPersistentData().putBoolean("creativeFlyed", creativeFlyed);
        });
    }
}
