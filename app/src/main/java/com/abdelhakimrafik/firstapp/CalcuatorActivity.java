package com.abdelhakimrafik.firstapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CalcuatorActivity extends AppCompatActivity {

    private TextView tv_history;
    private TextView tv_value;
    private Button btnClear;

    private int bracketsLeve = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_layout);

        // affect elements
        tv_history = findViewById(R.id.tv_history);
        tv_value = findViewById(R.id.tv_value);

        // assing clear btn
        btnClear = findViewById(R.id.btn_clear);

        btnClear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                tv_value.setText("");
                tv_history.setText("");
                return true;
            }
        });
    }

    public void btnNumHandler(View view) {
        tv_value.append(((Button) view).getText());
    }

    public void btnOpsHandler(View view) {
        String op = ((Button) view).getText().toString();
        String val = tv_value.getText().toString();

        if(val.length() > 0) {
            char lastChar = val.charAt(val.length() - 1);
            if (Character.isDigit(lastChar) || lastChar == ')') {
                tv_value.append(op);
            }
        }
    }

    public void btnBracketsHandler(View view) {
        String val = tv_value.getText().toString();
        if(val.length() > 0) {
            char lastChar = val.charAt(val.length() - 1);
            if (!Character.isDigit(lastChar) && lastChar != ')') {
                tv_value.append("(");
                bracketsLeve++;
            }
            else if(bracketsLeve > 0) {
                tv_value.append(")");
                bracketsLeve--;
            }
        }
        else {
            tv_value.append("(");
            bracketsLeve++;
        }
    }

    public void btnEqualHandler(View view) {
        String exp = tv_value.getText().toString();
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine se = sem.getEngineByName("javascript");
        double res = 0;
        try {
             res = (double) se.eval(exp);
        } catch (ScriptException e) {
            e.printStackTrace();
        }

        tv_history.append(exp + " = " + Double.toString(res) + "\n");
        tv_value.setText(Double.toString(res));
        bracketsLeve = 0;
    }

    public void btnClearHandler(View view) {
        String val = tv_value.getText().toString();
        if(val.length() > 0)
            tv_value.setText(val.substring(0, val.length()-1));
    }
}
