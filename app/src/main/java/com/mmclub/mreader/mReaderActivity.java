package com.mmclub.mreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class mReaderActivity extends Activity {

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        ImageView iv = (ImageView)findViewById(R.id.iv);
        Animation anima = AnimationUtils.loadAnimation(this, R.anim.anim);
     
        iv.startAnimation(anima);
        anima.setAnimationListener(new AnimationListener() {
			
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub start animation

			}
			
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub repeat animation
				
			}
			
			
			public void onAnimationEnd(Animation animation) {
				finish();
				Intent intent = new Intent(mReaderActivity.this,OpenCharpter.class);
				startActivity(intent);
				//finish();
				// TODO Auto-generated method stub end animation
				
			}
		});
    	
 
    }
    
}

