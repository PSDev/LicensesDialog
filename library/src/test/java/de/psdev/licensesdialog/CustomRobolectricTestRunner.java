package de.psdev.licensesdialog;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.Fs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Colin White on 2017-03-09.
 */
public class CustomRobolectricTestRunner extends RobolectricTestRunner {
	public CustomRobolectricTestRunner(final Class<?> testClass) throws InitializationError {
		super(testClass);
	}

	@Override
	protected AndroidManifest getAppManifest(final Config config) {
		// This is a work-around for the Robolectric issue described here:
		// https://github.com/robolectric/robolectric/issues/1430
		String basePath = "";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new ProcessBuilder("pwd").start().getInputStream()));
			basePath = reader.readLine().endsWith("library") ? "" : "library/";
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ignored) {}
			}
		}
		return new AndroidManifest(
				Fs.fileFromPath(basePath + "build/intermediates/bundles/debug/AndroidManifest.xml"),
				Fs.fileFromPath(basePath + "src/test/res"),
				Fs.fileFromPath(basePath + "build/intermediates/bundles/debug/assets"));
	}
}
