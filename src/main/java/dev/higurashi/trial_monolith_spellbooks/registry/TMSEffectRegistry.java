package dev.higurashi.trial_monolith_spellbooks.registry;

import dev.higurashi.trial_monolith_spellbooks.TrialMonolithSpellbooks;
import dev.higurashi.trial_monolith_spellbooks.common.effect.BeamEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TMSEffectRegistry {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS,  TrialMonolithSpellbooks.MOD_ID);
    public static void register(IEventBus bus) { EFFECTS.register(bus); }

    public static final RegistryObject<MobEffect> BEAM = EFFECTS.register("beam", BeamEffect::new);
}
