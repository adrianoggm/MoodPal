

package com.example.moodpal.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.moodpal.R;

public class GestureDrawerLayout extends DrawerLayout {
    private View drawerListView = null;
    public GestureDrawerLayout(@NonNull Context context) {
        super(context);
    }

    public GestureDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GestureDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        drawerListView = findViewById(R.id.nav_view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean parentResult = super.onTouchEvent(event);

        // Si tocamos fuera de la parte en la que podemos abrir el menu deslizando
        // y no esta visible el menu se envia al MainActivity
        if (event.getX() > 80) {
            return isDrawerVisible(drawerListView);
        }

        return parentResult;

        // Esto nos puede servir también si no utilizamos la función de abrir el menú deslizando
        // desde el lateral
        // return isDrawerVisible(drawerListView);
    }
}