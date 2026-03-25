package ro.ase.lab114a;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Student.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class StudentiDB extends RoomDatabase {
    public static final String DB_NAME = "studenti.db";
    private static StudentiDB instanta;

    public static StudentiDB getInstanta(Context context)
    {
        if(instanta == null)
        {
            instanta = Room.databaseBuilder(context, StudentiDB.class, DB_NAME)
                    .allowMainThreadQueries() //Eu pot sa execut Insert/Update/Delete pe thread-ul principal
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return instanta;
    }


    public abstract StudentDAO getStudentDao();





}
