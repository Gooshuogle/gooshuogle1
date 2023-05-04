package cn.ac.imoe.finalexp_contact;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class addContactActivity<data_list> extends AppCompatActivity {
    EditText ed_name,ed_phone;
    Spinner sp_college;
    Switch sw_best;
    RadioGroup rg_gend;
    Button btn_submit,btn_cancel;
    RadioButton rbgender;
    String[] college=new String[]{"信息技术学院","教育学院","外国语学院","国际教育学院","非本校人员"};
    String academy = null;
    String bestfriend;
    String gender = null;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Intent intent=getIntent();
        username = intent.getStringExtra("username");
        ed_name=(EditText)findViewById(R.id.ed_Modname);
        ed_phone=(EditText)findViewById(R.id.ed_Modphonenum);
        sw_best=(Switch)findViewById(R.id.sw_Modbestfriend);
        rg_gend=(RadioGroup)findViewById(R.id.rg_Modgender1);
        ArrayAdapter sp_college_adapter=new ArrayAdapter<String>(addContactActivity.this,R.layout.support_simple_spinner_dropdown_item,college);
        sp_college_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        btn_submit=(Button)findViewById(R.id.btn_Modsubmit);
        btn_cancel=(Button)findViewById(R.id.btn_Modcancel);
        sp_college=(Spinner)findViewById(R.id.sp_Modcollege);
        sp_college.setAdapter(sp_college_adapter);
        rbgender = (RadioButton) findViewById(R.id.rb_Modmale);
        rbgender.setChecked(true);
        academy = "信息技术学院";

        sp_college.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                academy=adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                JK8Helper helper = new JK8Helper(addContactActivity.this,"s.db",null,1);
                helper.getReadableDatabase();

                String name = ed_name.getText().toString().trim();
                String phone = ed_phone.getText().toString().trim();
                int i = 1;
                i = rg_gend.getCheckedRadioButtonId();
                rbgender = (RadioButton)findViewById(i);
                gender = rbgender.getText().toString().trim();

                if(sw_best.isChecked()){
                    bestfriend = "1";
                }else{
                    bestfriend = "0";
                }

                SQLiteDatabase db = helper.getReadableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("gender",gender);
                cv.put("dphone",phone);
                cv.put("academy",academy);
                cv.put("bestfriend",bestfriend);
                cv.put("dname",name);
                cv.put("owner",username);

                Long result = db.insert("contact",null,cv);
                Log.i("result",String.valueOf(result));
                db.close();

                Toast.makeText(addContactActivity.this,"添加成功！",Toast.LENGTH_LONG).show();
                finish();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ret_alert_builder = new AlertDialog.Builder(addContactActivity.this);
                ret_alert_builder.setTitle("确定要退出吗？");
                ret_alert_builder.setMessage("退出后所有数据将不被保存。");
                ret_alert_builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(addContactActivity.this,"未进行任何修改，返回通讯录",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
                ret_alert_builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(addContactActivity.this,"已返回创建页面",Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog ret_alert = ret_alert_builder.create();
                ret_alert.show();
            }
        });
    }
}