package com.gman.evaluator.engine;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author gman
 * @since 10.12.12 18:03
 */
public class ParserFactory {

    public static Parser crete(InputStream is) {
        try {
            final ParserProperties properties = new ParserProperties();
            properties.load(new InputStreamReader(is, "UTF-8"));
            final Parser parser = (Parser) Class.forName(properties.getType()).newInstance();
            parser.init(properties);
            return parser;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }
}
