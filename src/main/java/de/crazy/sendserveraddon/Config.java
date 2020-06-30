package de.crazy.sendserveraddon;

import com.google.gson.*;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;

public class Config {

    private String configFileName;
    private File configFile;

    private JsonObject config = null;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final JsonParser JSON_PARSER = new JsonParser();

    public Config(String name) {
        this.configFileName = name + ".json";
        this.configFile = new File(Paths.get(Minecraft.getMinecraft().mcDataDir.toString(), "LabyMod",
                "addons-config-global").toString(), this.configFileName);
        File directoryFile = this.configFile.getParentFile();
        if (!directoryFile.exists())
            directoryFile.mkdirs();

        this.loadConfig();
    }

    private void loadConfig() {
        JsonObject resourceConfig = this.getFromResource();
        try {
            this.config = (JsonObject) JSON_PARSER.parse(new BufferedReader(new InputStreamReader
                    (new FileInputStream(this.configFile))));
        } catch (FileNotFoundException | JsonParseException | ClassCastException exception) {
            this.config = new JsonObject();
        }
        this.config = this.compareJsonObjects(this.config, resourceConfig);
        this.save();
    }

    public void save() {
        this.writeFile(this.config);
    }

    public JsonObject getConfigAsJsonObject() {
        return this.config;
    }

    private JsonObject compareJsonObjects(JsonObject in, JsonObject from) {
        HashSet<String> keySet = new HashSet<>();
        in.entrySet().forEach(entry -> keySet.add(entry.getKey()));
        for (Map.Entry<String, JsonElement> entry: from.entrySet()) {
            if (!keySet.contains(entry.getKey()))
                in.add(entry.getKey(), entry.getValue());
        }
        return in;
    }

    private void writeFile(JsonObject jsonObject) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.configFile));
            writer.write(GSON.toJson(jsonObject));
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private JsonObject getFromResource() {
        InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream(this.configFileName);
        JsonObject result = new JsonObject();
        try {
            if (resourceStream != null) {
                result = (JsonObject) JSON_PARSER.parse(new BufferedReader(new InputStreamReader(resourceStream)));
                resourceStream.close();
            }
        } catch (JsonParseException | ClassCastException | IOException ignored) {}
        if (!this.configFile.exists())
            this.writeFile(result);
        return result;
    }

}
