package com.arisurya.project.customerapp.ui.main

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arisurya.project.customerapp.R
import com.arisurya.project.customerapp.data.model.User
import com.arisurya.project.customerapp.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.alert_dialog.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ListUserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!checkConnection()) showDialogNoConnection()
        recyclerViewFavoriteUser()
        favoriteUserViewModel()
    }

    private fun favoriteUserViewModel() {
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        viewModel.setFavoriteUser(this)
        viewModel.getFavoriteUser().observe(this, {
            if (it != null) {
                adapter.setData(it)
                if (it.isEmpty()) showNoFavorite(true)
                else showNoFavorite(false)
            }
        })
    }

    private fun recyclerViewFavoriteUser() {
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()
        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.adapter = adapter
        }

        adapter.setOnClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                if (!checkConnection()) showDialogNoConnection()
                else visitUser(data)
            }

        })
    }


    @Suppress("DEPRECATION")
    private fun checkConnection(): Boolean {
        val manager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        return networkInfo != null
    }


    private fun showDialogNoConnection() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.alert_dialog)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.btn_try_again.setOnClickListener {
            recreate()
        }
        dialog.show()
    }

    private fun visitUser(data: User) {
        val link = "https://github.com/${data.login}"
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
    }

    private fun showNoFavorite(state: Boolean) {
        if (state) {
            binding.imgNoFav.visibility = View.VISIBLE
            binding.tvNoFav.visibility = View.VISIBLE
        } else {
            binding.imgNoFav.visibility = View.GONE
            binding.tvNoFav.visibility = View.GONE
        }
    }
}
