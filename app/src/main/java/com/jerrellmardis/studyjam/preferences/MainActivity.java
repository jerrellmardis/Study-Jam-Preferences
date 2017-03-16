package com.jerrellmardis.studyjam.preferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
    implements SharedPreferences.OnSharedPreferenceChangeListener {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    init();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_settings:
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    if (key.equals(getString(R.string.pref_show_address))) {
      updateAddressFieldVisibility(sharedPreferences);
    } else if (key.equals(getString(R.string.pref_year))) {
      TextView yearText = (TextView) findViewById(R.id.year);
      yearText.setText(sharedPreferences.getString(key, ""));
    }
  }

  private void init() {
    final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    prefs.registerOnSharedPreferenceChangeListener(this);

    TextView nameText = (TextView) findViewById(R.id.name);
    nameText.setText(prefs.getString(getString(R.string.pref_student_name), ""));
    nameText.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override public void afterTextChanged(Editable s) {
        prefs.edit().putString(getString(R.string.pref_student_name), s.toString()).apply();
      }
    });

    updateAddressFieldVisibility(prefs);
  }

  private void updateAddressFieldVisibility(SharedPreferences prefs) {
    boolean showAddrField = prefs.getBoolean(getString(R.string.pref_show_address), true);
    TextView addrText = (TextView) findViewById(R.id.address);
    addrText.setVisibility(showAddrField ? View.VISIBLE : View.GONE);


  }
}
