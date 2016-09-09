package com.Shady_Selim.Quran;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	boolean doubleBackToExitPressedOnce = false;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	Toolbar toolbar;
	static AppBarLayout appBarLayout;
    static boolean hideBar = false;
	public int pagesCount;
	public static String currentSurah, currentHezb, currentJuz, currentPageN;

	private static FloatingActionMenu mFab;
	SharedPreferences prefs;
	private Menu menu;
	Set<String> setMark;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pagesCount = this.getResources().getInteger(R.integer.pagesCount);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		prefs = getApplicationContext().getSharedPreferences("Quran", 0);
		if (prefs.getBoolean("PREFERENCE_FIRST_RUN", true) != false){
			prefs.edit().clear().commit();
			prefs.edit().putBoolean("PREFERENCE_FIRST_RUN", false).commit();
		}
		mViewPager.setCurrentItem(prefs.getInt("LastPage", pagesCount));
		setMark = prefs.getStringSet("Bookmarks", new HashSet<String>()) ;
		appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
		appBarLayout.setExpanded(false, true);

		FloatingActionButton fabSurah = (FloatingActionButton) findViewById(R.id.action_surah);
		FloatingActionButton fabHezb = (FloatingActionButton) findViewById(R.id.action_hezb);
		FloatingActionButton fabJuz = (FloatingActionButton) findViewById(R.id.action_juz);
		FloatingActionButton fabBookmark = (FloatingActionButton) findViewById(R.id.action_bookmark);
		fabSurah.setOnClickListener(clickListener);
		fabHezb.setOnClickListener(clickListener);
		fabJuz.setOnClickListener(clickListener);
		fabBookmark.setOnClickListener(clickListener);
		mFab = (FloatingActionMenu) findViewById(R.id.fab);
	}

	@Override
	protected void onStop(){
		super.onStop();
		Editor edit = prefs.edit();
		edit.putInt("LastPage", mViewPager.getCurrentItem());
		if (edit.commit()) {
//			Toast.makeText(this, R.string.save_success , Toast.LENGTH_SHORT).show();
		}/*else{
			Toast.makeText(this, R.string.save_failed , Toast.LENGTH_SHORT).show();
		}*/
	}
	/*@Override
	public void onBackPressed() {
		if (mViewPager.getCurrentItem() == (pagesCount -1)) {
			// If the user is currently looking at the first step, allow the system to handle the
			// Back button. This calls finish() on this activity and pops the back stack.
			//super.onBackPressed();
			//Toast.makeText(this, "You clicked me!", Toast.LENGTH_SHORT).show();
			finish(); // that made the app disappear from the screen but it was still running in the background
			System.exit(0);
		} else {
if (doubleBackToExitPressedOnce) {
			super.onBackPressed();
			return;
		}

		this.doubleBackToExitPressedOnce = true;
		Toast.makeText(this, R.string.back_exit, Toast.LENGTH_SHORT).show();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				doubleBackToExitPressedOnce=false;
			}
		}, 2000);
		// Otherwise, select the previous step.
		mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
		}
	}*/
/*
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		int action = event.getAction();
		int keyCode = event.getKeyCode();
		switch (keyCode) {
			case KeyEvent.KEYCODE_VOLUME_UP:
				if (action == KeyEvent.ACTION_DOWN) {
					mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
				}
				return true;
			case KeyEvent.KEYCODE_VOLUME_DOWN:
				if (action == KeyEvent.ACTION_DOWN) {
					mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
				}
				return true;
			case KeyEvent.KEYCODE_BACK:
				if (action == KeyEvent.ACTION_DOWN) {
					if (doubleBackToExitPressedOnce) {
						super.onBackPressed();
					}

					this.doubleBackToExitPressedOnce = true;
					Toast.makeText(this, R.string.back_exit, Toast.LENGTH_SHORT).show();

					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							doubleBackToExitPressedOnce=false;
						}
					}, 2000);
					mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
				}
				return true;
			default:
				return super.dispatchKeyEvent(event);
		}
	}*/

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_VOLUME_UP:
				mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
				return true;
			case KeyEvent.KEYCODE_VOLUME_DOWN:
				mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
				return true;
			case KeyEvent.KEYCODE_BACK:
				if (doubleBackToExitPressedOnce) {
					super.onBackPressed();
				}
				this.doubleBackToExitPressedOnce = true;
				Snackbar.make(mViewPager,R.string.back_exit, Snackbar.LENGTH_SHORT).show();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {doubleBackToExitPressedOnce=false;}}, 2000);
				mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
				return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		this.menu = menu;
		checkBookmark();
		return true;
	}

	private void checkBookmark() {
		MenuItem item = menu.findItem(R.id.action_bookmark);
		for(String cPage: setMark){
			String page = cPage.substring(cPage.lastIndexOf(" ")+1, cPage.length());
			if (Integer.valueOf(currentPageN) < 1 || Integer.valueOf(currentPageN) > getResources().getInteger(R.integer.lastPage))
				item.setVisible(false);
			else if (page.equals(currentPageN)){//((pageNumber[i]).equals(currentPageN)) {
				item.setVisible(true);
				item.setIcon(R.drawable.ic_bookmark_24dp);
				item.setTitle(getString(R.string.bookmarked));
				break;
			}else {
				item.setVisible(true);
				item.setIcon(R.drawable.ic_bookmark_border_24dp);
				item.setTitle(getString(R.string.bookmark));
			}
		}
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			getBookmark();
			return fragment;
		}

		public void getBookmark(){


			int page = getResources().getInteger(R.integer.pagesCount) - mViewPager.getCurrentItem();
			int[] surahPages=  getResources().getIntArray(R.array.surahPage);
			int[] hezbPages=  getResources().getIntArray(R.array.hezb);
			String[] surahNames = getResources().getStringArray(R.array.surahs);
			currentPageN = String.valueOf(page - getResources().getInteger(R.integer.pagesLeading));
			for(int i=0; i < surahPages.length; i++){
				if(surahPages[i] == page) {
					currentSurah = surahNames[i];
					break;
				}
				else if (surahPages[i] > page && surahNames[1].equals("1")) {
					currentSurah = surahNames[i-1];
					break;
				}
			}
			String[] hezPart = {"1/4","1/2","3/4"};
			for(int i=0; i < hezbPages.length; i++){
				if(hezbPages[i] == page){
					currentJuz = getString(R.string.juz) + " " + (Integer.valueOf(i/8)+1);
					currentHezb = getString(R.string.hezb) + " " + (Integer.valueOf(i/4)+1);
					if(i%4 != 0){
						currentHezb = hezPart[i%4 - 1] + " " + currentHezb;
					}
					Snackbar.make(mViewPager,currentHezb, Snackbar.LENGTH_SHORT).show();
					break;
				}
				else if (hezbPages[i] > page  && hezbPages[1] == 1) {
					currentJuz = getString(R.string.juz) + " " + (Integer.valueOf(i/8)+1);
					currentHezb = getString(R.string.hezb) + " " + (Integer.valueOf(i/4)+1);
					if((i-1)%4 != 0){
						currentHezb = hezPart[(i-1)%4 - 1] + " " + currentHezb;
					}
					break;
				}
			}
			if (menu != null) {
				checkBookmark();
			}
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return pagesCount;
		}

	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_main_dummy, container, false);
			//TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
			//dummyTextView.setText("drawable/q00" + Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
			ImageView img = (ImageView) rootView.findViewById(R.id.imageQuran);
			//int currentPage= 10 - getArguments().getInt(ARG_SECTION_NUMBER) + 1;
			int currentPage= this.getResources().getInteger(R.integer.pagesCount) - getArguments().getInt(ARG_SECTION_NUMBER) + 1;
			img.setImageResource(rootView.getResources().getIdentifier("q"+ Integer.toString(currentPage), "drawable", rootView.getContext().getPackageName()));
			//img.setImageDrawable(rootView.getResources().getDrawable(rootView.getResources().getIdentifier("drawable/q001", null, rootView.getPackageName()))); //getPackageName()
			img.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
//						Toast.makeText(getContext(), "Current! " + "I am clicked", Toast.LENGTH_SHORT).show();
					if (Integer.valueOf(currentPageN) > 0)
					Snackbar.make(getView(),currentSurah +", " + currentHezb +", " + currentJuz +","+ getString(R.string.page) + currentPageN,Snackbar.LENGTH_LONG).show();
					if (hideBar){
                        appBarLayout.setExpanded(false, true);
                    }else{
                        appBarLayout.setExpanded(true, true);
                    }
                    hideBar = !hideBar;
					mFab.close(true);
				}
			});
			return rootView;
		}
	}

	@SuppressLint("ValidFragment")
	public class AboutDeveloper extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the Builder class for convenient dialog construction
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage(R.string.dialog_Developer)
					.setPositiveButton(R.string.visit, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							Uri webpage = Uri.parse("http://www.linkedin.com/in/ShadySelim");
							Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
							startActivity(webIntent);
						}
					})
					.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// User cancelled the dialog
						}
					});
			// Create the AlertDialog object and return it
			return builder.create();
		}
	}

	public static boolean contains(Integer[] arr, Integer item) {
		for(Integer s: arr){
			if(s.equals(item))
				return true;
		}
		return false;
	}

	@SuppressLint("ValidFragment")
	public class SelectSurah extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			List<String> surahItems = new ArrayList<>();
			String[] hezPart = {"1/4","1/2","3/4"};
			Integer[] madinan = {2, 3, 4, 5, 8, 9, 13, 22, 24, 33, 47, 48, 49, 55, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 76, 98, 110};
			String[] surahArray = getResources().getStringArray(R.array.surahs);
			for(int i=0; i< surahArray.length;  i++){//Arrays.asList(madinan).contains(i+1)
					String tanjil = (contains(madinan, i+1))?getString(R.string.medinan):getString(R.string.meccan);
					surahItems.add(i+1 + " - " + surahArray[i] + " " + tanjil);
			}
			CharSequence[] Surahs = surahItems.toArray(new CharSequence[surahItems.size()]);
			builder.setTitle(R.string.surah)
					.setItems(Surahs, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// The 'which' argument contains the index position
							// of the selected item
							int[] page = getResources().getIntArray(R.array.surahPage);
							/*Toast.makeText(getContext(), "You clicked me! " + which , Toast.LENGTH_SHORT).show();
							Toast.makeText(getContext(),  "Current! " + mViewPager.getCurrentItem() , Toast.LENGTH_SHORT).show();
							Toast.makeText(getContext(),  "Page! " + page[which] , Toast.LENGTH_SHORT).show();*/
							mViewPager.setCurrentItem(pagesCount - page[which]);
							/*
							<array name="icons">
								<item>@drawable/home</item>
								<item>@drawable/settings</item>
								<item>@drawable/logout</item>
							</array>
							<array name="colors">
								<item>#FFFF0000</item>
								<item>#FF00FF00</item>
								<item>#FF0000FF</item>
							</array>
							Resources res = getResources();
							TypedArray icons = res.	obtainTypedArray(R.array.icons);
							Drawable drawable = icons.getDrawable(0);

							TypedArray colors = res.obtainTypedArray(R.array.colors);
							int color = colors.getColor(0,0);*/
						}
					});
			return builder.create();
		}
	}

	@SuppressLint("ValidFragment")
	public class SelectHezb extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			final int[] page = getResources().getIntArray(R.array.hezb);
			List<String> hezbItems = new ArrayList<>();
			String[] hezPart = {"1/4","1/2","3/4"};
			String hezbString = getString(R.string.hezb);
			for(int i=0; i< page.length;  i++){
				if(i%4 == 0){
					hezbItems.add(hezbString + " " + (Integer.valueOf(i/4) +1));
					continue;
				}
				hezbItems.add(hezPart[i%4 - 1]);
			}
			CharSequence[] hez = hezbItems.toArray(new CharSequence[hezbItems.size()]);
			builder.setTitle(R.string.hezb)
					.setItems(hez, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							mViewPager.setCurrentItem(pagesCount - page[which]);
						}
					});
			return builder.create();
		}
	}

	@SuppressLint("ValidFragment")
	public class SelectJuz extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			List<String> juzItems = new ArrayList<>();
			int j=1;
			String juzString = getString(R.string.juz);
			int[] hezb = getResources().getIntArray(R.array.hezb);
			final int[] page = new int[30];
			for(int i=0; i< hezb.length;  i++){
				if(i%8 == 0){
					juzItems.add(juzString + " " + j);
					page[j-1]=hezb[i];
					j++;
				}
			}
			CharSequence[] hez = juzItems.toArray(new CharSequence[juzItems.size()]);
			builder.setTitle(R.string.juz)
					.setItems(hez, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							mViewPager.setCurrentItem(pagesCount - page[which]);
						}
					});
			return builder.create();
		}
	}

	@SuppressLint("ValidFragment")
	public class SelectBookmark extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			int k = 0;
			CharSequence[] pageName = new CharSequence[setMark.size()];
			CharSequence[] pageNumber = new CharSequence[setMark.size()];
			for(String page: setMark){
				pageName[k] = page;
				pageNumber[k] = page.substring(page.lastIndexOf(" ")+1, page.length());
				k++;
			}
//			CharSequence[] pageName = setName.toArray(new CharSequence[setName.size()]);
//			nameFormat.substring(nameFormat.lastIndexOf(" ")+1, nameFormat.length())
			final CharSequence[] pageNumbers = pageNumber;
			builder.setTitle(R.string.bookmark)
					.setItems(pageName, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							mViewPager.setCurrentItem(pagesCount - Integer.valueOf(pageNumbers[which].toString()) - getResources().getInteger(R.integer.pagesLeading));
						}
					});
			return builder.create();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		DialogFragment newFragment;
		Editor edit;
		switch (item.getItemId()) {
	    /*
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	            Intent intent = new Intent(this, HomeActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        */
			case R.id.action_local:
				edit = prefs.edit();
				edit.putInt("LastPage", mViewPager.getCurrentItem());
				if (edit.commit()) {
					Locale locale = new Locale("ar");
					if (item.getTitle().equals("En")){
						locale = new Locale("en");
					}
					Locale.setDefault(locale);
					Configuration config = new Configuration();
					config.locale = locale;
					getBaseContext().getResources().updateConfiguration(config,getResources().getDisplayMetrics());
					startActivity(new Intent(this, MainActivity.class));
					finish();
				}
				return true;
			case R.id.action_about:
				newFragment = new AboutDeveloper();
				newFragment.show(getSupportFragmentManager(), "what");
				return true;
			case R.id.action_bookmark:
				/*setPage.clear();
				setName.clear();*/
//				MenuItem item = menu.findItem(R.id.action_bookmark);
				String nameFormat = currentSurah + ", " + currentJuz + ", " + currentHezb + ", " + getString(R.string.page) + " " + currentPageN;
				if (item.getTitle() == getString(R.string.bookmarked)) {
					item.setIcon(R.drawable.ic_bookmark_border_24dp);
					item.setTitle(getString(R.string.bookmark));
					setMark.remove(nameFormat);
				} else {
					item.setIcon(R.drawable.ic_bookmark_24dp);
					item.setTitle(getString(R.string.bookmarked));
					setMark.add(nameFormat);
					edit = prefs.edit();
					edit.putStringSet("Bookmarks", setMark);
					if (edit.commit()) {
						Snackbar.make(mViewPager,R.string.bookmark_save_success, Snackbar.LENGTH_SHORT).show();
					}else{
						Snackbar.make(mViewPager,R.string.bookmark_error, Snackbar.LENGTH_SHORT).show();
					}
				}
				/*
				*/
//				Toast.makeText(getBaseContext(),"Jz" +currentJuz + "\nSur" + currentSurah + " \nhe" + currentHezb + " \npag" + currentPageN, Toast.LENGTH_LONG).show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private View.OnClickListener clickListener = new View.OnClickListener() {
		DialogFragment newFragment;
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.action_surah:
					newFragment = new SelectSurah();
					newFragment.show(getSupportFragmentManager(), "what");
					mFab.close(true);
					break;
				case R.id.action_juz:
					newFragment = new SelectJuz();
					newFragment.show(getSupportFragmentManager(), "what");
					mFab.close(true);
					break;
				case R.id.action_hezb:
					newFragment = new SelectHezb();
					newFragment.show(getSupportFragmentManager(), "what");
					mFab.close(true);
					break;
				case R.id.action_bookmark:
					newFragment = new SelectBookmark();
					newFragment.show(getSupportFragmentManager(), "what");
//					Toast.makeText(getBaseContext(),"Jz" +currentJuz + "\nSur" + currentSurah + " \nhe" + currentHezb + " \npag" + currentPageN, Toast.LENGTH_LONG).show();
					mFab.close(true);
					break;
			}
		}
	};

	/*@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}*/

}
