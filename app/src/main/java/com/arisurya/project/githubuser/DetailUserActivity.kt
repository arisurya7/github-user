package com.arisurya.project.githubuser

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.arisurya.project.githubuser.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailUserBinding

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        binding.tvUserName.text = user.name
        binding.tvUserUsername.text = user.username
        binding.tvUserCompany.text = " " + user.company
        binding.tvUserLocation.text = " " + user.location
        binding.tvUserRepository.text = user.repository.toString()
        binding.tvUserFollower.text = user.follower.toString()
        binding.tvUserFollowing.text = user.following.toString()
        binding.imgUserAvatar.setImageResource(user.avatar)
        binding.btnShare.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnShare -> shareInfo()
            binding.btnBack -> backHome()
        }
    }

    private fun shareInfo() {
        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        val message = """
                    [INFO DETAIL GITHUB USER]
                    Name       : ${user.name}
                    Username   : ${user.username}
                    Company    : ${user.company}
                    Location   : ${user.location}
                    Repository : ${user.repository}
                    Follower   : ${user.follower}
                    Following  : ${user.following}
                """.trimIndent()
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, message)
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "subject here")
        startActivity(Intent.createChooser(shareIntent, "Share info via"))
    }

    private fun backHome() {
        val moveIntent = Intent(this, MainActivity::class.java)
        startActivity(moveIntent)
    }
}