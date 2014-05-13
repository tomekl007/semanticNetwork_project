package com.semantic.vcards.creator.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author Tomasz Lelek
 * @since 2014-05-12
 */
@Path("/index.htm")
public class StaticService {
    @GET
    public String getIndex(){
        return this.getClass().getResource("assets/index.htm").toString();
    }
}
