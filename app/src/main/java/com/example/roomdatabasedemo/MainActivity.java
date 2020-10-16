package com.example.roomdatabasedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.roomdatabasedemo.databinding.ActivityMainBinding;
import com.example.roomdatabasedemo.model.Employee;
import com.facebook.stetho.Stetho;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mDb = AppDatabase.getInMemoryDatabase(getApplicationContext());
        //populateEmployList();
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hàm xử lý click
                String name = binding.etName.getText().toString();
                String designation = binding.etDesignation.getText().toString();

                Employee employee = new Employee();
                employee.name = name;
                employee.designation = designation;

                mDb.employDao().insertEmploy(employee);
                Toast.makeText(MainActivity.this, "Saved successfully", Toast.LENGTH_SHORT).show();
                binding.etName.setText("");
                binding.etDesignation.setText("");
                binding.etName.requestFocus();
                populateEmployList();
            }
        });
    }

    private void populateEmployList() {
        binding.txtList.setText("");
        List<Employee> employeeList = mDb.employDao().findAllEmploySync();
        for (Employee employee : employeeList) {
            binding.txtList.append(employee.name + " : " + employee.designation);
            binding.txtList.append("\n");
        }
    }
}