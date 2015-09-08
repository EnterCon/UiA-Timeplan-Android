package martinothamar.uiatimeplan;

import martinothamar.uiatimeplan.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.skyfishjy.library.RippleBackground;



public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_start);
        Typeface face;

        face = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue-UltraLight.otf");
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setTypeface(face, Typeface.BOLD);
        final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);
        final ShimmerFrameLayout container =
                (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        container.startShimmerAnimation();
        rippleBackground.startRippleAnimation();


        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                try {
                    startActivity(intent);
                } catch (Exception ex) {
                    System.out.print(ex.getMessage());
                } finally {
                    rippleBackground.stopRippleAnimation();
                    container.stopShimmerAnimation();
                }
            }

        }.start();

        ImageView imageView=(ImageView)findViewById(R.id.centerImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                try {
                    startActivity(intent);
                } catch (Exception ex) {
                    System.out.print(ex.getMessage());
                } finally {
                    rippleBackground.stopRippleAnimation();
                    container.stopShimmerAnimation();
                }
            }
        });
    }


}
