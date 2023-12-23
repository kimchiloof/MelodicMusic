package com.kimchiloof.melodic_music.config;

import com.kimchiloof.melodic_music.MelodicMusic;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = MelodicMusic.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MelodicMusicConfig
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue REPLACE_DEFAULT_SOUNDS = BUILDER
            .comment("Whether to force replace the default sounds with user-defined sounds.")
            .define("replaceDefaultSounds", true);

    private static final ForgeConfigSpec.IntValue FADE_DURATION = BUILDER
            .comment("The time in seconds it takes for a new music track to fade in/out.")
            .defineInRange("fadeDuration", 20, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue BUFFER_DURATION = BUILDER
            .comment("The max time in seconds remaining of the previous track in which Melodic Music" +
                    " will wait for the track to finish before playing a new one (if called for).")
            .defineInRange("bufferDuration", 10, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue SILENCE_DURATION = BUILDER
            .comment("The average time in seconds of silence between tracks.")
            .defineInRange("silenceDuration", 90, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue SILENCE_VARIABILITY = BUILDER
            .comment("The maximum time in seconds of variability in silence duration.")
            .defineInRange("silenceVariability", 30, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> SOUND_CUES_LIST = BUILDER
            .comment("""
                    A list of all sound cues and their respective audio tracks.
                    
                    Goes from lowest to highest priority.
                    In the format of:
                    {filter}[{val}]=sound1,sound2,...
                     """)
            .defineList("soundCues", List.of(
                    "time[day]=",
                    "time[night]=",
                    "weather[rain]=",
                    "elevation[100->MAX]=",
                    "elevation[0->50]=",
                    "elevation[MIN->50]=",
                    "biome[#minecraft:is_ocean]=",
                    "biome[minecraft:savanna]=",
                    "structure[minecraft:village]time[day]=",
                    "structure[minecraft:village]time[night]="
            ), o -> o instanceof String);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean replaceDefaultSounds;
    public static int fadeDuration;
    public static int bufferDuration;
    public static int silenceDuration;
    public static int silenceVariability;
    public static List<String> soundCues;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        replaceDefaultSounds = REPLACE_DEFAULT_SOUNDS.get();
        fadeDuration = FADE_DURATION.get();
        bufferDuration = BUFFER_DURATION.get();
        silenceDuration = SILENCE_DURATION.get();
        silenceVariability = SILENCE_VARIABILITY.get();
        soundCues = new ArrayList<>(SOUND_CUES_LIST.get());
    }
}
