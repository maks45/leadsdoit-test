package com.leadsdoit.test.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.leadsdoit.test.dao.UserDao;
import com.leadsdoit.test.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class SlotDb extends RoomDatabase {
    public abstract UserDao getUserDao();
}
