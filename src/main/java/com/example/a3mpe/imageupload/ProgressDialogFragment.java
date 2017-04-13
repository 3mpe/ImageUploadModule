package com.example.a3mpe.imageupload;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

class ProgressDialogFragment extends DialogFragment {

    public static ProgressDialogFragment getInstance() {
        return new ProgressDialogFragment();
    }

    public void showProgress(FragmentManager fragmentManager) {
        show(fragmentManager, "");
    }

    public void dissmissProgress() {
        dismiss();
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setStyle(STYLE_NO_FRAME, 0);
        return inflater.inflate(R.layout.progress_dialog, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

}