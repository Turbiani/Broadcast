package turbiani.android.dominando.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String MINHA_ACAO = "turbiani.android.dominando.broadcast.MINHA_ACAO";

    ReceiverInterno  mReceiver;
    InternetReceiver internetReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mReceiver = new ReceiverInterno();
        internetReceiver = new InternetReceiver();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        IntentFilter filterLocal = new IntentFilter(MINHA_ACAO);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, filterLocal);

        //EXEMPLO DE RECEIVER DE EVENTO DE SISTEMA - QUANDO O USUÁRIO LIGA A TELA
        //MAS AINDA NÃO SAIU DA TELA DE BLOQUEIO
        IntentFilter filterSistema = new IntentFilter(Intent.ACTION_USER_PRESENT);
        registerReceiver(mReceiver, filterSistema);

        //REGISTRANDO OUTRO RECEIVER DE SISTEMA, PARA VERIFICAR A CONECTIVDADE DO APP
        IntentFilter filterInternet = new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION
        );
        registerReceiver(internetReceiver, filterInternet);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mReceiver);
        unregisterReceiver(mReceiver);
        unregisterReceiver(internetReceiver);
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

    class InternetReceiver extends BroadcastReceiver{
        boolean mPrimeiraVez = true;
        @Override
        public void onReceive(Context context, Intent intent) {
            //VOU FAZER ESSE IF PORQUE ALGUNS EVENTOS DE BROADCAST RECEIVER
            //SAO EXECUTADOS NA HORA QUE SAO REGISTRADOS....
            //COMO QUERO MOSTRAR O COMPORTAMENTO DO APP QUANDO LIGO E DESLIGO A INTERNET
            //VOU IGNORAR A PRIMEIRA CHAMADA DESTE EVENTO, QUE SERA REALIZADA LOGO QUE ABRIR O APP
            //PERDENDO A GRAÇA =/ RSRS

            if(mPrimeiraVez){
                mPrimeiraVez = false;
                return;
            }

            if(ConnectivityManager.CONNECTIVITY_ACTION.equalsIgnoreCase(intent.getAction())){
                TextView txtMensagem = (TextView)findViewById(R.id.txtMensagem);
                txtMensagem.setText(isConnected() ? "Estou conectado =)"
                : "Não estou mais =/");
            }
        }

        private boolean isConnected(){
            ConnectivityManager cm =
                    (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return  activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
        }
    }
}
