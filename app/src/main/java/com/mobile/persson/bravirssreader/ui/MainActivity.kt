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
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : BaseLifecycleActivity<FeedViewModel>(), NavigationView.OnNavigationItemSelectedListener,
        SwipeRefreshLayout.OnRefreshListener {

    override val viewModelClass = FeedViewModel::class.java

    private val rv by lazy { findViewById(R.id.rv) as RecyclerView }
    private val adapter = FeedAdapter(this)
    private var urlList: List<FeedUrlEntity> = ArrayList()
    private val vRefresh by unsafeLazy { findViewById(R.id.lRefresh) as SwipeRefreshLayout }
    private var selectedUrlFeed: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        Realm.init(applicationContext)

        vRefresh.setOnRefreshListener(this)

        val localFeeds = LocalData().getRss()
        if (localFeeds.size > 0) {
            val feeds: MutableList<FeedItem> = mutableListOf()
            for (item in localFeeds) {
                val feed = FeedItem()
                feed.title = item.title
                feed.description = item.description
                feed.pubDate = item.pubDate
                feed.rss = item.rss
                feeds.add(feed)
            }

            adapter.addFeeds(feeds)
        }

        configNavigationDrawer()
        configAdapter()
        observeLiveData()
    }

    override fun onResume() {
        super.onResume()
        configItemUrlsNavigation()
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
                addUrl()
            }
            else -> {
                viewModel.getFeed(item.title.toString())
                selectedUrlFeed = item.title.toString()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
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
            nav_view.menu.add(i, i, i, item.url)
        }
    }

    private fun configAdapter() {
        rv.setHasFixedSize(true)
        rv.adapter = adapter
    }

    private fun observeLiveData() {
        viewModel.isLoadingLiveData.observe(this, Observer<Boolean> {
            it?.let { vRefresh.isRefreshing = it }
        })
        viewModel.feedsLiveData.observe(this, Observer<Feed> {
            it?.let { adapter.addFeeds(it.channel?.feedItems) }
        })
        viewModel.throwableLiveData.observe(this, Observer<Throwable> {
            it?.let { Snackbar.make(rv, it.localizedMessage, Snackbar.LENGTH_LONG).show() }
        })
    }

    private fun addUrl() {
        val intent = Intent(this, AddFeedActivity::class.java)
        startActivity(intent)
    }

    override fun onRefresh() {
        if (selectedUrlFeed != null) {
            viewModel.getFeed(selectedUrlFeed!!)
        } else {
            vRefresh.isRefreshing = false
        }
    }

}

