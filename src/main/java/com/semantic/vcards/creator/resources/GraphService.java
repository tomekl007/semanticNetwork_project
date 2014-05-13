package com.semantic.vcards.creator.resources;

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
        return "{\"list\":[{\"environment\":\"staging\",\"serviceName\":\"csvreports-dataservice\",\"status\":\"ok\",\"" +
                "dependecies\":[\"configuration-service\"]},{\"environment\":\"staging\",\"serviceName\":\"csvreports-generator\"," +
                "\"status\":\"ok\",\"dependecies\":[\"csvreports-dataservice\",\"configuration-service\"]}," +
                "{\"environment\":\"staging\",\"serviceName\":\"configuration-service\",\"status\":\"ok\",\"dependecies\":[]}]} ";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getGraphData(String json){
        VCard vCard = JsonObjectMapper.jsonToVcard(json);
        return "empty";
    }
}
