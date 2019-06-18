package com.example.myapptest;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class Welcome extends Activity implements OnGestureListener {
    private GestureDetector detector;
    private ViewFlipper flipper;
    private Button button;
    ImageView []iamges=new ImageView[4];
    int i = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutwelcome);
        iamges[0]=(ImageView) findViewById(R.id.imageview1);
        iamges[1]=(ImageView) findViewById(R.id.imageview2);
        iamges[2]=(ImageView) findViewById(R.id.imageview3);
        iamges[3]=(ImageView) findViewById(R.id.imageview4);

        detector = new GestureDetector(this);
        flipper = (ViewFlipper) this.findViewById(R.id.ViewFlipper1);
        flipper.addView(addImageView(R.drawable.png1o));
//        flipper.addView(addImageView(R.drawable.png2o));
//        flipper.addView(addImageView(R.drawable.png3o));
        flipper.addView(LayoutInflater.from(Welcome.this).inflate(R.layout.test1,null,false));
        flipper.addView(LayoutInflater.from(Welcome.this).inflate(R.layout.test2,null,false));

        flipper.addView(addView());

    }

    private View addImageView(int id) {
        ImageView iv = new ImageView(this);
        iv.setImageResource(id);
        return iv;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        return this.detector.onTouchEvent(event);
    }

    private View addView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.layoutwelcome2, null);
        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Intent intent = new
                // Intent(getApplicationContext(),ActContent.class);
                // startActivity(intent);
                // finish();
                Toast.makeText(Welcome.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() > 120) {
            if (i < 3) {
                i++;
                setImage(i);
                this.flipper.setInAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.animation_right_in));
                this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.animation_left_out));
                this.flipper.showNext();
            }
            return true;
        }
        else if (e1.getX() - e2.getX() < -120) {
            if (i > 0) {
                i--;
                setImage(i);
                this.flipper.setInAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.animation_left_in));
                this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.animation_right_out));
                this.flipper.showPrevious();
            }
            return true;
        }
        return false;

    }

    void setImage(int i)
    {
        for(int j=0;j<4;j++)
        {
            if(j!=i)
                iamges[j].setImageResource(R.drawable.xiao);
            else
                iamges[j].setImageResource(R.drawable.da);
        }
    }
}