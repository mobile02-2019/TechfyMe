package com.me.techfy.techfyme;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.design.widget.BottomNavigationView;

import android.support.annotation.ColorInt;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MenuHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Bundle bundle;
    BottomNavigationView menuDeBaixo;
    int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_home);

        menuDeBaixo = findViewById(R.id.navigationView);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.framelayout_home_id, new NoticiaFragment());
        transaction.commit();


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(getDrawable(R.drawable.techfyme_logo_action_bar));
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        aqui comeca selecao dos favoritos pelo usuario = lista dos favoritos-->
        Intent intent = getIntent();
        bundle = intent.getExtras();


        if(bundle.getBoolean("ck_android")) {
            menuDeBaixo.getMenu().add(0, contador, 0, "Android").setIcon(R.drawable.android_icon);
            contador++;
        }
        if(bundle.getBoolean("ck_mercado")) {
            menuDeBaixo.getMenu().add(0, contador, 0, "Mercado").setIcon(R.drawable.marketplace_icon);
            contador++;
        }
        if(bundle.getBoolean("ck_mobile")) {
            menuDeBaixo.getMenu().add(0, contador, 0, "Mobile").setIcon(R.drawable.mobile_icons);
            contador++;
        }
        if(bundle.getBoolean("ck_google")) {
            menuDeBaixo.getMenu().add(0, contador, 0, "Google").setIcon(R.drawable.google_icons);
            contador++;
        }
//        todo: verificar and match onde estao esses indexes
        if(bundle.getBoolean("ck_apple")){
            menuDeBaixo.getMenu().add(0, contador, 0, "Apple").setIcon(R.drawable.icon_apple_preto);
            contador++;
        }
        if(bundle.getBoolean("ck_futuro")){
            menuDeBaixo.getMenu().add(0, contador, 0, "Futuro").setIcon(R.drawable.icon_futuro_preto);
            contador++;
        }
        if(bundle.getBoolean("ck_ios")){
            menuDeBaixo.getMenu().add(0, contador, 0, "IOS").setIcon(R.drawable.icon_ios_preto);
            contador++;
        }
        if(bundle.getBoolean("ck_so")){
            menuDeBaixo.getMenu().add(0, contador, 0, "Sistema Operacional").setIcon(R.drawable.icon_pc_preto);
            contador++;
        }
            }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_editar) {
            chamarConteudo();

        } else if (id == R.id.nav_apple) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void chamarConteudo(){
        Intent intent = new Intent(this, PreferenciaActivity.class);
        startActivity(intent);

    }

}
