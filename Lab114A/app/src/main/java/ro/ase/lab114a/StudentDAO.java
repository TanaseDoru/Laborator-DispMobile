package ro.ase.lab114a;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDAO { // DAO = Data Access Object

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

    @Query("select * from studenti where nume = :name")
    List<Student> getAllByName(String name);


}
