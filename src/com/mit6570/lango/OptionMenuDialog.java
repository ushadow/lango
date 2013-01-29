package com.mit6570.lango;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class OptionMenuDialog {
  public static void showAbout(Context context) {
    AlertDialog.Builder b = new AlertDialog.Builder(context);
    b.setPositiveButton(android.R.string.ok, new OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        
      }
    });
    b.setTitle(R.string.menu_about);
    b.setMessage(R.string.about);
    b.show();
  }
  
  public static void showHelp(Context context) {
    AlertDialog.Builder b = new AlertDialog.Builder(context);
    b.setPositiveButton(android.R.string.ok, new OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        
      }
    });
    b.setTitle(R.string.menu_help);
    b.setMessage(R.string.ex_help);
    b.show();
  }
}