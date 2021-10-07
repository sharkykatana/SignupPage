package androidsamples.java.signuppage;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MainActivity extends AppCompatActivity {
    private EditText email, password;
    private Button loginButton;
    private TextView registerLink;

    private SQLiteDatabase db;
    private SQLiteOpenHelper openHelper;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.Login);
        password = findViewById(R.id.Password);
        loginButton = findViewById(R.id.Login_Button);
        registerLink = findViewById(R.id.Register_Link);

        openHelper = new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();

        loginButton.setOnClickListener(view -> {
            String iEmail = email.getText().toString().trim();
            String iPassword = password.getText().toString().trim();

            if (iEmail.isEmpty() || iPassword.isEmpty())
            {
                Toast.makeText(MainActivity.this, R.string.empty_credentials, Toast.LENGTH_SHORT).show();
            }
            else {
                cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_4 + "=? AND " +
                                     DatabaseHelper.COL_5 + "=?", new String[]{iEmail, iPassword});

                if (cursor != null)
                {
                    if (cursor.getCount() > 0)
                    {
                        startActivity(new Intent(MainActivity.this, SamplePage.class));
                        Toast.makeText(getApplicationContext(), R.string.login_success_message, Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(getApplicationContext(), R.string.login_error_message, Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), R.string.login_error_message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerLink.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
            finish();
        });

    }

}