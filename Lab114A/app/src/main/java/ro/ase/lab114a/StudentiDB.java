package ro.ase.lab114a;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class}, version = 1, exportSchema = false)
public abstract class StudentiDB extends RoomDatabase {
    




}
