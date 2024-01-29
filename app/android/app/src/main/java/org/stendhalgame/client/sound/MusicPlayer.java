/***************************************************************************
 *                 Copyright © 2022-2024 - Faiumoni e. V.                  *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package org.stendhalgame.client.sound;

import java.io.IOException;

import org.stendhalgame.client.Logger;
import org.stendhalgame.client.MainActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;


public class MusicPlayer {

	private static MediaPlayer mplayer = null;


	public static void playMusic(final Context ctx, final int id, final boolean loop) {
		if (isPlaying()) {
			Logger.debug("freeing up MusicPlayer instance to play new song");
			stopMusic();
		}

		Logger.debug("starting music (loop: " + loop + ")");

		mplayer = new MediaPlayer();
		mplayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(final MediaPlayer mp) {
				Logger.debug("starting music");
				// FIXME: not working
				//mplayer.setLooping(loop);
				mplayer.start();
			}
		});
		mplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(final MediaPlayer mp) {
				// free up resources & reset player to null
				stopMusic();

				// MediaPlayer.setLooping not working so manually loop
				if (loop) {
					playMusic(ctx, id, loop);
				}
			}
		});

		try {
			mplayer.setDataSource(ctx, parseResourceUri(id));
			mplayer.prepareAsync();
		} catch (final IOException e) {
			Logger.error("failed to load resource " + id + ":\n" + e.getStackTrace());
		}
	}

	public static void playMusic(final int id, final boolean loop) {
		playMusic(MainActivity.get(), id, loop);
	}

	public static void stopMusic() {
		if (mplayer != null) {
			if (mplayer.isPlaying()) {
				Logger.debug("stopping music");
				mplayer.stop();
			}
			mplayer.release();
			mplayer = null;
		}
	}

	public static boolean isPlaying() {
		return (mplayer != null);
	}

	private static Uri parseResourceUri(final int id) {
		return Uri.parse("android.resource://" + MainActivity.get().getPackageName()
			+ "/" + id);
	}
}
