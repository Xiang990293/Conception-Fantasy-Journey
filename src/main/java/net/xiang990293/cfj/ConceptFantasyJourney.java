package net.xiang990293.cfj;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.server.ServerTickCallback;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xiang990293.cfj.block.CfjBlocks;
import net.xiang990293.cfj.block.entity.CfjBlockEntities;
import net.xiang990293.cfj.block.entity.ConceptSimulatorBlockEntity;
import net.xiang990293.cfj.damageType.CfjDamageTypes;
import net.xiang990293.cfj.item.CfjItemGroup;
import net.xiang990293.cfj.item.CfjItems;
import net.xiang990293.cfj.network.CfjNetworkingContants;
import net.xiang990293.cfj.screen.CfjScreenHandlers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConceptFantasyJourney implements ModInitializer {
	public static final String MOD_ID = "concept_fantasy_journey";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final ItemGroup ConceptFantasyJourneyItems = FabricItemGroup.builder()
			.icon(() -> new ItemStack(CfjItems.ValentineChocolate))
			.displayName(Text.translatable("itemGroup.tutorial.test_group"))
			.entries((context, entries) -> {
				entries.add(CfjItems.ValentineChocolate);
			})
			.build();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		CfjItemGroup.registerItemGroups();
		CfjItems.registerCfjItems();
		CfjBlocks.registerCfjBlocks();
		CfjBlockEntities.registerCfjBlockEntities();
		CfjScreenHandlers.registerScreenHandlers();

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (!entity.isInvulnerable() && !player.isSpectator() &&
					player.getMainHandStack().isOf(CfjItems.PureLoveSword)) {
				entity.damage(CfjDamageTypes.of(world, CfjDamageTypes.EMOTIONAL_DAMAGE), 1.0F);

				ItemEntity entityToSpawn = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(CfjItems.ValentineChocolate));
				entityToSpawn.setPickupDelay(10);

				world.spawnEntity(entityToSpawn);
			}

			return ActionResult.PASS;
		});

		EntityElytraEvents.CUSTOM.register((entity, tickElytra) -> {
			PlayerEntity player;
			if (!(entity instanceof PlayerEntity)) {
				return false;
			}
			player = (PlayerEntity) entity;

			NbtCompound nbt = new NbtCompound();
			player.writeNbt(nbt);

			if (player.isCreative() || player.isSpectator() || !player.getEquippedStack(EquipmentSlot.CHEST).isOf(CfjItems.WINGS)){
				return false;
			}

			if (player.isOnGround() &&
					player.getEquippedStack(EquipmentSlot.CHEST).isOf(CfjItems.WINGS)) {
				NbtCompound nbtCompound = new NbtCompound();
				nbtCompound.putBoolean("flying", true);
				nbtCompound.putBoolean("mayfly", true);
				nbt.put("abilities", nbtCompound);
				nbt.putBoolean("invulnerable", true);
				player.readNbt(nbt);
			}else{
				nbt = new NbtCompound();
				NbtCompound nbtCompound = new NbtCompound();
				player.writeNbt(nbt);
				nbtCompound.putBoolean("flying", false);
				nbtCompound.putBoolean("mayfly", false);
				nbt.put("abilities", nbtCompound);
				nbt.putBoolean("invulnerable", false);

            }

			return false;
		});

		ServerTickCallback.EVENT.register((server) -> {
			for (ServerPlayerEntity player : PlayerLookup.all(server)) {
				NbtCompound nbt = new NbtCompound();
				player.writeNbt(nbt);
				if (player.isCreative() || player.isSpectator()) {
					return;
				}else if (player.getEquippedStack(EquipmentSlot.CHEST).isOf(CfjItems.WINGS) && !player.isOnGround() && !nbt.getCompound("abilities").getBoolean("flying")) {
					NbtCompound nbtCompound = new NbtCompound();
					nbtCompound.putBoolean("flying", true);
					nbtCompound.putBoolean("mayfly", true);
					nbt.put("abilities", nbtCompound);
					nbt.putBoolean("invulnerable", true);
					player.readNbt(nbt);

					PacketByteBuf buf = PacketByteBufs.create();
					buf.writeBoolean(true); //flying
					buf.writeBoolean(true); //mayfly
					buf.writeBoolean(true); //Invulnerable
					ServerPlayNetworking.send(player, CfjNetworkingContants.Wing_Fly_Sync_ID, buf);
				}else if (!player.getEquippedStack(EquipmentSlot.CHEST).isOf(CfjItems.WINGS) && nbt.getCompound("abilities").getBoolean("flying")){
					NbtCompound nbtCompound = new NbtCompound();
					nbtCompound.putBoolean("flying", false);
					nbtCompound.putBoolean("mayfly", false);
					nbt.put("abilities", nbtCompound);
					nbt.putBoolean("invulnerable", false);
					player.readNbt(nbt);

					PacketByteBuf buf = PacketByteBufs.create();
					buf.writeBoolean(false); //flying
					buf.writeBoolean(false); //mayfly
					buf.writeBoolean(false); //Invulnerable
					ServerPlayNetworking.send(player, CfjNetworkingContants.Wing_Fly_Sync_ID, buf);
				}
			}
		});

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

		ClientPlayNetworking.registerGlobalReceiver(CfjNetworkingContants.Wing_Fly_Sync_ID, (client, handler, buf, responseSender) -> {
			Boolean flying = buf.readBoolean();
			Boolean mayfly = buf.readBoolean();
			Boolean	invulnerable = buf.readBoolean();
			client.execute(() -> {
				PlayerEntity player = client.player;
				NbtCompound nbt = new NbtCompound();
				NbtCompound nbtCompound = new NbtCompound();
				player.writeNbt(nbt);
				nbtCompound.putBoolean("flying", flying);
				nbtCompound.putBoolean("mayfly", mayfly);
				nbt.put("abilities", nbtCompound);
				nbt.putBoolean("invulnerable", invulnerable);
				player.readNbt(nbt);
			});
		});
	}
}