package com.littlelearners.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.graphics.Color;
import android.view.View;

public class MainActivity extends Activity {
  @Override public void onCreate(Bundle b){ super.onCreate(b); getWindow().setStatusBarColor(Color.TRANSPARENT); WebView w=new WebView(this); w.setBackgroundColor(Color.rgb(255,247,232)); WebSettings s=w.getSettings(); s.setJavaScriptEnabled(true); s.setDomStorageEnabled(true); s.setAllowFileAccess(true); s.setMediaPlaybackRequiresUserGesture(false); w.setOverScrollMode(View.OVER_SCROLL_NEVER); w.loadUrl("file:///android_asset/index.html"); setContentView(w); }
}
