package lf.lookingfor;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.TimeZone;

public class MyEditTextTimePicker  implements View.OnClickListener {
    EditText _editText;
    private int _hours;
    private int _minutes;
    private Context _context;

    public MyEditTextTimePicker(Context context, int editTextViewID)
    {
        Activity act = (Activity)context;
        this._editText = (EditText)act.findViewById(editTextViewID);
        this._editText.setOnClickListener(this);
        this._context = context;
    }

    @Override
    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePicker = new TimePickerDialog(_context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                _editText.setText(hourOfDay + ":" + minute);
            }
        }, hour, minute, true);
        timePicker.setTitle("Select Time:");
        timePicker.show();
    }

}