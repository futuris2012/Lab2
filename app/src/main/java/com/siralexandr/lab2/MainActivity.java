package com.siralexandr.lab2;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Locale;

import javax.xml.datatype.Duration;

public class MainActivity extends AppCompatActivity  {
    RadioGroup rg;
    Spinner spinner;
    Toast t;
    EditText editText1;
    TextView meter,santi,kilo,inch,feet,mile;
    private Locale NewLocale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

//
       //
               super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rg=(RadioGroup)findViewById(R.id.rg) ;
        spinner=(Spinner)findViewById(R.id.spinner);
        editText1=(EditText)findViewById(R.id.editText);
        meter=(TextView)findViewById(R.id.meter);
        santi=(TextView)findViewById(R.id.santimeter);
        kilo=(TextView)findViewById(R.id.kilometer);
        inch=(TextView)findViewById(R.id.inch);
        feet=(TextView)findViewById(R.id.feet);
        mile=(TextView)findViewById(R.id.mile);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                String edit=editText1.getText().toString();
                Button b=(Button)findViewById(rg.getCheckedRadioButtonId());
                String str=b.getText().toString();
                if(edit.equals("")){editText1.setText("0");edit="0";}
                BigDecimal data=toMeter(str,edit);
                fromMetersToAll(data);
            }
        });
        editText1.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                String edit=editText1.getText().toString();
                Button b=(Button)findViewById(rg.getCheckedRadioButtonId());
                String str=b.getText().toString();
                if(edit.equals("")){s.append('0');edit="0";}
                BigDecimal data=toMeter(str,edit);
                fromMetersToAll(data);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.langs);
                if(choose[selectedItemPosition].equals("Russian")){
                    setLocale("ru");}
                else if(choose[selectedItemPosition].equals("Английский")){
                    setLocale("en");}
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    public void fromMetersToAll(BigDecimal data){
        meter.setText(data.toString());

        santi.setText(data.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_CEILING).toString());
        kilo.setText(data.divide(new BigDecimal(1000),2,BigDecimal.ROUND_CEILING).toString());
        feet.setText(data.divide(new BigDecimal(0.3048),2,BigDecimal.ROUND_CEILING).toString());
        inch.setText(data.divide(new BigDecimal(0.0254),2,BigDecimal.ROUND_CEILING).toString());
        mile.setText(data.divide(new BigDecimal(1609.344),2,BigDecimal.ROUND_CEILING).toString());
    }

    public BigDecimal toMeter(String s,String s1){
        String str=getString(R.string.meter);
        if(s.equals(getString(R.string.meter))|s.equals("Метр")){return new BigDecimal(s1);}
        else if(s.equals(getString(R.string.santimeter))|s.equals("Сантиметр")){return new BigDecimal(s1).divide(new BigDecimal(100),2,BigDecimal.ROUND_CEILING);}
        else if(s.equals(getString(R.string.kilometer))|s.equals("Километр")){return new BigDecimal(s1).multiply(new BigDecimal(1000).setScale(2,BigDecimal.ROUND_CEILING));}
        else if(s.equals(getString(R.string.feet))|s.equals("Фут")){return new BigDecimal(s1).multiply(new BigDecimal(0.3048).setScale(2,BigDecimal.ROUND_CEILING));}
        else if(s.equals(getString(R.string.inch))|s.equals("Дюйм")){return new BigDecimal(s1).multiply(new BigDecimal(0.0254).setScale(2,BigDecimal.ROUND_CEILING));}
        else if(s.equals(getString(R.string.mile))|s.equals("Миля")){return new BigDecimal(s1).multiply(new BigDecimal(1609.344).setScale(2,BigDecimal.ROUND_CEILING));}
        else{return new BigDecimal(0);}
    }
    void setLocale(String mLang) {
        NewLocale = new Locale(mLang);
        Locale.setDefault(NewLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = NewLocale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        restart();
    }
    protected void restart() {
        Intent intent = getIntent();
        finish();

        startActivity(intent);
    }
}
