package net.xiang990293.cfj.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelProvider;
import net.minecraft.item.ArmorItem;
import net.xiang990293.cfj.item.CfjItems;

public class CfjModelProvider extends FabricModelProvider {

    public CfjModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
//        BlockStateModelGenerator.BlockTexturePool
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator){
//        itemModelGenerator.registerArmor((ArmorItem) CfjItems.UnbreakableHelmet);
        itemModelGenerator.registerArmor((ArmorItem) CfjItems.WINGS);
    }
}
