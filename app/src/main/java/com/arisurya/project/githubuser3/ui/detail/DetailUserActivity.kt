package com.arisurya.project.githubuser3.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.arisurya.project.githubuser3.R
import com.arisurya.project.githubuser3.data.model.User
import com.arisurya.project.githubuser3.databinding.ActivityDetailUserBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Suppress("USELESS_ELVIS")
class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    private lateinit var user :User
    private var menuIconFav : Boolean =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customActionBarDetailUser()
        user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, user.login)
        showViewModelDetailUser()
        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.viewPager.adapter = sectionPagerAdapter
        binding.detailTabs.setupWithViewPager(binding.viewPager)


    }

    private fun showViewModelDetailUser() {
        showProgressBar(true)
        viewModel = ViewModelProvider(
            this
        ).get(DetailUserViewModel::class.java)
        user.login.let { viewModel.setUserDetail(it) }
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                showProgressBar(false)
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .into(binding.imgUserAvatar)

                    binding.tvUserName.text = it.name ?: "  -  "
                    binding.tvUserUsername.text = it.login ?: "  -  "
                    binding.tvUserCompany.text = it.company ?: "  -  "
                    binding.tvUserLocation.text = it.location ?: "  -  "
                    binding.countRepository.text = it.public_repos.toString()
                    binding.countFollowers.text = it.followers.toString()
                    binding.countFollowing.text = it.following.toString()

            }
        })
    }

    private fun customActionBarDetailUser() {
        val actionBar = supportActionBar
        actionBar?.title = null
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.elevation = 0F
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_detail_user, menu)
        val tgFav =menu?.findItem(R.id.nav_toggle_fav)
        checkIcon(tgFav)
        return super.onCreateOptionsMenu(menu)
    }



    private fun checkIcon(tgFav:MenuItem?){
        menuIconFav = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkDetailUser(user.id)
            withContext(Dispatchers.Main){
                if(count!=null){
                    menuIconFav = count>0
                    if(menuIconFav)tgFav?.setIcon(R.drawable.ic_baseline_favorite_white_full)
                    else tgFav?.setIcon(R.drawable.ic_baseline_favorite_border_white)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_toggle_fav->{
                menuIconFav = !menuIconFav
                if(menuIconFav){
                    viewModel.addUserFavorite(user.login, user.id, user.avatar_url)
                    item.setIcon(R.drawable.ic_baseline_favorite_white_full)
                    Toast.makeText(this,getString(R.string.add_fav),Toast.LENGTH_SHORT).show()
                }else{
                    viewModel.removeUserFavorite(user.id)
                    item.setIcon(R.drawable.ic_baseline_favorite_border_white)
                    Toast.makeText(this,getString(R.string.rm_fav),Toast.LENGTH_SHORT).show()
                }
            }
            R.id.nav_detail_user -> shareInfo()

        }
        return super.onOptionsItemSelected(item)
    }


    private fun shareInfo() {
        val user = viewModel.getUserDetail().value
        val message = """
                    [INFO DETAIL GITHUB USER]
                    Name        : ${user?.name}
                    Username    : ${user?.login}
                    Company     : ${user?.company}
                    Repository  : ${user?.public_repos}
                    Followers   : ${user?.followers}
                    Following   : ${user?.following}
                    Link        : https://github.com/${user?.login}
                """.trimIndent()
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, message)
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "subject here")
        startActivity(Intent.createChooser(shareIntent, "Share info via"))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showProgressBar(state: Boolean) {
        if (state) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }



}