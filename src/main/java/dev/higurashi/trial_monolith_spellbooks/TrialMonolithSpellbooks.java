package dev.higurashi.eeeab_spellbooks;

import com.mojang.logging.LogUtils;
import dev.higurashi.eeeab_spellbooks.registry.TMSSpellRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(TrialMonolithSpellbooks.MOD_ID)
public class TrialMonolithSpellbooks {
    public static final String MOD_ID = "trial_monolith_spellbooks";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TrialMonolithSpellbooks() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        TMSSpellRegistry.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }
}
