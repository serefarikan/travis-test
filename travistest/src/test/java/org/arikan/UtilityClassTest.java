package org.arikan;

import org.junit.Test;

public class UtilityClassTest {

    private IUtility utility = new Utility();

    @Test
    public void doesSomething(){
        String someResult = utility.doSomething();
    }
}
