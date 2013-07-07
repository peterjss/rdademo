/*
* Copyright (c) 2012 iASPEC. All Rights Reserved.
*/

package com.iaspec.rda.plugins.rfid.external.dao;


import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.Serializable;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * Class description goes here.
 * User: Peter
 *
 * @author <a href="mailto:yang.li@iaspec.com">Peter Li</a>
 * @version 1.00 13-1-11
 */
public class BdbPersistenceStore<T extends Serializable> implements PersistenceStore<T>
{
    private static final Log log = LogFactory.getLog(BdbPersistenceStore.class);

    private final static String MESSAGE_DBNAME = "MESSAGE_DB";
    private final static String MESSAGE_CLASS_DBNAME = "MESSAGE_CLASS_DB";

    private String bdbStorePath;
    // Default 10M
    private long bdbCheckpointBytes = 10 * 1024 * 1024;
    // Default 5M
    private long bdbCacheSize = 5 * 1024 * 1024;

    private Environment bdbEnvironment;
    private Database bdb;
    private StoredClassCatalog bdbClassCatalog;

    private final Class<T> dataClass;

    public String getBdbStorePath()
    {
        return bdbStorePath;
    }

    /**
     * Set the BDB data folder, if it doesn't exist, it will be creating automatically.
     *
     * @param storePath
     */
    public void setBdbStorePath(final String storePath)
    {
        this.bdbStorePath = storePath;
    }

    public long getBdbCheckpointBytes()
    {
        return bdbCheckpointBytes;
    }

    /**
     * Set the BDB parameter CheckpointBytes, the default is 10M.
     */
    public void setBdbCheckpointBytes(final long dbdCheckpointBytes)
    {
        this.bdbCheckpointBytes = dbdCheckpointBytes;
    }

    public long getBdbCacheSize()
    {
        return bdbCacheSize;
    }

    /**
     * Set BDB CacheSize, default is 5M.
     */
    public void setBdbCacheSize(final long dbdCacheSize)
    {
        this.bdbCacheSize = dbdCacheSize;
    }

    public void init() throws DatabaseException
    {
        if (null == bdbStorePath)
        {
            throw new IllegalStateException("Member bdbStorePath is null!");
        }

        open();
    }

    private void open() throws DatabaseException
    {
        final File bdbDir = new File(bdbStorePath);
        if (!bdbDir.exists())
        {
            if (!bdbDir.mkdirs())
            {
                throw new RuntimeException("Fail to create the store directory(" + bdbStorePath
                        + ") for bdb persistence store!");
            }
        }

        final boolean readOnly = false;

        final EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setReadOnly(readOnly);
        envConfig.setAllowCreate(true);
        envConfig.setTransactional(true); // must setting
        // checkpoint occupied after data file increase some bytes
        envConfig.setConfigParam("je.checkpointer.bytesInterval", String
                .valueOf(bdbCheckpointBytes));

        final EnvironmentMutableConfig envMutableConfig = new EnvironmentMutableConfig();
        envMutableConfig.setCacheSize(bdbCacheSize);

        bdbEnvironment = new Environment(bdbDir, envConfig);
        bdbEnvironment.setMutableConfig(envMutableConfig);

        final DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setReadOnly(readOnly);
        dbConfig.setAllowCreate(!readOnly);
        dbConfig.setSortedDuplicates(false);
        dbConfig.setTransactional(true);

        bdb = bdbEnvironment.openDatabase(null, MESSAGE_DBNAME, dbConfig);

        // create class info db
        final Database classDb = bdbEnvironment.openDatabase(null, MESSAGE_CLASS_DBNAME, dbConfig);
        bdbClassCatalog = new StoredClassCatalog(classDb);
    }

    public void close() throws DatabaseException
    {
        // close db first, then close environment
        if (null != bdb)
        {
            bdb.close();
            bdb = null;
        }

        if (null != bdbClassCatalog)
        {
            bdbClassCatalog.close();
            bdbClassCatalog = null;
        }

        if (bdbEnvironment != null)
        {
            bdbEnvironment.cleanLog();
            bdbEnvironment.close();

            bdbEnvironment = null;
        }
    }

    /**
     * Can get a instance by {@link #createBdbPersistenceStore(Class)} as well。
     *
     * @param clazz the class which stores the type of data.
     */
    public BdbPersistenceStore(final Class<T> clazz)
    {
        dataClass = clazz;
    }

    /**
     * The method of build BdbPersistenceStore.
     *
     * @param <DT>     Data type.
     * @param dataType the class which stores the type of data.
     * @return
     */
    public static <DT extends Serializable> BdbPersistenceStore<DT> createBdbPersistenceStore(
            final Class<DT> dataType)
    {
        return new BdbPersistenceStore<DT>(dataType);
    }

    /**
     * Return the DataBaseEntry as the key which generate with UUID.
     */
    private static DatabaseEntry generateKeyEntry()
    {
        final String uuid = UUID.randomUUID().toString();
        return generateKeyEntry(uuid);
    }

    /**
     * Return the DataBaseEntry as the key which generate with a String provided.
     */
    private static DatabaseEntry generateKeyEntry(final String key)
    {
        final DatabaseEntry entry = new DatabaseEntry();
        final EntryBinding keyBinding = TupleBinding.getPrimitiveBinding(String.class);
        keyBinding.objectToEntry(key, entry);
        return entry;
    }

    /**
     * Restore the key from DatabaseEntry.
     */
    private static String restoreKey(final DatabaseEntry entry)
    {
        final EntryBinding keyBinding = TupleBinding.getPrimitiveBinding(String.class);
        return (String) keyBinding.entryToObject(entry);
    }

    private DatabaseEntry generateDataEntry(final Object object)
    {
        final DatabaseEntry entry = new DatabaseEntry();
        final EntryBinding dataBinding = new SerialBinding(bdbClassCatalog, dataClass);
        dataBinding.objectToEntry(object, entry);

        return entry;
    }

    /**
     * Restore the data from DatabaseEntry.
     */
    private T restoreData(final DatabaseEntry entry)
    {
        final EntryBinding dataBinding = new SerialBinding(bdbClassCatalog, dataClass);
        @SuppressWarnings("unchecked")
        final T data = (T) dataBinding.entryToObject(entry);
        return data;
    }

    /**
     * Put a object, and it will generate a key, if fails,
     */
    // FIXME ignore exception?
    public String write(final T object)
    {
        if (object != null)
        {
            DatabaseEntry keyEntry = generateKeyEntry();
            String key = restoreKey(keyEntry);
            try
            {

                bdb.put(null, keyEntry, generateDataEntry(object));

                if (log.isTraceEnabled())
                {
                    log.trace("write object to db: " + object);
                }
            }
            catch (final DatabaseException e)
            {
                log.fatal("write message failed ", e);
            }
            return key;
        }
        return null;
    }

    /**
     * Write multiple objects.
     */
    // FIXME ignore exception?
    public void batchWrite(final List<T> objectList)
    {
        for (final T obj : objectList)
        {
            write(obj);
        }
    }

    /**
     * Read data.
     *
     * @return entry of data, if there is nothing in db, will return null.
     */
    public Entry<String, T> read()
    {
        SimpleEntry<String, T> entry = null;
        Cursor cursor = null;

        try
        {
            cursor = bdb.openCursor(null, null);
            final DatabaseEntry foundKey = new DatabaseEntry();
            final DatabaseEntry foundData = new DatabaseEntry();

            if (cursor.getNext(foundKey, foundData, LockMode.DEFAULT) == OperationStatus.SUCCESS)
            {
                entry = new SimpleEntry<String, T>(restoreKey(foundKey), restoreData(foundData));
            }

            if (log.isTraceEnabled())
            {
                log.trace("read object from db");
            }
        }
        catch (final DatabaseException e)
        {
            log.error("Don't warry, read message from db error, " + "late read again: "
                    + e.getMessage());
        }
        finally
        {
            if (cursor != null)
            {
                try
                {
                    cursor.close();
                }
                catch (final DatabaseException e)
                {
                    // ignore
                }
            }
        }

        return entry;
    }

    /**
     * Read data.
     *
     * @return entry of data, if there is nothing in store, will return a empty map.
     */
    public Map<String, T> batchRead(final int count)
    {
        final Map<String, T> map = new HashMap<String, T>(count);
        Cursor cursor = null;

        try
        {
            cursor = bdb.openCursor(null, null);
            final DatabaseEntry foundKey = new DatabaseEntry();
            final DatabaseEntry foundData = new DatabaseEntry();

            int readedNum = 0;
            while (cursor.getNext(foundKey, foundData, LockMode.DEFAULT) == OperationStatus.SUCCESS
                    && readedNum < count)
            {
                map.put(restoreKey(foundKey), restoreData(foundData));
                readedNum++;
            }

            if (log.isTraceEnabled())
            {
                log.trace("read object from db, count= " + readedNum);
            }
        }
        catch (final DatabaseException e)
        {
            log.error("Don't warry, read message from db error, " + "late read again: "
                    + e.getMessage());
        }
        finally
        {
            if (cursor != null)
            {
                try
                {
                    cursor.close();
                }
                catch (final DatabaseException e)
                {
                    // ignore
                }
            }
        }

        return map;
    }

    public Entry<String, T> read(String key)
    {
        Cursor cursor = null;
        SimpleEntry<String, T> entry = null;
        try
        {
            DatabaseEntry queryKey = new DatabaseEntry();
            DatabaseEntry value = new DatabaseEntry();
            queryKey.setData(key.getBytes("UTF-8"));

            cursor = bdb.openCursor(null, null);
            OperationStatus status = cursor.getNext(queryKey, value, LockMode.DEFAULT);
//            OperationStatus status = bdb.get(null, queryKey, value,
//                    LockMode.DEFAULT);
            if (status == OperationStatus.SUCCESS)
            {
//            return new Session(value.getData());
//                ByteArrayInputStream bi = new ByteArrayInputStream(value.getData());
//                ObjectInputStream oi = new ObjectInputStream(bi);
                entry = new SimpleEntry<String, T>(restoreKey(queryKey), restoreData(value));
                return entry;
            }
        }
        catch (final Exception e)
        {
            log.error("Don't warry, read message from db error, " + "late read again: "
                    + e.getMessage());
        }
        finally
        {
            if (cursor != null)
            {
                try
                {
                    cursor.close();
                }
                catch (final DatabaseException e)
                {
                    // ignore
                }
            }
        }
        return null;
    }

    // FIXME ignore exception?
    public void delete(final String key)
    {
        if (key == null)
        {
            return;
        }

        Transaction transaction = null;
        try
        {
            final TransactionConfig txnConfig = new TransactionConfig();
            txnConfig.setSync(true);

            transaction = bdbEnvironment.beginTransaction(null, txnConfig);
            if (log.isTraceEnabled())
            {
                log.trace("BDB: begin to transaction");
            }

            bdb.delete(transaction, generateKeyEntry(key));
            if (log.isTraceEnabled())
            {
                log.trace("BDB: delete message key=" + key);
            }

            transaction.commit();
            transaction = null;
            if (log.isTraceEnabled())
            {
                log.trace("BDB: end of transaction");
            }
        }
        catch (final DatabaseException e)
        {
            log.fatal("BDB: delete failed: " + e.getMessage());
        }
        catch (final Throwable t)
        {
            log.fatal("BDB: delete failed: " + t.getMessage());
        }
        finally
        {
            if (transaction != null)
            {
                try
                {
                    transaction.abort();
                }
                catch (final DatabaseException e1)
                {
                }
            }
        }
    }

    public void delete(final List<String> keys)
    {
        // TODO rebuild this method in order to avoid that transaction is open/close frequently.
        for (final String key : keys)
        {
            delete(key);
        }
    }
}
