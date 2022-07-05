package com.arisurya.project.githubuser

object DataUser {
    private val userNames = arrayOf(
        "Jake Wharton",
        "Amit Shekhar",
        "Romain Guy",
        "Chris Banes",
        "David",
        "Ravi Tamada",
        "Deny Prasetyo",
        "Budi Oktaviyan",
        "Hendi Santika",
        "Sidiq Permana",
    )

    private val userUsername = arrayOf(
        "JakeWharton",
        "amitshekhariitbhu",
        "romainguy",
        "chrisbanes",
        "tipsy",
        "ravi8x",
        "jasoet",
        "budioktaviyan",
        "hendisantika",
        "sidiqpermana"
    )

    private val userLocation = arrayOf(
        "Pittsburgh, PA, USA",
        "New Delhi, India",
        "California",
        "Sydney, Australia",
        "Trondheim, Norway",
        "India",
        "Kotagede, Yogyakarta, Indonesia",
        "Jakarta, Indonesia",
        "Bojongsoang - Bandung Jawa Barat",
        "Jakarta Indonesia"
    )

    private val userCompany = arrayOf(
        "Google, Inc.",
        "MindOrksOpenSource",
        "Google",
        "Google working on @android",
        "Working Group Two",
        "AndroidHive | Droid5",
        "gojek-engineering",
        "KotlinID",
        "JVMDeveloperID @KotlinID @IDDevOps",
        "Nusantara Beta Studio"
    )

    private val userFollower = arrayOf(
        56995, 5153, 7972, 14725, 788, 18628, 277, 178, 428, 465
    )

    private val userFollowing = arrayOf(
        12, 2, 0, 1, 0, 2, 39, 23, 61, 10
    )

    private val userRepository = arrayOf(
        102, 37, 9, 30, 56, 28, 44, 110, 1064, 65
    )

    private val userAvatar = arrayOf(
        R.mipmap.user1,
        R.mipmap.user2,
        R.mipmap.user3,
        R.mipmap.user4,
        R.mipmap.user5,
        R.mipmap.user6,
        R.mipmap.user7,
        R.mipmap.user8,
        R.mipmap.user9,
        R.mipmap.user10

    )

    val listData: ArrayList<User>
        get() {
            val list = arrayListOf<User>()
            for (position in userNames.indices) {
                val user = User()
                user.name = userNames[position]
                user.username = userUsername[position]
                user.company = userCompany[position]
                user.location = userLocation[position]
                user.follower = userFollower[position]
                user.following = userFollowing[position]
                user.repository = userRepository[position]
                user.avatar = userAvatar[position]
                list.add(user)
            }
            return list
        }


}