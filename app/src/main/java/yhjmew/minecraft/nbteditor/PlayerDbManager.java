package yhjmew.minecraft.nbteditor;

import org.iq80.leveldb.*;
import org.iq80.leveldb.impl.Iq80DBFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerDbManager {

    private DB db;
    private File dbFolder;

    public PlayerDbManager(String dbFolderPath) throws Exception {
        this.dbFolder = new File(dbFolderPath);
        if (!dbFolder.exists()) {
            dbFolder.mkdirs();
        }

        File lockFile = new File(dbFolder, "LOCK");
        if (lockFile.exists()) {
            lockFile.delete();
        }

        Options options = new Options();
        options.createIfMissing(true);

        try {
            this.db = Iq80DBFactory.factory.open(dbFolder, options);
        } catch (Exception e) {
            throw new Exception("DB_CORRUPT: " + e.getMessage());
        }
    }

    public static void tryRepair(String dbFolderPath) throws Exception {
        File folder = new File(dbFolderPath);
        Options options = new Options();
        options.createIfMissing(true);

        try {
            Iq80DBFactory.factory.repair(folder, options);
            DB tempDb = Iq80DBFactory.factory.open(folder, options);
            tempDb.close();
        } catch (Exception e) {
            File currentFile = new File(folder, "CURRENT");
            if (currentFile.exists()) {
                currentFile.delete();
                Iq80DBFactory.factory.repair(folder, options);
                DB tempDb2 = Iq80DBFactory.factory.open(folder, options);
                tempDb2.close();
            } else {
                // 【修复】借用 NbtTranslator.getString
                throw new Exception(NbtTranslator.getString(R.string.msg_the_repair_failed_and_the_data_was_completely_damaged) + e.getMessage());
            }
        }
    }

    public byte[] readLocalPlayer() throws Exception {
        if (db == null)
            throw new Exception(NbtTranslator.getString(R.string.msg_database_is_not_open));

        byte[] key = "~local_player".getBytes(StandardCharsets.UTF_8);
        byte[] value = db.get(key);

        if (value == null) {
            DBIterator iterator = db.iterator();
            try {
                for (iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
                    String keyStr = new String(iterator.peekNext().getKey(), StandardCharsets.UTF_8);
                    if (keyStr.contains("local_player") || keyStr.contains("player_server")) {
                        return iterator.peekNext().getValue();
                    }
                }
            } finally {
                iterator.close();
            }
            throw new Exception(NbtTranslator.getString(R.string.msg_player_data_not_found));
        }
        return value;
    }

    public byte[] readSpecificKey(String keyString) throws Exception {
        if (db == null) throw new Exception(NbtTranslator.getString(R.string.msg_dn_is_not_open));
        byte[] keyBytes = keyString.getBytes(StandardCharsets.UTF_8);
        byte[] val = db.get(keyBytes);
        if (val == null)
            throw new Exception(NbtTranslator.getString(R.string.msg_data_is_empty_colon) + keyString);
        return val;
    }

    public byte[] readRawKey(byte[] keyBytes) throws Exception {
        if (db == null) throw new Exception(NbtTranslator.getString(R.string.msg_dn_is_not_open));
        byte[] val = db.get(keyBytes);
        if (val == null)
            throw new Exception(NbtTranslator.getString(R.string.msg_the_data_corresponding_to_this_key_was_not_found));
        return val;
    }

    public void writeLocalPlayer(byte[] nbtData) throws Exception {
        if (db == null)
            throw new Exception(NbtTranslator.getString(R.string.msg_database_is_not_open));
        try {
            byte[] key = "~local_player".getBytes(StandardCharsets.UTF_8);
            db.put(key, nbtData);
        } catch (Throwable e) {
            throw new Exception(NbtTranslator.getString(R.string.msg_write_failed_colon) + e.toString());
        }
    }

    public void writeSpecificKey(String keyStr, byte[] data) throws Exception {
        if (db == null)
            throw new Exception(NbtTranslator.getString(R.string.msg_database_shutdown));
        byte[] k = keyStr.getBytes(StandardCharsets.UTF_8);
        db.put(k, data);
    }

    public void deleteKey(String keyStr) throws Exception {
        if (db == null)
            throw new Exception(NbtTranslator.getString(R.string.msg_database_shutdown));
        byte[] k = keyStr.getBytes(StandardCharsets.UTF_8);
        db.delete(k);
    }

    public List<String> listMapKeys() throws Exception {
        if (db == null)
            throw new Exception(NbtTranslator.getString(R.string.msg_database_shutdown));
        List<String> list = new ArrayList<>();
        DBIterator iterator = db.iterator();
        try {
            byte[] prefix = "map_".getBytes(StandardCharsets.UTF_8);
            iterator.seek(prefix);
            while (iterator.hasNext()) {
                String keyStr = new String(iterator.peekNext().getKey(), StandardCharsets.UTF_8);
                if (!keyStr.startsWith("map_")) break;
                list.add(keyStr);
                iterator.next();
            }
        } finally {
            iterator.close();
        }
        Collections.sort(list);
        return list;
    }

    public List<String> listPlayerKeys() throws Exception {
        if (db == null)
            throw new Exception(NbtTranslator.getString(R.string.msg_database_shutdown));
        List<String> players = new ArrayList<>();
        players.add("~local_player");
        DBIterator iterator = db.iterator();
        try {
            byte[] prefix = "player".getBytes(StandardCharsets.UTF_8);
            iterator.seek(prefix);
            while (iterator.hasNext()) {
                String keyStr = new String(iterator.peekNext().getKey(), StandardCharsets.UTF_8);
                if (!keyStr.startsWith("player")) break;
                if (keyStr.startsWith("player_") || keyStr.equals("player")) {
                    players.add(keyStr);
                }
                iterator.next();
            }
        } finally {
            iterator.close();
        }
        Collections.sort(players);
        return players;
    }

    public List<String> listVillageKeys() throws Exception {
        if (db == null)
            throw new Exception(NbtTranslator.getString(R.string.msg_database_shutdown));
        List<String> list = new ArrayList<>();
        DBIterator iterator = db.iterator();
        try {
            byte[] prefix = "VILLAGE_".getBytes(StandardCharsets.UTF_8);
            iterator.seek(prefix);
            while (iterator.hasNext()) {
                String keyStr = new String(iterator.peekNext().getKey(), StandardCharsets.UTF_8);
                if (!keyStr.startsWith("VILLAGE_")) break;
                list.add(keyStr);
                iterator.next();
            }
        } finally {
            iterator.close();
        }
        Collections.sort(list);
        return list;
    }

    public void close() {
        try {
            if (db != null) db.close();
        } catch (Throwable e) {
        }
    }
}