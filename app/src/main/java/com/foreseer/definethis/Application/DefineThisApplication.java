package com.foreseer.definethis.Application;

import android.app.Application;
import android.content.res.Configuration;

import com.foreseer.definethis.Data.Repository;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Konstantin "Foreseer" Buinak on 22.06.2017.
 */

public class DefineThisApplication extends Application {

    public static final String SETTINGS_FILE_NAME = "definethispref";
    public static final String SETTING_LAST_SORTED = "lastSorted";

    public static final String WORD_DATABASE = "definethis_word.realm";
    public static final String DELETED_RECORDS_DATABASE = "definethis_deleted.realm";

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration wordRealmConfig = new RealmConfiguration.Builder()
                .name(WORD_DATABASE)
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(wordRealmConfig);
        Repository.getInstance().setWordConfiguration(wordRealmConfig);

        RealmConfiguration deletedRecordsRealmConfig = new RealmConfiguration.Builder()
                .name(DELETED_RECORDS_DATABASE)
                .schemaVersion(0)
                .build();
        Repository.getInstance().setDeletedRecordsConfiguration(deletedRecordsRealmConfig);
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
