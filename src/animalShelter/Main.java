package animalShelter;

import com.sun.source.tree.WhileLoopTree;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Main extends JFrame{
    private JDBC db = new JDBC();
    private Connection connection;
    public static ArrayList<Animal> animalList = new ArrayList<Animal>();


    public Main() throws SQLException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.white);
        fillAnimalList();
        setupPanels();
        setTitle("Welcome!");
        setBounds(100, 100, 800, 550);
        setVisible(true);
    }

    public void setupPanels() throws SQLException {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(mainPanel);

        JLabel welcome = new JLabel("Lovely Paws Animal Shelter");
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            BufferedImage img = ImageIO.read(new File("src/logo/loving paw.png"));
            welcome.setIcon(new ImageIcon(img));
        } catch (IOException e){
            e.getMessage();
        }
        //welcome.setIcon(new ImageIcon(Main.class.getResource("src/logo/loving paw.png")));
        welcome.setForeground(new Color(75, 161, 222));
        welcome.setFont(new Font("Lava Kannada", Font.PLAIN, 40));

        JButton login = new JButton("Admin Login");
        login.setHorizontalAlignment(SwingConstants.LEFT);

        JButton appointment = new JButton("Make An Appointment");

        JButton adoption = new JButton("Start An Adoption");
        adoption.setHorizontalAlignment(SwingConstants.RIGHT);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        JList animalnameList = new JList(animalList.toArray());



        animalnameList.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        animalnameList.setToolTipText("");
        animalnameList.setBackground(new Color(202, 232, 255));
        animalnameList.setFont(new Font("Lava Kannada", Font.PLAIN, 13));
        animalnameList.setModel(new AnimalListModel());
        JTextArea animalinfoOutput = new JTextArea();
        animalnameList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = animalnameList.getSelectedIndex();
                animalinfoOutput.setText("");
                animalinfoOutput.append("Pet ID: " + animalList.get(index).getPetID() + "\n");
                animalinfoOutput.append("Name: " + animalList.get(index).getName() + "\n");
                animalinfoOutput.append("Breed: " + animalList.get(index).getBreed() + "\n");
                animalinfoOutput.append("Gender: " + animalList.get(index).getGender() + "\n");
                animalinfoOutput.append("Age: " + animalList.get(index).getAge() + "\n");
                animalinfoOutput.append("Description: " + animalList.get(index).getDescription() + "\n");
            }
        });

        animalnameList.setSelectedIndex(0);
        animalnameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);



        JScrollPane animalInfo = new JScrollPane();
        animalInfo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        animalInfo.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton liveChat = new JButton("Live Customer Service");
        liveChat.setForeground(new Color(81, 126, 204));
        liveChat.setBackground(new Color(81, 126, 204));



        GroupLayout gl_mainPanel = new GroupLayout(mainPanel);
        gl_mainPanel.setHorizontalGroup(
                gl_mainPanel.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_mainPanel.createSequentialGroup()
                                .addGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_mainPanel.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(welcome, GroupLayout.PREFERRED_SIZE, 766, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_mainPanel.createSequentialGroup()
                                                .addGap(126)
                                                .addGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(gl_mainPanel.createSequentialGroup()
                                                                .addComponent(login)
                                                                .addGap(55)
                                                                .addComponent(appointment)
                                                                .addGap(44)
                                                                .addComponent(adoption))
                                                        .addGroup(gl_mainPanel.createSequentialGroup()
                                                                .addGap(26)
                                                                .addGroup(gl_mainPanel.createParallelGroup(Alignment.TRAILING)
                                                                        .addComponent(liveChat)
                                                                        .addGroup(Alignment.LEADING, gl_mainPanel.createSequentialGroup()
                                                                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(62)
                                                                                .addComponent(animalInfo, GroupLayout.PREFERRED_SIZE, 297, GroupLayout.PREFERRED_SIZE)))))))
                                .addGap(18))
        );
        gl_mainPanel.setVerticalGroup(
                gl_mainPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_mainPanel.createSequentialGroup()
                                .addComponent(welcome, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_mainPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(login)
                                        .addComponent(appointment)
                                        .addComponent(adoption))
                                .addGap(32)
                                .addGroup(gl_mainPanel.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(gl_mainPanel.createSequentialGroup()
                                                .addComponent(animalInfo, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                                                .addGap(18)
                                                .addComponent(liveChat)
                                                .addGap(33))
                                        .addGroup(Alignment.LEADING, gl_mainPanel.createSequentialGroup()
                                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE)
                                                .addGap(80))))
        );


        animalinfoOutput.setWrapStyleWord(true);
        animalinfoOutput.setRows(10);
        animalInfo.setViewportView(animalinfoOutput);
        scrollPane.setViewportView(animalnameList);
        animalinfoOutput.setLineWrap(true);
        mainPanel.setLayout(gl_mainPanel);


        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login().setVisible(true);
                dispose();
            }
        });

        appointment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Appointment().setVisible(true);
            }
        });

        adoption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Adopt().setVisible(true);
                dispose();
            }
        });

        liveChat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Server server = new Server();
                server.setVisible(true);
                Thread serverThread = new Thread(server);
                serverThread.start();

            }
        });
    }

    public void fillAnimalList() throws SQLException {
        try {
            connection = db.getCon();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        String sql = "SELECT * FROM animal";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while(rs.next()){
            Animal animal = new Animal(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6));
            animalList.add(animal);
        }
    }

    public static void main(String[] args) throws SQLException {
        // Create a new instance of the LoginGUI class
        Main page = new Main();
    }
}




