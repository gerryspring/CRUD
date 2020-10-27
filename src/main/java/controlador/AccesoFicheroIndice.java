package controlador;

import modelo.Cliente;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.util.ArrayList;

public class AccesoFicheroIndice extends ControladorFicheros {

    public AccesoFicheroIndice(String ruta) throws IOException {
        super(ruta);
    }

    public AccesoFicheroIndice() throws IOException {
        this("C:/Users/ADMIN/Desktop/carpeta/Indices.DAT");
    }


    public void grabar(String rfc) throws IOException {
        int count = getTotalRegistros();

        archivo.seek(archivo.length());
        archivo.writeUTF(rfc);
        archivo.writeInt(count);
        archivo.writeChar('A');

        count++;
    }

    public String getRFC(int index) throws IOException {
        archivo.seek(index * getTamañoRegistro());
        return archivo.readUTF();
    }

    public int getPosicion(int index) throws IOException {
        archivo.seek(index * getTamañoRegistro());
        archivo.skipBytes(12);
        return archivo.readInt();
    }

    public char getEstado(int index) throws IOException {
        archivo.seek(index * getTamañoRegistro());
        archivo.skipBytes(16);
        return archivo.readChar();
    }

    @Override
    public int getTamañoRegistro() {
        return 18;
    }

    public void ordenar() throws IOException {
        String rfci, rfcj;
        int numeroRegistroi, numeroRegistroj;
        char estadoi, estadoj;

        archivo.seek(0);

        for (int i = 0; i < getTotalRegistros() - 1; i++) {
            for (int j = i+1; j < getTotalRegistros(); j++) {
                archivo.seek(i * getTamañoRegistro());
                rfci = archivo.readUTF();
                numeroRegistroi = archivo.readInt();
                estadoi = archivo.readChar();

                archivo.seek(j * getTamañoRegistro());
                rfcj = archivo.readUTF();
                numeroRegistroj = archivo.readInt();
                estadoj = archivo.readChar();

                if (rfci.compareToIgnoreCase(rfcj) > 0) {
                    archivo.seek(i * getTamañoRegistro());
                    archivo.writeUTF(rfcj);
                    archivo.writeInt(numeroRegistroj);
                    archivo.writeChar(estadoj);

                    archivo.seek(j * getTamañoRegistro());
                    archivo.writeUTF(rfci);
                    archivo.writeInt(numeroRegistroi);
                    archivo.writeChar(estadoi);
                }
            }
        }
    }


    public boolean validarEstado(int index) throws IOException {


        archivo.seek(index * getTamañoRegistro());
        archivo.skipBytes(16);
        if (archivo.readChar() == 'B') {
            return false;
        }
        return true;
    }

    public boolean validarExistencia(String rfc) throws IOException {

        int resultado =busquedaBinaria(rfc);

        if(resultado!=-1)
            return true;

        return false;
    }

    public int busquedaBinaria(String rfc ) throws IOException {
        int centro,primero,ultimo;
        String valorCentro;
        primero = getTotalRegistros()-1;

        System.out.println("Total de registros: " + getTotalRegistros());
        ultimo = 0;

        System.out.println("Ultimo:" + ultimo + " menor igual " + primero);

        while(ultimo<=primero){
            centro = (primero+ultimo) / 2;
            System.out.println("Centro: " + centro);
            archivo.seek(centro*getTamañoRegistro());

            valorCentro = archivo.readUTF().trim();

            System.out.println("Tamaño:" + rfc.length() + "\tTamaño: " + valorCentro.length());
            System.out.println("Comparando a" + rfc + "con " + valorCentro);

            int pos = rfc.compareToIgnoreCase(valorCentro);
            System.out.println("Pos resultado: "+ pos);

            if(pos==0) {
                System.out.println("Son iguales");
                return centro;
            }else if(pos>0){
                System.out.println("No son iguales res");
                ultimo = centro+1;
                System.out.println("Ultimo es igual a : " + ultimo);
            }else{
                System.out.println("No se que pasa aqui" + primero);
                primero  = centro-1;
            }
        }
        return -1;
    }

    public void modificar(int pos) throws IOException {
        archivo.seek(pos * getTamañoRegistro());
        archivo.skipBytes(16);
        archivo.writeChar('B');
    }

    public static void main(String[] args) {

    }
}
