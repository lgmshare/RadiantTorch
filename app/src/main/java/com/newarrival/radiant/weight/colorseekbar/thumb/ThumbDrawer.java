package com.newarrival.radiant.weight.colorseekbar.thumb;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.newarrival.radiant.weight.colorseekbar.BaseSeekBar;


/**
 * @author -17.2℃ <rtugeek@gmail.com>
 */
public interface ThumbDrawer {
    /**
     * draw thumb
     *
     * @param thumbBounds Thumb's {@link android.graphics.Rect} draw in seekbar, the rectangle's
     *                    width and height specified by {@link #getWidth()} and {@link #getHeight()}.
     *                    the rectangle's center point always equals to coordinates that represented
     *                    by seekbar's current progress.
     * @param seekBar     {@link com.newarrival.radiant.weight.colorseekbar.ColorSeekBar}
     *                    or {@link com.newarrival.radiant.weight.colorseekbar.AlphaSeekBar}.
     * @param canvas      canvas that draws thumb.
     */
    void onDrawThumb(RectF thumbBounds, BaseSeekBar seekBar, Canvas canvas);

    /**
     * @return specify thumb's width
     */
    int getWidth();

    /**
     * @return specify thumb's height
     */
    int getHeight();
}
