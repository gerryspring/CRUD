package controlador;

import modelo.Cliente;
import modelo.Utilidades;

import java.io.IOException;
import java.util.ArrayList;

public class AccesoFicheroCliente extends ControladorFicheros {

    protected AccesoFicheroIndice FicheroIndice;

    public AccesoFicheroCliente(String ruta) throws IOException {
        super(ruta);
        FicheroIndice = new AccesoFicheroIndice();
    }

    public AccesoFicheroCliente() throws IOException {
        this("C:/Users/ADMIN/Desktop/carpeta/Clientes.DAT");

    }


    public boolean grabar(Cliente cliente) throws IOException {

        String rfc = cliente.getRFC(), name = Utilidades.padString(cliente.getName(), 40);
        int edad = cliente.getAge(), idcity = cliente.getCityID();

        if (getTotalRegistros() != 0) {
            if (FicheroIndice.validarExistencia(rfc))
                return false;
        }

        FicheroIndice.grabar(rfc);

        archivo.seek(archivo.length());
        archivo.writeUTF(name);
        archivo.writeInt(edad);
        archivo.writeInt(idcity);

        FicheroIndice.ordenar();
        return true;
    }


    public ArrayList<Cliente> getRegistros() throws IOException {

        FicheroIndice.ordenar();

        ArrayList<Cliente> array = new ArrayList<>();

        String rfc, name;
        int age, idcity, posicion;
        char estado;

        for (int i = 0; i < getTotalRegistros(); i++) {

            archivo.seek(i * getTamañoRegistro());

            rfc = FicheroIndice.getRFC(i);
            posicion = FicheroIndice.getPosicion(i);
            estado = FicheroIndice.getEstado(i);

            if(estado=='B')
                continue;

            archivo.seek(posicion * getTamañoRegistro());
            name = archivo.readUTF();
            age = archivo.readInt();
            idcity = archivo.readInt();

            Cliente cliente = new Cliente(rfc, name, age, idcity);
            array.add(cliente);
        }

        return array;
    }

    public Cliente getCliente(String rfc) throws IOException {

        String nombre;
        int edad;
        int idciudad;


        int pos = FicheroIndice.busquedaBinaria(rfc);

            if(pos!=-1){
        if(!FicheroIndice.validarEstado(pos)) {
            return null;
        }

            archivo.seek(pos * getTamañoRegistro());

            nombre = archivo.readUTF();
            edad = archivo.readInt();
            idciudad = archivo.readInt();

            Cliente clienteRecuperado = new Cliente(rfc, nombre, edad, idciudad);

            return clienteRecuperado;
        }

        return null;


    }

    public void actualizarCliente(String rfc, String name, String age, String idCity) throws IOException {

        int pos = FicheroIndice.busquedaBinaria(rfc);

        if (FicheroIndice.validarExistencia(rfc) && FicheroIndice.validarEstado(pos)) {
            int newage = Integer.parseInt(age);
            int newidcity = Integer.parseInt(idCity);

            archivo.seek(pos * getTamañoRegistro());

            archivo.writeUTF(name);
            archivo.writeInt(newage);
            archivo.writeInt(newidcity);

        }
    }

    public boolean eliminarCliente(String rfc) throws IOException {

        int pos = FicheroIndice.busquedaBinaria(rfc);

        if (pos != -1 && FicheroIndice.validarEstado(pos)) {
            FicheroIndice.modificar(pos);
            return true;
        }
        return false;
    }

    @Override
    public int getTamañoRegistro() {
        return 50;
    }
}
