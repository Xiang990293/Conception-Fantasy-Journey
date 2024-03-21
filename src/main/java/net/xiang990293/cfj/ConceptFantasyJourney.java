package net.xiang990293.cfj;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
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

		ServerPlayNetworking.registerGlobalReceiver(CfjNetworkingContants.Concept_Simulator_Start_Calculating_ID, (server, player, handler, buf, responseSender) -> {
			Boolean isCalculating = buf.readBoolean();
			BlockPos pos = buf.readBlockPos();
			server.execute(() -> {
				World world = player.getEntityWorld();
				BlockEntity blockEntity = world.getBlockEntity(pos);
				if (blockEntity instanceof ConceptSimulatorBlockEntity) {
					((ConceptSimulatorBlockEntity) blockEntity).isCalculating = isCalculating;
				}
			});
		});

		ServerPlayNetworking.registerGlobalReceiver(CfjNetworkingContants.Concept_Simulator_Switch_Simulating_ID, (server, player, handler, buf, responseSender) -> {
			Boolean isSimulating = buf.readBoolean();
			BlockPos pos = buf.readBlockPos();
			server.execute(() -> {
				World world = player.getEntityWorld();
				BlockEntity blockEntity = world.getBlockEntity(pos);
				if (blockEntity instanceof ConceptSimulatorBlockEntity) {
					((ConceptSimulatorBlockEntity) blockEntity).isSimulating = isSimulating;
				}
			});
		});
	}
}