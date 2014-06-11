package com.techmale.vertx;

import com.techmale.oss.js.MJsonWrapper;
import mjson.Json;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.json.JsonObject;

import java.util.Iterator;

/**
 * Created by rillingworth on 10/06/14.
 */
public class BootstrapVerticle extends VerticleBase {

    /**
     * Define config based constants here
     */
    String exampleString;
    MJsonWrapper config;

    public void init(){
        JsonObject configJsonObject = getConfig("/config_base.json");
        config = new MJsonWrapper(configJsonObject.toString());
        exampleString = config.get("exampleString").asString();
    }

    /**
     * Vertx required start function
     */
    public void start(){
        init();
        loadConfiguredModules();
        container.logger().info("started Bootstrap - " + exampleString);
    }

    public void loadConfiguredModules(){
        Iterator<Json> it = config.get("modules").asJsonList().iterator();
        while(it.hasNext()){
            // get modules specs
            MJsonWrapper moduleSpecs = new MJsonWrapper(it.next());
            final String name = moduleSpecs.get("fullname").asString();
            int instances = moduleSpecs.get("instances").asInteger();
            String moduleConfStr = moduleSpecs.get("config").toString();
            JsonObject moduleConf = new JsonObject(moduleConfStr);

            container.logger().info("###### DEPLOYING #########");
            container.logger().info(String.format("Name:      %s",name));
            container.logger().info(String.format("instances: %s",instances));
            container.logger().info(String.format("config:    %s",moduleConfStr));

            container.deployModule(name,moduleConf,instances,new Handler<AsyncResult<String>>() {
                @Override
                public void handle(AsyncResult<String> stringAsyncResult) {
                    if(stringAsyncResult.succeeded()){
                        container.logger().info("Module ["+name+"] deployment SUCCESS");
                    }else{
                        container.logger().error("Module [" + name + "] deployment FAILED");
                    }
                }
            });
        }
    }

    /**
     * CRUD operations on Modules
     * Deploy, Undeploy, List modules,
     */
    class CrudAPI {
        public void deploy(){}
        public void undeploy(){}
        public void update(){}


    }


}
