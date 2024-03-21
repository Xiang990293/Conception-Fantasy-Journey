package net.xiang990293.cfj.block.entity;

import com.mojang.datafixers.types.Type;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.xiang990293.cfj.ConceptFantasyJourney;
import net.xiang990293.cfj.block.CfjBlocks;

import java.util.Set;

public class CfjBlockEntities {
    public static final BlockEntityType<ConceptSimulatorBlockEntity> CONCEPT_SIMULATOR_BLOCK_ENTITY = registerBlockEntities("concept_simulator_block_entity",  FabricBlockEntityTypeBuilder.create(ConceptSimulatorBlockEntity::new, CfjBlocks.ConceptSimulatorBlock).build());


    private static BlockEntityType<ConceptSimulatorBlockEntity> registerBlockEntities(String name, BlockEntityType blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(ConceptFantasyJourney.MOD_ID, name), blockEntityType);
    }

    public static void registerCfjBlockEntities() {
        ConceptFantasyJourney.LOGGER.info("Registering Mod Block Entities for " + ConceptFantasyJourney.MOD_ID);
    }
}
