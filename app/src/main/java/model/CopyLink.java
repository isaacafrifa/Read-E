package model;

import android.content.Context;
import android.widget.Toast;

public class CopyLink {
private Context context;

    public CopyLink(Context context) {
        this.context = context;
    }

    //Copy Link Method
    public void copyLink(String text){
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Link", text);
        assert clipboard != null;
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "Link Copied", Toast.LENGTH_SHORT).show();
    }


}
