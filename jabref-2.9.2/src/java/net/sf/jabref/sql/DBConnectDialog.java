/*  Copyright (C) 2003-2011 JabRef contributors.
    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/
package net.sf.jabref.sql;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import net.sf.jabref.Globals;

import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;


/**
 * Dialog box for collecting database connection strings from the user
 *
 * @author pattonlk
 */
public class DBConnectDialog extends JDialog {

    // labels
    JLabel lblServerType     = new JLabel();
    JLabel lblServerHostname = new JLabel();
    JLabel lblDatabase       = new JLabel();
    JLabel lblUsername       = new JLabel();
    JLabel lblPassword       = new JLabel();

    // input fields
    JComboBox cmbServerType = new JComboBox();
    JTextField txtServerHostname = new JTextField(40);
    JTextField txtDatabase = new JTextField(40);
    JTextField txtUsername = new JTextField(40);        
    JPasswordField pwdPassword = new JPasswordField(40);
    JButton btnConnect = new JButton();
    JButton btnCancel = new JButton();

    // array for holding components on left-hand and right-hand sides
    ArrayList<JLabel> lhs = new ArrayList<JLabel>();
    ArrayList<JComponent> rhs = new ArrayList<JComponent>();

    DBStrings dbStrings = new DBStrings();

    private boolean connectToDB = false;

    /** Creates a new instance of DBConnectDialog */
    public DBConnectDialog( JFrame parent, DBStrings dbs) {

        super(parent, Globals.lang("Connect to SQL Database"), true);

        this.setResizable(false);
        this.setLocationRelativeTo(parent);

        dbStrings = dbs;

        // build collections of components
        lhs.add(lblServerType);
        lhs.add(lblServerHostname);
        lhs.add(lblDatabase);
        lhs.add(lblUsername);
        lhs.add(lblPassword);

        rhs.add(cmbServerType);
        rhs.add(txtServerHostname);
        rhs.add(txtDatabase);
        rhs.add(txtUsername);
        rhs.add(pwdPassword);

        // setup label text
        lblServerType.setText(Globals.lang("Server Type :"));
        lblServerHostname.setText(Globals.lang("Server Hostname :"));
        lblDatabase.setText(Globals.lang("Database :"));
        lblUsername.setText(Globals.lang("Username :"));
        lblPassword.setText(Globals.lang("Password :"));

        // set label text alignment
        for (JLabel label : lhs){
            label.setHorizontalAlignment(JLabel.RIGHT);
        }
        
        // set button text
        btnConnect.setText(Globals.lang("Connect"));
        btnCancel.setText(Globals.lang("Cancel"));

        // init input fields to current DB strings
        String srvSel = dbStrings.getServerType();
        String[] srv = dbStrings.getServerTypes();
        for (int i=0; i<srv.length; i++) {
           cmbServerType.addItem(srv[i]);
        }

        cmbServerType.setSelectedItem(srvSel);
        txtServerHostname.setText(dbStrings.getServerHostname());
        txtDatabase.setText(dbStrings.getDatabase());
        txtUsername.setText(dbStrings.getUsername());
        pwdPassword.setText(dbStrings.getPassword());
        
        //------------------------------
        extractUsers();           //coloquei este método aqui para já combinar o nome do banco de dados com seu respectivo usuário na abertura desta janela de visualização
        //---------------------------------
        

        // construct dialog
        DefaultFormBuilder builder = new DefaultFormBuilder(new
                                 FormLayout("right:pref, 4dlu, fill:pref", ""));  

        builder.getPanel().setBorder(BorderFactory.createEmptyBorder(5,5,5,5));


        // add labels and input fields
        builder.append(lblServerType);
        builder.append(cmbServerType);
        builder.nextLine();
        builder.append(lblServerHostname);
        builder.append(txtServerHostname);
        builder.nextLine();
        builder.append(lblDatabase);
        builder.append(txtDatabase);
        builder.nextLine();
        builder.append(lblUsername);
        builder.append(txtUsername);
        builder.nextLine();
        builder.append(lblPassword);
        builder.append(pwdPassword);
        builder.nextLine();

        // add the panel to the CENTER of your dialog:
        getContentPane().add(builder.getPanel(), BorderLayout.CENTER);

        // add buttons are added in a similar way:
        ButtonBarBuilder bb = new ButtonBarBuilder();
        bb.addGlue();
        bb.addButton(btnConnect);
        bb.addButton(btnCancel);
        bb.addGlue();

        // add the buttons to the SOUTH of your dialog:
        getContentPane().add(bb.getPanel(), BorderLayout.SOUTH);
        pack();

        ActionListener connectAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String errorMessage = checkInput();

                if ( errorMessage==null) {
                    storeSettings();
                    setVisible(false);
                    setConnectToDB(true);
                } else {
                    JOptionPane.showMessageDialog(null, errorMessage,
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        };
    
        //----------------------------------------------------------------------------------------------------        
        ActionListener verificaAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

              
            	extractUsers();               //esta action foi colocada para estar sempre verificando se o nome do banco de dados corresponde ao do seu usuário
           
            }
        };
        
        //--------------------------------------------------------------------------------------------------------------------   
        

        btnConnect.addActionListener(connectAction);
        txtDatabase.addActionListener(connectAction);
        txtServerHostname.addActionListener(connectAction);
        txtUsername.addActionListener(connectAction);
        pwdPassword.addActionListener(connectAction);
        
        cmbServerType.addActionListener(verificaAction);  //tive que acrescentar esta ação ao comboBox

        AbstractAction cancelAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                setConnectToDB(false);
            }
        };
        btnCancel.addActionListener(cancelAction);

        // Key bindings:
        ActionMap am = builder.getPanel().getActionMap();
        InputMap im = builder.getPanel().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(Globals.prefs.getKey("Close dialog"), "close");
        am.put("close", cancelAction);
    }

	
		//-------------------------------------------------------------------------------------------
        
	private void extractUsers() {
		if (cmbServerType.getSelectedItem().toString().equals("MySQL")) {
			System.out.println(cmbServerType.getSelectedItem().toString());
			txtUsername.setText("root");
                                                                      //método adicionado para tentar solucionar a mudança solicitada
		} else if (cmbServerType.getSelectedItem().toString()
				.equals("PostgreSQL")) {
			System.out.println(cmbServerType.getSelectedItem().toString());
			txtUsername.setText("postgres");

		}
	}
       //-----------------------------------------------------------------------------------------------
	

    /**
     * Checks the user input, and ensures that required fields have entries
     *
     * @return 
     *      Appropriate error message to be displayed to user
     */
    private String checkInput () {

        String[] fields = {"Server Hostname", "Database", "Username"};
        String[] errors = new String[fields.length];
        int cnt = 0;

        if (txtServerHostname.getText().trim().equals("")) {
            errors[cnt] = fields[0];
            cnt++;
        }

        if (txtDatabase.getText().trim().equals("")) {
            errors[cnt] = fields[1];
            cnt++;
        }

        if (txtUsername.getText().trim().equals("")) {
            errors[cnt] = fields[2];
            cnt++;
        }
        
        String errMsg = Globals.lang("Please specify the ");

        switch (cnt) {
            case 0:
                errMsg = null;
                break;
            case 1:
                errMsg = errMsg + errors[0] + ".";
                break;
            case 2:
                errMsg = errMsg + errors[0] + " and " + errors[1] + "."; 
                break;
            case 3:
                errMsg = errMsg + errors[0] + ", " +  errors[1] 
                      + ", and " + errors[2] + ".";
                break;
            default:

        }

        return errMsg;
    }

    /**
     * Save user input.
     */
    private void storeSettings () {
        dbStrings.setServerType(cmbServerType.getSelectedItem().toString());
        dbStrings.setServerHostname(txtServerHostname.getText());
        dbStrings.setDatabase(txtDatabase.getText());
        dbStrings.setUsername(txtUsername.getText());

        // Store these settings so they appear as default next time:
        dbStrings.storeToPreferences();

        char[] pwd = pwdPassword.getPassword();
        String tmp = "";
        for (int i=0; i<pwd.length; i++) {
            tmp = tmp + pwd[i];
        }
        dbStrings.setPassword(tmp);
        tmp = "";
        Arrays.fill(pwd, '0');

    }

    public DBStrings getDBStrings() {
        return dbStrings;
    }

    public void setDBStrings(DBStrings dbStrings) { 
        this.dbStrings = dbStrings;
    }

    public boolean getConnectToDB() {
        return connectToDB;
    }

    public void setConnectToDB(boolean connectToDB) {
        this.connectToDB = connectToDB;
    }

}
