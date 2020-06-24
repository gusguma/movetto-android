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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.movetto.R;
import com.movetto.dtos.DirectionDto;
import com.movetto.dtos.TravelDto;
import com.movetto.dtos.UserDto;
import com.movetto.dtos.validations.ErrorStrings;
import com.movetto.view_models.CustomerViewModel;
import com.movetto.view_models.TravelViewModel;

import org.json.JSONException;

import java.time.LocalDateTime;
import java.util.Calendar;

public class TravelDataFragment extends Fragment
        implements View.OnFocusChangeListener, View.OnClickListener{

    private static final String ZERO = "0";
    private static final String BAR = "/";
    private static final String POINTS = ":";
    private final Calendar calendar = Calendar.getInstance();
    private int yearSelected = calendar.get(Calendar.YEAR);
    private int monthSelected = calendar.get(Calendar.MONTH);
    private int daySelected = calendar.get(Calendar.DAY_OF_MONTH);
    private int hourSelected = calendar.get(Calendar.HOUR_OF_DAY);
    private int minuteSelected = calendar.get(Calendar.MINUTE);

    private View root;
    private CustomerViewModel customerViewModel;
    private TravelViewModel travelViewModel;
    private EditText people;
    private EditText date;
    private EditText time;
    private ImageButton buttonDate;
    private ImageButton buttonTime;
    private Bundle data;
    private DirectionDto directionStart;
    private DirectionDto directionEnd;
    private UserDto customer;
    private TravelDto travel;
    private LocalDateTime travelStart;
    private Button buttonSave;

    public TravelDataFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater, container);
        setComponents();
        getBundleData();
        setCustomer();
        setFormFieldsListener();
        return root;
    }
    private void setViewModels() {
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        travelViewModel = new ViewModelProvider(this).get(TravelViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_travel_data, container, false);
    }

    private void setComponents() {
        people = root.findViewById(R.id.travel_data_people_edit);
        date = root.findViewById(R.id.travel_data_date_edit);
        time = root.findViewById(R.id.travel_data_hour_edit);
        buttonDate = root.findViewById(R.id.travel_data_date_button);
        buttonTime = root.findViewById(R.id.travel_data_hour_button);
        buttonSave = root.findViewById(R.id.travel_data_save_button);
        date.setText(formatDate());
    }

    private void setFormFieldsListener() {
        people.setOnFocusChangeListener(this);
        date.setOnFocusChangeListener(this);
        time.setOnFocusChangeListener(this);
        buttonDate.setOnClickListener(this);
        buttonTime.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
    }

    private void getBundleData() {
        data = getArguments();
        if (data != null && data.getSerializable("directionStart") != null){
            directionStart = (DirectionDto) data.getSerializable("directionStart");
        }
        if (data != null && data.getSerializable("directionEnd") != null){
            directionEnd = (DirectionDto) data.getSerializable("directionEnd");
        }
    }

    private void setCustomer(){
        customerViewModel.readCustomer().observe(getViewLifecycleOwner(), new Observer<UserDto>() {
            @Override
            public void onChanged(UserDto userDto) {
                if (userDto != null) {
                    customer = userDto;
                    setTravel();
                }
            }
        });
    }

    private void setTravel(){
        travel = new TravelDto(
                customer,directionStart,directionEnd
        );
    }

    private boolean isFormValidate(){
        boolean isValidate = true;
        EditText[] editTexts = new EditText[]{
                people,date,time
        };
        for (EditText editText:editTexts) {
            String text = editText.getText().toString();
            CharSequence error = editText.getError();
            if (error != null || text.isEmpty())
                isValidate = false;
        }
        return isValidate;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText editText = (EditText) v;
        if (editText.hasFocus()){
            editText.setError(null);
        } else {
            if(editText.getText().toString().isEmpty()){
                editText.setError(ErrorStrings.EMPTY);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.travel_data_date_button){
            setDate();
        }
        if (v.getId() == R.id.travel_data_hour_button){
            setTime();
        }
        if (v.getId() == R.id.travel_data_save_button && isFormValidate()){
            updateTravel();
        }
    }

    private String formatDate() {
        return dateFormat(daySelected) + BAR
                + dateFormat(monthSelected + 1) + BAR + yearSelected;
    }

    private void setDate(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(root.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                yearSelected = year;
                monthSelected = month;
                daySelected = dayOfMonth;
                date.setText(formatDate());
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

    private void updateTravel(){
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
                        saveTravel();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                } else {
                    getResponseError();
                }
            }
        });
    }

    private void saveTravel() throws JsonProcessingException, JSONException {
        travelViewModel.saveTravel(travel).observe(getViewLifecycleOwner(), new Observer<TravelDto>() {
            @Override
            public void onChanged(TravelDto travelDto) {
                if (travelDto != null) {
                    travel = travelDto;
                    System.out.println(travel.getDistance());
                    getResponseOk();
                } else {
                    getResponseError();
                }
            }
        });
    }

    private void getResponseOk(){
        data.putInt("travelId", travel.getId());
        data.putInt("serviceId", travel.getId());
        Navigation.findNavController(root).navigate(
                R.id.action_nav_travel_data_to_nav_travel_detail, data);
        Toast.makeText(root.getContext()
                ,"Viaje Guardado Correctamente",Toast.LENGTH_LONG).show();
    }

    private void getResponseError(){
        Navigation.findNavController(root).navigate(
                R.id.action_nav_travel_people_to_nav_travel_empty, data);
        Toast.makeText(root.getContext()
                ,"No se ha podido guardar el Viaje",Toast.LENGTH_LONG).show();
    }
}
