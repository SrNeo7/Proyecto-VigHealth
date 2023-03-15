package com.joseantonio.personalproject.proyectovighealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    @Override
    public void setContentView(View view){
        drawerLayout = (DrawerLayout)getLayoutInflater().inflate(R.layout.activity_drawer_base,null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.menu_drawer_open,R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()){
            case R.id.nav_principal:
                startActivity(new Intent(this,PrincipalActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_tension:
                startActivity(new Intent(this,NuevaTensionActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_peso:
                startActivity(new Intent(this,PesoSeguimientoActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_actividad:
                startActivity(new Intent(this,ListaActividadesActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_alergia:
                startActivity(new Intent(this,AlergiaActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_medicamentos:
                startActivity(new Intent(this,ListaMedicamentosActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_hidratacion:
                startActivity(new Intent(this,HidratacionActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_ajustes:
                startActivity(new Intent(this,ConfiguracionActivity.class));
                overridePendingTransition(0,0);
                break;
        }
        return false;
    }

    /**
     * Funcion para poner el titulo de la actividad en la toolbar
     * @param titleString
     */
    protected  void allocateActivityTitle(String titleString){
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(titleString);
        }
    }
}