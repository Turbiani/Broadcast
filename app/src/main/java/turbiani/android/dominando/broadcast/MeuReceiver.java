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
        //ISSO É SÓ UM TESTE, NÃO USE NA SUA APP....POIS É BEM CHATO HAHA
        //TODA VEZ QUE INICIAR O CELULAR VAI ABRIR NOSSO APP
        /*if(Intent.ACTION_BOOT_COMPLETED.equalsIgnoreCase(acao)){
            Intent it = new Intent(context, MainActivity.class);
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(it);
        }*/
    }
}
