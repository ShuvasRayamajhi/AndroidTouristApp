package com.rayamajs.cwapp;

import android.app.Activity;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class Log_FormTest {

    @Rule
    public ActivityTestRule<Log_Form> mActivityTestRule = new ActivityTestRule<Log_Form>(Log_Form.class);

    private Log_Form mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch()
    {
        View view = mActivity.findViewById(R.id.tvMainText);
        assertNotNull(view);
    }
    @After
    public void tearDown() throws Exception {
    }
}