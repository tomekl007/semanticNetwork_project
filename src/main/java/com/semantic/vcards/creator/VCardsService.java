package com.semantic.vcards.creator;

import com.semantic.vcards.creator.resources.VCardManager;
import com.yammer.dropwizard.Service;
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

    }

    @Override
    public void run(VCardsConfiguration configuration, Environment environment) throws Exception {
        environment.addResource(new VCardManager());

    }
}


