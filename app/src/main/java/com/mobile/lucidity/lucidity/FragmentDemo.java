package com.mobile.lucidity.lucidity;

/**
 * Java class for demographics tab
 */
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class FragmentDemo extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_demo, container, false);

        calendarDateMask(rootView);

        String[] educations = new String[]{"No schooling completed",
                "Nursery school to 8th grade",
                "Some high school, no diploma",
                "High school graduate or equivalent",
                "Some college credit, no degree",
                "Trade/technical/vocational training",
                "Associate degree",
                "Bachelor’s degree",
                "Master’s degree",
                "Professional degree",
                "Doctorate degree"};

        String[] ethnicities = new String[]{"White",
                "Hispanic or Latino",
                "Black or African American",
                "Native American or American Indian",
                "Asian / Pacific Islander",
                "Other",
                "More than one"};

        String[] marital = new String[]{"Single, never married",
                "Married or domestic partnership",
                "Widowed",
                "Divorced",
                "Separated"};

        maintainChecked(rootView);
        setSpinner(rootView, educations, R.id.education, "Select Your Education");
        setSpinner(rootView, ethnicities, R.id.ethnicity, "Select Your Ethnicity");
        setSpinner(rootView, marital, R.id.marital, "Select Your Marital Status");



        return rootView;
    }


    public void maintainChecked(View rootView){
        final RadioButton rb_male = (RadioButton) rootView.findViewById(R.id.sex_male);
        final RadioButton rb_female = (RadioButton) rootView.findViewById(R.id.sex_female);
        final RadioButton rb_other = (RadioButton) rootView.findViewById(R.id.sex_other);

        /*rb_male.isChecked();
        rb_male.setChecked(false);*/

        rb_male.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rb_female.setChecked(false);
                rb_other.setChecked(false);
            }
        });

        rb_female.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rb_male.setChecked(false);
                rb_other.setChecked(false);
            }
        });

        rb_other.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rb_male.setChecked(false);
                rb_female.setChecked(false);
            }
        });

    }

    public void setSpinner(View rootView, String[] content, int id, String prompt){
        final Spinner spinner = rootView.findViewById(id);

        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), android.R.layout.simple_list_item_1);
        adapter.addAll(content);
        adapter.add(prompt);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });


    }

    public void calendarDateMask(View rootView){
        //TODO: make calendar view of Date input field
        /*TextWatcher tw = new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();

            //https://stackoverflow.com/questions/16889502/how-to-mask-an-edittext-to-show-the-dd-mm-yyyy-date-format
        };

        EditText date = (EditText)rootView.findViewById(R.id.birth_date);
        date.addTextChangedListener(tw);*/

    }


}
