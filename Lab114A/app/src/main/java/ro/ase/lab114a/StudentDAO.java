package ro.ase.lab114a;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface StudentDAO {

    @Insert
    void insert(Student student);

    @Insert
    void insert(List<Student> studentList);

    @Query("select * from studenti")
    List<Student> getAll();

    @Query("delete from studenti")
    void deleteAll();

    @Delete
    void delete(Student student);

    @Update
    void update(Student student);

    @Query("select * from studenti where nume=:name")
    List<Student> getAllByName(String name);
}
