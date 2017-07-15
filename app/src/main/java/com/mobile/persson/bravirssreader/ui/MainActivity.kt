package com.mobile.persson.bravirssreader.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.mobile.persson.bravirssreader.R
import com.mobile.persson.bravirssreader.base.BaseLifecycleActivity
import com.mobile.persson.bravirssreader.data.model.Feed
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.content.Intent
import io.realm.Realm

class MainActivity : BaseLifecycleActivity<FeedsViewModel>(), NavigationView.OnNavigationItemSelectedListener {

    override val viewModelClass = FeedsViewModel::class.java

    private val rv by lazy { findViewById(R.id.rv) as RecyclerView }
    private val adapter = FeedsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        Realm.init(applicationContext)

        configNavigationDrawer()
        configAdapter()
        observeLiveData()
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
            R.id.nav_camera -> {
                addUrl()
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

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

    private fun configAdapter() {
        rv.setHasFixedSize(true)
        rv.adapter = adapter
    }

    private fun observeLiveData() {
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

}

