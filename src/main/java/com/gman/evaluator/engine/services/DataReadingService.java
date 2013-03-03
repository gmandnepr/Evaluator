package com.gman.evaluator.engine.services;

import com.gman.evaluator.engine.Items;
import com.gman.evaluator.gui.ComponentUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author gman
 * @since 03.03.13 17:01
 */
public class DataReadingService extends AbstractService<Items> {

    @Override
    public Items call() throws Exception {

        final AtomicReference<Items> itemsHolder = new AtomicReference<Items>();

        ComponentUtils.openFileOperation(new ComponentUtils.OpenFileOperation() {
            @Override
            public void perform(InputStream is) throws IOException {
                try {
                    itemsHolder.set((Items) new ObjectInputStream(is).readObject());
                } catch (ClassNotFoundException e1) {
                    throw new IOException(e1);
                }
            }
        });

        return itemsHolder.get();
    }
}
