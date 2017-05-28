package com.ivianuu.dusty.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.BaseAdapter;

import com.ivianuu.dusty.Dusty;
import com.ivianuu.dusty.annotations.Clear;

/**
 * @author Manuel Wrage (IVIanuu)
 */

public class UserFragment extends MyFragment {

    @Clear
    BaseAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dusty.setDebug(true);
        Dusty.register(this);
    }
}
