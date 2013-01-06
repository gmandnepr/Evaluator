package com.gman.evaluator.engine;

import java.util.List;

/**
 * @author gman
 * @since 26.11.12 20:09
 */
public interface Parser {

    void init(ParserProperties properties);

    ParserProperties getProperties();

    List<Item> parse(String content);
}
