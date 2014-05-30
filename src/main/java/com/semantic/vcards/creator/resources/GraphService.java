package com.semantic.vcards.creator.resources;

import com.semantic.vcards.creator.helpers.FileHelper;
import com.semantic.vcards.creator.json.JsonObjectMapper;
import com.semantic.vcards.creator.model.VCard;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Tomasz Lelek
 * @since 2014-05-13
 */
@Path("/graphData")
public class GraphService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getGraphData(){
        String fileName = "/exampleGraphData.json";
        return FileHelper.getContentFromFile(fileName, this.getClass());
    }


    /**
     * it consumes json, pars it to vCard object,
     * and prepare data structure, which will be used
     * in graph generating at client side
     * @param json
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getGraphData(String json){
        VCard vCard = JsonObjectMapper.jsonToVcard(json);
        System.out.println(" --->" + vCard);
        String jsonFile =  FileHelper.getContentFromFile("/genericRdf.json", this.getClass());
        return fillPlaceholders(jsonFile, vCard);
    }

    private String fillPlaceholders(String json, VCard vCard) {
        return json.replace("#nickname", vCard.getNickName())
                .replace("#fullname", vCard.getFullName())
                .replace("#family", vCard.getFamilyName())
                .replace("#given", vCard.getGivenName());
    }
}
