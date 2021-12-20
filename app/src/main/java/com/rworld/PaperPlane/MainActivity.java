/*
 *      MainActivity.java
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

import android.os.Bundle;

import com.rworld.core.v2.GameActivity;
import com.rworld.core.v2.GameScene;

import java.util.Random;

public class MainActivity extends GameActivity {

    public static final Random random = new Random();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        GameActivity.Debug = false;
        GameActivity.RenderMeshBoundaries = false;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected GameScene onCreateScreens() {
        return new Scene1(this);
    }
}
