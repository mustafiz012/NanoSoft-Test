package com.example.musta.nanosoft_test.viewpager;

import com.example.musta.nanosoft_test.R;

/**
 * Created by musta on 6/2/2017.
 */

public enum ModelObject {
    RED(R.string.red, R.layout.first_fragment),
    BLUE(R.string.blue, R.layout.second_fragment),
    GREEN(R.string.green, R.layout.third_fragment);

    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }
}
