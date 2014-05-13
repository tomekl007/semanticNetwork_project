package com.semantic.vcards.creator.resources;

import com.semantic.vcards.creator.json.JsonObjectMapper;
import com.semantic.vcards.creator.model.VCard;
import org.apache.commons.io.FileUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;

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
        return getJsonFromFile(fileName);
    }

    private String getJsonFromFile(String fileName) {
        try {
            File jsonFile;
            jsonFile = new File(getClass().getResource(fileName).toURI());
            return FileUtils.readFileToString(jsonFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getGraphData(String json){
        VCard vCard = JsonObjectMapper.jsonToVcard(json);
        System.out.println(" --->" + vCard);
        String jsonFile =  getJsonFromFile("/genericRdf.json");
        return fillPlaceholders(jsonFile, vCard);
    }

    private String fillPlaceholders(String json, VCard vCard) {
        return json.replace("#nickname", vCard.getNickName())
                .replace("#fullname", vCard.getFullName())
                .replace("#family", vCard.getFamilyName())
                .replace("#given", vCard.getGivenName());
    }
}
