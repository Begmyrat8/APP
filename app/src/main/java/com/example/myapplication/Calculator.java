package com.example.myapplication;

import static com.example.myapplication.R.id.btn0;
import static com.example.myapplication.R.id.btn1;
import static com.example.myapplication.R.id.btn2;
import static com.example.myapplication.R.id.btn3;
import static com.example.myapplication.R.id.btn4;
import static com.example.myapplication.R.id.btn5;
import static com.example.myapplication.R.id.btn6;
import static com.example.myapplication.R.id.btn7;
import static com.example.myapplication.R.id.btn8;
import static com.example.myapplication.R.id.btn9;
import static com.example.myapplication.R.id.btnDivide;
import static com.example.myapplication.R.id.btnDot;
import static com.example.myapplication.R.id.btnMinus;
import static com.example.myapplication.R.id.btnMultiply;
import static com.example.myapplication.R.id.btnPlus;
import static com.example.myapplication.R.id.btnPlusMinus;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class Calculator extends AppCompatActivity {

    boolean isNew = true;
    TextView solution_tv,result_tv;
    String op ;
    String firstNumber;
    Button C;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        solution_tv = findViewById(R.id.solution_tv);
        result_tv = findViewById(R.id.result_tv);
        C = findViewById(R.id.btnC);

        C.setOnLongClickListener(v -> {
            solution_tv.setText("0");
            return true;
        });

    }


    @SuppressLint("NonConstantResourceId")
    public void numberClick(View view) {
        if (isNew) {
            solution_tv.setText("");
        }
        isNew = false;
        String number = solution_tv.getText().toString();

        switch (view.getId()){
            case btn1:
                if (zeroIsFirst(number) && number.length() == 1){
                    number = number.substring(1);
                }
                number += "1";break;
            case btn2:
                if (zeroIsFirst(number) && number.length() == 1){
                    number = number.substring(1);
                }
                number += "2";break;
            case btn3:
                if (zeroIsFirst(number) && number.length() == 1){
                    number = number.substring(1);
                }
                number += "3";break;
            case btn4:
                if (zeroIsFirst(number) && number.length() == 1){
                    number = number.substring(1);
                }
                number += "4";break;
            case btn5:
                if (zeroIsFirst(number) && number.length() == 1){
                    number = number.substring(1);
                }
                number += "5";break;
            case btn6:
                if (zeroIsFirst(number) && number.length() == 1){
                    number = number.substring(1);
                }
                number += "6";break;
            case btn7:
                if (zeroIsFirst(number) && number.length() == 1){
                    number = number.substring(1);
                }
                number += "7";break;
            case btn8:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                    number += "8";break;
            case btn9:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number += "9";break;

            case btn0:
                if (zeroIsFirst(number) && number.length() == 1){
                    number = "0";
                }else {
                    number += "0";
                }
                break;

            case btnDot:
                if (!Dot(number)) {
                    number += ".";}
                break;

            case btnPlusMinus:
                if (numberIsZero(number)){
                    number = "0";
                }else {
                    if (PlusMinus(number)){
                        number = number.substring(1);
                    }else{
                        number = "-" + number;
                    }break;
                }
        }
        solution_tv.setText(number);
    }

    private boolean zeroIsFirst(String number){
        if (number.equals("")){
            return true;
        }
        return number.charAt(0) == '0';
    }

    public boolean numberIsZero(String number){
        return number.equals("0") || number.equals("");
    }

    public boolean PlusMinus(String number){
        return number.charAt(0) == '-';
    }

    @SuppressLint("NonConstantResourceId")
    public void operation(View view) {
        isNew = true;
        firstNumber = solution_tv.getText().toString();
        switch (view.getId()){
            case btnPlus: op = "+"; break;
            case btnMinus: op = "-"; break;
            case btnMultiply: op = "*"; break;
            case btnDivide: op = "/"; break;
        }
    }

    @SuppressLint("SetTextI18n")
    public void equalClick(View view) {
        Double result = 0.0;
        String secondNumber = solution_tv.getText().toString();

        if (Double.parseDouble(secondNumber) < 0.00000000000000001 && op.equals("/") || secondNumber.equals("") && op.equals("/")) {
            Toast.makeText(this, "На ноль делить нелзя", Toast.LENGTH_LONG).show();
        } else {
            switch (op) {

                case "+":
                    result = Double.parseDouble(firstNumber) + Double.parseDouble(secondNumber);
                    break;
                case "-":
                    result = Double.parseDouble(firstNumber) - Double.parseDouble(secondNumber);
                    break;
                case "*":
                    result = Double.parseDouble(firstNumber) * Double.parseDouble(secondNumber);
                    break;
                case "/":
                    result = Double.parseDouble(firstNumber) / Double.parseDouble(secondNumber);
                    break;
                default:
                    break;
            }
            DecimalFormat format = new DecimalFormat();
            solution_tv.setText(format.format(result));
            result_tv.setText(firstNumber + op + secondNumber + " = " + format.format(result));
        }
    }

    public void acClick(View view) {
        isNew = true;
        solution_tv.setText("0");
        result_tv.setText("0");
    }

    public boolean Dot (String number){
        return number.contains(".");
    }

    public void cClick(View view) {
        String val = solution_tv.getText().toString();
        if (val.equals("")){
            solution_tv.setText("0");
        }else {
            val = val.substring(0, val.length() - 1);
            solution_tv.setText(val);
        }
    }

    public void percentClick(View view) {

        if (op.equals("")){
            String number = solution_tv.getText().toString();
            double temp = Double.parseDouble(number) / 100;
            number = temp + "";
            solution_tv.setText(number);
            result_tv.setText(number);
        }else {
            Double  result = 0.0;
            String secondNumber = solution_tv.getText().toString();
            switch (op) {
                case "+":
                    result = Double.parseDouble(firstNumber) + Double.parseDouble(firstNumber) * Double.parseDouble(secondNumber) / 100;
                    break;
                case "-":
                    result = Double.parseDouble(firstNumber) - Double.parseDouble(firstNumber) * Double.parseDouble(secondNumber) / 100;
                    break;
                case "*":
                    result = Double.parseDouble(firstNumber) * Double.parseDouble(firstNumber) * Double.parseDouble(secondNumber) / 100;
                    break;
                case "/":
                    result = Double.parseDouble(firstNumber) / Double.parseDouble(firstNumber) * Double.parseDouble(secondNumber) * 100;
                    break;
            }
            DecimalFormat format = new DecimalFormat("");
            solution_tv.setText(format.format(result));
            result_tv.setText(format.format(result));
            op = "";
        }
    }



}