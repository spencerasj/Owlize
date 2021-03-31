package team.project.owlize;

import androidx.appcompat.app.AppCompatActivity;

public class manual_dashboard extends AppCompatActivity {

//    private ArrayList<String> items;
//    private ArrayAdapter<String> itemsAdapter;
//    private ListView lvItems;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_manual_dashboard);
//        // ADD HERE
//        lvItems = (ListView) findViewById(R.id.lvItems);
//        items = new ArrayList<String>();
////        readItems();
//        itemsAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, items);
//        lvItems.setAdapter(itemsAdapter);
//        // setup remove listener method call
//        setupListViewListener();
//    }
//
//    // Attaches a long click listener to the listview
//    private void setupListViewListener() {
//        lvItems.setOnItemLongClickListener(
//        new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
//                // Remove the item within array at position
//                items.remove(pos);
//                // Refresh the adapter
//                itemsAdapter.notifyDataSetChanged();
////                writeItems();
//                // Return true consumes the long click event (marks it handled)
//                return true;
//            }
//
//        });
//    }
//
//    public void onAddItem(View v) {
//        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
//        String itemText = etNewItem.getText().toString();
//        itemsAdapter.add(itemText);
//        etNewItem.setText("");
////        writeItems();
//    }
//
//    private void readItems() {
//        File filesDir = getFilesDir();
//        File todoFile = new File(filesDir, "todo.txt");
//        try {
//            items = new ArrayList<String>(FileUtils.readLines(todoFile));
//        } catch (IOException e) {
//            items = new ArrayList<String>();
//        }
//    }
//
//    private void writeItems() {
//        File filesDir = getFilesDir();
//        File todoFile = new File(filesDir, "todo.txt");
//        try {
//            FileUtils.writeLines(todoFile, items);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}