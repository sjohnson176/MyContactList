package com.example.mycontactlist;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;

public class DatePickerDialog extends DialogFragment {

    public interface SaveDateListener {

        void didFinishDatePickerDialog(Calendar selectedTime);

    }

    public DatePickerDialog() {
        // empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.select_date, container);

        getDialog().setTitle("Select Date");

        final DatePicker dp = (DatePicker)view.findViewById(R.id.birthdayPicker);

        Button saveButton = (Button) view.findViewById(R.id.buttonSelect);
        saveButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Calendar selectedTime =  Calendar.getInstance();
                int selectedYear = dp.getYear();
                int selectedMonth = dp.getMonth();
                int selectedDayOfMonth = dp.getDayOfMonth();
                selectedTime.set(selectedYear, selectedMonth, selectedDayOfMonth);
                saveItem(selectedTime);

            }

        });

        Button cancelButton = (Button) view.findViewById(R.id.buttonCancel);
        cancelButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                getDialog().dismiss();

            }

        });

        return view;

    }

    private void saveItem(Calendar selectedTime) {

        SaveDateListener activity = (SaveDateListener) getActivity();

        activity.didFinishDatePickerDialog(selectedTime);
        getDialog().dismiss();

    }

}
