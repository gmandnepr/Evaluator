package com.gman.evaluator.engine;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author gman
 * @since 26.11.12 20:02
 */
public interface Item extends Serializable {

    String getLink();

    String getName();

    double getPrice();

    Date getDate();

    int getDeclaredPropertiesCount();

    Set<String> getDeclaredProperties();

    double getProperty(String name);

    boolean isEvaluable();
}
