package com.example.rxkotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rxkotlin.databinding.ActivityMainBinding
import com.example.rxkotlin.demorxjava.subject.AsysncSubjectActivity
import com.example.rxkotlin.demorxjava.subject.BehaviorSubjectActivity
import com.example.rxkotlin.demorxjava.subject.ReplaySubjectActivity
import com.example.rxkotlin.demorxjava.subject.SubjectActivity
import com.example.rxkotlin.obsever.MaybeActivity
import com.example.rxkotlin.obsever.ObserverActivity
import com.example.rxkotlin.obsever.SingleActivity
import com.example.rxkotlin.rxkotlinwithroom.ActivityRxKotlinWithRoom

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPublishSubject.setOnClickListener {
            val  intent = Intent(this@MainActivity, SubjectActivity::class.java)
            startActivity(intent)
        }

        binding.btnReplaySubject.setOnClickListener {
            val  intent = Intent(this@MainActivity, ReplaySubjectActivity::class.java)
            startActivity(intent)
        }

        binding.btnSearch.setOnClickListener {
            val  intent = Intent(this@MainActivity, ActivityRxKotlinWithRoom::class.java)
            startActivity(intent)
        }

        binding.btnBehaviorSubject.setOnClickListener {
            val  intent = Intent(this@MainActivity, BehaviorSubjectActivity::class.java)
            startActivity(intent)
        }

        binding.btnAsyncSubject.setOnClickListener {
            val  intent = Intent(this@MainActivity, AsysncSubjectActivity::class.java)
            startActivity(intent)
        }

        binding.btnObserver.setOnClickListener {
            val  intent = Intent(this@MainActivity, ObserverActivity::class.java)
            startActivity(intent)
        }

        binding.btnSingle.setOnClickListener {
            val  intent = Intent(this@MainActivity, SingleActivity::class.java)
            startActivity(intent)
        }

        binding.btnMaybe.setOnClickListener {
            val  intent = Intent(this@MainActivity, MaybeActivity::class.java)
            startActivity(intent)
        }
    }

}

fun Context.toast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
