package com.gman.evaluator.gui.components;

import com.gman.evaluator.engine.Parser;
import com.gman.evaluator.engine.ParserFactory;
import com.gman.evaluator.gui.ComponentCreator;
import com.gman.evaluator.gui.ComponentUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author gman
 * @since 15.12.12 18:31
 */
public class ParserCreator implements ComponentCreator<Parser> {

    private Parser parser = null;

    private void setParser(Parser parser) {
        this.parser = parser;
    }

    @Override
    public Parser create() {
        ComponentUtils.openFileOperation(new ComponentUtils.OpenFileOperation() {
            @Override
            public void perform(InputStream is) throws IOException {
                setParser(ParserFactory.crete(is));
            }
        });
        return parser;
    }

    @Override
    public Parser update(Parser old) {
        return create();//TODO disable some properties
    }
}
