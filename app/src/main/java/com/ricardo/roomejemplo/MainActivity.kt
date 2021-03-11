package com.ricardo.roomejemplo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val nuevaPalabraActivityRequestCode = 1
    private val wordViewModel: PalabraViewModel by viewModels {
        PalabraViewModelFactory((application as PalabrasApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = PalabraListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        wordViewModel.allWords.observe(this, Observer { palabras ->
            // Update the cached copy of the words in the adapter.
            palabras?.let { adapter.submitList(it) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NuevaPalabraActivity::class.java)
            startActivityForResult(intent, nuevaPalabraActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == nuevaPalabraActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NuevaPalabraActivity.EXTRA_REPLY)?.let {
                val word = Palabra(it)
                wordViewModel.insert(word)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }
}