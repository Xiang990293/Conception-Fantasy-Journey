package net.xiang990293.cfj.item.tool;

import net.fabricmc.fabric.impl.recipe.ingredient.builtin.ComponentsIngredient;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.UnbreakableComponent;
import net.minecraft.item.*;

public class PureLoveTools {
    public static final ToolItem PureLoveSword = new PureLoveSwordItem(CfjPureLoveToolMaterial.INSTANCE,0, 5.0f, new Item.Settings()
            .fireproof()
            .component(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(true))
    );

//    public static final ToolItem PureLovePickaxe = new PickaxeItem(CfjPureLoveToolMaterial.INSTANCE,5000, 5.0f, new FabricItemSettings()
//            .fireproof()
//            .component(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(true))
//    );
//
//    public static final ToolItem PureLoveAxe = new AxeItem(CfjPureLoveToolMaterial.INSTANCE,15000, 5.0f, new FabricItemSettings()
//            .fireproof()
//            .component(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(true))
//    );
//
//    public static final ToolItem PureLoveShovel = new ShovelItem(CfjPureLoveToolMaterial.INSTANCE,5000, 5.0f, new FabricItemSettings()
//            .fireproof()
//            .component(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(true))
//    );
//
//    public static final ToolItem PureLoveHoe = new HoeItem(CfjPureLoveToolMaterial.INSTANCE,8000, 5.0f, new FabricItemSettings()
//            .fireproof()
//            .component(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(true))
//    );
}
