package com.sarafizand.sarafizand.utils;

import android.view.View;

/**
 * Created by Mohammed on 6/11/2017.
 */

public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
