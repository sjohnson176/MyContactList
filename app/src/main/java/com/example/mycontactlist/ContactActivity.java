package com.example.mycontactlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ToggleButton;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.text.format.DateFormat;
import android.os.Bundle;


import java.util.Calendar;

public class ContactActivity extends AppCompatActivity implements DatePickerDialog.SaveDateListener {
    private Contact currentContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initListButton();
        initMapButton();
        initSettingsButton();
        initToggleButton();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            initContact(extras.getInt("contactid"));

        }
        else {
            currentContact = new Contact();

        }
        setForEditing(false);
        initChangeDateButton();
        initTextChangedEvents();
        initSaveButton();

    }

    private void initListButton() {

        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(ContactActivity.this,
                        ContactListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }

        });

    }

    private void initMapButton() {

        ImageButton ibMap = (ImageButton) findViewById(R.id.imageButtonMap);
        ibMap.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(ContactActivity.this,
                        ContactMapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }

        });

    }

    private void initSettingsButton() {

        ImageButton ibSettings = (ImageButton) findViewById(R.id.imageButtonSettings);
        ibSettings.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(ContactActivity.this,
                        ContactSettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }

        });

    }

    private void initToggleButton() {

        final ToggleButton editToggle = (ToggleButton)findViewById(R.id.toggleButtonEdit);
        editToggle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                setForEditing(editToggle.isChecked());
            }

        });

    }

    private void setForEditing(boolean enabled) {

        EditText editName = (EditText) findViewById(R.id.editName);
        EditText editAddress = (EditText) findViewById(R.id.editAddress);
        EditText editCity = (EditText) findViewById(R.id.editCity);
        EditText editState = (EditText) findViewById(R.id.editState);
        EditText editZipCode = (EditText) findViewById(R.id.editZipCode);
        EditText editHome = (EditText) findViewById(R.id.editHome);
        EditText editCell = (EditText) findViewById(R.id.editCell);
        EditText editEmail = (EditText) findViewById(R.id.editEMail);
        Button buttonChange = (Button) findViewById(R.id.btnBirthday);
        Button buttonSave = (Button) findViewById(R.id.buttonSave);

        editName.setEnabled(enabled);
        editAddress.setEnabled(enabled);
        editCity.setEnabled(enabled);
        editState.setEnabled(enabled);
        editZipCode.setEnabled(enabled);
        editHome.setEnabled(enabled);
        editCell.setEnabled(enabled);
        editEmail.setEnabled(enabled);
        buttonChange.setEnabled(enabled);
        buttonSave.setEnabled(enabled);

        if (enabled) {

            editName.requestFocus();

        }
        else {

            ScrollView s = (ScrollView) findViewById(R.id.ScrollView);
            s.fullScroll(ScrollView.FOCUS_UP);
            s.clearFocus();

        }

    }

    @Override
    public void didFinishDatePickerDialog(Calendar selectedTime) {

        TextView birthday = (TextView) findViewById(R.id.textBirthday);
        birthday.setText(DateFormat.format("MM/dd/yyyy",
                selectedTime.getTimeInMillis()).toString());

        currentContact.setBirthday(selectedTime);

    }

    private void initChangeDateButton() {

        Button changeDate = (Button) findViewById(R.id.btnBirthday);
        changeDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentManager fm = getSupportFragmentManager();
                DatePickerDialog datePickerDialog = new DatePickerDialog();
                datePickerDialog.show(fm, "DatePick");

            }

        });
    }

    private void initTextChangedEvents() {
        final EditText etContactName = (EditText) findViewById(R.id.editName);
        etContactName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                currentContact.setContactName(etContactName.getText().toString());

            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // auto-generated method stub

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // auto-generated method stub

            }
        });

        final EditText etStreetAddress = (EditText) findViewById(R.id.editAddress);
        etStreetAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                currentContact.setStreetAddress(etStreetAddress.getText().toString());

            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // auto-generated method stub

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // auto-generated method stub

            }

        });

        final EditText etCity = (EditText) findViewById(R.id.editCity);
        etCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                currentContact.setCity(etCity.getText().toString());

            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // auto-generated method stub

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // auto-generated method stub

            }

        });

        final EditText etState = (EditText) findViewById(R.id.editState);
        etState.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            currentContact.setState(etState.getText().toString());

        }
        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            // auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // auto-generated method stub

        }

        });

        final EditText etZipCode = (EditText) findViewById(R.id.editZipCode);
        etZipCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                currentContact.setZipCode(etZipCode.getText().toString());

            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // auto-generated method stub

            }

        });

        final EditText etHome = (EditText) findViewById(R.id.editHome);
        etHome.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                currentContact.setPhoneNumber(etHome.getText().toString());

            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // auto-generated method stub

            }

        });

        final EditText etCell = (EditText) findViewById(R.id.editCell);
        etCell.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                currentContact.setCellNumber(etCell.getText().toString());

            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // auto-generated method stub

            }

        });

        final EditText etEMail = (EditText) findViewById(R.id.editEMail);
        etEMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                currentContact.setEMail(etEMail.getText().toString());

            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // auto-generated method stub

            }

        });

        etHome.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        etCell.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

    }

    private void initSaveButton() {
        Button saveButton = (Button) findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean wasSuccessful = false;
                hideKeyBoard();
                ContactDataSource ds = new ContactDataSource(ContactActivity.this);
                try {
                    ds.open();

                    if (currentContact.getContactID() == -1) {
                        wasSuccessful = ds.insertContact(currentContact);

                        if (wasSuccessful) {
                         int newId = ds.getLastContactId();
                         currentContact.setContactID(newId);
                        }

                    }
                    else {
                        wasSuccessful = ds.updateContact(currentContact);

                    }
                    ds.close();

                }
                catch (Exception e) {
                    wasSuccessful = false;

                }

                if (wasSuccessful) {
                    ToggleButton editToggle = (ToggleButton) findViewById(R.id.toggleButtonEdit);
                    editToggle.toggle();
                    setForEditing(false);

                }

            }

        });

    }

    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        EditText editName = (EditText) findViewById(R.id.editName);
        imm.hideSoftInputFromWindow(editName.getWindowToken(), 0);
        EditText editAddress = (EditText) findViewById(R.id.editAddress);
        imm.hideSoftInputFromWindow(editAddress.getWindowToken(), 0);
        EditText editCity = (EditText) findViewById(R.id.editCity);
        imm.hideSoftInputFromWindow(editCity.getWindowToken(), 0);
        EditText editState = (EditText) findViewById(R.id.editState);
        imm.hideSoftInputFromWindow(editState.getWindowToken(), 0);
        EditText editZipCode = (EditText) findViewById(R.id.editZipCode);
        imm.hideSoftInputFromWindow(editZipCode.getWindowToken(), 0);
        EditText editHome = (EditText) findViewById(R.id.editHome);
        imm.hideSoftInputFromWindow(editHome.getWindowToken(), 0);
        EditText editCell = (EditText) findViewById(R.id.editCell);
        imm.hideSoftInputFromWindow(editCell.getWindowToken(), 0);
        EditText editEMail = (EditText) findViewById(R.id.editEMail);
        imm.hideSoftInputFromWindow(editEMail.getWindowToken(), 0);

    }


    private void initContact(int id) {
        ContactDataSource ds = new ContactDataSource(ContactActivity.this);
        try {
            ds.open();
            currentContact = ds.getSpecificContact(id);
            ds.close();

        }
        catch (Exception e) {
            Toast.makeText(this, "Load Contact Failed", Toast.LENGTH_LONG).show();

        }

        EditText editName = (EditText) findViewById(R.id.editName);
        EditText editAddress = (EditText) findViewById(R.id.editAddress);
        EditText editCity = (EditText) findViewById(R.id.editCity);
        EditText editState = (EditText) findViewById(R.id.editState);
        EditText editZipCode = (EditText) findViewById(R.id.editZipCode);
        EditText editHome = (EditText) findViewById(R.id.editHome);
        EditText editCell = (EditText) findViewById(R.id.editCell);
        EditText editEmail = (EditText) findViewById(R.id.editEMail);
        TextView birthDay = (TextView) findViewById(R.id.textBirthday);

        editName.setText(currentContact.getContactName());
        editAddress.setText(currentContact.getStreetAddress());
        editCity.setText(currentContact.getCity());
        editState.setText((currentContact.getState()));
        editZipCode.setText(currentContact.getZipCode());
        editHome.setText(currentContact.getPhoneNumber());
        editCell.setText(currentContact.getCellNumber());
        editEmail.setText(currentContact.getEMail());
        birthDay.setText(DateFormat.format("MM/dd/yyyy",
                currentContact.getBirthday().getTimeInMillis()).toString());
    }
}
