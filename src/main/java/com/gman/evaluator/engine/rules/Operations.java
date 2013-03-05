package com.gman.evaluator.engine.rules;

import com.gman.evaluator.engine.rules.operations.AfterOperation;
import com.gman.evaluator.engine.rules.operations.BeforeOperation;
import com.gman.evaluator.engine.rules.operations.ContainsOperation;
import com.gman.evaluator.engine.rules.operations.EqualsOperation;
import com.gman.evaluator.engine.rules.operations.GreaterOperation;
import com.gman.evaluator.engine.rules.operations.GreaterOrEqualsOperation;
import com.gman.evaluator.engine.rules.operations.IsNotOperation;
import com.gman.evaluator.engine.rules.operations.IsNotSetOperation;
import com.gman.evaluator.engine.rules.operations.IsOperation;
import com.gman.evaluator.engine.rules.operations.IsSetOperation;
import com.gman.evaluator.engine.rules.operations.LessOperation;
import com.gman.evaluator.engine.rules.operations.LessOrEqualsOperation;

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
