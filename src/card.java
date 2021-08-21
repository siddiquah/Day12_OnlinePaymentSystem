import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

public class card {
    private JTextField txtCardno;
    private JTextField txtamtno;
    private JButton payButton;
    private JPanel mainPanel;


    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/card", "root","root");
            System.out.println("Success");
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    public card() {

        connect();

        txtCardno.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent arg0) {
                if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    String accno = txtCardno.getText();
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        pst = con.prepareStatement("select * from details where cardno = ?");
                        pst.setString(1, accno);
                        rs = pst.executeQuery();

                        if(rs.next() == false)
                        {
                            JOptionPane.showMessageDialog(null,"Card No no found");
                        }

                        else
                        {
                            txtamtno.requestFocus();

                        }
                    } catch (ClassNotFoundException e)
                    {
                        e.printStackTrace();
                    } catch (SQLException e)
                    {
                        e.printStackTrace();
                    }

                }
            }
        });

        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    String sqlupdate= "update details set amount= amount- ? where cardno=? ";
                    pst=con.prepareStatement(sqlupdate);
                    pst.setString(1,txtamtno.getText());
                    pst.setString(2,txtCardno.getText());

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Paid...!!!!");
                } catch (ClassNotFoundException e) {

                    e.printStackTrace();
                } catch (SQLException e) {

                    e.printStackTrace();
                }
            }
        });
    }






    public static void main(String[] args) {
        JFrame frame = new JFrame("card");
        frame.setContentPane(new card().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
