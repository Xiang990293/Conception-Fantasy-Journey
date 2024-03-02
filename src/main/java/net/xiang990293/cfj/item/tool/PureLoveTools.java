package net.xiang990293.cfj.item.tool;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;

public class PureLoveTools {
    public static final ToolItem PureLoveSword = new PureLoveSwordItem(CfjPureLoveToolMaterial.INSTANCE,0, 5.0f, new FabricItemSettings()
            .fireproof()
            .maxDamage(-1)
    );

//    public static final ToolItem PureLovePickaxe = new PickaxeItem(CfjPureLoveToolMaterial.INSTANCE,5000, 5.0f, new FabricItemSettings()
//            .fireproof()
//            .maxDamage(-1)
//    );
//
//    public static final ToolItem PureLoveAxe = new AxeItem(CfjPureLoveToolMaterial.INSTANCE,15000, 5.0f, new FabricItemSettings()
//            .fireproof()
//            .maxDamage(-1)
//    );
//
//    public static final ToolItem PureLoveShovel = new ShovelItem(CfjPureLoveToolMaterial.INSTANCE,5000, 5.0f, new FabricItemSettings()
//            .fireproof()
//            .maxDamage(-1)
//    );
//
//    public static final ToolItem PureLoveHoe = new HoeItem(CfjPureLoveToolMaterial.INSTANCE,8000, 5.0f, new FabricItemSettings()
//            .fireproof()
//            .maxDamage(-1)
//    );
}
