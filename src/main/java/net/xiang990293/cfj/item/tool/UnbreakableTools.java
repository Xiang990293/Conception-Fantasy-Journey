package net.xiang990293.cfj.item.tool;

import net.minecraft.item.*;

import java.util.function.Consumer;

public class UnbreakableTools {
    public static final ToolItem UnbreakableSword = new UnbreakableSwordItem(CfjUnbreakableToolMaterial.INSTANCE,2147483647, 5.0f, new Item.Settings()
            .fireproof()
            .maxDamage(-1)
    );

    public static final ToolItem UnbreakablePickaxe = new UnbreakablePickaxeItem(CfjUnbreakableToolMaterial.INSTANCE,2147483647, 5.0f, new Item.Settings()
            .fireproof()
            .maxDamage(-1)
    );

    public static final ToolItem UnbreakableAxe = new UnbreakableAxeItem(CfjUnbreakableToolMaterial.INSTANCE,2147483647, 5.0f, new Item.Settings()
            .fireproof()
            .maxDamage(-1)
    );

    public static final ToolItem UnbreakableShovel = new UnbreakableShovelItem(CfjUnbreakableToolMaterial.INSTANCE,2147483647, 5.0f, new Item.Settings()
            .fireproof()
            .maxDamage(-1)
    );

    public static final ToolItem UnbreakableHoe = new UnbreakableHoeItem(CfjUnbreakableToolMaterial.INSTANCE,2147483647, 5.0f, new Item.Settings()
            .fireproof()
            .maxDamage(-1)
    );
}
