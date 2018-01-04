package com.ivianuu.dusty.sample;

import android.database.sqlite.SQLiteOpenHelper;
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
    public void onPause() {
        super.onPause();
        Dusty.dust(this);
    }
}
