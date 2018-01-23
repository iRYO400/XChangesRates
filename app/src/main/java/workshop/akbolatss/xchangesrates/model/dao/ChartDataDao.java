package workshop.akbolatss.xchangesrates.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.internal.SqlUtils;

import java.util.ArrayList;
import java.util.List;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "CHART_DATA".
*/
public class ChartDataDao extends AbstractDao<ChartData, Long> {

    public static final String TABLENAME = "CHART_DATA";

    /**
     * Properties of entity ChartData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Exchange = new Property(1, String.class, "exchange", false, "EXCHANGE");
        public final static Property Coin = new Property(2, String.class, "coin", false, "COIN");
        public final static Property Currency = new Property(3, String.class, "currency", false, "CURRENCY");
        public final static Property Source = new Property(4, String.class, "source", false, "SOURCE");
        public final static Property IsActive = new Property(5, Boolean.class, "isActive", false, "IS_ACTIVE");
        public final static Property Timing = new Property(6, String.class, "timing", false, "TIMING");
        public final static Property InfoId = new Property(7, Long.class, "infoId", false, "INFO_ID");
    }

    private DaoSession daoSession;


    public ChartDataDao(DaoConfig config) {
        super(config);
    }
    
    public ChartDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CHART_DATA\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"EXCHANGE\" TEXT," + // 1: exchange
                "\"COIN\" TEXT," + // 2: coin
                "\"CURRENCY\" TEXT," + // 3: currency
                "\"SOURCE\" TEXT," + // 4: source
                "\"IS_ACTIVE\" INTEGER," + // 5: isActive
                "\"TIMING\" TEXT," + // 6: timing
                "\"INFO_ID\" INTEGER);"); // 7: infoId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CHART_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ChartData entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String exchange = entity.getExchange();
        if (exchange != null) {
            stmt.bindString(2, exchange);
        }
 
        String coin = entity.getCoin();
        if (coin != null) {
            stmt.bindString(3, coin);
        }
 
        String currency = entity.getCurrency();
        if (currency != null) {
            stmt.bindString(4, currency);
        }
 
        String source = entity.getSource();
        if (source != null) {
            stmt.bindString(5, source);
        }
 
        Boolean isActive = entity.getIsActive();
        if (isActive != null) {
            stmt.bindLong(6, isActive ? 1L: 0L);
        }
 
        String timing = entity.getTiming();
        if (timing != null) {
            stmt.bindString(7, timing);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ChartData entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String exchange = entity.getExchange();
        if (exchange != null) {
            stmt.bindString(2, exchange);
        }
 
        String coin = entity.getCoin();
        if (coin != null) {
            stmt.bindString(3, coin);
        }
 
        String currency = entity.getCurrency();
        if (currency != null) {
            stmt.bindString(4, currency);
        }
 
        String source = entity.getSource();
        if (source != null) {
            stmt.bindString(5, source);
        }
 
        Boolean isActive = entity.getIsActive();
        if (isActive != null) {
            stmt.bindLong(6, isActive ? 1L: 0L);
        }
 
        String timing = entity.getTiming();
        if (timing != null) {
            stmt.bindString(7, timing);
        }
    }

    @Override
    protected final void attachEntity(ChartData entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ChartData readEntity(Cursor cursor, int offset) {
        ChartData entity = new ChartData( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // exchange
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // coin
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // currency
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // source
            cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0, // isActive
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // timing
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ChartData entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setExchange(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCoin(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCurrency(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSource(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setIsActive(cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0);
        entity.setTiming(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ChartData entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ChartData entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ChartData entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getChartDataInfoDao().getAllColumns());
            builder.append(" FROM CHART_DATA T");
            builder.append(" LEFT JOIN CHART_DATA_INFO T0 ON T.\"INFO_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected ChartData loadCurrentDeep(Cursor cursor, boolean lock) {
        ChartData entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        ChartDataInfo chartDataInfo = loadCurrentOther(daoSession.getChartDataInfoDao(), cursor, offset);
        entity.setChartDataInfo(chartDataInfo);

        return entity;    
    }

    public ChartData loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<ChartData> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<ChartData> list = new ArrayList<ChartData>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<ChartData> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<ChartData> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
