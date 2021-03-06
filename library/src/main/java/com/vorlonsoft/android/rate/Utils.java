/*
 * Copyright 2017 - 2018 Vorlonsoft LLC
 *
 * Licensed under The MIT License (MIT)
 */

package com.vorlonsoft.android.rate;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import static com.vorlonsoft.android.rate.Constants.Utils.EMPTY_STRING;
import static com.vorlonsoft.android.rate.Constants.Utils.EMPTY_STRING_ARRAY;
import static com.vorlonsoft.android.rate.Constants.Utils.TAG;
import static com.vorlonsoft.android.rate.Constants.Utils.UTILITY_CLASS_MESSAGE;

/**
 * <p>Utils Class - utils class of the AndroidRate library.</p>
 *
 * @since    0.5.0
 * @version  1.2.1
 * @author   Alexander Savin
 * @author   Shintaro Katafuchi
 */

final class Utils {

    private Utils() {
        throw new UnsupportedOperationException("Utils" + UTILITY_CLASS_MESSAGE);
    }

    static boolean isLollipop() {
        return ((Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) ||
                (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1));
    }

    /**
     * <p>Creates {@link android.app.AlertDialog.Builder}.</p>
     *
     * @param context activity context
     * @param themeResId theme resource ID
     * @return created {@link android.app.AlertDialog.Builder} object
     */
    @SuppressLint("ObsoleteSdkInt")
    @SuppressWarnings("ConstantConditions")
    @Nullable
    static AlertDialog.Builder getDialogBuilder(@NonNull final Context context, final int themeResId) {
        if (context == null) {
            Log.i(TAG, "Failed to create AlertDialog.Builder.");
            return null;
        } else if (themeResId == 0) {
            return new AlertDialog.Builder(context);
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return new AlertDialog.Builder(context);
        } else {
            return new AlertDialog.Builder(context, themeResId);
        }
    }

    /**
     * <p>Creates {@link androidx.appcompat.app.AlertDialog.Builder}.</p>
     *
     * @param context activity context
     * @param themeResId theme resource ID
     * @return created {@link androidx.appcompat.app.AlertDialog.Builder} object
     */
    @SuppressWarnings("ConstantConditions")
    @Nullable
    @RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    static androidx.appcompat.app.AlertDialog.Builder getAppCompatDialogBuilder(@NonNull final Context context,
                                                                                final int themeResId) {
        if (context == null) {
            Log.i(TAG, "Failed to create AlertDialog.Builder.");
            return null;
        } else if (themeResId == 0) {
            return new androidx.appcompat.app.AlertDialog.Builder(context);
        } else {
            return new androidx.appcompat.app.AlertDialog.Builder(context, themeResId);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    static String[] isPackagesExists(@NonNull final Context context, @NonNull final String[] targetPackages) {
        if ((context == null) || (targetPackages == null) || (targetPackages.length == 0)) {
            if (context == null) {
                Log.i(TAG, "Failed to get installed applications.");
            }
            if (targetPackages == null) {
                Log.i(TAG, "Null pointer to an array of target packages.");
            }
            return EMPTY_STRING_ARRAY;
        }

        final List<ApplicationInfo> applicationInfo = context.getPackageManager().getInstalledApplications(0);
        if (targetPackages.length == 1) {
            if ((targetPackages[0] != null) && (targetPackages[0].hashCode() != EMPTY_STRING.hashCode())) {
                for (ApplicationInfo anApplicationInfo : applicationInfo) {
                    if (targetPackages[0].equals(anApplicationInfo.packageName)) {
                        return new String[]{targetPackages[0]};
                    }
                }
            }
            return EMPTY_STRING_ARRAY;
        } else {
            final ArrayList<String> packageNames = new ArrayList<>();
            for (String aTargetPackage : targetPackages) {
                if ((aTargetPackage != null) && (aTargetPackage.hashCode() != EMPTY_STRING.hashCode())) {
                    for (ApplicationInfo anApplicationInfo : applicationInfo) {
                        if (aTargetPackage.equals(anApplicationInfo.packageName)) {
                            packageNames.add(aTargetPackage);
                            break;
                        }
                    }
                }
            }
            return packageNames.toArray(new String[0]);
        }
    }
}