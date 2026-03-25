package ro.ase.lab114a;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Student.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class StudentiDB extends RoomDatabase {
    




}
