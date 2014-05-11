import com.ontologycentral.ldspider.Crawler;
import com.ontologycentral.ldspider.frontier.BasicFrontier;
import com.ontologycentral.ldspider.frontier.Frontier;
import com.ontologycentral.ldspider.hooks.content.ContentHandler;
import com.ontologycentral.ldspider.hooks.content.ContentHandlerNx;
import com.ontologycentral.ldspider.hooks.content.ContentHandlerRdfXml;
import com.ontologycentral.ldspider.hooks.content.ContentHandlers;
import com.ontologycentral.ldspider.hooks.error.ErrorHandler;
import com.ontologycentral.ldspider.hooks.error.ErrorHandlerLogger;
import com.ontologycentral.ldspider.hooks.links.LinkFilter;
import com.ontologycentral.ldspider.hooks.links.LinkFilterDomain;
import com.ontologycentral.ldspider.hooks.sink.Sink;
import com.ontologycentral.ldspider.hooks.sink.SinkCallback;
import org.semanticweb.yars.nx.parser.Callback;
import org.semanticweb.yars.util.CallbackNQOutputStream;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Tomasz Lelek
 * @since 2014-05-11
 */
public class LdSpiderExample {
    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
        String seedUri = "http://harth.org/andreas/foaf.rdf";
        int numberOfThreads = 2;
        Crawler crawler = new Crawler(numberOfThreads);
        Frontier frontier = new BasicFrontier();
        //frontier.setBlacklist(CrawlerConstants.BLACKLIST);
        frontier.add(new URI(seedUri));

        LinkFilter linkFilter = new LinkFilterDomain(frontier);
        crawler.setLinkFilter(linkFilter);

        ContentHandler contentHandler = new ContentHandlers(new ContentHandlerRdfXml(), new ContentHandlerNx());
        crawler.setContentHandler(contentHandler);

        File outputFile = new File("result");
        OutputStream os = new FileOutputStream(outputFile);
        Sink sink = new SinkCallback(new CallbackNQOutputStream(os));
        crawler.setOutputCallback(sink);


        //Print to Stdout
        PrintStream ps = System.out;
        //Print to file
        FileOutputStream fos = new FileOutputStream(new File("errorLogFile"));

        //Add printstream and file stream to error handler
        Callback rcb = new CallbackNQOutputStream(fos);
        ErrorHandler eh = new ErrorHandlerLogger(ps, rcb);
        rcb.startDocument();

        //Connect hooks with error handler
        crawler.setErrorHandler(eh);
        frontier.setErrorHandler(eh);
        linkFilter.setErrorHandler(eh);

        int depth = 2;
        int maxURIs = 100;
        boolean includeABox = true;
        boolean includeTBox = false;

        crawler.evaluateBreadthFirst(frontier, depth, maxURIs, 1, Crawler.Mode.ABOX_AND_TBOX);
    }
}
