package vista;

import controlador.AccesoFicheroCliente;
import modelo.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Consultados extends JFrame {

    AccesoFicheroCliente FicheroCliente;
    JTable table;

    Consultados() {
        super("Listado de usuarios");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);

        try {

            FicheroCliente = new AccesoFicheroCliente();

            ArrayList<Cliente> clientes = FicheroCliente.getRegistros();


            String[] titles = {"RFC", "NOMBRE", "EDAD", "ID Ciudad"};

            DefaultTableModel tablemodel = new DefaultTableModel(titles, 0);

            for (Cliente item : clientes) {

                Object[] row = {item.getRFC(), item.getName().trim().toUpperCase(), item.getAge(), item.getCityID()};
                tablemodel.addRow(row);

            }

            table = new JTable(tablemodel);
            table.setEnabled(false);
            add(new JScrollPane(table), BorderLayout.CENTER);

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
