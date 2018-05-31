package org.nusabit.multimodule;

import org.junit.Ignore;
import org.junit.Test;
import org.nusabit.appsatu.MainActivity;

import static org.junit.Assert.*;

public class MainActivityTest {

    @Test
    public void onCreate() {
    }

//    @Ignore
//    @Test
//    public void bar() {
//        fail("Not yet implemented!");
//    }

    @Test
    public  void bar()
    {
        MainActivity bar = new MainActivity();
        assertEquals("bar called", bar.bar());
    }

}