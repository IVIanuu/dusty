package com.ivianuu.dusty;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Manuel Wrage (IVIanuu)
 */

public class Dusty {

    private static final String TAG = "Dusty";

    private static boolean debug;

    public static void setDebug(boolean debug) {
        Dusty.debug = debug;
    }

    @VisibleForTesting
    static final Map<Class<?>, Constructor> CLEARINGS = new LinkedHashMap<>();

    public static void register(final Fragment target) {
        if (debug) Log.d(TAG, "registering target: " + target.getClass().getSimpleName());
        final Constructor constructor = findClearingConstructorForClass(target.getClass());
        if (constructor != null) {
            final FragmentManager fragmentManager = target.getFragmentManager();

            fragmentManager.registerFragmentLifecycleCallbacks(
                    new FragmentManager.FragmentLifecycleCallbacks() {
                        @Override
                        public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
                            // check target
                            if (f == target) {
                                try {
                                    if (debug) Log.d(TAG, "clearing target " + target.getClass().getSimpleName());
                                    // clear target
                                    constructor.newInstance(target);
                                } catch (Exception e) {
                                    if (debug) Log.e(TAG, e.getMessage());
                                }

                                // unregister lifecycle callback
                                fragmentManager.unregisterFragmentLifecycleCallbacks(this);
                            }
                        }
                    }, false);
        }
    }

    @Nullable
    private static Constructor findClearingConstructorForClass(Class<?> cls) {
        if (debug) Log.d(TAG, "finding auto clearing for " + cls.getName());
        Constructor clearingConstructor = CLEARINGS.get(cls);
        if (clearingConstructor != null) {
            if (debug) Log.d(TAG, "auto clearing already cached " + cls.getName());
            return clearingConstructor;
        }
        String clsName = cls.getName();
        if (clsName.startsWith("android.") || clsName.startsWith("java.")) {
            if (debug) Log.d(TAG, "is android or java " + cls.getName());
            return null;
        }
        try {
            Class<?> bindingClass = cls.getClassLoader().loadClass(clsName + "_AutoClearing");
            //noinspection unchecked
            clearingConstructor =  bindingClass.getConstructor(cls);
            if (debug) Log.d(TAG, "auto clearing for " + cls.getName() + " found");
        } catch (ClassNotFoundException e) {
            clearingConstructor = findClearingConstructorForClass(cls.getSuperclass());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to find binding constructor for " + clsName, e);
        }

        CLEARINGS.put(cls, clearingConstructor);
        return clearingConstructor;
    }
}
