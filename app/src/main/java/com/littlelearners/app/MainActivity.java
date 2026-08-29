package com.littlelearners.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
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
  private final Handler handler = new Handler();

  @Override public void onCreate(Bundle b) {
    super.onCreate(b);
    getWindow().setStatusBarColor(Color.TRANSPARENT);
    tone = new ToneGenerator(AudioManager.STREAM_MUSIC, 85);
    tts = new TextToSpeech(this, status -> {
      if (status == TextToSpeech.SUCCESS) {
        tts.setLanguage(Locale.US);
        tts.setSpeechRate(0.78f);
        tts.setPitch(1.18f);
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
      playTone(ToneGenerator.TONE_PROP_ACK, 0);
      handler.postDelayed(() -> playTone(ToneGenerator.TONE_PROP_BEEP2, 0), 120);
      handler.postDelayed(() -> playTone(ToneGenerator.TONE_PROP_ACK, 0), 250);
    }
    @JavascriptInterface public void tryAgain() {
      playTone(ToneGenerator.TONE_PROP_NACK, 0);
    }
    @JavascriptInterface public void tap() {
      playTone(ToneGenerator.TONE_PROP_BEEP, 0);
    }
    private void playTone(int toneType, int ignored) {
      if (tone != null) tone.startTone(toneType, 100);
    }
  }

  @Override protected void onDestroy() {
    if (tts != null) { tts.stop(); tts.shutdown(); }
    if (tone != null) tone.release();
    super.onDestroy();
  }
}
