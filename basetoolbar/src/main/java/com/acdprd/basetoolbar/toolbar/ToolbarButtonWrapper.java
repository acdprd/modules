package com.acdprd.basetoolbar.toolbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import su.bnet.impulseit.R;
import su.bnet.impulseit.databinding.ViewButtonWrapperBinding;
import su.bnet.impulseit.helpers.command.ICommandVoid;
import su.bnet.impulseit.view.CustomView;


public class ToolbarButtonWrapper extends CustomView<ViewButtonWrapperBinding> {
    private static final String TAG = ToolbarButtonWrapper.class.getSimpleName();

    public ToolbarButtonWrapper(@NonNull Context context) {
        super(context);
    }

    public ToolbarButtonWrapper(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolbarButtonWrapper(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setListener(final ICommandVoid l) {
        binding.buttonWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (l != null) l.exec();
            }
        });
    }

    public void setIcon(Drawable drawable) {
        binding.buttonWrapper.setIcon(drawable);
    }

    public void setIcon(@DrawableRes int icon) {
        binding.buttonWrapper.setIcon(getResources().getDrawable(icon));
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.view_button_wrapper;
    }


}
