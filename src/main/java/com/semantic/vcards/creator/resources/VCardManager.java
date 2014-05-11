package com.semantic.vcards.creator.resources;

import com.semantic.vcards.creator.model.VCard;
import com.semantic.vcards.creator.rdf.parser.VCardParser;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author Tomasz Lelek
 * @since 2014-05-11
 */
@Path("/vcard")
public class VCardManager {
    @POST
    //@Consumes(MediaType.APPLICATION_JSON)
    public String createNewVcard(@FormParam("fullname") String fullName, @FormParam("nickname") String nickname) {//todo it should be json
        System.out.println("receive vCard" + fullName);
        VCard vCard = new VCard();
        vCard.setFullName(fullName);
        vCard.setNickName(nickname);

        return VCardParser.parse(vCard);
    }
}
