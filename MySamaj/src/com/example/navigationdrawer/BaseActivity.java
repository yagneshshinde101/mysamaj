package com.example.navigationdrawer;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.addressdetail.AddressActivity;
import com.example.feedback.FeedBackActivity;
import com.example.help.HelpActivity;
import com.example.imagesqldata.R;
import com.example.loginpanel.LogoutActivity;
import com.example.mysamajmain.AboutUs;
import com.example.mysamajmain.MainActivity;
import com.example.searchdetails.SearchDetailsActivity;

public class BaseActivity extends Activity {

	/**
	 * Frame layout: Which is going to be used as parent layout for child
	 * activity layout. This layout is protected so that child activity can
	 * access this
	 * */
	protected FrameLayout frameLayout;

	/**
	 * ListView to add navigation drawer item in it. We have made it protected
	 * to access it in child class. We will just use it in child class to make
	 * item selected according to activity opened.
	 */

	protected ListView mDrawerList;

	/**
	 * List item array for navigation drawer items.
	 * */
	protected String[] listArray = { "My Family Details", "Address",
			"Directory", "About Us", "Help", "Feedback", "Logout" };

	/**
	 * Static variable for selected item position. Which can be used in child
	 * activity to know which item is selected from the list.
	 * */
	protected static int position;

	/**
	 * This flag is used just to check that launcher activity is called first
	 * time so that we can open appropriate Activity on launch and make list
	 * item position selected accordingly.
	 * */
	private static boolean isLaunch = true;

	/**
	 * Base layout node of this Activity.
	 * */
	private DrawerLayout mDrawerLayout;

	/**
	 * Drawer listner class for drawer open, close etc.
	 */
	private ActionBarDrawerToggle actionBarDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_drawer_base_layout);

		frameLayout = (FrameLayout) findViewById(R.id.content_frame);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		// mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
		// GravityCompat.START);

		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, listArray));
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				openActivity(position);
			}
		});

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		actionBarDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_menu_white_24dp, /*
										 * nav drawer image to replace 'Up'
										 * caret
										 */
		R.string.open_drawer, /* "open drawer" description for accessibility */
		R.string.close_drawer) /* "close drawer" description for accessibility */
		{
			@Override
			public void onDrawerClosed(View drawerView) {
				getActionBar().setTitle(listArray[position]);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(getString(R.string.app_name));
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
				super.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				super.onDrawerSlide(drawerView, slideOffset);
			}

			@Override
			public void onDrawerStateChanged(int newState) {
				super.onDrawerStateChanged(newState);
			}
		};
		mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

		/**
		 * As we are calling BaseActivity from manifest file and this base
		 * activity is intended just to add navigation drawer in our app. We
		 * have to open some activity with layout on launch. So we are checking
		 * if this BaseActivity is called first time then we are opening our
		 * first activity.
		 * */
		if (isLaunch) {
			/**
			 * Setting this flag false so that next time it will not open our
			 * first activity. We have to use this flag because we are using
			 * this BaseActivity as parent activity to our other activity. In
			 * this case this base activity will always be call when any child
			 * activity will launch.
			 */
			isLaunch = false;
			openActivity(0);
		}
	}

	/**
	 * @param position
	 * 
	 *            Launching activity when any list item is clicked.
	 */
	protected void openActivity(int position) {

		/**
		 * We can set title & itemChecked here but as this BaseActivity is
		 * parent for other activity, So whenever any activity is going to
		 * launch this BaseActivity is also going to be called and it will reset
		 * this value because of initialization in onCreate method. So that we
		 * are setting this in child activity.
		 */
		// mDrawerList.setItemChecked(position, true);
		// setTitle(listArray[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
		BaseActivity.position = position; // Setting currently selected position
											// in this field so that it will be
											// available in our child
											// activities.

		switch (position) {
		case 0:
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;
		case 1:
			startActivity(new Intent(this, AddressActivity.class));
			break;
		case 2:
			startActivity(new Intent(this, SearchDetailsActivity.class));
			break;
		case 3:
			startActivity(new Intent(this, AboutUs.class));
			break;
		case 4:
			startActivity(new Intent(this, HelpActivity.class));
			break;
		case 5:
			startActivity(new Intent(this, FeedBackActivity.class));
			break;
		case 6:
			startActivity(new Intent(this, LogoutActivity.class));
		default:
			break;
		}

		/*
		 * Toast.makeText(this, "Selected Item Position::" + position,
		 * Toast.LENGTH_LONG).show();
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		// return super.onCreateOptionsMenu(menu);
		if (menu != null) {
			menu.findItem(R.id.action_search).setVisible(false);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
		case R.id.refresh:
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		actionBarDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		actionBarDrawerToggle.onConfigurationChanged(newConfig);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.refresh).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/* We can override onBackPressed method to toggle navigation drawer */

	@Override
	public void onBackPressed() {
		if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			mDrawerLayout.openDrawer(mDrawerList);
		}
	}

}
