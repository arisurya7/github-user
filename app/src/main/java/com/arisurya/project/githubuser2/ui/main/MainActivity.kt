@file:Suppress("DEPRECATION")
package com.arisurya.project.githubuser2.ui.main
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arisurya.project.githubuser2.R
import com.arisurya.project.githubuser2.data.model.User
import com.arisurya.project.githubuser2.databinding.ActivityMainBinding
import com.arisurya.project.githubuser2.ui.detail.DetailUserActivity
import kotlinx.android.synthetic.main.alert_dialog.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = null
        actionBar.elevation = 0F

        if (!checkConnection()) showDialogNoConnection()

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                if (!checkConnection()) {
                    showDialogNoConnection()
                } else {
                    Intent(this@MainActivity, DetailUserActivity::class.java).also {
                        it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                        startActivity(it)
                    }
                }

            }

        })

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModel::class.java)


        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

            btnSearch.setOnClickListener {
                if (!checkConnection()) showDialogNoConnection()
                else searchUsers()
            }

            btnJoin.setOnClickListener {
                joinGithub()
            }

            edtSearch.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUsers()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel.getSearchUsers().observe(this, {
            if (it != null) {
                adapter.setData(it)
                showProgressBar(false)
                showIconStart(false)
                if (it.size == 0) {
                    showIconStart(true)
                }

            }
        })

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
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMenu(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMenu(selectedMenu: Int) {
        when (selectedMenu) {
            R.id.nav_setting -> settingLanguage()
            R.id.nav_exit -> exitDialog()
        }
    }


    private fun exitDialog() {
        val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.title_exit))
        alertDialog.setMessage(getString(R.string.ask_exit))
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes)) { dialog, _ ->
            finish()
            dialog.dismiss()
        }
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.no)) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED)
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
    }

    private fun settingLanguage() {
        val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        startActivity(mIntent)
    }

    private fun joinGithub() {
        val joinGithub = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/join"))
        startActivity(joinGithub)
    }

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


}