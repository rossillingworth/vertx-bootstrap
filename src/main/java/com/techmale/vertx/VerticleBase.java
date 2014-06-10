package com.techmale.vertx;

import com.techmale.util.FileHelper;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

import java.io.IOException;

public abstract class VerticleBase extends Verticle {


    /**
     * Build config from internal default JSON
     * and external specified JSON
     *
     * @param defaultJSONConfigPath The name of the default config JSON file
     * @return merge of internal & external config
     */
    public JsonObject getConfig(final String defaultJSONConfigPath) {
        String configText = null;

        try {
            configText = FileHelper.readFileAsString(defaultJSONConfigPath);
        } catch (IllegalArgumentException | IOException e) {
            container.logger().error("An error occurred while attempting to read the default config file", e);
            container.exit();
        }

        container.logger().info("Base Config Loaded");
        JsonObject configBase = new JsonObject(configText);

        if (container.config().equals(new JsonObject("{}"))) {
            container.logger().info("No overriding config provided, continuing with default values");
        } else {
            configBase = configBase.mergeIn(container.config());
            container.logger().info("Default config partially or fully overridden");
        }

        return configBase;
    }
}