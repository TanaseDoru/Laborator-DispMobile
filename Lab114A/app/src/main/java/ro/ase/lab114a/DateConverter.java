package ro.ase.lab114a;

import java.util.Date;

import androidx.room.TypeConverter;

public class DateConverter {

    @TypeConverter
    public Date fromTimeStamp(Long value)
    {
        return value==null? null: new Date(value);
    }

    @TypeConverter
    public Long dateToTimeStamp(Date date)
    {
        if(date==null)
            return null;
        else
            return date.getTime();
    }
}
