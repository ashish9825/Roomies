package com.example.ashishpanjwani.rommies.Utils;

import android.support.v7.widget.RecyclerView;

public abstract class HIdingScrollListener extends RecyclerView.OnScrollListener {

    private static final int HIDE_THRESHOLD = 20;
    private int scrolledDistance = 0;
    private boolean controlVisible = true;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (scrolledDistance > HIDE_THRESHOLD && controlVisible) {
            onHide();
            controlVisible = false;
            scrolledDistance = 0;
        }
        else if (scrolledDistance < -HIDE_THRESHOLD && !controlVisible) {
            onShow();
            controlVisible = true;
            scrolledDistance = 0;
        }

        if ((controlVisible && dy>0) || (!controlVisible && dy<0)) {
            scrolledDistance += dy;
        }
    }
    public abstract void onHide();
    public abstract void onShow();
}
