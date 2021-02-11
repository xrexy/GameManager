package org.shingetsu.gamemanager.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.shingetsu.gamemanager.GameManager;

import java.io.File;
import java.io.IOException;

public abstract class AbstractYamlFile {
    private final GameManager gameManager = GameManager.getInstance();
    private File file = null;
    private FileConfiguration config = null;

    /**
     * Creates mentioned file.
     * <br><br>
     * <b>[!]</b> If "isResource()" is true
     * file MUST be in folder "resources"
     *
     * @return Same class
     */
    public AbstractYamlFile create() {
        file = new File(gameManager.getDataFolder(), getFileName());
        config = new YamlConfiguration();

        if (!file.exists()) {
            file.getParentFile().mkdirs();

            if (!isResource()) {
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else
                gameManager.saveResource(getFileName(), gameManager.getResource(getFileName()) == null);

            load();
        }
        return this;
    }

    private void load() {
        try {
            config.load(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Saves file lol
     */
    public void save() {
        try {
            config.save(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public abstract String getFileName();

    /**
     * @return Is file a local resource
     */
    public abstract boolean isResource();
}
