package io.nerditall.serion.views;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by a0_ on 2/7/16.
 */
public class GridSize extends RecyclerView.ItemDecoration{

    private int SPAN;
    private int SPACE;
    private boolean edge;

    public GridSize(boolean edge, int SPACE, int SPAN) {
        this.edge = edge;
        this.SPACE = SPACE;
        this.SPAN = SPAN;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,RecyclerView.State state){
        int position = parent.getChildAdapterPosition(view);
        int column = position % SPAN;

        if(edge){
            outRect.left = SPACE - column * SPACE /SPAN;
            outRect.right = (column+1)*SPACE/SPAN;
            if(position < SPAN){
                outRect.top = SPACE;
            }
            outRect.bottom = SPACE;
        } else {
            outRect.left = column * SPACE / SPAN;
            outRect.right = SPACE - (column+1) * SPACE /SPAN;

            if(position >= SPAN){
                outRect.top = SPACE;
            }
        }
    }
}
