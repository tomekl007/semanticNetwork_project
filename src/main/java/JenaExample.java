import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.VCARD;

import java.io.InputStream;

/**
 * @author Tomasz Lelek
 * @since 2014-05-11
 */
public class JenaExample {

    static String personURI = "http://somewhere/JohnSmith";
    public static void main(String[] args) {
        // some definitions



        // create an empty Model
        Model model = getSimpleModel();
        iterateOver(model);
        iterateOver(getModelWithEmptyNode());

        Model model2 = getModelWithEmptyNode();
        // now write the model in XML form to a file


        model2.write(System.out);
        model2.write(System.out, "RDF/XML-ABBREV");
        // now write the model in N-TRIPLES form to a file
        model2.write(System.out, "N-TRIPLES");

        prefixesMapping();

        navigate();

        selectorExample();

        operationsOnUnion();

        containers();

        literals();

    }

    private static Model getSimpleModel() {
        String fullName = "John Smith";
        Model model = ModelFactory.createDefaultModel();

        Resource johnSmith =
                model.createResource(personURI)
                        .addProperty(VCARD.FN, fullName);
        return model;
    }

    private static void navigate() {

        Resource vcard = getModelWithEmptyNode().getResource(personURI);

        String fullName = "tomek lelek";
        // set up the output
        System.out.println("The nicknames of \""
                + fullName + "\" are:");
// list the nicknames
        StmtIterator iter = vcard.listProperties(VCARD.NICKNAME);
        while (iter.hasNext()) {
            System.out.println("    " + iter.nextStatement()
                    .getObject()
                    .toString());
        }
    }

    public static Model getModelWithEmptyNode() {
        // some definitions

        String givenName = "John";
        String familyName = "Smith";
        String fullName = givenName + " " + familyName;

// create an empty Model
        Model model = ModelFactory.createDefaultModel();

// create the resource
//   and add the properties cascading style
        Resource johnSmith
                = model.createResource(personURI)
                .addProperty(VCARD.FN, fullName)
                .addProperty(VCARD.NICKNAME, "leo")
                .addProperty(VCARD.N,
                        model.createResource()
                                .addProperty(VCARD.Given, givenName)
                                .addProperty(VCARD.Family, familyName));
        return model;
    }

    public static void iterateOver(Model model) {
        // list the statements in the Model
        StmtIterator iter = model.listStatements();

// print out the predicate, subject and object of each statement
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();  // get next statement
            Resource subject = stmt.getSubject();     // get the subject
            Property predicate = stmt.getPredicate();   // get the predicate
            RDFNode object = stmt.getObject();      // get the object

            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }

            System.out.println(" .");
        }
    }

    public void readAndWriteRDF() {
        String inputFileName = "fileName";
        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        // use the FileManager to find the input file
        InputStream in = FileManager.get().open(inputFileName);
        if (in == null) {
            throw new IllegalArgumentException(
                    "File: " + inputFileName + " not found");
        }

        // read the RDF/XML file
        model.read(in, null);

        // write it to standard out
        model.write(System.out);

    }

    public static void prefixesMapping(){
        Model m = ModelFactory.createDefaultModel();
        String nsA = "http://somewhere/else#";
        String nsB = "http://nowhere/else#";
        Resource root = m.createResource( nsA + "root" );
        Property P = m.createProperty( nsA + "P" );
        Property Q = m.createProperty( nsB + "Q" );
        Resource x = m.createResource( nsA + "x" );
        Resource y = m.createResource( nsA + "y" );
        Resource z = m.createResource( nsA + "z" );
        m.add( root, P, x ).add( root, P, y ).add( y, Q, z );
        System.out.println( "# -- no special prefixes defined" );
        m.write( System.out );
        System.out.println( "# -- nsA defined" );
        m.setNsPrefix( "nsA", nsA );
        m.write( System.out );
        System.out.println( "# -- nsA and cat defined" );
        m.setNsPrefix( "cat", nsB );
        m.write( System.out );
    }

    public static void selectorExample(){
        Model model = getModelWithEmptyNode();

        // select all the resources with a VCARD.FN property
        ResIterator iter = model.listSubjectsWithProperty(VCARD.FN);
        if (iter.hasNext()) {
            System.out.println("The database contains vcards for:");
            while (iter.hasNext()) {
                System.out.println("  " + iter.nextResource()
                        .getProperty(VCARD.FN)
                        .getString());
            }
        } else {
            System.out.println("No vcards were found in the database");
        }

        /*StmtIterator iter =
                model.listStatements(new SimpleSelector(subject, predicate, object)
                */
    }

    public static void operationsOnUnion(){
        Model model1 = getModelWithEmptyNode();
        Model model2 = getSimpleModel();

        // merge the Models
        Model model = model1.union(model2);

        // print the Model as RDF/XML
        model.write(System.out, "RDF/XML-ABBREV");
    }

    public static void containers(){
        Model model = ModelFactory.createDefaultModel();
        // create a bag
        Bag smiths = model.createBag();

// select all the resources with a VCARD.FN property
// whose value ends with "Smith"
        StmtIterator iter = model.listStatements(
                new SimpleSelector(null, VCARD.FN, (RDFNode) null) {
                    public boolean selects(Statement s) {
                        return s.getString().endsWith("Smith");
                    }
                });
// add the Smith's to the bag
        while (iter.hasNext()) {
            smiths.add(iter.nextStatement().getSubject());
        }
    }

    public static void literals(){
        Model model = ModelFactory.createDefaultModel();
        // create the resource
        Resource r = model.createResource();

// add the property
        r.addProperty(RDFS.label, model.createLiteral("chat", "en"))
                .addProperty(RDFS.label, model.createLiteral("chat", "fr"))
                .addProperty(RDFS.label, model.createLiteral("<em>chat</em>", true));

// write out the Model
        model.write(System.out);
    }
}
