// Generated by view binder compiler. Do not edit!
package com.albertmed.sizlectorcodigos.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.view.PreviewView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.albertmed.sizlectorcodigos.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentScannerBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final PreviewView cameraPreview;

  private FragmentScannerBinding(@NonNull ConstraintLayout rootView,
      @NonNull PreviewView cameraPreview) {
    this.rootView = rootView;
    this.cameraPreview = cameraPreview;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentScannerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentScannerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_scanner, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentScannerBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.camera_preview;
      PreviewView cameraPreview = ViewBindings.findChildViewById(rootView, id);
      if (cameraPreview == null) {
        break missingId;
      }

      return new FragmentScannerBinding((ConstraintLayout) rootView, cameraPreview);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
