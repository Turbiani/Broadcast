package turbiani.android.dominando.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String MINHA_ACAO = "turbiani.android.dominando.broadcast.MINHA_ACAO";

    ReceiverInterno mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mReceiver = new ReceiverInterno();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        IntentFilter filterLocal = new IntentFilter(MINHA_ACAO);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, filterLocal);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mReceiver);
    }

    public void enviarBroadcast(View v){
        Intent it = new Intent(MINHA_ACAO);
        sendBroadcast(it);
    }

    public void enviarLocalBroadcast(View v){
        Intent it = new Intent(MINHA_ACAO);
        LocalBroadcastManager.getInstance(this)
            .sendBroadcast(it);
    }


    class ReceiverInterno extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            TextView txtMensagem = (TextView)findViewById(R.id.txtMensagem);
            txtMensagem.setText("Ação:\n" + intent.getAction());
        }
    }
}
