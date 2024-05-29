// Generated by view binder compiler. Do not edit!
package com.example.movieapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.movieapp.R;
import com.google.android.material.textfield.TextInputEditText;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityForgotPasswordBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppCompatButton btnEnter;

  @NonNull
  public final TextInputEditText editEmail;

  @NonNull
  public final AppCompatImageView imvLogoApp;

  @NonNull
  public final ConstraintLayout lyDescIntro;

  @NonNull
  public final ConstraintLayout lySigninAuth;

  @NonNull
  public final LinearLayout lyToolbar;

  @NonNull
  public final LinearLayout main;

  @NonNull
  public final AppCompatTextView txtSignin;

  @NonNull
  public final TextView txtSigninLabel;

  private ActivityForgotPasswordBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppCompatButton btnEnter, @NonNull TextInputEditText editEmail,
      @NonNull AppCompatImageView imvLogoApp, @NonNull ConstraintLayout lyDescIntro,
      @NonNull ConstraintLayout lySigninAuth, @NonNull LinearLayout lyToolbar,
      @NonNull LinearLayout main, @NonNull AppCompatTextView txtSignin,
      @NonNull TextView txtSigninLabel) {
    this.rootView = rootView;
    this.btnEnter = btnEnter;
    this.editEmail = editEmail;
    this.imvLogoApp = imvLogoApp;
    this.lyDescIntro = lyDescIntro;
    this.lySigninAuth = lySigninAuth;
    this.lyToolbar = lyToolbar;
    this.main = main;
    this.txtSignin = txtSignin;
    this.txtSigninLabel = txtSigninLabel;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityForgotPasswordBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityForgotPasswordBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_forgot_password, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityForgotPasswordBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnEnter;
      AppCompatButton btnEnter = ViewBindings.findChildViewById(rootView, id);
      if (btnEnter == null) {
        break missingId;
      }

      id = R.id.editEmail;
      TextInputEditText editEmail = ViewBindings.findChildViewById(rootView, id);
      if (editEmail == null) {
        break missingId;
      }

      id = R.id.imvLogoApp;
      AppCompatImageView imvLogoApp = ViewBindings.findChildViewById(rootView, id);
      if (imvLogoApp == null) {
        break missingId;
      }

      id = R.id.lyDescIntro;
      ConstraintLayout lyDescIntro = ViewBindings.findChildViewById(rootView, id);
      if (lyDescIntro == null) {
        break missingId;
      }

      id = R.id.lySigninAuth;
      ConstraintLayout lySigninAuth = ViewBindings.findChildViewById(rootView, id);
      if (lySigninAuth == null) {
        break missingId;
      }

      id = R.id.lyToolbar;
      LinearLayout lyToolbar = ViewBindings.findChildViewById(rootView, id);
      if (lyToolbar == null) {
        break missingId;
      }

      id = R.id.main;
      LinearLayout main = ViewBindings.findChildViewById(rootView, id);
      if (main == null) {
        break missingId;
      }

      id = R.id.txtSignin;
      AppCompatTextView txtSignin = ViewBindings.findChildViewById(rootView, id);
      if (txtSignin == null) {
        break missingId;
      }

      id = R.id.txtSigninLabel;
      TextView txtSigninLabel = ViewBindings.findChildViewById(rootView, id);
      if (txtSigninLabel == null) {
        break missingId;
      }

      return new ActivityForgotPasswordBinding((ConstraintLayout) rootView, btnEnter, editEmail,
          imvLogoApp, lyDescIntro, lySigninAuth, lyToolbar, main, txtSignin, txtSigninLabel);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
