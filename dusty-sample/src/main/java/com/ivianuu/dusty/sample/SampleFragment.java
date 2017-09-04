package com.ivianuu.dusty.sample;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.BaseAdapter;

import com.ivianuu.dusty.Dusty;
import com.ivianuu.dusty.annotations.Clear;

/**
 * @author Manuel Wrage (IVIanuu)
 */
public class SampleFragment extends Fragment {

    @Clear SQLiteOpenHelper sqLiteOpenHelper;
    @Clear NotificationManagerCompat notificationManagerCompat;
    @Clear BaseAdapter myAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dusty.register(this); // register this fragment
    }
}
