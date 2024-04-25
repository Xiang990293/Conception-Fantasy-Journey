package net.xiang990293.cfj.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.xiang990293.cfj.ConceptFantasyJourney;
import net.xiang990293.cfj.block.entity.ConceptSimulatorBlockEntity;
import net.xiang990293.cfj.item.wing.WingItem;
import net.xiang990293.cfj.util.FlyingData;
import net.xiang990293.cfj.util.IEntityDataSaver;

public class CfjNetworkingContants {
    public static final Identifier Concept_Simulator_Sync_ID = new Identifier(ConceptFantasyJourney.MOD_ID, "concept_simulator_sync");
    public static final Identifier Concept_Simulator_Finish_Calculating_ID = new Identifier(ConceptFantasyJourney.MOD_ID, "concept_simulator_finish_calculating");
    public static final Identifier Wing_Fly_Sync_C2S_ID = new Identifier(ConceptFantasyJourney.MOD_ID, "wing_fly_sync_c2s");
    public static final Identifier Wing_Fly_Sync_S2C_ID = new Identifier(ConceptFantasyJourney.MOD_ID, "wing_fly_sync_s2c");
    public static final Identifier Custom_Wing_Fly_Sync_ID = new Identifier(ConceptFantasyJourney.MOD_ID, "custom_wing_fly_sync");
    public static final Identifier Wing_Fly_High_ID = new Identifier(ConceptFantasyJourney.MOD_ID, "wing_fly_high");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(CfjNetworkingContants.Concept_Simulator_Sync_ID, (server, player, handler, buf, responseSender) -> {
            Boolean isCalculating = buf.readBoolean();
            Boolean isSimulating = buf.readBoolean();
            BlockPos pos = buf.readBlockPos();
            server.execute(() -> {
                World world = player.getEntityWorld();
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof ConceptSimulatorBlockEntity) {
                    ((ConceptSimulatorBlockEntity) blockEntity).isCalculating = isCalculating;
                    ((ConceptSimulatorBlockEntity) blockEntity).isSimulating = isSimulating;
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(CfjNetworkingContants.Wing_Fly_High_ID, (server, player, handler, buf, responseSender) -> {
            float flySpeed = buf.readFloat();
            server.execute(() -> {
                player.addVelocity(new Vec3d(0.0f,flySpeed,0.0f));
                player.velocityModified = true;
            });
        });

        // sync from client to server the NBT state
        ServerPlayNetworking.registerGlobalReceiver(CfjNetworkingContants.Wing_Fly_Sync_C2S_ID, (server, player, handler, buf, responseSender) -> {
            Boolean flying = buf.readBoolean();
            Boolean mayfly = buf.readBoolean();
            Boolean elytraFly = buf.readBoolean();
            Boolean creativeFlyed = buf.readBoolean();
            server.execute(() -> {
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
        });
    }

    public static void registerS2CPackets() {
        // sync to client when calculating done
        ClientPlayNetworking.registerGlobalReceiver(CfjNetworkingContants.Concept_Simulator_Finish_Calculating_ID, (client, handler, buf, responseSender) -> {
            Boolean isCalculated = buf.readBoolean();
            BlockPos pos = buf.readBlockPos();
            client.execute(() -> {
                World world = client.world;
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof ConceptSimulatorBlockEntity) {
                    ((ConceptSimulatorBlockEntity) blockEntity).isCalculated = isCalculated;
                }
            });
        });

        // sync from server to client the NBT state abd let player flying or mayfly
        ClientPlayNetworking.registerGlobalReceiver(CfjNetworkingContants.Wing_Fly_Sync_S2C_ID, (client, handler, buf, responseSender) -> {
            Boolean flying = buf.readBoolean();
            Boolean mayfly = buf.readBoolean();
            client.execute(() -> {
                PlayerEntity player = client.player;
                NbtCompound nbt = new NbtCompound();
                NbtCompound abilities = new NbtCompound();
                player.writeNbt(nbt);
                abilities.putBoolean("flying", flying);
                abilities.putBoolean("mayfly", mayfly);
                nbt.put("abilities", abilities);
                player.readNbt(nbt);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(CfjNetworkingContants.Custom_Wing_Fly_Sync_ID, (client, handler, buf, responseSender) -> {
            Boolean elytraFly = buf.readBoolean();
            Boolean creativeFlyed = buf.readBoolean();
            client.execute(() -> {
                ((IEntityDataSaver) client.player).getPersistentData().putBoolean("elytraFly", elytraFly);
                ((IEntityDataSaver) client.player).getPersistentData().putBoolean("creativeFlyed", creativeFlyed);
            });
        });
    }
}
