package dev.higurashi.trial_monolith_spellbooks.setup;

import dev.higurashi.trial_monolith_spellbooks.common.entity.SummonedTrialMonolith;
import dev.higurashi.trial_monolith_spellbooks.registry.TMSEntityRegistry;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonSetup {
    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(TMSEntityRegistry.SUMMONED_TRIAL_MONOLITH.get(), SummonedTrialMonolith.createAttributes());
    }
}
