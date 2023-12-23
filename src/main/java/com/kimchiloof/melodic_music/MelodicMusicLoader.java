package com.kimchiloof.melodic_music;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MelodicMusicLoader {
    public static File mainDir;
    public static File soundDir;
    public static List<String> soundPaths = new ArrayList<>();

    public static void load() {
        mainDir = new File("config/melodic_music");
        if (!mainDir.exists()) {
            boolean madeNewMainDir = mainDir.mkdir();
            if (!madeNewMainDir) MelodicMusic.LOGGER.error("Failed to create Melodic Music main config directory");
        }

        soundDir = new File(mainDir, "sounds");
        if (!soundDir.exists()) {
            boolean madeNewSoundDir = soundDir.mkdir();
            if (!madeNewSoundDir) MelodicMusic.LOGGER.error("Failed to create Melodic Music sound directory");
        }

        checkSounds(soundDir);
    }

    private static void checkSounds(File directory) {
        File[] files = directory.listFiles();

        if (files == null || files.length == 0) {
            MelodicMusic.LOGGER.warn("Empty directory found at " + directory.getAbsolutePath());
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                checkSounds(file);
            } else if (file.getName().endsWith(".ogg")) {
                MelodicMusic.LOGGER.info("Found sound " + file.getName() + " at " + file.getPath());

                if (file.getName().matches(".*[^a-z0-9\\-_.].*"))
                    MelodicMusic.LOGGER.warn("Sound" + file.getName() + " contains invalid characters, may cause issues.");

                soundPaths.add(file.getPath());
            }
        }
    }
}
