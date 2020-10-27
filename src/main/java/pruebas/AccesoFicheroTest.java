package pruebas;

import controlador.ControladorFicheros;
import modelo.Utilidades;

import java.io.IOException;

public class AccesoFicheroTest extends ControladorFicheros {
    protected AccesoFicheroTest(String ruta) throws IOException {
        super(ruta);
    }

    public AccesoFicheroTest() throws IOException {
        this("C:/Users/ADMIN/Desktop/carpeta/Test.DAT");

    }

    public void grabar(String dato) throws IOException {
        String dato_ = Utilidades.padString(dato, 38);
        archivo.seek(archivo.length());
        archivo.writeUTF(dato_);

        System.out.println("Longitud del archivo:" + archivo.length());
    }

    public String getDato(String rfc) throws IOException {
        int d = getIndex(rfc);
        System.out.println(d);
        if (d != -1) {
            archivo.seek(d*getTamañoRegistro());

            String dat = archivo.readUTF();
            System.out.println(dat);
            return dat;
        }
        return null;
    }

    public int getIndex(String dato) throws IOException {

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

            System.out.println("Tamaño:" + dato.length() + "\tTamaño: " + valorCentro.length());
            System.out.println("Comparando a" + dato + "con " + valorCentro);

            int pos = dato.compareToIgnoreCase(valorCentro);
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




    @Override
    public int getTamañoRegistro() {
        return 40;
    }

    public static void main(String[] args) {
        String dato = Utilidades.padString("hola",38);
        String dato2 = Utilidades.padString("hola",38);
        int pos = dato.compareToIgnoreCase(dato2);
        System.out.println(pos);
    }

}