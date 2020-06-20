package com.example.logictest.Common;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Utility {

    public static void showSnackBar(String message, View coordinatorLayout) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }
}
