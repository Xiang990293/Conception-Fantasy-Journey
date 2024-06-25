package net.xiang990293.cfj.network.wrapperLookup;

import com.mojang.serialization.DynamicOps;
import net.minecraft.registry.*;

import java.util.Optional;
import java.util.stream.Stream;

public class ConceptSimulatorWrapperLookup implements RegistryWrapper.WrapperLookup {
    @Override
    public Stream<RegistryKey<? extends Registry<?>>> streamAllRegistryKeys() {
        return null;
    }

    @Override
    public <T> Optional<RegistryWrapper.Impl<T>> getOptionalWrapper(RegistryKey<? extends Registry<? extends T>> registryRef) {
        return Optional.empty();
    }

    @Override
    public <T> RegistryWrapper.Impl<T> getWrapperOrThrow(RegistryKey<? extends Registry<? extends T>> registryRef) {
        return RegistryWrapper.WrapperLookup.super.getWrapperOrThrow(registryRef);
    }

    @Override
    public <V> RegistryOps<V> getOps(DynamicOps<V> delegate) {
        return RegistryWrapper.WrapperLookup.super.getOps(delegate);
    }

    @Override
    public RegistryEntryLookup.RegistryLookup createRegistryLookup() {
        return RegistryWrapper.WrapperLookup.super.createRegistryLookup();
    }
}
