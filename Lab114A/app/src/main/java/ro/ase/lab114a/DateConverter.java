package ro.ase.lab114a;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public Date fromTimeStamp(Long value)
    {
        return value == null ? null : new Date(value);




    }
    @TypeConverter
    public Long dateToTimeStamp(Date date)
    {
        if(date == null)
        {
            return null;
        }
        return date.getTime();

    }


}
