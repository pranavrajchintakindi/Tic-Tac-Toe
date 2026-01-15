import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class MainFrame implements ActionListener{
      String user,password;int breaker=0,want=1,status;
      Connection con = null;
      Statement st = null,stt = null;
      PreparedStatement pst=null,pstt=null,pstw=null,pstl=null,pstd=null,psts=null,pstu=null;
      ImageIcon logo,play_button;
      JTextField c_user,l_user;
      JPasswordField c_pass,l_pass;
      JButton c_button,l_button,creation,play;
      JFrame create,login,home;
      JLabel user_id,pass,ls_user,ls_score,uds_user,uds_score,uds_win,uds_draw,uds_lose;
      JLabel col_1[]= new JLabel[10];JLabel col_2[]= new JLabel[10];
      JPanel leader,user_details,user_part,score_part;
      MainFrame(){
            logo = new ImageIcon("assets/logo.png");
            play_button = new ImageIcon("assets/play.jpg");
            try{
                  Class.forName("com.mysql.cj.jdbc.Driver");
                  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tic_tac_toe","/*user_id*/","/*password*/");
                  if(con!=null){
                        JOptionPane.showMessageDialog(null,"Game Connected","Connected",JOptionPane.INFORMATION_MESSAGE);
                        loginAccount();
                  }
                  else{JOptionPane.showMessageDialog(null,"Game not Connected","Not Connected",JOptionPane.INFORMATION_MESSAGE);}
            }
            catch(Exception e){System.out.println(e);}
            finally{
                  try{
                        pst.close();pstt.close();pstu.close();pstw.close();pstl.close();pstd.close();psts.close();
                        st.close();stt.close();
                        con.close();
                  }
                  catch(Exception e){System.out.println(e);}
            }
      }
      void leader_setup(){
            //usershow
            ls_user= new JLabel("USER ID");
            ls_user.setFont(new Font("Times Romen",Font.BOLD, 20));
            ls_user.setForeground(Color.WHITE);
            //scoreshow
            ls_score = new JLabel("SCORE");
            ls_score.setFont(new Font("Times Romen",Font.BOLD, 20));
            ls_score.setForeground(Color.WHITE);
            for(int i=0;i<=9;i++){
                  col_1[i] = new JLabel();
                  col_1[i].setFont(new Font("Times Romen",Font.BOLD, 10));
                  col_1[i].setForeground(Color.WHITE);col_1[i].setBackground(Color.RED);
            }
            for(int i=0;i<=9;i++){
                  col_2[i] = new JLabel();
                  col_2[i].setFont(new Font("Times Romen",Font.BOLD, 15));
                  col_2[i].setForeground(Color.WHITE);col_2[i].setBackground(Color.RED);
            }
            try{
                  st=con.createStatement();stt=con.createStatement();
                  ResultSet rs =st.executeQuery("select user_id from user order by score desc limit 10;");
                  for(int i=0;rs.next()&&(i<=9);i++){
                        col_1[i].setText(rs.getString(1));
                  }
                  ResultSet rst=stt.executeQuery("select score from user order by score desc limit 10;");
                  for(int i=0;rst.next()&&(i<=9);i++){
                        col_2[i].setText(rst.getString(1));
                  }
                  leader.add(ls_user);leader.add(ls_score);
                  for(int i=0;i<=9;i++){
                        leader.add(col_1[i]);leader.add(col_2[i]);
                  }
            }catch(Exception e){System.out.println(e);}
      }
      void user_details_setup(){
            uds_user = new JLabel("USER ID: "+user);uds_user.setForeground(Color.WHITE);
            uds_user.setFont(new Font("Times Romen",Font.BOLD, 20));
            user_part = new JPanel();score_part =new JPanel();
            user_part.setLayout(new GridLayout(4,1));
            try{
                  //win
                  uds_win = new JLabel();uds_win.setFont(new Font("Times Romen",Font.BOLD, 15));
                  uds_win.setForeground(Color.WHITE);
                  pstw=con.prepareStatement("select win from user where user_id=?;");
                  pstw.setString(1, user);
                  ResultSet rstw = pstw.executeQuery();
                  if(rstw.next()){uds_win.setText("Win: "+rstw.getString(1));}
                  //lose
                  uds_lose = new JLabel();uds_lose.setForeground(Color.WHITE);
                  uds_lose.setFont(new Font("Times Romen",Font.BOLD, 15));
                  pstl=con.prepareStatement("select lose from user where user_id=?;");
                  pstl.setString(1, user);
                  ResultSet rstl = pstl.executeQuery();
                  if(rstl.next()){uds_lose.setText("Lose: "+rstl.getString(1));}
                  //draw
                  uds_draw = new JLabel();uds_draw.setForeground(Color.WHITE);
                  uds_draw.setFont(new Font("Times Romen",Font.BOLD, 15));
                  pstd=con.prepareStatement("select draw from user where user_id=?;");
                  pstd.setString(1, user);
                  ResultSet rstd = pstd.executeQuery();
                  if(rstd.next()){uds_draw.setText("Draw: "+rstd.getString(1));}
                  //score
                  uds_score = new JLabel();uds_score.setForeground(Color.WHITE);
                  uds_score.setFont(new Font("Times Romen",Font.BOLD, 50));
                  psts=con.prepareStatement("select score from user where user_id=?;");
                  psts.setString(1, user);
                  ResultSet rsts = psts.executeQuery();
                  if(rsts.next()){uds_score.setText("Score: "+rsts.getString(1));}
                  //user part
                  user_part.setBackground(new Color(30, 98, 57));
                  user_part.add(uds_user);
                  user_part.add(uds_win);user_part.add(uds_draw);user_part.add(uds_lose);
                  //score part
                  score_part.setBackground(new Color(30, 98, 57));
                  score_part.add(uds_score);
                  //play
                  play = new JButton(play_button);play.addActionListener(this);
                  //adding all
                  user_details.add(user_part);
                  user_details.add(score_part);
                  user_details.add(play);
            }catch(Exception e){System.out.println(e);}
      }
      void homepage(){
            //leader board
            leader = new JPanel();
            leader.setLayout(new GridLayout(11,2));
            leader.setForeground(Color.WHITE);
            leader.setBackground(new Color(30, 98, 57));
            leader_setup();
            //user_details
            user_details = new JPanel();
            user_details.setLayout(new GridLayout(3,1,5,5));
            user_details.setForeground(Color.WHITE);
            user_details.setBackground(new Color(84, 54, 33));
            user_details_setup();
            //window
            home = new JFrame();
            home.setTitle("homepage");
            home.setLayout(new GridLayout(1,2,5,5));
            home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            home.setSize(600,450);
            home.setResizable(false);
            home.setIconImage(logo.getImage());
            home.getContentPane().setBackground(new Color(84, 54, 33));
            home.add(leader);home.add(user_details);
            home.setVisible(true);
      }
      void createAccount(){
            //userid
            user_id = new JLabel("USER ID:  ");
            user_id.setFont(new Font("Times Romen",Font.BOLD, 20));
            user_id.setForeground(Color.WHITE);
            c_user = new JTextField("username");
            c_user.setPreferredSize(new Dimension(300, 50));
            c_user.setForeground(Color.WHITE);
            c_user.setBackground(new Color(30, 98, 57));
            c_user.setFont(new Font("Times Roman", Font.PLAIN, 20));
            //password
            pass = new JLabel("PASSWORD");
            pass.setFont(new Font("Times Romen",Font.BOLD, 20));
            pass.setForeground(Color.WHITE);
            c_pass = new JPasswordField();
            c_pass.setPreferredSize(new Dimension(300, 50));
            c_pass.setForeground(Color.WHITE);
            c_pass.setBackground(new Color(30, 98, 57));
            //button
            c_button = new JButton("create");
            c_button.setFont(new Font("Times Romen",Font.BOLD, 20));
            c_button.setForeground(Color.WHITE);
            c_button.setBackground(new Color(30, 98, 57));
            c_button.addActionListener(this);
            //window
            create = new JFrame();
            create.setTitle("Create Account");
            create.setLayout(new FlowLayout(FlowLayout.CENTER,20,60));
            create.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            create.setSize(500,400);
            create.setResizable(false);
            create.setIconImage(logo.getImage());
            create.getContentPane().setBackground(new Color(30, 98, 57));
            create.add(user_id);create.add(c_user);
            create.add(pass);create.add(c_pass);
            create.add(c_button);
            create.setVisible(true);
      }
      void loginAccount(){
            //userid
            JLabel user_id = new JLabel("USER ID:  ");
            user_id.setFont(new Font("Times Romen",Font.BOLD, 20));
            user_id.setForeground(Color.WHITE);
            l_user = new JTextField("username");
            l_user.setPreferredSize(new Dimension(300, 50));
            l_user.setForeground(Color.WHITE);
            l_user.setBackground(new Color(30, 98, 57));
            l_user.setFont(new Font("Times Roman", Font.PLAIN, 20));
            //password
            JLabel pass = new JLabel("PASSWORD");
            pass.setFont(new Font("Times Romen",Font.BOLD, 20));
            pass.setForeground(Color.WHITE);
            l_pass = new JPasswordField();
            l_pass.setPreferredSize(new Dimension(300, 50));
            l_pass.setForeground(Color.WHITE);
            l_pass.setBackground(new Color(30, 98, 57));
            //login button
            l_button = new JButton("login");
            l_button.setFont(new Font("Times Romen",Font.BOLD, 20));
            l_button.setForeground(Color.WHITE);
            l_button.setBackground(new Color(30, 98, 57));
            l_button.addActionListener(this);
            //create button
            creation = new JButton("create account");
            creation.setFont(new Font("Times Romen",Font.BOLD, 20));
            creation.setForeground(Color.WHITE);
            creation.setBackground(new Color(30, 98, 57));
            creation.addActionListener(this);
            //window
            login = new JFrame();
            login.setTitle("Login");
            login.setLayout(new FlowLayout(FlowLayout.CENTER,20,60));
            login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            login.setSize(500,400);
            login.setResizable(false);
            login.setIconImage(logo.getImage());
            login.getContentPane().setBackground(new Color(30, 98, 57));
            login.add(user_id);login.add(l_user);
            login.add(pass);login.add(l_pass);
            login.add(l_button);login.add(creation);
            login.setVisible(true);
      }
      void score_status(int status){
            this.status=status;updater();homepage();
      }
      public void actionPerformed(ActionEvent e){
            if(e.getSource()==c_button){
                  user = c_user.getText();password=new String(c_pass.getPassword());
                  try{
                        pst=con.prepareStatement("insert into user (user_id,password)values(?,?)");
                        pst.setString(2, password);pst.setString(1, user);
                        pst.executeUpdate();
                  }catch(Exception ex){System.out.println(ex);}
                  create.dispose();
            }
            if(e.getSource()==l_button){
                  user = l_user.getText();password=new String(l_pass.getPassword());
                  try{
                        pstt = con.prepareStatement("select * from user where user_id = ? && password = ?;");
                        pstt.setString(2,password);pstt.setString(1, user);
                        ResultSet rst = pstt.executeQuery();
                        if(rst.next()){
                              homepage();
                              login.dispose();
                        }
                        else{JOptionPane.showMessageDialog(null,"user id or password incorrect!","error",JOptionPane.INFORMATION_MESSAGE);}
                  }catch(Exception ex){System.out.println(ex);}
            }
            if(e.getSource()==creation){
                  createAccount();
            }
            if(e.getSource()==play){
                  home.dispose();
                  new Game(this);
            }
      }
      void updater(){
            try{
                  if(status==1){
                        pstu =con.prepareStatement("update user set win = win + 1 where user_id=?;");
                  }
                  if(status==0){
                        pstu =con.prepareStatement("update user set lose = lose + 1 where user_id=?;");
                  }
                  if(status==2){
                        pstu =con.prepareStatement("update user set draw = draw + 1 where user_id=?;");
                  }pstu.setString(1,user);pstu.executeUpdate();
            }catch(Exception ex){System.out.println(ex);}
      }

}

