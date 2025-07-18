// Generated by view binder compiler. Do not edit!
package com.albertmed.sizlectorcodigos.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.albertmed.sizlectorcodigos.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentHomeBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView emptyText;

  @NonNull
  public final ProgressBar loadingIndicator;

  @NonNull
  public final Button scanButton;

  @NonNull
  public final RecyclerView scansRecyclerView;

  private FragmentHomeBinding(@NonNull ConstraintLayout rootView, @NonNull TextView emptyText,
      @NonNull ProgressBar loadingIndicator, @NonNull Button scanButton,
      @NonNull RecyclerView scansRecyclerView) {
    this.rootView = rootView;
    this.emptyText = emptyText;
    this.loadingIndicator = loadingIndicator;
    this.scanButton = scanButton;
    this.scansRecyclerView = scansRecyclerView;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_home, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentHomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.empty_text;
      TextView emptyText = ViewBindings.findChildViewById(rootView, id);
      if (emptyText == null) {
        break missingId;
      }

      id = R.id.loading_indicator;
      ProgressBar loadingIndicator = ViewBindings.findChildViewById(rootView, id);
      if (loadingIndicator == null) {
        break missingId;
      }

      id = R.id.scan_button;
      Button scanButton = ViewBindings.findChildViewById(rootView, id);
      if (scanButton == null) {
        break missingId;
      }

      id = R.id.scans_recycler_view;
      RecyclerView scansRecyclerView = ViewBindings.findChildViewById(rootView, id);
      if (scansRecyclerView == null) {
        break missingId;
      }

      return new FragmentHomeBinding((ConstraintLayout) rootView, emptyText, loadingIndicator,
          scanButton, scansRecyclerView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
