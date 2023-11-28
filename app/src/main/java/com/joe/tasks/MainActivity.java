package com.joe.tasks;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    ListView listview;
    ArrayList listTask;
    MyCustomAdapter adapter;
    Button addTask;
    EditText textTask;
    Base base = new Base(MainActivity.this);
    Base done = new Base(MainActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.list_view);
        addTask = (Button) findViewById(R.id.addTask);
        textTask = (EditText) findViewById(R.id.textTask);
        listTask = new ArrayList();
        adapter = new MyCustomAdapter(listTask);
        listview.setAdapter(adapter);
        setViewTask();
        addTask.setOnClickListener(v -> {
            // check if textTask is empty
            if(textTask.getText().toString().isEmpty()){
                // show hint if textTask is empty
                textTask.setHint("Please enter a task");
                return;
            }
            // add task to database
            base.insetTask(textTask.getText().toString());
            setViewTask();
            // reset textTask
            textTask.setText("");
            // close keyboard after add task
            InputMethodManager imm = (InputMethodManager) getSystemService(MainActivity.this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(addTask.getWindowToken(), 0);
        });
    }
    public void setViewTask(){
        listTask.removeAll(listTask);
        Cursor c = base.getAllTask();
        for (int i = 0; i < c.getCount(); i++) {
            // Position the cursor
            c.moveToNext();
            // Fetch your data values
            int ids = c.getInt(c.getColumnIndexOrThrow("id"));
            String textTask = c.getString(c.getColumnIndexOrThrow("textTask"));
            int isCompleted = c.getInt(c.getColumnIndexOrThrow("isCompleted"));
            listTask.add(new Task(ids,textTask,isCompleted));
        }
        adapter.notifyDataSetChanged();
    }
    class MyCustomAdapter extends BaseAdapter {
        ArrayList<Task> Items = new ArrayList<Task>();
        MyCustomAdapter(ArrayList Items){
            this.Items = Items;
        }
        @Override
        public int getCount() {
            return Items.size();
        }
        @Override
        public Object getItem(int position) {
            return Items.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.item,null);
            TextView textItem = (TextView) view.findViewById(R.id.text_task);
            ImageButton btnRemove = (ImageButton) view.findViewById(R.id.btn_remove);
            ImageButton comleted = (ImageButton) view.findViewById(R.id.btn_completed);
            LinearLayout itemsLayout = (LinearLayout) view.findViewById(R.id.itemsLayout);
            textItem.setText(Items.get(position).getTextTask());
            // change itemsLayout background if task is completed to input_background_done.xml
            if(Items.get(position).getIsCompleted() == 1){
                itemsLayout.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.input_background_done));
                comleted.setVisibility(View.GONE);
            }
            btnRemove.setOnClickListener(v -> {
                base.deleteTask(Items.get(position).getIdTask());
                setViewTask();
            });
            comleted.setOnClickListener(view1 -> {
                base.markCompleted(Items.get(position).getIdTask());
                setViewTask();
            });

            // when click on calader show taskDone for that day
            textItem.setOnClickListener(v -> {
                // get date from textItem
                String date = textItem.getText().toString().split(" ")[0];
                // get all taskDone for that day
                Cursor c = done.getAllTaskDone(date);
                // show taskDone for that day
                done.showTaskDone();
            });

            return view;
        }
    }
}