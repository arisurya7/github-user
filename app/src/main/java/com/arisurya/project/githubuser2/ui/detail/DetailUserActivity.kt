package com.arisurya.project.githubuser2.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.arisurya.project.githubuser2.R
import com.arisurya.project.githubuser2.databinding.ActivityDetailUserBinding
import com.bumptech.glide.Glide

@Suppress("USELESS_ELVIS")
class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = null
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.elevation = 0F

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        showProgressBar(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailUserViewModel::class.java)
        viewModel.setUserDetail(username!!)
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                showProgressBar(false)
                binding.apply {
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .into(imgUserAvatar)

                    tvUserName.text = it.name ?: "  -  "
                    tvUserUsername.text = it.login ?: "  -  "
                    tvUserCompany.text = it.company ?: "  -  "
                    tvUserLocation.text = it.location ?: "  -  "
                    countRepository.text = it.public_repos.toString()
                    countFollowers.text = it.followers.toString()
                    countFollowing.text = it.following.toString()

                }
            }
        })


        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            detailTabs.setupWithViewPager(viewPager)
        }


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_detail_user, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMenu(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMenu(selectedMenu: Int) {
        when (selectedMenu) {
            R.id.nav_detail_user -> shareInfo()
        }
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
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}