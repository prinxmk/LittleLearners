package com.littlelearners.app;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.view.View;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import java.util.Locale;

public class MainActivity extends Activity {
  private TextToSpeech tts;
  private ToneGenerator tone;

  @Override public void onCreate(Bundle b) {
    super.onCreate(b);
    getWindow().setStatusBarColor(Color.TRANSPARENT);
    tone = new ToneGenerator(AudioManager.STREAM_MUSIC, 85);
    tts = new TextToSpeech(this, status -> {
      if (status == TextToSpeech.SUCCESS) {
        int result = tts.setLanguage(Locale.US);
        tts.setSpeechRate(0.80f);
        tts.setPitch(1.20f);
      }
    });
    WebView w = new WebView(this);
    w.setBackgroundColor(Color.rgb(255,247,232));
    WebSettings s = w.getSettings();
    s.setJavaScriptEnabled(true);
    s.setDomStorageEnabled(true);
    s.setAllowFileAccess(true);
    s.setMediaPlaybackRequiresUserGesture(false);
    s.setBuiltInZoomControls(false);
    w.setOverScrollMode(View.OVER_SCROLL_NEVER);
    w.addJavascriptInterface(new KidsBridge(), "AndroidBridge");
    w.loadUrl("file:///android_asset/index.html");
    setContentView(w);
  }

  public class KidsBridge {
    @JavascriptInterface public void speak(String text) {
      if (tts != null) tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "ll-voice");
    }
    @JavascriptInterface public void cheer() {
      if (tone == null) return;
      tone.startTone(ToneGenerator.TONE_PROP_ACK, 120);
      new android.os.Handler().postDelayed(() -> tone.startTone(ToneGenerator.TONE_PROP_BEEP2, 130), 140);
      new android.os.Handler().postDelayed(() -> tone.startTone(ToneGenerator.TONE_PROP_ACK, 160), 300);
    }
    @JavascriptInterface public void tryAgain() {
      if (tone != null) tone.startTone(ToneGenerator.TONE_PROP_NACK, 130);
    }
    @JavascriptInterface public void tap() {
      if (tone != null) tone.startTone(ToneGenerator.TONE_PROP_BEEP, 55);
    }
  }

  @Override protected void onDestroy() {
    if (tts != null) { tts.stop(); tts.shutdown(); }
    if (tone != null) tone.release();
    super.onDestroy();
  }
}