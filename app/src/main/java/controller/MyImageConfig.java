package controller;

import android.widget.ImageView;

import com.blo.reade.R;

public class MyImageConfig {


    public void imagecheck(String link,ImageView imageView){
        //if image is from bbc source and is null, set bbc logo
        if (link.matches("(.*)www.bbc.co.uk/news(.*)")) {
            imageView.setImageResource(R.drawable.bbcnewslogo1);
            // imageView.setBackgroundResource(R.drawable.bbcnewslogo1);
        }
        //BBC Sports Check
        else if (link.matches("(.*)www.bbc.co.uk/sport(.*)")){
            imageView.setImageResource(R.drawable.bbcsportslogo);
        }
        //CBS NBA Check
        else if (link.matches("(.*)https://www.cbssports.com/(.*)")){
            imageView.setImageResource(R.drawable.cbssports);
        }

        ///default image here
        else {
            imageView.setImageResource(R.drawable.testimage);
            // imageView.setBackgroundResource(R.drawable.testimage);
        }
    }
}
