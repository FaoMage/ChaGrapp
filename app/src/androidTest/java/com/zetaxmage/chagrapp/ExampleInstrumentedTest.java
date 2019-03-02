package com.zetaxmage.chagrapp;

import android.content.Context;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented WhatsNewActivity, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under WhatsNewActivity.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.zetaxmage.chagrapp", appContext.getPackageName());
    }
}
