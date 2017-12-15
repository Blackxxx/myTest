
package Demo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class SendBraodcastDemo {

    public static final void SendBroadcast(Context constant, int one) {
        Intent intent = new Intent();
        intent.setAction(Constant.COLOUR);
        intent.putExtra(Constant.BLACK, one);
        constant.sendBroadcast(intent);
    }

    public static final void DynamicSendBroadcast(Context context, String status) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.COLOUR);
        MyReceiver mReceiver = new MyReceiver();
        filter.addCategory(status);
        context.registerReceiver(mReceiver, filter);
    }

    public static final void SendOrderBroadcast(Context context, String status) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.COLOUR);
        MyReceiver mReceiver = new MyReceiver();
        filter.addCategory(status);
        context.registerReceiver(mReceiver, filter);

    }

}
