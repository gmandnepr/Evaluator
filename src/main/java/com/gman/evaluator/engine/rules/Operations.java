package com.gman.evaluator.engine.rules;

import com.gman.evaluator.engine.rules.operations.*;

/**
 * @author gman
 * @since 15.12.12 15:52
 */
public class Operations {

    public static final Operation[] OPERATIONS = {
            new EqualsOperation(),
            new LessOperation(),
            new GreaterOperation(),
            new LessOrEqualsOperation(),
            new GreaterOrEqualsOperation(),

            new ContainsOperation(),

            new IsSetOperation(),
            new IsNotSetOperation(),

            new IsOperation(),
            new IsNotOperation(),

            new BeforeOperation(),
            new AfterOperation()
    };
}
