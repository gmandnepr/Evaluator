package com.gman.evaluator.engine.services.analitics.abnomal;

/**
 * @author gman
 * @since 09.03.13 11:03
 */
public enum AbnormalRemoverFactory {

    DEFAULT(new DefaultAbnormalRemover()),
    NO(new NoAbnormalRemover()),
    DIFF_5(new DefaultAbnormalRemover(0.05)),
    DIFF_10(new DefaultAbnormalRemover(0.10)),
    DIFF_15(new DefaultAbnormalRemover(0.15)),
    DIFF_20(new DefaultAbnormalRemover(0.20)),
    DIFF_25(new DefaultAbnormalRemover(0.25)),
    DIFF_30(new DefaultAbnormalRemover(0.30)),
    DIFF_35(new DefaultAbnormalRemover(0.35));

    private final AbnormalRemover impl;

    private AbnormalRemoverFactory(AbnormalRemover impl) {
        this.impl = impl;
    }

    public AbnormalRemover create() {
        return impl;
    }
}
