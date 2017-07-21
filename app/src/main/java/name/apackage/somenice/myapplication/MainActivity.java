package name.apackage.somenice.myapplication;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.service.carrier.CarrierService;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import Droid.Droid;

/**
 * Gets the data from the ContentProvider and shows a series of flash cards.
 */

public class MainActivity extends AppCompatActivity {

    private Button b1;
    TextView Sample_word;
    TextView word_definition;

    private int CurrentState;

    private final int SHOW_HIDE = 0;

    private final int SHOW_DEFINITION = 1;
    private int mword,mdef;



    Cursor mdata;



    @Override

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1= (Button) findViewById(R.id.button_next);
        new Shoe_next().execute();
        Sample_word=(TextView)findViewById(R.id.text_view_word);
        word_definition=(TextView)findViewById(R.id.text_view_definition);

    }

    public void onButtonClick(View view)
    {
        switch (CurrentState){
            case SHOW_HIDE:
                showDefinition();
                break;
            case SHOW_DEFINITION:
                showNext();
                break;

            }
    }

    public void showDefinition(){
        if(mdata!= null){
            word_definition.setVisibility(View.VISIBLE);
        }

        b1.setText(getString(R.string.next_word));


         CurrentState= SHOW_DEFINITION;

    }

    public void showNext(){
        if(mdata!=null){
            if(!mdata.moveToNext()){
                mdata.moveToFirst();
            }
        }
        b1.setText(getString(R.string.show_definition));
        CurrentState=SHOW_HIDE;
        Sample_word.setText(mdata.getString(mword));
        word_definition.setText(mdata.getString(mdef));

    }

    public class Shoe_next extends AsyncTask<Void,Void,Cursor> {


        @Override
        protected Cursor doInBackground(Void... params) {

            ContentResolver resolver = getContentResolver();
            Cursor cursor = resolver.query(Droid.CONTENT_URI, null, null, null, null);
            return cursor;
        }

        @Override

    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        mdata=cursor;
           mword= mdata.getColumnIndex(Droid.COLUMN_WORD);
      mdef=mdata.getColumnIndex(Droid.COLUMN_DEFINITION);
          showNext();
}   }

    }

