package com.me.techfy.techfyme;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Bundle bundle;
    private BottomNavigationView menuDeBaixo;
    int contador = 0;
    public static final String CHAVE_KEY = "chave_key";
    private static final String TAG = "Home";
    private FirebaseAuth firebaseAuth;
    public static final String VEIO_DA_HOME = "veio_da_home";
    TextView textNome;
    TextView textEmail;
    private CircleImageView imagePerfil;
    private StorageReference storageRef;
    private FirebaseStorage storage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_home);

        menuDeBaixo = findViewById(R.id.navigationView);

        firebaseAuth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();


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

        Intent intent = getIntent();
        bundle = intent.getExtras();

//        menuDeBaixo.getMenu().clear();

//        reSetupMenu();

        if (bundle.getBoolean(PreferenciaActivity.CK_ANDROID)) {
            setupMenuItem("Android", R.drawable.android_icon);
        }
        if (bundle.getBoolean(PreferenciaActivity.CK_APPLE)) {
            setupMenuItem("Apple", R.drawable.icon_apple_preto);
        }
        if (bundle.getBoolean(PreferenciaActivity.CK_BLOCKCHAIN)) {
            setupMenuItem("Blockchain", R.drawable.blockchainpreto);
        }
        if (bundle.getBoolean(PreferenciaActivity.CK_CLOUD)) {
            setupMenuItem("Cloud", R.drawable.nuvempreta);
        }
        if (bundle.getBoolean(PreferenciaActivity.CK_CRIPTOMOEDAS)) {
            setupMenuItem("Criptomoedas", R.drawable.moedapreta);
        }
        if (bundle.getBoolean(PreferenciaActivity.CK_EBUSINESS)) {
            setupMenuItem("E-business", R.drawable.businesspreto);
        }
        if (bundle.getBoolean(PreferenciaActivity.CK_GAMES)) {
            setupMenuItem("Games", R.drawable.gamepreta);
        }
        if (bundle.getBoolean(PreferenciaActivity.CK_INTELIGENCIAARTIFICIAL)) {
            setupMenuItem("I.A", R.drawable.iapreto);
        }
        if (bundle.getBoolean(PreferenciaActivity.CK_MOBILE)) {
            setupMenuItem("Mobile", R.drawable.mobile_icons);
        }
        if (bundle.getBoolean(PreferenciaActivity.CK_SISTEMAOPERACIONAL)) {
            setupMenuItem("S.O", R.drawable.icon_pc_preto);
        }

        bundleMenuHome(intent);

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
        bundle.putString(CHAVE_KEY, "getBundleToHome");

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        NoticiaFragment noticiaFragment = new NoticiaFragment();
        noticiaFragment.setArguments(bundle);
        transaction.replace(R.id.framelayout_home_id, noticiaFragment);
        transaction.commit();
    }

//    public void reSetupMenu(){
//        menuDeBaixo.getMenu().clear();
//    }

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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        textNome = findViewById(R.id.textView_nome_id);
        textEmail = findViewById(R.id.textView_email_id);
        imagePerfil = findViewById(R.id.image_profile_id);

        FirebaseUser user = firebaseAuth.getCurrentUser();


        if (user != null) {

            if (user.getPhotoUrl() == null) {

                storageRef.child(firebaseAuth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        Picasso.get().load(uri).into(imagePerfil);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            } else {
                Picasso.get().load(user.getPhotoUrl()).into(imagePerfil);
            }
            textNome.setText(user.getDisplayName());
            textEmail.setText(user.getEmail());
        }
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
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
            setupFragment("Technology");
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void sair() {
        firebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void chamarConteudo() {
        Intent intent = new Intent(this, PreferenciaActivity.class);
        startActivity(intent);
    }

    private void bundleMenuHome (Intent intent) {
        bundle = new Bundle();
        bundle.putBoolean(VEIO_DA_HOME, true);
        intent.putExtras(bundle);
    }


}