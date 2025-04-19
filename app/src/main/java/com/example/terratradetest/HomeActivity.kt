package com.example.terratradetest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.landrenting.Model.LandModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fab: FloatingActionButton
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var menuButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        // Plein écran sans barre système
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )

        // Google sign out setup
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Initialisation des vues
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        fab = findViewById(R.id.fab)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        menuButton = findViewById(R.id.menuButton)

        // Gestion du Drawer Toggle
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Clic sur l'icône menu (ImageView)
        menuButton.setOnClickListener {
            drawerLayout.openDrawer(navigationView)
        }

        // Clic sur le FAB
        fab.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.bottomsheetlayout, null)

            val dialog = com.google.android.material.bottomsheet.BottomSheetDialog(this)
            dialog.setContentView(dialogView)

            // Fermer le dialog avec la croix
            val cancelButton = dialogView.findViewById<ImageView>(R.id.cancelButton)
            cancelButton.setOnClickListener {
                dialog.dismiss()
            }

            // Click sur "Sale/Rente your land"
//            val creatads = dialogView.findViewById<LinearLayout>(R.id.layoutad)
//            layoutVideo.setOnClickListener {
//                startActivity(Intent(this, CreateLandActivity::class.java))
//                dialog.dismiss()
//            }

            dialog.show()
        }

        // Gestion des clics du menu bas
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    findViewById<CardView>(R.id.cardView).visibility = View.VISIBLE
                    loadFragment(HomeFragment())
                    true
                }
                R.id.search -> {
                    findViewById<CardView>(R.id.cardView).visibility = View.VISIBLE
                    loadFragment(SearchFragment())
                    true
                }
                R.id.favorit -> {
                    findViewById<CardView>(R.id.cardView).visibility = View.GONE
                    loadFragment(FavoriteFragment())
                    true
                }
                else -> false
            }

        }



        // Handle clicks on navigation drawer menu items
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    logOut()
                    true
                }
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_settings -> {
                    // startActivity(Intent(this, SettingsActivity::class.java))
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_share -> {
                    // Share the app using an Intent
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "Check out this awesome app!")
                        type = "text/plain"
                    }
                    startActivity(Intent.createChooser(shareIntent, "Share via"))
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_about -> {
//                    go to about
                    drawerLayout.closeDrawers()
                    true
                }
                else -> false
            }
        }


    }
    // Fonction loadFragment
    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    //log Out function using google firebase
    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        googleSignInClient.signOut().addOnCompleteListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }



}
