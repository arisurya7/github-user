@file:Suppress("DEPRECATION")

package com.arisurya.project.githubuser3.ui.main

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arisurya.project.githubuser3.R
import com.arisurya.project.githubuser3.data.model.User
import com.arisurya.project.githubuser3.databinding.ActivityMainBinding
import com.arisurya.project.githubuser3.ui.detail.DetailUserActivity
import com.arisurya.project.githubuser3.ui.favorite.FavoriteActivity
import com.arisurya.project.githubuser3.ui.settings.SettingsActivity
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customActionBarMain()
        if (!checkConnection()) showDialogNoConnection()
        recyclerListUser()
        initialViewModel()
        getUserViewModel()
    }

    private fun initialViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModel::class.java)
    }

    private fun getUserViewModel() {
        viewModel.getSearchUsers().observe(this, {
            if (it != null) {
                adapter.setData(it)
                showProgressBar(false)
                showIconStart(false)
                if (it.size == 0) showIconStart(true)
            }
        })
    }

    private fun recyclerListUser() {
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                if (!checkConnection()) {
                    showDialogNoConnection()
                } else {
                    Intent(this@MainActivity, DetailUserActivity::class.java).also {
                        it.putExtra(DetailUserActivity.EXTRA_USER, data)
                        startActivity(it)
                    }
                }

            }

        })

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
            btnSearch.setOnClickListener(this@MainActivity)
            btnJoin.setOnClickListener(this@MainActivity)
            edtSearch.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUsers()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
    }

    private fun searchUsers() {
        binding.apply {
            val query = edtSearch.text.toString()
            if (query.isEmpty()) return
            showProgressBar(true)
            showIconStart(false)
            viewModel.setSearchUsers(query)
        }

    }

    private fun showProgressBar(state: Boolean) {
        if (state) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }

    private fun showIconStart(state: Boolean) {
        if (state) {
            binding.imgSearch.visibility = View.VISIBLE
            binding.descImgSearch.visibility = View.VISIBLE
        } else {
            binding.imgSearch.visibility = View.GONE
            binding.descImgSearch.visibility = View.GONE
        }
    }


    private fun favoriteUserActivity() {
        startActivity(Intent(this, FavoriteActivity::class.java))
    }

    private fun joinGithub() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/join")))
    }

    private fun settingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

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

    private fun customActionBarMain() {
        val actionBar = supportActionBar
        actionBar?.title = null
        actionBar?.elevation = 0F
    }

    private fun setMenu(selectedMenu: Int) {
        when (selectedMenu) {
            R.id.nav_favorite -> favoriteUserActivity()
            R.id.nav_settings -> settingsActivity()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMenu(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnJoin -> joinGithub()
            binding.btnSearch -> {
                if (!checkConnection()) showDialogNoConnection()
                else searchUsers()
            }
        }
    }

}