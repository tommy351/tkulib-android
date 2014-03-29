package tw.tku.tkulib;

import java.io.File;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by SkyArrow on 2014/3/29.
 */
public class DaoGenerator {
    private static final int DB_VERSION = 1;
    private static final String DB_PKG = "tw.tku.tkulib.model";
    private static final String DB_PATH = "../tkulib/src-gen";

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(DB_VERSION, DB_PKG);

        // Book
        Entity book = schema.addEntity("Book");
        book.setSuperclass("BookBase");

        book.addIdProperty();
        book.addStringProperty("title");
        book.addStringProperty("author");
        book.addStringProperty("publisher");
        book.addStringProperty("thumbnail");
        book.addStringProperty("isbn");
        book.addBooleanProperty("starred");

        // Generate DAO
        File file = new File(DB_PATH);
        if (!file.exists()) file.mkdirs();

        new de.greenrobot.daogenerator.DaoGenerator().generateAll(schema, DB_PATH);
    }
}
