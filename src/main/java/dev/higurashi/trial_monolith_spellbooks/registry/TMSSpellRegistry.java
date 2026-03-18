package dev.higurashi.trial_monolith_spellbooks.registry;

import dev.higurashi.trial_monolith_spellbooks.TrialMonolithSpellbooks;
import dev.higurashi.trial_monolith_spellbooks.common.spell.eldritch.ManyManyMonolithLaser;
import dev.higurashi.trial_monolith_spellbooks.common.spell.eldritch.MonolithLaserSpell;
import dev.higurashi.trial_monolith_spellbooks.common.spell.eldritch.TargetMonolithLaser;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TMSSpellRegistry {
    public static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(SpellRegistry.SPELL_REGISTRY_KEY, TrialMonolithSpellbooks.MOD_ID);
    private static RegistryObject<AbstractSpell> registerSpell(AbstractSpell spell) { return SPELLS.register(spell.getSpellName(), () -> spell); };
    public static void register(IEventBus bus) { SPELLS.register(bus); }

    public static final RegistryObject<AbstractSpell> MONOLITH_LASER_SPELL = registerSpell(new MonolithLaserSpell());
    public static final RegistryObject<AbstractSpell> MANY_MONOLITH_LASER_SPELL = registerSpell(new ManyManyMonolithLaser());
    public static final RegistryObject<AbstractSpell> TARGET_MONOLITH_LASER = registerSpell(new TargetMonolithLaser());
}
