package com.example.lab3_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    protected void showResult(double start, double end, int count, long num, long a, View v) {
        TextView timeText = (TextView) findViewById(R.id.TimeResult);
        TextView countText = (TextView) findViewById(R.id.CountResult);

        String finalTime = new Double(end - start).toString();
        timeText.setText("Час обрахунку:\n" + finalTime);
        countText.setText("Кількість ітерацій:\n" + count);

        layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.popupwindow, null);

        popupWindow = new PopupWindow(container, 600, 200, true);
        ((TextView)popupWindow.getContentView().findViewById(R.id.resultCount)).setText("Кількість ітерацій:\n" + count);
        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, 300, 300);
    }

    public void onButtonClick(View v) {
        EditText input = (EditText) findViewById(R.id.Input);
        TextView resultText = (TextView) findViewById(R.id.Result);
        TextView timeText = (TextView) findViewById(R.id.TimeResult);
        TextView countText = (TextView) findViewById(R.id.CountResult);

        if (input.getText().toString().trim().equals("")) {
            resultText.setText("Невірно введені дані!");
            return;
        }

        long num = Long.parseLong(input.getText().toString());
        long a = (long) Math.ceil(Math.sqrt(num));
        double end, start = System.nanoTime();
        int count = 0;

        if (a * a == num) {
            end = System.nanoTime();
            resultText.setText("√" + num + "\n[ " + a + ", " + a + " ]");
            showResult(start, end, count, num, a, v);

            return;
        }

        if ((num & 1) == 0) {
            resultText.setText("Число є парним\n[ " + num / 2 + ", " + 2 + " ]");
            end = System.nanoTime();
            showResult(start, end, count, num, a, v);

            return;
        }

        long b;
        while (true) {
            long c = a * a - num;
            b = (long) (Math.sqrt(c));

            if (b * b == c)
                break;
            count++;
            a += 1;
        }
        end = System.nanoTime();
        resultText.setText("[ " + (a - b) + ", " + (a + b) + " ]");
        showResult(start, end, count, num, a, v);

        return;
    }

    public void Clear(View v) {
        EditText input = (EditText) findViewById(R.id.Input);
        TextView result = (TextView) findViewById(R.id.Result);
        TextView timeResult = (TextView) findViewById(R.id.TimeResult);
        TextView countResult = (TextView) findViewById(R.id.CountResult);

        input.setText(null);
        result.setText(null);
        timeResult.setText(null);
        countResult.setText(null);
    }
}
