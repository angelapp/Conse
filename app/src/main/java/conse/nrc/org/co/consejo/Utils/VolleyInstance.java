package conse.nrc.org.co.consejo.Utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Helper class that is used to provide references to initialized
 * RequestQueue(s) and ImageLoader(s)
 * 
 * @author Ognyan Bankov
 * 
 */
public class VolleyInstance {
	// private static final int MAX_IMAGE_CACHE_ENTIRES = 100;

	private static RequestQueue mRequestQueue;
	private static ImageLoader mImageLoader;

	private VolleyInstance() {
		// no instances
	}

	public static void init(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);
		// mImageLoader = new ImageLoader(mRequestQueue, new
		// BitmapLruCache(MAX_IMAGE_CACHE_ENTIRES));
	}

	public static RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue==null){
            init(context);
            return mRequestQueue;
        }
		return mRequestQueue;
	}

	/**
	 * Returns instance of ImageLoader initialized with {@see FakeImageCache}
	 * which effectively means that no memory caching is used. This is useful
	 * for images that you know that will be show only once.
	 * 
	 * @return
	 */
	public static ImageLoader getImageLoader() {
		if (mImageLoader != null) {
			return mImageLoader;
		} else {
			throw new IllegalStateException("ImageLoader not initialized");
		}
	}
}