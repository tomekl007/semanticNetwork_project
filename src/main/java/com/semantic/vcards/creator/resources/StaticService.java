package com.semantic.vcards.creator.resources;

import com.semantic.vcards.creator.helpers.FileHelper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author Tomasz Lelek
 * @since 2014-05-12
 */

@Path("/")
public class StaticService {
    @Path("index")
    @GET
    public String getIndex(){
        return FileHelper.getContentFromFile("/index.htm", this.getClass());
    }

    @Path("main.js")
    @GET
    public String getMainJs(){
        return FileHelper.getContentFromFile("/main.js", this.getClass());
    }

    @Path("sigma.min.js")
    @GET
    public String getSigmaMainJs(){
        return FileHelper.getContentFromFile("/sigma.min.js", this.getClass());
    }

    @Path("style.css")
    @GET
    public String getStyleCss(){
        return FileHelper.getContentFromFile("/style.css", this.getClass());
    }

    @Path("demo2.js")
    @GET
    public String getDemo2Js(){
        return FileHelper.getContentFromFile("/demo2.js", this.getClass());
    }

    @Path("js.js")
    @GET
    public String getJsJS(){
        return FileHelper.getContentFromFile("/js.js", this.getClass());
    }
}
