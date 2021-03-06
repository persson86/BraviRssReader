package com.mobile.persson.bravirssreader.ui

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.mobile.persson.bravirssreader.R
import com.mobile.persson.bravirssreader.base.BaseLifecycleActivity
import com.mobile.persson.bravirssreader.data.LocalData
import com.mobile.persson.bravirssreader.data.db.entity.FeedUrlEntity
import com.mobile.persson.bravirssreader.data.model.Feed
import com.mobile.persson.bravirssreader.data.model.FeedItem
import com.mobile.persson.bravirssreader.unsafeLazy
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : BaseLifecycleActivity<FeedViewModel>(), NavigationView.OnNavigationItemSelectedListener,
        SwipeRefreshLayout.OnRefreshListener {

    override val viewModelClass = FeedViewModel::class.java

    private val rv by lazy { findViewById(R.id.rv) as RecyclerView }
    private val adapter = FeedAdapter(this)
    private var urlList: List<FeedUrlEntity> = ArrayList()
    private val lRefresh by unsafeLazy { findViewById(R.id.lRefresh) as SwipeRefreshLayout }
    private var selectedUrlFeed: String? = null
    private var isNewFeed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        this.title = getString(R.string.string_saved_feeds)

        configNavigationDrawer()
        configViews()
        observeLiveData()
        showLocalFeeds()
    }

    override fun onResume() {
        super.onResume()
        configItemUrlsNavigation()
        loadNewFeedIfIsNeeded()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_add_url -> {
                nav_view.menu.getItem(1).isCheckable = false
                addUrl()
                isNewFeed = true
            }
            R.id.nav_local_feed -> {
                this.title = getString(R.string.string_saved_feeds)
                nav_view.menu.getItem(0).isChecked = true
                showLocalFeeds()
            }
            else -> {
                val title = item.title.toString()
                this.title = title
                nav_view.menu.getItem(0).isChecked = false
                var feedUrl: String = getString(R.string.string_http)
                feedUrl = feedUrl.plus(title)
                viewModel.getFeed(feedUrl)
                selectedUrlFeed = feedUrl
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onRefresh() {
        if (selectedUrlFeed != null) {
            viewModel.getFeed(selectedUrlFeed!!)
        } else {
            lRefresh.isRefreshing = false
        }
    }

    private fun configNavigationDrawer() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun configItemUrlsNavigation() {
        urlList = viewModel.getUrls()

        var i = 0
        for (item in urlList) {
            i++
            nav_view.menu.removeItem(i)
            nav_view.menu.add(i, i, i, item.url!!.substring(7))
        }
    }

    private fun configViews() {
        lRefresh.setOnRefreshListener(this)
        rv.setHasFixedSize(true)
        rv.adapter = adapter
    }

    private fun observeLiveData() {
        viewModel.isLoadingLiveData.observe(this, Observer<Boolean> {
            it?.let { lRefresh.isRefreshing = it }
        })

        viewModel.feedsLiveData.observe(this, Observer<Feed> {
            it?.let {
                lRefresh.isEnabled = true
                adapter.addFeeds(it.channel?.feedItems)
            }
        })

        viewModel.throwableLiveData.observe(this, Observer<Throwable> {
            it?.let {
                val feeds: MutableList<FeedItem> = mutableListOf()
                adapter.addFeeds(feeds)
                Snackbar.make(rv, it.localizedMessage, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun showLocalFeeds() {
        nav_view.menu.getItem(0).isChecked = true
        val localFeeds = LocalData().getRss()
        if (localFeeds.isNotEmpty()) {

            lRefresh.isEnabled = false

            val feeds: MutableList<FeedItem> = mutableListOf()
            for (item in localFeeds) {
                val feed = FeedItem()
                feed.title = item.title
                feed.description = item.description
                feed.pubDate = item.pubDate
                feed.link = item.link
                feed.rss = item.rss
                feeds.add(feed)
            }

            adapter.addFeeds(feeds)
        }
    }

    private fun addUrl() {
        val intent = Intent(this, AddFeedActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    private fun loadNewFeedIfIsNeeded() {
        if (isNewFeed) {
            val size: Int = urlList.size - 1
            val url: String = urlList[(size)].url!!
            viewModel.getFeed(url)
            selectedUrlFeed = url
            val title = url.substring(7)
            this.title = title
            isNewFeed = false
            nav_view.menu.getItem(0).isChecked = false
        }
    }
}

