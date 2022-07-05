package com.arisurya.project.githubuser3.ui.favorite

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arisurya.project.githubuser3.R
import com.arisurya.project.githubuser3.data.local.FavoriteUser
import com.arisurya.project.githubuser3.data.model.User
import com.arisurya.project.githubuser3.databinding.ActivityFavoriteBinding
import com.arisurya.project.githubuser3.ui.detail.DetailUserActivity
import com.arisurya.project.githubuser3.ui.main.ListUserAdapter


class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: ListUserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customActionBarFavorite()
        recyclerViewFavorite()
        initialViewModel()
        getViewModelFavorite()
    }

    private fun getViewModelFavorite() {
        viewModel.getFavoriteUser()?.observe(this, {
            if (it != null) {
                val list = mapList(it)
                adapter.setData(list)
                if (it.isEmpty()) {
                    showNoFavorite(true)
                } else {
                    showNoFavorite(false)
                }
            }
        })
    }

    private fun initialViewModel() {
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
    }

    private fun recyclerViewFavorite() {
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                if (!checkConnection()) {
                    showDialogNoConnection()
                } else {
                    Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                        it.putExtra(DetailUserActivity.EXTRA_USER, data)
                        startActivity(it)
                    }
                }

            }

        })

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.adapter = adapter
        }
    }

    private fun customActionBarFavorite() {
        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.fav_user)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users) {
            val userMapped = User(
                user.login,
                user.id,
                user.avatar_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
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
        dialog.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.alert_dialog)
        dialog.setCancelable(false)

        @Suppress("LocalVariableName")
        val btn_try_again = dialog.findViewById<Button>(R.id.btn_try_again)
        btn_try_again.setOnClickListener {
            recreate()
        }
        dialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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