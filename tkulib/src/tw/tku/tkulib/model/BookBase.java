package tw.tku.tkulib.model;

import tw.tku.tkulib.Constant;

/**
 * Created by SkyArrow on 2014/3/29.
 */
public abstract class BookBase {
    public abstract Long getId();

    public String getUrl() {
        return getUrl(getId());
    }

    public static String getUrl(long id) {
        return String.format(Constant.BOOK_URL, id);
    }
}
