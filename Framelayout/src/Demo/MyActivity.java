
package Demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.framelayout.R;

public class MyActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.black);
        Button btnBroadcast = (Button) this.findViewById(R.id.button);
        btnBroadcast.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                SendBraodcastDemo.SendBroadcast(MyActivity.this,
                        Constant.Status.ONE);
            }
        });
    }
}
