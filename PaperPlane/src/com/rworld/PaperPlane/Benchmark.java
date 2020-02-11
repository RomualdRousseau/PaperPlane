/*
 *      Benchmark.java
 *
 *      Copyright 2011 Romuald Rousseau <romualdrousseau@gmail.com>
 *
 *      This program is free software; you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation; either version 2 of the License, or
 *      (at your option) any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program; if not, write to the Free Software
 *      Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *      MA 02110-1301, USA.
 */

package com.rworld.PaperPlane;

import com.rworld.core.v2.Log;

public class Benchmark {

    public void update(float time) {
        mFrameCount++;
        mTimerTime += time;

        if (mTimerTime >= 5.0f) {
            mLastFPS = (mFrameCount / mTimerTime);
            mFrameCount = 0;
            mTimerTime = 0.0f;
            mMessage.setLength(0);
            mMessage.append("Benchmark: ").append((int) mLastFPS).append("fps");
        }
        if (mLastFPS > 0.0f) {
            Log.i(mMessage.toString());
            mLastFPS = 0.0f;
        }
    }

    private int mFrameCount = 0;
    private float mTimerTime = 0.0f;
    private float mLastFPS = 0.0f;
    private StringBuilder mMessage = new StringBuilder();
}
