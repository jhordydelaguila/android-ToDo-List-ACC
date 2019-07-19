package com.example.delaguila.todolistaac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.delaguila.todolistaac.database.AppDatabase;
import com.example.delaguila.todolistaac.database.TaskEntry;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    private static final String TAG = AddTaskActivity.class.getSimpleName();
    private static final int PRIORITY_HIGH = 1;
    private static final int PRIORITY_MEDIUM = 2;
    private static final int PRIORITY_LOW = 3;

    EditText mEditTex;
    RadioGroup mRadioGroup;
    Button mButton;

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        initViews();

        mDb = AppDatabase.getInstance(getApplicationContext());

    }

    private void initViews() {
        mEditTex = findViewById(R.id.editTextTaskDescription);
        mRadioGroup = findViewById(R.id.radioGroup);

        mButton = findViewById(R.id.saveButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
    }

    private void onSaveButtonClicked() {
        String description = mEditTex.getText().toString();
        int priority = getPriorityFromViews();
        Date date = new Date();

        final TaskEntry taskEntry = new TaskEntry(description, priority, date);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.taskDao().insertTask(taskEntry);
                finish();
            }
        });
    }

    private int getPriorityFromViews() {
        int priority = 1;
        int checkedId = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.radButton1:
                priority = PRIORITY_HIGH;
                break;
            case R.id.radButton2:
                priority = PRIORITY_MEDIUM;
                break;
            case R.id.radButton3:
                priority = PRIORITY_LOW;
                break;
        }

        return priority;
    }

}
