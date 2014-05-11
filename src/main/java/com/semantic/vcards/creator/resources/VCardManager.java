package com.semantic.vcards.creator.resources;

import com.semantic.vcards.creator.json.JsonObjectMapper;
import com.semantic.vcards.creator.model.VCard;
import com.semantic.vcards.creator.rdf.parser.VCardParser;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * @author Tomasz Lelek
 * @since 2014-05-11
 */
@Path("/vcard")
public class VCardManager {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createNewVcard(String json){
        VCard vCard = JsonObjectMapper.jsonToVcard(json);
        System.out.println("receive vCard" + vCard.getFullName());
        return VCardParser.parse(vCard);
    }
}
