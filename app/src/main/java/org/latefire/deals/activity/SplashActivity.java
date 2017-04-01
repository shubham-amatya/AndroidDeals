package org.latefire.deals.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.latefire.deals.R;
import org.latefire.deals.auth.AuthActivity;

/**
 * Created by phongnguyen on 3/20/17.
 */

public class SplashActivity extends BaseActivity {
  private FirebaseAuth mFirebaseAuth;
  private FirebaseUser mFirebaseUser;
  private GoogleApiClient mGoogleApiClient;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    mFirebaseAuth = FirebaseAuth.getInstance();
    mFirebaseUser = mFirebaseAuth.getCurrentUser();

    //TODO: can't sign in google so temporality solution is opposite with true case (mFirebaseUser == null)
    new Handler().postDelayed(() -> {
      Intent t;
      if (mFirebaseUser == null) {
        t = new Intent(SplashActivity.this, AuthActivity.class);
      } else {
        t = new Intent(SplashActivity.this, HomeActivityCustomer.class);
      }
      t.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
      startActivity(t);
    }, 1500);
  }
}
