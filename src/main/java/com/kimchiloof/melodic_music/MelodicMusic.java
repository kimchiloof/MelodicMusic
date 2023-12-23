package com.kimchiloof.melodic_music;

import com.kimchiloof.melodic_music.config.MelodicMusicConfig;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(MelodicMusic.MODID)
public class MelodicMusic
{
    public static final String MODID = "melodic_music";
    public static final Logger LOGGER = LogUtils.getLogger();


    public static final String[] SOUND_CUES = {
            "day",
            "night",
            "rain",

            "mountain",
            "underground",
            "underground_deep",
            "underwater",
            "village_day",
            "village_night",

            "nether",
            "end",

            "combat"
    };

    public MelodicMusic()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MelodicMusicSounds.register(modEventBus);

        modEventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(
                ModConfig.Type.CLIENT,
                MelodicMusicConfig.SPEC,
                "melodic_music/melodic_music-properties.toml"
        );
    }

    private void clientSetup(final FMLClientSetupEvent event)
    {
        MelodicMusicLoader.load();
    }
}
