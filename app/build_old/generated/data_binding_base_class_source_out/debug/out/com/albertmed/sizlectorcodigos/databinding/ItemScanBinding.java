// Generated by view binder compiler. Do not edit!
package com.albertmed.sizlectorcodigos.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.albertmed.sizlectorcodigos.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemScanBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView scanDataText;

  @NonNull
  public final TextView scanTimestampText;

  private ItemScanBinding(@NonNull LinearLayout rootView, @NonNull TextView scanDataText,
      @NonNull TextView scanTimestampText) {
    this.rootView = rootView;
    this.scanDataText = scanDataText;
    this.scanTimestampText = scanTimestampText;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemScanBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemScanBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_scan, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemScanBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.scan_data_text;
      TextView scanDataText = ViewBindings.findChildViewById(rootView, id);
      if (scanDataText == null) {
        break missingId;
      }

      id = R.id.scan_timestamp_text;
      TextView scanTimestampText = ViewBindings.findChildViewById(rootView, id);
      if (scanTimestampText == null) {
        break missingId;
      }

      return new ItemScanBinding((LinearLayout) rootView, scanDataText, scanTimestampText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
