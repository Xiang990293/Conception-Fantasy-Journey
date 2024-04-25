package net.xiang990293.cfj;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.xiang990293.cfj.datagen.*;

public class ConceptFantasyJourneyDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

//        pack.addProvider(ModBlockTagProvider::new);
//        pack.addProvider(ModItemTagProvider::new);
//        pack.addProvider(ModLootTableProvider::new);
        pack.addProvider(CfjModelProvider::new);
//        pack.addProvider(ModRecipeProvider::new);
//        pack.addProvider(ModPoiTagProvider::new);
    }
}
