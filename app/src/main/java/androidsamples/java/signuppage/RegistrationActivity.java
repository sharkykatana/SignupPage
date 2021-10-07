package androidsamples.java.signuppage;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class RegistrationActivity extends AppCompatActivity {
    private EditText name, phNo, email, password;
    private Button registerBtn, backBtn;

    private SQLiteDatabase db;
    private SQLiteOpenHelper openHelper;

    public void dataSubmission(String dName, String dPhNo, String dEmail, String dPassword)
    {
        ContentValues cv = new ContentValues();

        cv.put(DatabaseHelper.COL_2,dName);
        cv.put(DatabaseHelper.COL_3,dPhNo);
        cv.put(DatabaseHelper.COL_4,dEmail);
        cv.put(DatabaseHelper.COL_5,dPassword);

        long id = db.insert(DatabaseHelper.TABLE_NAME, null, cv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        name = findViewById(R.id.Name);
        phNo = findViewById(R.id.PhNo);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Pwd);

        registerBtn = findViewById(R.id.Register_Button);
        backBtn = findViewById(R.id.Login_Button);

        openHelper = new DatabaseHelper(this);
        db = openHelper.getWritableDatabase();

        registerBtn.setOnClickListener(view -> {
            String iName = name.getText().toString().trim();
            String iPhNo = phNo.getText().toString().trim();
            String iEmail = email.getText().toString().trim();
            String iPassword = password.getText().toString().trim();

            if(iName.isEmpty() || iPhNo.isEmpty() || iEmail.isEmpty() || iPassword.isEmpty())
            {
                Toast.makeText(RegistrationActivity.this, R.string.field_blank , Toast.LENGTH_SHORT).show();
            }
            else{
                dataSubmission(iName,iPhNo,iEmail,iPassword);
                Toast.makeText(RegistrationActivity.this, R.string.register_success , Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                finish();
            }
        });

        backBtn.setOnClickListener(view -> {
            startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
            finish();
        });
    }

}
