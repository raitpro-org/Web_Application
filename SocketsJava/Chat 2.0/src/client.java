import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;
import java.net.*;



public class client
{
    static Scanner in = new Scanner(System.in);
    static DataOutputStream dos;
    static DataInputStream dis;
    static Socket s;
    static boss server;
    static String username;


	/**
	 * File sharing
	 */
    static FileInputStream fis = null;
    static BufferedInputStream bis = null;
    static OutputStream os = null;
    static InputStream is = null;
    static FileOutputStream fos = null;
    static BufferedOutputStream bos = null;
    static String FILE_TO_RECEIVE;
    static int bytesRead;
    static int current = 0;


    //-------------------------------





    static JTextPane chatMessages = new JTextPane();
    static JScrollPane JPchatMessages = new JScrollPane(chatMessages);

    static String msgHistory = new String("");


    public static void main(final String args[]) throws IOException
    {
            JFrame frame1 = new JFrame("Chat 2.0");
            JFrame frame2 = new JFrame("Chatroom");
            JFrame frame3 = new JFrame("Choose a file to send");


        	/**
        	 * Frame 2
        	 */

            frame2.setSize(500,500);
            frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame2.getContentPane().setLayout(null);


            JLabel helloUser = new JLabel("Say hi!");
            helloUser.setBackground(Color.WHITE);
            helloUser.setBounds(1, 1, 328, 24);
            frame2.getContentPane().add(helloUser);


            JButton logOutButton = new JButton("Logout");
            logOutButton.setBackground(Color.GRAY);
            logOutButton.setBounds(397, 1, 88, 24);
            frame2.getContentPane().add(logOutButton);
            chatMessages.setBackground(Color.LIGHT_GRAY);


            chatMessages.setEditable(false);
            JPchatMessages.setBounds(1, 25, 484, 413);
            frame2.getContentPane().add(JPchatMessages);



            JTextField message = new JTextField(20);
            message.setBackground(Color.WHITE);
            message.setBounds(1, 438, 328, 24);
            frame2.getContentPane().add(message);



            JButton send = new JButton("Send");
            send.setBackground(Color.GRAY);
            send.setBounds(329, 438, 68, 24);
            frame2.getContentPane().add(send);
            send.addActionListener(new ActionListener()
                                    {
                                        public void actionPerformed(ActionEvent sendButtonClick)
                                        {
                                            String msg = message.getText();
                                            message.setText(null);
                                            try
                                            {
                                                dos.writeUTF(username + " : " + msg);
                                            }
                                            catch(IOException e){}
                                        }
                                    });



            JButton sendFile = new JButton("Send File");
            sendFile.setBackground(Color.GRAY);
            sendFile.setBounds(397, 438, 88, 24);
            frame2.getContentPane().add(sendFile);
            sendFile.addActionListener(new ActionListener()
                                    {
                                        public void actionPerformed(ActionEvent sendFileButtonClick)
                                        {
                                            frame3.setVisible(true);
                                        }
                                    });
//----------------------------------------------------------------




        	/**
        	 * Frame 1
        	 */

            frame1.setSize(517,363);
            frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame1.getContentPane().setLayout(null);


            //backgrnd image
            JLabel background=new JLabel(new ImageIcon("logo.png"),JLabel.CENTER);
            background.setBounds(0, 0, 486, 95);
            frame1.getContentPane().add(background);


            //Label
            JLabel enter = new JLabel("Username");
            enter.setBounds(85, 149, 76, 13);
            frame1.getContentPane().add(enter);


            //Username Textfield
            JTextField usernameTextArea = new JTextField(10);
            usernameTextArea.setBounds(202, 146, 119, 19);
            frame1.getContentPane().add(usernameTextArea);


            //Login Button
            JButton login = new JButton("Login");
            login.setBounds(224, 213, 73, 21);
            frame1.getContentPane().add(login);
            
            //Password Textfield
            JTextField textField = new JTextField(0);
            textField.setBounds(202, 184, 119, 19);
            frame1.getContentPane().add(textField);
            textField.setColumns(10);
            
            //PASSWORD label
            JLabel lblNewLabel = new JLabel("Password");
            lblNewLabel.setBounds(85, 187, 76, 13);
            frame1.getContentPane().add(lblNewLabel);

            login.addActionListener(new ActionListener()
                                    {
                                        public void actionPerformed(ActionEvent buttonClick)
                                        {
                                            username = usernameTextArea.getText();
                                            helloUser.setText("Hello "+username+". Welcome !");
                                            frame1.setVisible(false);

                                            try
                                            {
                                                s = new Socket("192.168.247.134", 7777);
                                                dos = new DataOutputStream(s.getOutputStream());
                                                dis = new DataInputStream(s.getInputStream());

                                                server = new boss(dis);
                                                Thread t = new Thread(server);
                                                t.start();

                                                frame2.setVisible(true);
                                            }

                                            catch(IOException e)
                                            {
                                                System.out.println("Server unavailable to connect. Press Ctrl+C to exit..");
                                            }
                                        }
                                    });




            frame1.setVisible(true);
//----------------------------------------------------------------




        	/**
        	 * Frame 3
        	 */
            frame3.setSize(500,700);
            frame3.setLayout(new GridBagLayout());


            JFileChooser fileSelector = new JFileChooser();
            frame3.add(fileSelector, new GridBagConstraints(1,0,1,1,.25,.25,GridBagConstraints.CENTER,            GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));
            fileSelector.addActionListener(new ActionListener()
                                    {
                                        public void actionPerformed(ActionEvent fileChooserEvent)
                                        {
                                            String command = fileChooserEvent.getActionCommand();
                                            if(command.equals(JFileChooser.APPROVE_SELECTION))
                                            {
                                                File fileToBeSent = fileSelector.getSelectedFile();
                                                frame3.setVisible(false);

                                                try{
                                                    byte [] fileByteArray  = new byte [(int)fileToBeSent.length()];


                                                    fis = new FileInputStream(fileToBeSent);
                                                    bis = new BufferedInputStream(fis);
                                                    bis.read(fileByteArray,0,fileByteArray.length);
                                                    os = s.getOutputStream();
                                                    dos.flush();
                                                    os.flush();
                                                    dos.flush();
                                                    dos.writeUTF("46511231dsfdsfsd#@$#$#@^$%#@*$#^");
                                                    os.write(fileByteArray,0,fileByteArray.length);
                                                    os.flush();
                                                    System.out.println("Done.");
                                                    updateMessageArea("File Successfully Sent.");

                                                    if (os != null)
                                                        os.close();


                                                }
                                                catch(Exception e){}

                                            }
                                        }
                                    });



//----------------------------------------------------------------



    }



    public static void updateMessageArea(String msg)
    {
        msgHistory = msgHistory + "\n";
        msgHistory = msgHistory + msg;
        chatMessages.setText(msgHistory);
    }


    public static void reconnect()
    {
        try
        {
            s.close();
            s = new Socket("192.168.0.101", 7777);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
            server = new boss(dis);
            Thread newConnection = new Thread(server);
            newConnection.start();

        }
        catch(Exception e)
        {
            System.out.println("Exception caught in reconnect().");
        }
    }



    public static void receiveFile()
    {
        String workingDir = System.getProperty("user.dir");
        String FILE_TO_RECEIVED = workingDir+ "/imageReceived.jpg";
        int FILE_SIZE = 70000;
        int bytesRead;
        int current = 0;

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try {
            byte [] mybytearray  = new byte [FILE_SIZE];
            InputStream is = s.getInputStream();
            fos = new FileOutputStream(FILE_TO_RECEIVED);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray,0,mybytearray.length);
            current = bytesRead;

            bos.write(mybytearray, 0 , current);
            bos.close();
            System.out.println("File " + FILE_TO_RECEIVED+ " downloaded (" + current + " bytes read)");
            }
        catch(Exception e)
        {
            System.out.println("Exception caught in receiveFile().");

        }

    }


}



class boss extends Thread
{
    DataInputStream disServer;
    String secretCode = new String("46511231dsfdsfsd#@$#$#@^$%#@*$#^");

    public boss(DataInputStream z)
    {
        disServer = z;
    }

    public void run()
    {



        while(true)
        {
            try
            {
                String str = disServer.readUTF();
                if(str.equals(secretCode))
                {
                    client.receiveFile();
                }
                else
                    client.updateMessageArea(str);
            }

            catch(IOException e)
            {
                System.out.println("Exception in run method. Reconnecting..");

                client.reconnect();
                break;
            }

        }
    }
}
