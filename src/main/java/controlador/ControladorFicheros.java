package controlador;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class ControladorFicheros {

    final private String ruta;
    protected RandomAccessFile archivo;

    protected ControladorFicheros(String ruta) throws IOException {
        this.ruta = ruta;
        archivo = new RandomAccessFile(new File(this.ruta), "rw");
    }

    private String getRuta() {
        return ruta;
    }

    public boolean cerrar() throws IOException {
        try {
            archivo.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }


    public int getTotalRegistros() throws IOException {
        return (int) Math.ceil((double) archivo.length() / (double) getTamañoRegistro());
    }

    public abstract int getTamañoRegistro();

}