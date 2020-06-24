package com.movetto.activities.ui.travel;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.movetto.R;
import com.movetto.dtos.TravelDto;
import com.movetto.dtos.TravelStatus;
import com.movetto.dtos.validations.ErrorStrings;
import com.movetto.view_models.TravelViewModel;

import org.json.JSONException;

import java.time.LocalDateTime;
import java.util.Calendar;

public class TravelDetailDataFragment extends Fragment
        implements View.OnFocusChangeListener, View.OnClickListener{

    private static final String ZERO = "0";
    private static final String BAR = "/";
    private static final String POINTS = ":";
    private int yearSelected;
    private int monthSelected;
    private int daySelected;
    private int hourSelected;
    private int minuteSelected;

    private View root;
    private TravelViewModel travelViewModel;
    private EditText people;
    private EditText date;
    private EditText time;
    private ImageButton buttonDate;
    private ImageButton buttonTime;
    private FloatingActionButton buttonSave;
    private int travelId;
    private TravelDto travel;
    private LocalDateTime travelStart;
    private long duration;
    private Bundle data;

    public TravelDetailDataFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater, container);
        setComponents();
        setFormFieldsListener();
        setTravelId();
        setTravelDataInput();
        setCalendar();
        return root;
    }

    private void setViewModels() {
        travelViewModel = new ViewModelProvider(this).get(TravelViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_travel_detail_data, container, false);
    }

    private void setComponents() {
        people = root.findViewById(R.id.travel_detail_data_people_edit);
        date = root.findViewById(R.id.travel_detail_data_date_edit);
        time = root.findViewById(R.id.travel_detail_data_hour_edit);
        buttonDate = root.findViewById(R.id.travel_detail_data_date_button);
        buttonTime = root.findViewById(R.id.travel_detail_data_hour_button);
        buttonSave = root.findViewById(R.id.trave_detail_data_save_button);
    }

    private void setFormFieldsListener() {
        people.setOnFocusChangeListener(this);
        date.setOnFocusChangeListener(this);
        time.setOnFocusChangeListener(this);
        buttonDate.setOnClickListener(this);
        buttonTime.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
    }

    private void setTravelId() {
        data = getArguments();
        if (data != null && data.getInt("travelId") != 0) {
            travelId = data.getInt("travelId");
            data.putInt("serviceId", travelId);
        }
    }

    private void setTravelDataInput() {
        travelViewModel.readTravelById(travelId).observe(getViewLifecycleOwner(), new Observer<TravelDto>() {
            @Override
            public void onChanged(TravelDto travelDto) {
                travel = travelDto;
                people.setText(String.valueOf(travel.getPeople()));
                yearSelected = travel.getStart().getYear();
                monthSelected = travel.getStart().getMonthValue();
                daySelected = travel.getStart().getDayOfMonth();
                hourSelected = travel.getStart().getHour();
                minuteSelected = travel.getStart().getMinute();
                duration = travel.getTravelDuration();
                date.setText(setDateInput());
                time.setText(setTimeInput());
                checkTravelStatus();
            }
        });
    }

    private void checkTravelStatus(){
        if (travel.getStatus() != TravelStatus.SAVED) {
            people.setEnabled(false);
            buttonDate.setVisibility(View.GONE);
            buttonTime.setVisibility(View.GONE);
            buttonSave.setVisibility(View.GONE);
        }
    }

    private String setDateInput(){
        return dateFormat(daySelected) + BAR
                + dateFormat(monthSelected + 1) + BAR
                + yearSelected;
    }

    private String setTimeInput(){
        return dateFormat(hourSelected) + POINTS + dateFormat(minuteSelected);
    }

    private void setCalendar() {
        Calendar c = Calendar.getInstance();
        yearSelected = c.get(Calendar.YEAR);
        monthSelected = c.get(Calendar.MONTH);
        daySelected = c.get(Calendar.DAY_OF_MONTH);
    }

    private void setDate(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(root.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                yearSelected = year;
                monthSelected = month;
                daySelected = dayOfMonth;
                String dateFormatted = dateFormat(daySelected) + BAR
                        + dateFormat(monthSelected + 1) + BAR + yearSelected;
                date.setText(dateFormatted);
            }
        },yearSelected,monthSelected,daySelected);
        datePickerDialog.show();
    }

    private void setTime(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(root.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String timeFormatted =  dateFormat(hourOfDay) + POINTS + dateFormat(minute);
                time.setText(timeFormatted);
                hourSelected = hourOfDay;
                minuteSelected = minute;
            }
        }, hourSelected,minuteSelected, true);
        timePickerDialog.show();
    }

    private String dateFormat(int time) {
        return (time < 10) ? ZERO + time : String.valueOf(time);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText editText = (EditText) v;
        if (editText.hasFocus())
            editText.setError(null);
        if (editText.getText().toString().isEmpty()) {
            editText.setError(ErrorStrings.EMPTY);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.travel_detail_data_date_button){
            setDate();
        }
        if (v.getId() == R.id.travel_detail_data_hour_button){
            setTime();
        }
        if (v.getId() == R.id.trave_detail_data_save_button && isFormValidate()){
            updateData();
        }
    }

    private boolean isFormValidate() {
        boolean isValidate = true;
        EditText[] editTexts = new EditText[]{
                date, time, people
        };
        for (EditText editText : editTexts) {
            String text = editText.getText().toString();
            CharSequence error = editText.getError();
            if (error != null || text.isEmpty())
                isValidate = false;
        }
        return isValidate;
    }

    private void updateData(){
        String peopleSelected = people.getText().toString();
        travelStart = LocalDateTime.of(
                yearSelected,monthSelected,daySelected,hourSelected,minuteSelected);
        travel.setPeople(Integer.parseInt(peopleSelected));
        travel.setStart(travelStart);
        setDistance();
    }

    private void setDistance(){
        travelViewModel.getTravelDistance(travel).observe(getViewLifecycleOwner(), new Observer<TravelDto>() {
            @Override
            public void onChanged(TravelDto travelDto) {
                if (travelDto != null) {
                    travel = travelDto;
                    try {
                        updateTravel();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                } else {
                    updateTravelError();
                }
            }
        });
    }

    private void updateTravel() throws JsonProcessingException, JSONException {
        travelViewModel.updateTravel(travel).observe(getViewLifecycleOwner(), new Observer<TravelDto>() {
            @Override
            public void onChanged(TravelDto travelDto) {
                if (travelDto != null) {
                    updateTravelOk();
                } else {
                    updateTravelError();
                }
            }
        });
    }

    private void updateTravelOk() {
        Navigation.findNavController(root).navigate(
                R.id.action_nav_travel_detail_self, data);
        Toast.makeText(root.getContext(),
                "Viaje Actualizado",
                Toast.LENGTH_LONG).show();
    }

    private void updateTravelError() {
        Navigation.findNavController(root).navigate(
                R.id.action_nav_travel_detail_self, data);
        Toast.makeText(root.getContext()
                , "No se ha podido actualizar el viaje",
                Toast.LENGTH_LONG).show();
    }
}
