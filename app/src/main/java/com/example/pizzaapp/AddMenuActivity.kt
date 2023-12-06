package com.example.pizzaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AddMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)

        //hide title bar
       getSupportActionBar()?.hide()

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            val view = inflater.inflate(R.layout.fragment_makanan, container, false)
            val rvmakanan: RecyclerView = view.findViewById(R.id.recyclerMakanan)
            rvmakanan.layoutManager = LinearLayoutManager(activity)
            rvmakanan.adapter = MenuAdapter()

            //instance button add menu
            val buttonAdd : Button = view.findViewById(R.id.buttonAddMenu)
            //event saat button add menu di klik
            buttonAdd.setOnClickListener {
                requireActivity().run{
                    startActivity(Intent(this, AddMenuActivity::class.java))
                    finish()
                }
            }

            return view
        }
    }
}