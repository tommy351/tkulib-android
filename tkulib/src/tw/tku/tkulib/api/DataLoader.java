package tw.tku.tkulib.api;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import tw.tku.tkulib.util.HttpRequestHelper;
import tw.tku.tkulib.util.L;

/**
 * Created by SkyArrow on 2014/3/29.
 */
public class DataLoader {
    private static DataLoader instance;
    private Context context;

    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:%s";
    private static final String SANMIN_IMAGE_URL = "http://www.sanmin.com.tw/Assets/product_images/%s/%s.jpg";

    private DataLoader(Context context) {
        this.context = context;
    }

    public static DataLoader getInstance(Context context) {
        if (instance == null) {
            instance = new DataLoader(context.getApplicationContext());
        }

        return instance;
    }

    public String getThumbnail(String isbn) throws IOException, JSONException {
        String str = HttpRequestHelper.getString(String.format(GOOGLE_BOOKS_API_URL, isbn));
        JSONObject json = new JSONObject(str);

        L.v("Get thumbnail: %s", isbn);

        if (json.getInt("totalItems") > 0) {
            JSONObject volumeInfo = json.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo");

            if (volumeInfo.has("imageLinks")) {
                return volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
            }
        }

        if (isbn.matches("^(978)?(957|986)")) {
            return String.format(SANMIN_IMAGE_URL, isbn.substring(3), isbn.substring(3, isbn.length() - 1));
        }

        return null;
    }
}
