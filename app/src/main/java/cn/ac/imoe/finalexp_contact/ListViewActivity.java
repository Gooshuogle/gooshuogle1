package cn.ac.imoe.finalexp_contact;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewActivity extends AppCompatActivity {
    JK8Helper helper = null;
    TextView tv_usr;
    ListView contacts;
    Button btn_add,btn_logout;
    String username;
    List<contacts> c = new ArrayList<>();
    int a = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        Intent intent_main=getIntent();
        username=intent_main.getStringExtra("username");
    }

    @Override
    protected void onResume() {
        super.onResume();
        c.clear();
        query();
        contacts=(ListView)findViewById(R.id.LV_contact);
        tv_usr=(TextView)findViewById(R.id.tv_username);
        tv_usr.setText(username);
        btn_add=(Button)findViewById(R.id.btn_addContact);
        btn_logout=(Button)findViewById(R.id.btn_logout);
        List<Map<String,Object>> ContactList=new ArrayList<>();
        for(int i=0;i<c.size();i++){
            Map<String,Object> mapper=new HashMap<>();
            mapper.put("id",a);
            c.get(i).setId(a++);
            mapper.put("name",c.get(i).dname);
            mapper.put("phone",c.get(i).dphone);
            mapper.put("bestfriend",Integer.valueOf(c.get(i).bestfriend)==1?"★":"");
            mapper.put("college",c.get(i).academy);
            ContactList.add(mapper);
        }
        SimpleAdapter LV_Adp = new SimpleAdapter(ListViewActivity.this,ContactList,R.layout.layout_lv_contactobject,new String[]{"name","phone","bestfriend","college"},new int[]{R.id.tv_name,R.id.tv_phone,R.id.tv_star,R.id.tv_discollege});
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent("cn.ac.imoe.finalexp.addContact");
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + c.get(i).dphone));
                startActivity(intent);
            }
        });
        contacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent_modify = new Intent(ListViewActivity.this,ModifyContactActivity.class);
                intent_modify.putExtra("dphone",c.get(i).dphone);
                intent_modify.putExtra("username",username);
                startActivity(intent_modify);
                return true;
            }
        });
        contacts.setAdapter(LV_Adp);
    }

    private void query(){
        helper = new JK8Helper(ListViewActivity.this,"s.db",null,1);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("contact",new String[]{"dphone","dname","academy","bestfriend","gender","owner"},"owner=?"
                ,new String[]{username} ,null,null,null);
        while (cursor.moveToNext()){
            contacts con = new contacts();
            con.setDphone(cursor.getString(0));
            con.setDname(cursor.getString(1));
            con.setAcademy(cursor.getString(2));
            con.setBestfriend(cursor.getString(3));
            con.setGender(cursor.getString(4));
            con.setOwner(cursor.getString(5));
            c.add(con);
        }
        cursor.close();
        db.close();
    }
}
