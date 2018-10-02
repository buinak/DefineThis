package com.foreseer.definethis;

import android.app.Application;
import android.content.res.Configuration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Konstantin "Foreseer" Buinak on 22.06.2017.
 */

public class DefineThisApplication extends Application {

    public static final String SETTINGS_FILE_NAME = "definethispref";
    public static final String SETTING_LAST_SORTED = "lastSorted";

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("definethis.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
