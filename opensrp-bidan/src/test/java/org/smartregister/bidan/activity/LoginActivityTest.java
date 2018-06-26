package org.smartregister.bidan.activity;

import android.content.Intent;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.smartregister.bidan.BaseUnitTest;
import org.smartregister.bidan.event.ViewConfigurationSyncCompleteEvent;

import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.spy;

/**
 * Created by ndegwamartin on 18/12/2017.
 */

public class LoginActivityTest extends BaseUnitTest {

    private ActivityController<LoginActivity> controller;
    private LoginActivity activity;

    @Before
    public void setUp() {
        org.mockito.MockitoAnnotations.initMocks(this);
        Intent intent = new Intent(RuntimeEnvironment.application, LoginActivity.class);
        controller = Robolectric.buildActivity(LoginActivity.class, intent);
        activity = controller.get();
        controller.setup();
    }

    private void destroyController() {
        try {
            activity.finish();
            controller.pause().stop().destroy(); //destroy controller if we can

        } catch (Exception e) {
            Log.e(getClass().getCanonicalName(), e.getMessage());
        }
    }

    @After
    public void tearDown() {
        destroyController();
    }

    @Test
    public void refreshViewsCallsRefreshViewsOnSyncCompleteEvent() {

        ViewConfigurationSyncCompleteEvent viewConfigurationSyncCompleteEvent = new ViewConfigurationSyncCompleteEvent();
        LoginActivity spyActivity = spy(activity);
        junit.framework.Assert.assertNotNull(spyActivity);
        spyActivity.refreshViews(viewConfigurationSyncCompleteEvent);
        Mockito.verify(spyActivity, times(1)).refreshViews(viewConfigurationSyncCompleteEvent);
    }
}
