package com.rayamajs.cwapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class MemberPageTest {

    @Rule
    public ActivityTestRule<MemberPage> mActivityTestRule = new ActivityTestRule<MemberPage>(MemberPage.class);

    private MemberPage mActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(camera_page.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor2 = getInstrumentation().addMonitor(review.class.getName(),null,false);

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

    @Test
    public void testLaunceNotes()
    {
        assertNotNull(mActivity.findViewById(R.id.review_btn));
        onView(withId(R.id.review_btn)).perform(click());
        Activity noteActivity = getInstrumentation().waitForMonitorWithTimeout(monitor2, 5000);
        assertNotNull(noteActivity);
        noteActivity.finish();
    }

    @Test
    public void testLaunchCamera()
    {
        assertNotNull(mActivity.findViewById(R.id.camera_btn));
        onView(withId(R.id.camera_btn)).perform(click());
        Activity cameraActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNotNull(cameraActivity);
        cameraActivity.finish();
    }



    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }

}