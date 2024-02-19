package net.xiang990293.cfj.item.tool;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.item.v1.CustomDamageHandler;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;

import java.util.function.Consumer;

public class UnbreakableTools {
    public static final ToolItem UnbreakableSword = new UnbreakableSwordItem(CfjUnbreakableToolMaterial.INSTANCE,10000, 5.0f, new FabricItemSettings()
            .fireproof()
            .maxDamage(-1)
    );

    public static final ToolItem UnbreakablePickaxe = new PickaxeItem(CfjUnbreakableToolMaterial.INSTANCE,5000, 5.0f, new FabricItemSettings()
            .fireproof()
            .maxDamage(-1)
    );

    public static final ToolItem UnbreakableAxe = new AxeItem(CfjUnbreakableToolMaterial.INSTANCE,15000, 5.0f, new FabricItemSettings()
            .fireproof()
            .maxDamage(-1)
    );

    public static final ToolItem UnbreakableShovel = new ShovelItem(CfjUnbreakableToolMaterial.INSTANCE,5000, 5.0f, new FabricItemSettings()
            .fireproof()
            .maxDamage(-1)
    );

    public static final ToolItem UnbreakableHoe = new HoeItem(CfjUnbreakableToolMaterial.INSTANCE,8000, 5.0f, new FabricItemSettings()
            .fireproof()
            .maxDamage(-1)
    );
}
