package com.gman.evaluator.engine.services;

import com.gman.evaluator.engine.DataHolder;
import com.gman.evaluator.engine.Items;
import com.gman.evaluator.engine.services.analitics.abnomal.AbnormalRemover;
import com.gman.evaluator.engine.services.analitics.abnomal.DefaultAbnormalRemover;

/**
 * @author gman
 * @since 09.03.13 13:47
 */
public class FilteringService extends AbstractService<Items> {

    private final DataHolder<Items> items;

    private final AbnormalRemover abnormalRemover = new DefaultAbnormalRemover();

    public FilteringService(DataHolder<Items> items) {
        super("Filtering");
        this.items = items;
    }

    @Override
    public Items call() throws Exception {
        final Items processing = items.get();
        if (processing.isEmpty()) {
            throw new IllegalStateException("Nothing to filter!");
        }

        callback.processed("Filtering", 100);

        final Items processed = abnormalRemover.remove(processing);

        callback.processed("Done", 100);

        return processed;
    }
}
