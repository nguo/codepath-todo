package codepath.apps.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ToDoActivity extends ActionBarActivity {
	/** View for the list of to do items */
	ListView lvItems;
	
	/** List of items inside the list view */
	ArrayList<String> items;
	
	/** Array adapter for the list of items */
	ArrayAdapter<String> itemsAdapter;
	
	/** request code for edit activity */
	int REQUEST_CODE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }
    
    /**
     * Sets up event listeners for the list view
     */
    private void setupListViewListener() {
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
    		@Override
    		public boolean onItemLongClick(AdapterView<?> aView, View item, int pos, long id) {
    			items.remove(pos);
    			itemsAdapter.notifyDataSetInvalidated();
    			saveItems();
    			return true;
    		}
    	});
    	lvItems.setOnItemClickListener(new OnItemClickListener() {
    		@Override
    		public void onItemClick(AdapterView<?> aView, View item, int pos, long id) {
    			// first parameter is the context, second is the class of the activity to launch
    			Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
    			i.putExtra("hintText", items.get(pos));
    			i.putExtra("itemPos", pos);
    			startActivityForResult(i, REQUEST_CODE); // brings up the second activity
    		}
    	});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * Add a to do item to the adapter
     */
    public void addToDoItem(View v) {
    	EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    	String newText = etNewItem.getText().toString();
    	if (newText.length() > 0) {
    		itemsAdapter.add(etNewItem.getText().toString());
        	saveItems();
        	etNewItem.setText("");
    	}
    }
    
    /** read items from file */
    private void readItems() {
    	File filesDir = getFilesDir();
    	File toDoFile = new File(filesDir, "todo.txt");
    	try {
    		items = new ArrayList<String>(FileUtils.readLines(toDoFile));
    	} catch (IOException e) {
    		items = new ArrayList<String>();
    		e.printStackTrace();
    	}
    }
    
    /** save items to file */
    private void saveItems() {
    	File filesDir = getFilesDir();
    	File toDoFile = new File(filesDir, "todo.txt");
    	try {
    		FileUtils.writeLines(toDoFile, items);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
    		String newItem = data.getExtras().getString("newItem");
    		int itemPos = data.getIntExtra("itemPos", 0);
    		items.set(itemPos, newItem);
    		itemsAdapter.notifyDataSetChanged();
    		saveItems();
		}
	}
}
