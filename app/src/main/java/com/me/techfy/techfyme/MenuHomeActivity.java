package com.me.techfy.techfyme;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MenuHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Bundle bundle;
    BottomNavigationView menuDeBaixo;
    int contador = 0;
    public static final String CHAVE_KEY = "chave_key";
    private static final String TAG = "Home";
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_home);

        menuDeBaixo = findViewById(R.id.navigationView);

        firebaseAuth = FirebaseAuth.getInstance();


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

        if (bundle.getBoolean("ck_android")) {
            setupMenuItem("Android", R.drawable.android_icon);
        }
        if (bundle.getBoolean("ck_apple")) {
            setupMenuItem("Apple", R.drawable.icon_apple_preto);
        }
        if (bundle.getBoolean("ck_blockchain")) {
            setupMenuItem("Blockchain", R.drawable.blockchainpreto);
        }
        if (bundle.getBoolean("ck_cloud")) {
            setupMenuItem("Cloud", R.drawable.nuvempreta);
        }
//
        if (bundle.getBoolean("ck_criptomoedas")) {
            setupMenuItem("Criptomoedas", R.drawable.moedapreta);
        }
        if (bundle.getBoolean("ck_ebusiness")) {
            setupMenuItem("E-business", R.drawable.businesspreto);
        }
        if (bundle.getBoolean("ck_games")) {
            setupMenuItem("Games", R.drawable.gamepreta);
        }

        if (bundle.getBoolean("ck_inteligenciaartificial")) {
            setupMenuItem("Inteligência Artificial", R.drawable.iapreto);
        }
        if (bundle.getBoolean("ck_mobile")) {
            setupMenuItem("Mobile", R.drawable.mobile_icons);
        }
        if (bundle.getBoolean("ck_sistemaoperacional")) {
            setupMenuItem("Sistema Operacional", R.drawable.icon_pc_preto);
        }



    }

    public void setupHome() {
        final MenuItem menuItem = menuDeBaixo.getMenu().add(0, 4, 0, "Home");

        menuItem.setIcon(R.drawable.homeazul);
        Drawable drawable = menuItem.getIcon();
        setupHomeFragment(menuItem);
        menuItem.setChecked(true);

        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(getResources().getColor(R.color.azulSecundario), PorterDuff.Mode.SRC_ATOP);
        }

        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                setupHomeFragment(item);
                item.setChecked(true);
                return true;
            }
        });




    }

    private void setupHomeFragment(MenuItem item) {
        Bundle bundle = new Bundle();
        bundle.putString(CHAVE_KEY, "home");

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        NoticiaFragment noticiaFragment = new NoticiaFragment();
        noticiaFragment.setArguments(bundle);
        transaction.replace(R.id.framelayout_home_id, noticiaFragment);
        transaction.commit();
    }

    private void setupMenuItem(String title, int icon) {
        if (contador == 2) {
            setupHome();
            contador++;
        }

        final MenuItem menuItem = menuDeBaixo.getMenu().add(0, contador, 0, title);
        menuItem.setIcon(icon);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                item.setChecked(true);
                setupFragment(menuItem.getTitle().toString());
                return true;
            }
        });
        contador++;
    }

    private void setupFragment(String query) {
        Bundle bundle = new Bundle();
        bundle.putString(CHAVE_KEY, query);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        NoticiaFragment noticiaFragment = new NoticiaFragment();
        noticiaFragment.setArguments(bundle);
        transaction.replace(R.id.framelayout_home_id, noticiaFragment);
        transaction.commit();
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
            setupFragment("apple");
        }
        else if (id == R.id.nav_android) {
            setupFragment("android");
        }
        else if (id == R.id.nav_cloud) {
            setupFragment("cloud");
        }
        else if (id == R.id.nav_blockchain) {
            setupFragment("blockchain");
        }
        else if (id == R.id.nav_criptomoedas) {
            setupFragment("criptomoedas");
        }
        else if (id == R.id.nav_ebusiness) {
            setupFragment("E-business");
        }
        else if (id == R.id.nav_games) {
            setupFragment("games");
        }

        else if (id == R.id.nav_home) {
            setupFragment("Home");

        }
        else if (id == R.id.nav_inteligencia_artificial) {
            setupFragment("inteligencia artificial");
        }
        else if (id == R.id.nav_mobile) {
            setupFragment("mobile");
        }

        else if (id == R.id.nav_sistemaoperacional) {
            setupFragment("sistema operacional");

        }
        else if (id == R.id.nav_sair) {
            sair();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void sair() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void chamarConteudo() {
        Intent intent = new Intent(this, PreferenciaActivity.class);
        startActivity(intent);

    }

    public void chamarMateriasMenu(final MenuItem menuItem) {
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                item.setChecked(true);
                setupFragment(menuItem.getTitle().toString());
                return true;
            }

        });
    }
}
