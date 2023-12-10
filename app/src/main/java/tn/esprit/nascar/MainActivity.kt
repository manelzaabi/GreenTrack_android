package tn.esprit.nascar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import tn.esprit.nascar.databinding.ActivityMainBinding
import tn.esprit.nascar.fragment.AboutFragment
import tn.esprit.nascar.fragment.DomesticFragment
import tn.esprit.nascar.fragment.WasteFragment
import tn.esprit.nascar.fragment.TransportFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar.appBar
        setSupportActionBar(toolbar)

        binding.btnNews.setOnClickListener {
            changeFragment(WasteFragment(), "")
        }

        binding.btnEvents.setOnClickListener {
            changeFragment(DomesticFragment(), "")
        }

        binding.btnProfile.setOnClickListener {
            changeFragment(TransportFragment(), "")
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, WasteFragment()).commit()
    }

    private fun changeFragment(fragment: Fragment, name: String) {

        if (name.isEmpty())
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        else
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("").commit()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.infoMenu -> {
                changeFragment(AboutFragment(),"AboutMe")
            }
        }

        return super.onOptionsItemSelected(item)
    }
}