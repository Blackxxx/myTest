package com.example.admin.materialdesigntest;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout); //设置DrawerLayout控件
        NavigationView navView = (NavigationView)findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }
     public boolean onCreateOptionsMenu(Menu menu){
         getMenuInflater().inflate(R.menu.toolbar,menu);
         return true;
     }
     @Override
    public boolean onOptionsItemSelected(MenuItem item){
         switch (item.getItemId()){
             case android.R.id.home:
                 mDrawerLayout.openDrawer(GravityCompat.START);
                 break;
             case R.id.backup:
                 Toast.makeText(this,"YOU",Toast.LENGTH_SHORT).show();
                 break;
             case R.id.delete:
                 Toast.makeText(this,"ARE",Toast.LENGTH_SHORT).show();
                 break;
             case R.id.settings:
                 Toast.makeText(this,"BEATIFUL",Toast.LENGTH_SHORT).show();
                 break;
             default:
         }
         return true;
     }
}
