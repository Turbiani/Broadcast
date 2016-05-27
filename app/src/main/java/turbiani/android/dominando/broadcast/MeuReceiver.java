package turbiani.android.dominando.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by turbiani on 26/05/16.
 */
public class MeuReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String acao = intent.getAction();
        Toast.makeText(context, "Ação: " + acao, Toast.LENGTH_SHORT).show();
    }
}
