package net.xiang990293.cfj.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.BooleanProperty;

public class ChargeableBlock extends Block {
    public static final BooleanProperty CHARGED = BooleanProperty.of("charged");
    public static final ChargeableBlock CHARGEABLE_BLOCK = new ChargeableBlock(FabricBlockSettings.copyOf(Blocks.STONE));

    public ChargeableBlock(Settings settings) {
        super(settings);
    }
}
