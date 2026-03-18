package dev.higurashi.trial_monolith_spellbooks.setup;

import dev.higurashi.trial_monolith_spellbooks.registry.TMSEntityRegistry;
import io.github.kosianodangoo.trialmonolith.client.entity.TrialMonolithRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // === SUMMONED ===
        event.registerEntityRenderer(TMSEntityRegistry.SUMMONED_TRIAL_MONOLITH.get(), TrialMonolithRenderer::new);
    }
}
