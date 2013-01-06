package com.gman.evaluator.engine.services;

import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.Items;
import com.gman.evaluator.engine.Parser;
import com.gman.evaluator.engine.UrlGenerator;
import com.gman.evaluator.engine.UrlUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gman
 * @since 11/30/12 9:40 AM
 */
public class DataExtractingService extends AbstractService<Items> {

    private static final int HTTP_OK = 200;

    private final Client client = Client.create();
    private final List<UrlGenerator> urlGenerators = new ArrayList<UrlGenerator>();
    private final Map<String, Parser> parsers = new HashMap<String, Parser>();

    public DataExtractingService() {
        super("Extracting data");
    }

    public void setUrlGenerators(List<UrlGenerator> urlGenerators) {
        this.urlGenerators.clear();
        this.urlGenerators.addAll(urlGenerators);
    }

    public void setParsers(List<Parser> parsers) {
        this.parsers.clear();
        for (Parser parser : parsers) {
            final String url = UrlUtils.extractSite(parser.getProperties().getTarget());
            this.parsers.put(url, parser);
        }
    }

    @Override
    public Items call() throws Exception {
        int loadedSize = 0;
        int parsedSize = 0;
        int processedSize = 0;
        int totalSize = 0;

        final Items items = new Items();

        for (final UrlGenerator urlGenerator : urlGenerators) {
            totalSize += urlGenerator.urlsCount();
        }

        for (final UrlGenerator urlGenerator : urlGenerators) {
            final Parser parser = parsers.get(urlGenerator.getSite());
            if (parser != null) {
                items.registerProperties(parser.getProperties().getFieldDefinitions());
                for (final String url : urlGenerator) {
                    final ClientResponse cr = client.resource(url).accept(MediaType.TEXT_HTML_TYPE).get(ClientResponse.class);
                    if (cr.getStatus() != HTTP_OK) {
                        continue;
                    }
                    final String content = cr.getEntity(String.class);
                    loadedSize += content.length();
                    final List<Item> parsed = parser.parse(content);
                    parsedSize += parsed.size();
                    items.addAll(parsed);

                    processedSize++;

                    callback.processed(String.format(
                            "Page: %03d \n Loaded %03d MiB \n Items %04d",
                            processedSize, loadedSize / 1024 / 1024, parsedSize), countPercentage(processedSize, totalSize));
                }
            }
        }

        return items;
    }
}
