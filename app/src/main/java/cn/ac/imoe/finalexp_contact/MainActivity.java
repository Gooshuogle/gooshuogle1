package cn.ac.imoe.finalexp_contact;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    JK8Helper helper = null;
    EditText ed_usrname;
    EditText ed_passwd;
    Button btn_login;
    Intent intent;

    @Override
    protected void onResume() {
        super.onResume();
        ed_usrname.setText("");
        ed_passwd.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed_usrname=(EditText)findViewById(R.id.ed_username);
        ed_passwd=(EditText)findViewById(R.id.ed_pwd);
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(query(ed_usrname.getText().toString().trim(),ed_passwd.getText().toString().trim()) == 1)
                    {
                        intent=new Intent(MainActivity.this,ListViewActivity.class);
                        intent.putExtra("username",ed_usrname.getText().toString().trim());
                        startActivity(intent);
                    }
                else{
                    Toast.makeText(MainActivity.this,"用户名或密码错误，请重试",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private int query(String dusername,String dpassword){
        helper = new JK8Helper(MainActivity.this,"s.db",null,1);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("user",new String[]{"dusername","dpassword"},"dusername=? AND dpassword=?"
                ,new String[]{dusername,dpassword},null,null,null);
        while (cursor.moveToNext()){
            return 1;
        }
        cursor.close();
        db.close();
        return 0;
    }
}