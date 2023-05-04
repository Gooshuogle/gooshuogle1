package cn.ac.imoe.finalexp_contact;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

public class ModifyContactActivity extends AppCompatActivity {
    Button btn_mod_submit,btn_mod_cancel,btn_mod_del;
    String[] college=new String[]{"信息技术学院","教育学院","外国语学院","国际教育学院","非本校人员"};
    EditText ed_modname,ed_modphone;
    Spinner sp_modcollege;
    RadioGroup rg_gender;
    RadioButton rb_male,rb_fmale;
    Switch sw_best;
    String academy;
    String owner;

    JK8Helper helper = new JK8Helper(ModifyContactActivity.this,"s.db",null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        owner=intent.getStringExtra("username");
        contacts c = queryForModify(intent.getStringExtra("dphone"),owner);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_contact);
        rg_gender=(RadioGroup)findViewById(R.id.rg_Modgender);
        ed_modname=(EditText)findViewById(R.id.ed_Modname);
        ed_modname.setText(c.dname);
        String ori_phone=c.getDphone();
        ed_modphone=(EditText)findViewById(R.id.ed_Modphonenum);
        ed_modphone.setText(c.dphone);
        rb_male=(RadioButton)findViewById(R.id.rb_Modmale);
        rb_fmale=(RadioButton)findViewById(R.id.rb_Modfemale);
        if(c.gender.trim().equals("男"))rb_male.setChecked(true); else if(c.gender.trim().equals("女")) rb_fmale.setChecked(true);
        sw_best=(Switch)findViewById(R.id.sw_Modbestfriend);
        if(c.bestfriend.equals("1"))sw_best.setChecked(true);else sw_best.setChecked(false);
        btn_mod_submit=(Button)findViewById(R.id.btn_Modsubmit);
        btn_mod_cancel=(Button)findViewById(R.id.btn_Modcancel);
        btn_mod_del=(Button)findViewById(R.id.btn_Moddelete);
        sp_modcollege=(Spinner)findViewById(R.id.sp_Modcollege);
        ArrayAdapter sp_college_adapter=new ArrayAdapter<String>(ModifyContactActivity.this,R.layout.support_simple_spinner_dropdown_item,college);
        sp_modcollege.setAdapter(sp_college_adapter);
        sp_modcollege.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                academy=adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn_mod_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JK8Helper helper = new JK8Helper(ModifyContactActivity.this,"s.db",null,1);
                helper.getReadableDatabase();

                String name = ed_modname.getText().toString().trim();
                String phone = ed_modphone.getText().toString().trim();
                 int i = 1;
                 i = rg_gender.getCheckedRadioButtonId();
                RadioButton rbgender = (RadioButton)findViewById(i);
                String gender = rbgender.getText().toString().trim();
                 String bestfriend;
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
                 cv.put("owner",owner);

                 db.update("contact",cv,"dphone=? AND owner=?",new String[]{ori_phone,owner});
                 db.close();

                Toast.makeText(ModifyContactActivity.this,"修改成功！",Toast.LENGTH_LONG).show();
                finish();
             }
     });

        btn_mod_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ret_alert_builder = new AlertDialog.Builder(ModifyContactActivity.this);
                ret_alert_builder.setTitle("确定要退出吗？");
                ret_alert_builder.setMessage("退出后所有数据将不被保存。");
                ret_alert_builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ModifyContactActivity.this,"未进行任何修改，返回通讯录",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
                ret_alert_builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ModifyContactActivity.this,"已返回创建页面",Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog ret_alert = ret_alert_builder.create();
                ret_alert.show();
            }
        });

        btn_mod_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = ed_modphone.getText().toString().trim();
                AlertDialog.Builder ret_alert_builder = new AlertDialog.Builder(ModifyContactActivity.this);
                ret_alert_builder.setTitle("真的要删除吗？");
                ret_alert_builder.setMessage("删除后无法撤销！");
                ret_alert_builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase db = helper.getWritableDatabase();
                        db.delete("contact","dphone="+phone,null);
                        db.close();
                        Toast.makeText(ModifyContactActivity.this,"删除成功",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
                ret_alert_builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ModifyContactActivity.this,"未进行任何修改。",Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog ret_alert = ret_alert_builder.create();
                ret_alert.show();
            }
        });
    }

    private contacts queryForModify(String dphone, String owner){
        JK8Helper helper = new JK8Helper(ModifyContactActivity.this,"s.db",null,1);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("contact",new String[]{"dphone","dname","academy","bestfriend","gender","owner"}, "dphone=? AND owner=?"
                ,new String[]{dphone,owner},null,null,null);
        cursor.moveToNext();
        contacts con = new contacts();
        con.setDphone(cursor.getString(0));
        con.setDname(cursor.getString(1));
        con.setAcademy(cursor.getString(2));
        con.setBestfriend(cursor.getString(3));
        con.setGender(cursor.getString(4));
        con.setOwner(cursor.getString(5));
        cursor.close();
        db.close();
        return con;
    }
}