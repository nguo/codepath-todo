package codepath.apps.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends ActionBarActivity {
	/** the position of this item in the list */
	int itemPos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		itemPos = getIntent().getIntExtra("itemPos", 0);
        String hintText = getIntent().getStringExtra("hintText");
        EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
        etEditItem.setHint(hintText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
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
	
	/** saves the item back to the list */
	public void saveItem(View v) {
	  EditText etName = (EditText) findViewById(R.id.etEditItem);
	  Intent data = new Intent();
	  data.putExtra("newItem", etName.getText().toString());
	  data.putExtra("itemPos", itemPos);
	  setResult(RESULT_OK, data);
	  finish();
	} 

}
