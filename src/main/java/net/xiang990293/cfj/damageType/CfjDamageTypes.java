package net.xiang990293.cfj.damageType;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import net.minecraft.util.Identifier;
import net.xiang990293.cfj.ConceptFantasyJourney;

public class CfjDamageTypes {
    public static final RegistryKey<DamageType> EMOTIONAL_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(ConceptFantasyJourney.MOD_ID, "emotional_damage_type"));

    public static DamageSource of(World world, RegistryKey<DamageType> key){
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
    }
}
