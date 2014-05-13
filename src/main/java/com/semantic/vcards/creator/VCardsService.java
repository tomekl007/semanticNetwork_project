package com.semantic.vcards.creator;

import com.semantic.vcards.creator.filter.CorsHeadersFilter;
import com.semantic.vcards.creator.resources.GraphService;
import com.semantic.vcards.creator.resources.VCardManager;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

/**
 * @author Tomasz Lelek
 * @since 2014-05-11
 */
public class VCardsService extends Service<VCardsConfiguration>{


        public static void main(String[] args) throws Exception {
            new VCardsService().run(new String[]{"server"});
        }


    @Override
    public void initialize(Bootstrap<VCardsConfiguration> bootstrap) {
        bootstrap.setName("vcardConfig");
        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.htm"));
    }

    @Override
    public void run(VCardsConfiguration configuration, Environment environment) throws Exception {

        environment.addFilter(new CorsHeadersFilter(), "/*");
        environment.addResource(new VCardManager());
        environment.addResource(new GraphService());
        //environment.addResource(new StaticService());

    }
}


