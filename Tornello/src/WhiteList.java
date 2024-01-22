import it.ipzs.cie.nis.ias.NisAutheticated;

import java.io.*;

public class WhiteList {
    public static boolean checkCie(String whiteList, NisAutheticated nisAutheticated){
        //controllo che NIS e HKpub sono presenti nel file
        try (BufferedReader reader = new BufferedReader(new FileReader(whiteList))) {
            String riga;
            while ((riga = reader.readLine()) != null) {
                if (riga.equals(nisAutheticated.getNis() + "#" + nisAutheticated.getHaskKpubIntServ())) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static boolean registraCie(String whiteList, NisAutheticated nisAutheticated) {
        //se la CIE non è presente in white_list.txt allora la registro nel file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(whiteList, true))) {
            // Verifica se il file non esiste e crea il file se necessario
            if (!fileEsiste(whiteList)) {
                writer.write("");  // Crea un nuovo file vuoto
            }
            if(checkCie(whiteList,nisAutheticated))
                System.out.println("La CIE è già stata registrata");
            else {
                // Scrivi la CIE nel file
                writer.write(nisAutheticated.getNis() + "#" + nisAutheticated.getHaskKpubIntServ());
                writer.newLine();
                return true;
            }
        }catch (Exception excp){throw new RuntimeException(excp);}
        return false;
    }
    public static boolean fileEsiste(String nomeFile) {
        return new java.io.File(nomeFile).exists();
    }

}
