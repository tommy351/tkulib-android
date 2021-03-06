package tw.tku.tkulib.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import tw.tku.tkulib.model.Book;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table BOOK.
*/
public class BookDao extends AbstractDao<Book, Long> {

    public static final String TABLENAME = "BOOK";

    /**
     * Properties of entity Book.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Author = new Property(2, String.class, "author", false, "AUTHOR");
        public final static Property Publisher = new Property(3, String.class, "publisher", false, "PUBLISHER");
        public final static Property Thumbnail = new Property(4, String.class, "thumbnail", false, "THUMBNAIL");
        public final static Property Isbn = new Property(5, String.class, "isbn", false, "ISBN");
        public final static Property Starred = new Property(6, Boolean.class, "starred", false, "STARRED");
        public final static Property LastRead = new Property(7, java.util.Date.class, "lastRead", false, "LAST_READ");
    };


    public BookDao(DaoConfig config) {
        super(config);
    }
    
    public BookDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'BOOK' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'TITLE' TEXT," + // 1: title
                "'AUTHOR' TEXT," + // 2: author
                "'PUBLISHER' TEXT," + // 3: publisher
                "'THUMBNAIL' TEXT," + // 4: thumbnail
                "'ISBN' TEXT," + // 5: isbn
                "'STARRED' INTEGER," + // 6: starred
                "'LAST_READ' INTEGER);"); // 7: lastRead
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'BOOK'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Book entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(3, author);
        }
 
        String publisher = entity.getPublisher();
        if (publisher != null) {
            stmt.bindString(4, publisher);
        }
 
        String thumbnail = entity.getThumbnail();
        if (thumbnail != null) {
            stmt.bindString(5, thumbnail);
        }
 
        String isbn = entity.getIsbn();
        if (isbn != null) {
            stmt.bindString(6, isbn);
        }
 
        Boolean starred = entity.getStarred();
        if (starred != null) {
            stmt.bindLong(7, starred ? 1l: 0l);
        }
 
        java.util.Date lastRead = entity.getLastRead();
        if (lastRead != null) {
            stmt.bindLong(8, lastRead.getTime());
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Book readEntity(Cursor cursor, int offset) {
        Book entity = new Book( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // title
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // author
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // publisher
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // thumbnail
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // isbn
            cursor.isNull(offset + 6) ? null : cursor.getShort(offset + 6) != 0, // starred
            cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)) // lastRead
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Book entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAuthor(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPublisher(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setThumbnail(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setIsbn(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setStarred(cursor.isNull(offset + 6) ? null : cursor.getShort(offset + 6) != 0);
        entity.setLastRead(cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Book entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Book entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
