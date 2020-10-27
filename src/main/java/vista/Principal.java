package vista;

import controlador.AccesoFicheroCliente;
import modelo.Cliente;
import modelo.Utilidades;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import static javax.swing.SwingConstants.CENTER;

public class Principal extends JFrame implements ActionListener, KeyListener {
    AccesoFicheroCliente FicheroCliente;

    JPanel capture;
    JLabel rfc, name, age, cityID;
    JTextField rfcT, nameT, ageT, cityIDT;
    JButton create, read, update, delete, list, clean;

    Font font;


    public Principal() {
        hazInterfaz();
        hazEscuchas();

        try {
            FicheroCliente = new AccesoFicheroCliente();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void hazInterfaz() {
        setTitle("CRUD");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        font = new Font("Lucida Sans", Font.PLAIN, 14);

        capture = new JPanel();
        capture.setLayout(new GridLayout(0, 2));

        rfc = new JLabel("RFC");
        name = new JLabel("NOMBRE");
        age = new JLabel("EDAD");
        cityID = new JLabel("ID CIUDAD");

        rfc.setFont(font);
        name.setFont(font);
        age.setFont(font);
        cityID.setFont(font);

        rfc.setHorizontalAlignment(CENTER);
        name.setHorizontalAlignment(CENTER);
        age.setHorizontalAlignment(CENTER);
        cityID.setHorizontalAlignment(CENTER);


        rfcT = new JTextField();
        nameT = new JTextField();
        ageT = new JTextField();
        cityIDT = new JTextField();

        create = new JButton("CREAR");
        read = new JButton("BUSCAR");
        update = new JButton("ACTUALIZAR");
        delete = new JButton("BORRAR");
        list = new JButton("LISTAR");
        clean = new JButton("LIMPIAR");


        capture.add(rfc);
        capture.add(rfcT);
        capture.add(name);
        capture.add(nameT);
        capture.add(age);
        capture.add(ageT);
        capture.add(cityID);
        capture.add(cityIDT);
        capture.add(create);
        capture.add(read);
        capture.add(update);
        capture.add(delete);
        capture.add(list);
        capture.add(clean);


        add(capture);
        setVisible(true);


    }


    private void hazEscuchas() {

        create.addActionListener(this);
        rfcT.addKeyListener(this);
        nameT.addKeyListener(this);
        ageT.addKeyListener(this);
        cityIDT.addKeyListener(this);
        clean.addActionListener(this);
        read.addActionListener(this);
        list.addActionListener(this);
        update.addActionListener(this);
        delete.addActionListener(this);

    }


    private Cliente getCliente() {

        if (!ValidarFormulario())
            return null;

        String rfc = rfcT.getText();
        String name = nameT.getText();
        int age = Integer.parseInt(ageT.getText());
        int idCity = Integer.parseInt(cityIDT.getText());

        return new Cliente(rfc, name, age, idCity);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == create) {
            Cliente cliente = getCliente();
            if (cliente == null) {
                return;
            }

            try {
                if (FicheroCliente.grabar(cliente)) {
                    Message("", "Cliente registrado.");
                    return;
                }
                Message("", "El cliente ya existe");
                return;
            } catch (IOException exception) {
            }
        }

        if (e.getSource() == clean) {
            clean();
            return;
        }

        if (e.getSource() == read) {

            if (!ValidarCampoBusqueda()) {
                return;
            }

            String rfc = rfcT.getText();

            try {
                Cliente cliente = FicheroCliente.getCliente(rfc);

                if (cliente == null) {
                    Message("Error", "Cliente dado de baja o no existe.");
                    return;
                }
                nameT.setText(cliente.getName().trim());
                ageT.setText(String.valueOf(cliente.getAge()));
                cityIDT.setText(String.valueOf(cliente.getCityID()));

                return;
            } catch (IOException exception) {
            }

        }
        if (e.getSource() == list) {
            new Consultados();
            return;
        }

        if (e.getSource() == update) {

            if (!ValidarFormulario()) {
                return;
            }

            String rfc = rfcT.getText();
            String name = Utilidades.padString(nameT.getText(), 40);
            String age = ageT.getText();
            String cityid = cityIDT.getText();

            try {
                Cliente cliente = FicheroCliente.getCliente(rfc);

                if (cliente != null) {
                    FicheroCliente.actualizarCliente(rfc, name, age, cityid);
                    Message("Success", "Cliente modificado.");
                    return;
                }
                Message("Error", "No se puede modificar un cliente que no existe.");

            } catch (IOException exception) {
            }


        }

        if (e.getSource() == delete) {
            if (!ValidarCampoBusqueda()) {
                return;
            }

            String rfc = rfcT.getText();

            try {
                if (FicheroCliente.eliminarCliente(rfc)) {
                    Message("Exito", "Cliente eliminado.");
                    return;
                }
                Message("Error", "No exite cliente con ese RFC");
                return;
            } catch (IOException exception) {
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == rfcT) {
            if (rfcT.getText().length() >= 10)
                e.consume();
            return;
        }


        if (e.getSource() == nameT)
            if (Character.isDigit(e.getKeyChar())) {
                e.consume();
                return;
            }


        if (e.getSource() == ageT) {
            if (Character.isLetter(e.getKeyChar()) || ageT.getText().length() > 1)
                e.consume();
            return;
        }

        if (e.getSource() == cityIDT)
            if (Character.isLetter(e.getKeyChar()) || cityIDT.getText().length() > 1) {
                e.consume();
                return;
            }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void clean() {
        rfcT.setText("");
        nameT.setText("");
        ageT.setText("");
        cityIDT.setText("");
    }

    public boolean ValidarFormulario() {

        String rfc = rfcT.getText();
        String name = nameT.getText();
        String age = ageT.getText();
        String idcity = cityIDT.getText();

        if (rfc.isEmpty() || rfc.length() != 10 || name.isEmpty() || age.isEmpty() || idcity.isEmpty()) {
            Message("Error", "Complete el formulario correctamente.");
            return false;
        }
        return true;

    }

    public boolean ValidarCampoBusqueda() {
        String rfc = rfcT.getText();

        if (rfc.isEmpty() || rfc.length() != 10) {

            Message("Error", "Ingrese rfc.");
            return false;

        }
        return true;
    }

    private void Message(String message, String title) {

        JOptionPane.showMessageDialog(this, title, message, 3);
    }
}