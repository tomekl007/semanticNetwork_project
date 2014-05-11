package com.semantic.vcards.creator.rdf.parser;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.vocabulary.VCARD;
import com.semantic.vcards.creator.model.VCard;

import java.io.StringWriter;
import java.io.Writer;

/**
 * @author Tomasz Lelek
 * @since 2014-05-11
 */
public class VCardParser {

    public static String parse(VCard vCard) {
        Model model = ModelFactory.createDefaultModel();

        String personUriPrefix = "http://newperson/";

        model.createResource(personUriPrefix + vCard.getFullName())
                .addProperty(VCARD.FN, vCard.getFullName())
                .addProperty(VCARD.NICKNAME, vCard.getNickName());
        Writer writer = new StringWriter();
        model.write(writer, "RDF/XML-ABBREV");
        return xmlAsCode(writer.toString());
    }

    private static String xmlAsCode(String s) {
        String xmp = "xmp";
        return "<" + xmp + ">" + s + "</" + xmp + ">";
    }
}
