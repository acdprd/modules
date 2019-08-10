package com.acdprd.modules.fragment;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import org.jetbrains.annotations.NotNull;

import kotlin.Unit;
import su.bnet.utils.permission.IPermissionHelper;
import su.bnet.utils.permission.PermissionListener;
import su.bnet.utils.permission.PermissionsHelper;

public class TestFragment extends Fragment implements IPermissionHelper {
    private PermissionListener pListener;

    public void test() {
        PermissionsHelper.INSTANCE.check(this, 1, Manifest.permission.READ_EXTERNAL_STORAGE, granted -> {
            handlePerm(granted);
            return Unit.INSTANCE;
        });
    }

    private void handlePerm(boolean granted) {

    }

    @Override
    public void setPermissionListener(@NotNull PermissionListener l) {
        pListener = l;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        pListener.handlePermissionResponse(requestCode, grantResults);
    }
}
