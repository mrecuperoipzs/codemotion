import it.ipzs.cie.nis.core.NfcTerminalImpl;
import it.ipzs.cie.nis.ias.NisAutheticated;
import it.ipzs.cie.nis.ias.NisSdk;
import it.ipzs.cie.nis.ias.NisSdkCallback;

public class MyCieApp implements NisSdkCallback {

    NisSdk nisSdk;
    boolean access;

    MyCieApp(){
        //init SDK
        nisSdk = new NisSdk(new NfcTerminalImpl(), this, false);
    }

    void registraCie(){
        access = false;
        //fase di enroll
        if(nisSdk.isReady()){
            //metodo per eseguire la registrazione
            nisSdk.enroll();
        }
    }

    void accessoConCie(){
        access = true;
        //fase di accesso
        if(nisSdk.isReady()){
            //metodo che mostra le operazioni di accesso da eseguire al tornello
            nisSdk.access();
        }
    }


    @Override
    public void onSuccess(NisAutheticated nisAutheticated) {
        //se sono in registrazione, controllo se la CIE è presente nella white_list.txt altrimenti la scrivo
        //se sono in accesso, controllo che la CIE è presente nella white_list.txt
        if(access) {
            if (WhiteList.checkCie("white_list.txt", nisAutheticated))
                System.out.println("Benvenuto:" + nisAutheticated.toString());
            else
                System.out.println("Accesso non consentito:" + nisAutheticated.toString() + " carta non registrata");
        }else{
            if(WhiteList.registraCie("white_list.txt", nisAutheticated))
                System.out.println("Cie registrata correttamente! " + nisAutheticated.toString());
        }
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
